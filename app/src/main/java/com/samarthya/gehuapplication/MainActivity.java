package com.samarthya.gehuapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
* This is a Java Class, to represent the JSON Response received during login from the login route.
* The Json Response contains a boolean and a message field, which is represented as a Java Class.
* This object also contains the studentId, which is required for the intent for the next activity.
*/
class LoginJsonObject {

	boolean authentication;
	String message;
	String studentId;

	LoginJsonObject(boolean authentication, String message, String studentId) {

		this.authentication = authentication;
		this.message = message;
		this.studentId = studentId;

	}

}

public class MainActivity extends AppCompatActivity {

	/**
	 * Important views of the Login Screen(MainActivity)
	 * */
	private EditText etStudentId;
	private EditText etStudentPassword;
	private CheckBox cbRememberStudentId;
	private ImageView ivStudentPasswordSetVisible;
	private TextView tvErrorMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// instantiating the important views of the login screen(main activity)
		etStudentId = findViewById(R.id.etStudentId);
		etStudentPassword = findViewById(R.id.etStudentPassword);
		cbRememberStudentId = findViewById(R.id.cbRememberStudentId);
		Button btnStudentLogin = findViewById(R.id.btnStudentLogin);
		ivStudentPasswordSetVisible = findViewById(R.id.ivStudentPasswordSetVisible);
		tvErrorMessage = findViewById(R.id.tvErrorMessage);
		
		// the saved student id should appear at the start of the application
		manageStudentIdPreference();

		// click listener for the password hide and show button
		AtomicBoolean isStudentPasswordVisible = new AtomicBoolean(false);
		ivStudentPasswordSetVisible.setOnClickListener(view -> {

			if (isStudentPasswordVisible.get()) {

				ivStudentPasswordSetVisible.setImageResource(R.drawable.make_password_visible);
				isStudentPasswordVisible.set(false);
				etStudentPassword.setTransformationMethod
						(PasswordTransformationMethod.getInstance());  // show password

			} else {

				ivStudentPasswordSetVisible.setImageResource(R.drawable.make_password_invisible);
				isStudentPasswordVisible.set(true);
				etStudentPassword.setTransformationMethod
						(HideReturnsTransformationMethod.getInstance());  // hide password

			}

		});

		// click listener for student login button
		btnStudentLogin.setOnClickListener(view -> {

			String studentId = etStudentId.getText().toString();
			String studentPassword = etStudentPassword.getText().toString();

			// check if any of the EditText are empty
			if (studentId.isEmpty() || studentPassword.isEmpty()) {

				tvErrorMessage.setVisibility(View.VISIBLE);
				tvErrorMessage.setText(R.string.enter_all_the_fields);

				return;

			}

			ExecutorService exec = Executors.newSingleThreadExecutor();
			Handler handler = new Handler(Looper.getMainLooper());

			exec.execute(() -> {

				LoginJsonObject loginJsonObject = null;
				
				try {
					loginJsonObject = authenticateStudentLogin(studentId, studentPassword);
				} catch (IOException | JSONException e) {
					e.printStackTrace();
				}

				// action to perform after json parsing
				LoginJsonObject finalLoginJsonObject = loginJsonObject;

				handler.post(() -> {

					Intent startBottomNavigationActivity = new Intent(
							this,
							com.samarthya.gehuapplication.BottomNavigationActivity.class
					);

					assert finalLoginJsonObject != null;
					startBottomNavigationActivity.putExtra("studentId", finalLoginJsonObject.studentId);

					if (finalLoginJsonObject.authentication) {
						startActivity(startBottomNavigationActivity);
					} else {
						tvErrorMessage.setVisibility(View.VISIBLE);
						tvErrorMessage.setText(finalLoginJsonObject.message);
					}

				});

			});

		});

	}
	
	private void manageStudentIdPreference() {

		SharedPreferences studentIdPreferences = 
				getSharedPreferences("studentIdPreferences", MODE_PRIVATE);

		// check if last time the checkbox was clicked or not
		if (studentIdPreferences.getBoolean("cbRememberStudentId", false)) {

			cbRememberStudentId.setChecked(true);
			etStudentId.setText(studentIdPreferences.getString("etStudentId", ""));

		} else {

			cbRememberStudentId.setChecked(false);
			etStudentId.setText("");

		}

		// click listener for the student id check box
		cbRememberStudentId.setOnClickListener(view -> {

			SharedPreferences.Editor studentIdPreferencesEditor = studentIdPreferences.edit();

			if (cbRememberStudentId.isChecked()) {

				String studentId = etStudentId.getText().toString();

				if (studentId.isEmpty()) {

					Toast.makeText(this, "Enter a Student Id!", Toast.LENGTH_SHORT)
							.show();

					cbRememberStudentId.setChecked(false);

					return;

				}

				// boolean preference -> is the checkbox selected or not
				studentIdPreferencesEditor.putBoolean("cbRememberStudentId", true);

				// string preference -> the student id to be remembered
				studentIdPreferencesEditor.putString("etStudentId", studentId);

			} else {

				// boolean preference -> is the checkbox selected or not
				studentIdPreferencesEditor.putBoolean("cbRememberStudentId", false);

			}

			// commit all the changes if made
			studentIdPreferencesEditor.apply();

		});
		
	}

	public LoginJsonObject authenticateStudentLogin(String studentId, String studentPassword)
			throws IOException, JSONException {

		// this line of code is only used while developing the application to pass the login
//		return new LoginJsonObject(true, "ok", studentId);

		// this code is commented out until next steps of bottom navigation activity
		URL backendLoginUrl = new URL("http://" + Server.SOCKET_ADDRESS +
				"/login?" + "studentId=" + studentId + "&studentPassword=" + studentPassword);

		HttpURLConnection httpURLConnection;
		InputStream inputStream;
		Scanner inputStreamReader;
		StringBuilder loginJsonResponse = new StringBuilder();

		httpURLConnection = (HttpURLConnection) backendLoginUrl.openConnection();
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setConnectTimeout(10000);
		httpURLConnection.setReadTimeout(10000);
		httpURLConnection.connect();

		inputStream = httpURLConnection.getInputStream();
		inputStreamReader = new Scanner(inputStream);

		while (inputStreamReader.hasNext()) {
			loginJsonResponse.append(inputStreamReader.nextLine());
		}

		inputStreamReader.close();
		inputStream.close();
		httpURLConnection.disconnect();

		// parse json response form here, stored in -> loginJsonResponse
		JSONObject root = new JSONObject(loginJsonResponse.toString());
		boolean authentication = root.getBoolean("authentication");
		String message = root.getString("message");

		return new LoginJsonObject(authentication, message, studentId);

	}

	// executed when the logout button is pressed in the settings fragment
	@Override
	protected void onResume() {

		super.onResume();
		etStudentPassword.setText("");

	}
}