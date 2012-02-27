package clock.screens;

import java.util.List;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.GeoPoint;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class MapScreen extends MapActivity {
	MapView mapView;
	public static final String LOCATION_NAME = "lacation";
	public static final String GP_LAT = "gpLat";
	public static final String GP_LNG = "gpLng";
	MapController mc;
	GeoPoint p;
	// Point touchPoint = new Pont()
	int locationName;
	boolean markerExist = false;
	List<Overlay> overlays;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		// получение названи€ локации, которую выбирают
		Bundle extras = getIntent().getExtras();
		String previousScreen = extras.getString(LOCATION_NAME);
		TextView message = (TextView) findViewById(R.id.textView2);
		int locationName = (int) Double.parseDouble(previousScreen);
		// message.setText("previous Screen is: " + locationName);
		int lt = extras.getInt(GP_LAT);
		int lg = extras.getInt(GP_LNG);

		message.setText("lt: " + lt + "; lg: " + lg);

		p = new GeoPoint(lt, lg);
		// zoooom
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);

		// ¬ыставл€ем карту на конкуретную позицию
		mc = mapView.getController();
		String coordinates[] = { "55.754044", "37.620384" };
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);
		GeoPoint pBegin = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		mc.animateTo(pBegin);
		mc.setZoom(10);

		MapOverlay mapOverlay = new MapOverlay();
		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		listOfOverlays.add(mapOverlay);
		mapView.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	// ќбработка нажати€ кнопки Done
	public void onClickDone(View v) {
		Intent intent = new Intent();
		intent.putExtra("gpLng", p.getLongitudeE6());
		intent.putExtra("gpLat", p.getLatitudeE6());
		setResult(RESULT_OK, intent);
		finish();
	}

	class MapOverlay extends com.google.android.maps.Overlay {

		@Override
		public boolean onTouchEvent(MotionEvent event, MapView mapView) {
			// ---when user lifts his finger---
			if (event.getAction() == 1) {
				p = mapView.getProjection().fromPixels((int) (event.getX()),
						(int) event.getY());
				Toast.makeText(
						getBaseContext(),
						p.getLatitudeE6() / 1E6 + "," + p.getLongitudeE6()
								/ 1E6, Toast.LENGTH_SHORT).show();
			}
			return false;
		}

		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);

			// ---translate the GeoPoint to screen pixels---
			Point screenPts = new Point();
			mapView.getProjection().toPixels(p, screenPts);

			// ---add the marker---
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.pushpin);
			canvas.drawBitmap(bmp, screenPts.x - 5, screenPts.y - 95, null);
			return true;
		}

	}

}
