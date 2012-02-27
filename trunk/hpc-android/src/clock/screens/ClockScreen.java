package clock.screens;

import java.text.Format;
import java.util.ArrayList;

import com.google.android.maps.GeoPoint;

import clock.server.Jabber;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ClockScreen extends Activity implements LocationListener {
  EditText Destination;
  EditText MessageBox;

  TextView StatusTitle, MyStatus;

  private Cursor mCursor;
  private static final String[] mContent = new String[] {// ???????????? ID
  /* DBContentProvider._ID, */DBContentProvider.CLOC_NAME, DBContentProvider.CLNG, DBContentProvider.CLAT };

  double latDB[] = new double[6];
  double lngDB[] = new double[6];
  String locDB[] = new String[6];

  double currentLat = 0;
  double currentLng = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.clock);

    StatusTitle = (TextView) findViewById(R.id.ClockTempTitle);
    MyStatus = (TextView) findViewById(R.id.textLocations);

    // <DB>
    mCursor = managedQuery(DBContentProvider.CONTENT_URI, mContent, null, null, null);
    // Достаем координаты из БД
    mCursor.moveToFirst();
    int i = 0;
    while (mCursor.moveToNext()) {
      latDB[i] = mCursor.getInt(mCursor.getColumnIndex(DBContentProvider.CLAT)) / 10 ^ 6;
      lngDB[i] = mCursor.getInt(mCursor.getColumnIndex(DBContentProvider.CLNG)) / 10 ^ 6;
      locDB[i] = mCursor.getString(mCursor.getColumnIndex(DBContentProvider.CLOC_NAME));
      i = i + 1;
    }

  }

  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.buttSend:
      // получение координат
      LocationManager locationManager;
      String context = Context.LOCATION_SERVICE;
      locationManager = (LocationManager) getSystemService(context);
      String provider = LocationManager.GPS_PROVIDER;
      locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 0, this);
      Location location = locationManager.getLastKnownLocation(provider);

      currentLat = location.getLatitude();
      currentLng = location.getLongitude();

      // String message = currentLocation(currentLng, currentLat);// текст для
      // отправки
      String message = Double.toString(currentLng) + " - " + Double.toString(currentLat);

      // Jabber.CLIENT.sendMessageForAll(message);
      MyStatus.setText(message);
      Jabber.CLIENT.setStatus(message);
      break;
    case R.id.buttUpdate:
      // получение координат
      // пользователей!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

      // TextView edtextFriendsLocations = (TextView)
      // findViewById(R.id.textLocations);
      // edtextFriendsLocations.setText("Locations:  " + currentLng);

      String stat = new String("Test");
      /* ArrayList<String> al = */Jabber.CLIENT.getStatusFromAll();
      // for (String entry : al) {
      // stat += entry + "\n";
      // }
      StatusTitle.setText(Jabber.CLIENT.getStatusFromAll());
      break;
    }

  }

  public void onLocationChanged(Location arg0) {
    // TODO Auto-generated method stub

  }

  public void onProviderDisabled(String provider) {
    // TODO Auto-generated method stub

  }

  public void onProviderEnabled(String provider) {
    // TODO Auto-generated method stub

  }

  public void onStatusChanged(String provider, int status, Bundle extras) {
    // TODO Auto-generated method stub

  }

  private String currentLocation(double lat, double lng) {
    String result = null;
    int i;
    double epsilant = 1;// ???????????????????????????????????????????????
    double distanses;
    double minD = 0;
    int minI = 0;
    for (i = 0; i < 6; ++i) {
      distanses = (lat - latDB[i]) * (lat - latDB[i]) + (lng - lngDB[i]) * (lng - lngDB[i]);
      if (i == 0) {
        minD = distanses;
      } else if (minD > distanses) {
        minD = distanses;
        minI = i;
      }
    }

    if (minD < epsilant) {
      result = locDB[minI];
    } else {
      result = "on the way";
    }
    return result;
  }
}
