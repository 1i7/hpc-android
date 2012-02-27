package clock.screens;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class PInfoScreen extends Activity {
	//public static final GeoPoint POINT_LOCATION = new GeoPoint(0,0);
	GeoPoint a = new GeoPoint(0,0);
	int numOfLoc = 6;//����������� ��������� �������
	public GeoPoint gp_arr[] = new GeoPoint[numOfLoc];//���������� ����� ����� �����!!!!
	//������� ������ �������
	ListView lv1;
	String lv_arr[] = new String[numOfLoc];
	int chosenLocation = 0; // ��������� �������
	
	//<dataBase>
	private Cursor mCursor; 
    private static final String[] mContent = new String[] {//???????????? ID
    	   /*DBContentProvider._ID,*/ DBContentProvider.CLOC_NAME, 
    	   DBContentProvider.CLNG, DBContentProvider.CLAT};
  //<\dataBase>
    
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.personalinfo);
        
        //<DB>  
        mCursor = managedQuery(DBContentProvider.CONTENT_URI, mContent, null, null, null);
        //������� ���������� �� ��
        mCursor.moveToFirst();
        int i = 0;
        while(mCursor.moveToNext()) {
            int pLat = mCursor.getInt(mCursor.getColumnIndex(DBContentProvider.CLAT));
            int pLng = mCursor.getInt(mCursor.getColumnIndex(DBContentProvider.CLNG));
            String pLoc = mCursor.getString(mCursor.getColumnIndex(DBContentProvider.CLOC_NAME));
            gp_arr[i] = new GeoPoint(pLat,pLng);
            lv_arr[i] = pLoc;
            i = i + 1;
        }
        
        //<\DB>
               
        lv1 = (ListView)findViewById(R.id.listView1);
        //������������� ������ � ListView
        lv1.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , lv_arr));
        lv1.setTextFilterEnabled(true);
      //������������ ������ �� ��������� ListView:
        lv1.setOnItemClickListener(new OnItemClickListener() 
        {
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) 
        	{
                //������� ��������, �� �������� ��������
                String itemname = new Integer(position).toString();  
                chosenLocation = new Integer(position).intValue();
                gotoMapActivity(itemname);
    				    				                
        	}
		});
    }
	
	//��-�� �������� �� ����� MapScreen, itemname - ��������� �������
	public void gotoMapActivity(String itemname)
	{
		Intent i = new Intent(this,MapScreen.class);
		//�������� ��� �������� � MapScreen �������, �� ������� ��������
        i.putExtra(MapScreen.LOCATION_NAME, itemname);
        i.putExtra(MapScreen.GP_LAT, gp_arr[chosenLocation].getLatitudeE6());
        i.putExtra(MapScreen.GP_LNG, gp_arr[chosenLocation].getLongitudeE6());
        startActivityForResult(i,1);
	}
	
	//��������� ��������� �� ���������� ������ �����������
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    int gpLng = data.getIntExtra("gpLng", 0);
	    int gpLat = data.getIntExtra("gpLat", 0);
			    
	    gp_arr[chosenLocation] = new GeoPoint(gpLat, gpLng);
	  }
	
	//��������� ������� ������ Next                                   ����������
	public void onClickDone(View v){
    	switch(v.getId())
    	{
    		case R.id.buttDone:
    			ContentValues values;
    			//���������� ��� ������ �� gp_arr[] � ��
    			for(int i = 0; i < numOfLoc; i++){
    				values = new ContentValues(3);
    				values.put(DBContentProvider.CLOC_NAME, lv_arr[i]);
    				values.put(DBContentProvider.CLAT,gp_arr[i].getLatitudeE6());
    				values.put(DBContentProvider.CLNG,gp_arr[i].getLongitudeE6());
    				getContentResolver().update(DBContentProvider.CONTENT_URI, values, DBContentProvider.CLOC_NAME + " = \"" + lv_arr[i] + "\"", null);//?????
    	            //mCursor.requery();
    			}
    			Intent i = new Intent(this, MenuScreen.class);
    			startActivity(i);
    			break;
    	}
    }

}
