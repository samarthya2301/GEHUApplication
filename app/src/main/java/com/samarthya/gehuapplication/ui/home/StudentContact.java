package com.samarthya.gehuapplication.ui.home;

import androidx.annotation.NonNull;

import com.samarthya.gehuapplication.BottomNavigationActivity;
import com.samarthya.gehuapplication.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This is a class to enclose the contact details of a particular student, fetched from
 * networking from the '/contact' route on our backend.
 * */
public class StudentContact {

	String studentId;
	String personalEmail;
	String collegeEmail;
	String phoneOne;
	String phoneTwo;
	String studentImageUrl;
	String studentName;

	StudentContact(String studentId, String personalEmail, String collegeEmail, String phoneOne,
				   String phoneTwo, String studentImageUrl, String studentName) {

		this.studentId = studentId;
		this.personalEmail = personalEmail;
		this.collegeEmail = collegeEmail;
		this.phoneOne = phoneOne;
		this.phoneTwo = phoneTwo;
		this.studentImageUrl = studentImageUrl;
		this.studentName = studentName;

	}

	public static StudentContact getStudentContactObject() throws IOException, JSONException {

		// perform the networking here
		ExecutorService exec = Executors.newSingleThreadExecutor();
		Future<StudentContact> resultFromThread = exec.submit(StudentContact::getStudentContactFromServer);

		try {
			return resultFromThread.get();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		return null;

	}

	private static StudentContact getStudentContactFromServer() throws IOException, JSONException {

		String studentId = BottomNavigationActivity.staticStudentIdForFragments;
		URL backendContactUrl = new URL("http://" + Server.SOCKET_ADDRESS +
				"/contact?" + "studentId=" + studentId);

		HttpURLConnection httpURLConnection;
		InputStream inputStream;
		Scanner inputStreamReader;
		StringBuilder contactJsonResponse = new StringBuilder();

		httpURLConnection = (HttpURLConnection) backendContactUrl.openConnection();
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setConnectTimeout(10000);
		httpURLConnection.setReadTimeout(10000);
		httpURLConnection.connect();

		inputStream = httpURLConnection.getInputStream();
		inputStreamReader = new Scanner(inputStream);

		while (inputStreamReader.hasNext()) {
			contactJsonResponse.append(inputStreamReader.nextLine());
		}

		inputStreamReader.close();
		inputStream.close();
		httpURLConnection.disconnect();

		// parse json stored in contactJsonResponse
		JSONObject root = new JSONObject(contactJsonResponse.toString());
		boolean resultsAvailable = root.getBoolean("resultsAvailable");
		String personalEmail = root.getString("personalEmail");
		String collegeEmail = root.getString("collegeEmail");
		String phoneOne = root.getString("phoneOne");
		String phoneTwo = root.getString("phoneTwo");
		String studentImageUrl = root.getString("studentImage");
		String studentName = root.getString("studentName");

		StudentContact studentContactObject = null;

		if (resultsAvailable) {
			studentContactObject = new StudentContact(
					studentId,
					personalEmail,
					collegeEmail,
					phoneOne,
					phoneTwo,
					studentImageUrl,
					studentName
			);
		}

		return studentContactObject;

	}

	@NonNull
	@Override
	public String toString() {
		return this.studentName + " " + this.studentId + " " + this.personalEmail;
	}

}
