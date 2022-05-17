package com.samarthya.gehuapplication.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samarthya.gehuapplication.BottomNavigationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SettingsViewModel extends ViewModel {

	private final MutableLiveData<String> passwordChangeFailureMessage;

	public SettingsViewModel() {

		passwordChangeFailureMessage = new MutableLiveData<>();
		passwordChangeFailureMessage.setValue("Password\nChanging\nUnsuccessful");

	}

	public boolean postNewPassword(String newPassword) throws IOException, JSONException {

		String studentId = BottomNavigationActivity.staticStudentIdForFragments;
		URL backendPasswordUrl = new URL("http://192.168.43.100:3000/password?" +
				"studentId=" + studentId + "&" +
				"newStudentPassword=" + newPassword);

		HttpURLConnection httpURLConnection;
		InputStream inputStream;
		Scanner inputStreamReader;
		StringBuilder passwordJsonResponse = new StringBuilder();

		httpURLConnection = (HttpURLConnection) backendPasswordUrl.openConnection();
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setConnectTimeout(10000);
		httpURLConnection.setReadTimeout(10000);
		httpURLConnection.connect();

		inputStream = httpURLConnection.getInputStream();
		inputStreamReader = new Scanner(inputStream);

		while (inputStreamReader.hasNext()) {
			passwordJsonResponse.append(inputStreamReader.nextLine());
		}

		inputStreamReader.close();
		inputStream.close();
		httpURLConnection.disconnect();

		// parse json stored in passwordJsonResponse
		JSONObject root = new JSONObject(passwordJsonResponse.toString());
		boolean rowFound = root.getBoolean("rowFound");
		boolean passwordChanged = root.getBoolean("passwordChanged");

		return rowFound && passwordChanged;

	}

	public LiveData<String> getPasswordChangeFailureMessage() {
		return passwordChangeFailureMessage;
	}

}