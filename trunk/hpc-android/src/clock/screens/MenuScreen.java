package clock.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MenuScreen extends Activity{
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }
	
	public void onClickMenu(View v){
    	switch(v.getId())
    	{
    		case R.id.buttPInfo:
    			Intent i = new Intent(this, PInfoScreen.class);
    			startActivity(i);
    			break;
    		case R.id.buttFrList:
    			Intent a = new Intent(this, FrListScreen.class);
    			startActivity(a);
    			break;
    		case R.id.buttAddFr:
    			Intent b = new Intent(this, AddFrScreen.class);
    			startActivity(b);
    			break;
    		case R.id.buttRqFr:
    			Intent c = new Intent(this, RequestFriendScreen.class);
    			startActivity(c);
    			break;
    		case R.id.buttClock:
    			Intent d = new Intent(this, ClockScreen.class);
    			startActivity(d);
    			break;
    		case R.id.buttView:
    			Intent f = new Intent(this, ClockViewScreen.class);
    			startActivity(f);
    			break;
    	}
    }
	

}
