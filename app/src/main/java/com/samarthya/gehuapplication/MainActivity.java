package com.samarthya.gehuapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

	/**
	 * Important views of the Login Screen(MainActivity)
	 */
	private EditText etStudentId;
	private EditText etStudentPassword;
	private CheckBox cbRememberStudentId;
	private Button btnStudentLogin;
	private ImageView ivStudentPasswordSetVisible;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// instantiating the important views of the login screen(main activity)
		etStudentId = findViewById(R.id.etStudentId);
		etStudentPassword = findViewById(R.id.etStudentPassword);
		cbRememberStudentId = findViewById(R.id.cbRememberStudentId);
		btnStudentLogin = findViewById(R.id.btnStudentLogin);
		ivStudentPasswordSetVisible = findViewById(R.id.ivStudentPasswordSetVisible);
		
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

					Toast.makeText(this, "Enter a Student Id!", Toast.LENGTH_SHORT).show();
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

}