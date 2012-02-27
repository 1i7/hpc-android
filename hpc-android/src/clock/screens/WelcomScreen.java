package clock.screens;

import clock.server.Jabber;
import android.R.bool;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class WelcomScreen extends Activity {
  EditText UserName, Password;
  TextView helpfield;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    TextView text1 = (TextView) findViewById(R.id.text1);
    text1.setText("WELCOME!");
    TextView text2 = (TextView) findViewById(R.id.text2);
    text2.setText("HarryPotter's clock");

    UserName = (EditText) findViewById(R.id.UserName);
    Password = (EditText) findViewById(R.id.Password);
    helpfield = (TextView) findViewById(R.id.helpfield);
  }

  public void onClickWelcom(View v) throws InterruptedException {
    switch (v.getId()) {
    case R.id.buttEnter:
      Intent i = new Intent(this, MenuScreen.class);
      Jabber.CLIENT = new Jabber(UserName.getText().toString(), Password.getText().toString());
      if (Jabber.CLIENT.run()) {
        Toast toast = Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      } else {
        Toast toast = Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        break;
      }
      startActivity(i);
      break;
    case R.id.buttRegister:
      Intent a = new Intent(this, RegisterScreen.class);
      startActivity(a);
      break;
    }
  }

}