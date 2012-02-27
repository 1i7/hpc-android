package clock.screens;

import clock.server.Jabber;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterScreen extends Activity {
	EditText NameBox, PasswordBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		NameBox = (EditText) findViewById(R.id.RegistrationNameBox);
		PasswordBox = (EditText) findViewById(R.id.RegistrationPasswordBox);
	}

	public void onClickRegister(View v) {
		if (Jabber.registerUser(NameBox.getText().toString(), PasswordBox
				.getText().toString())) {
			Toast toast = Toast.makeText(getApplicationContext(), "OK",
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			finish();
		} else {
			Toast toast = Toast.makeText(getApplicationContext(), "ERROR",
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}

	}

}
