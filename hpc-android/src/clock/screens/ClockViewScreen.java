package clock.screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ClockViewScreen extends Activity {

  LinearLayout mLinearLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    ImageView imageCat = new ImageView(getApplicationContext());
    imageCat.setImageResource(R.drawable.pushpin);
    // Create a LinearLayout in which to add the ImageView
    mLinearLayout = new LinearLayout(this);

    // Instantiate an ImageView and define its properties
    ImageView i = new ImageView(this);
    i.setImageResource(R.drawable.view);
    i.setAdjustViewBounds(true); // set the ImageView bounds to match the
                                 // Drawable's dimensions
    i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

    // Add the ImageView to the layout and set the layout as the content view
    mLinearLayout.addView(i);
    setContentView(mLinearLayout);
  }

}
