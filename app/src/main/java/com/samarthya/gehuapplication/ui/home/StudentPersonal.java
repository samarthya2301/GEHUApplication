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
 * This is a class to enclose the personal details of a particular student, fetched from
 * networking from the '/personal' route on our backend.
 * */
public class StudentPersonal {

	String studentId;
	String fatherName;
	String motherName;
	String dateOfBirth;
	String college;
	String course;
	String branch;
	String semester;
	String section;
	String classRollNo;
	String intermediatePercent;
	String highschoolPercent;
	String admissionDate;

	public StudentPersonal(String studentId, String fatherName, String motherName,
						   String dateOfBirth, String college, String course, String branch,
						   String semester, String section, String classRollNo,
						   String intermediatePercent, String highschoolPercent,
						   String admissionDate) {

		this.studentId = studentId;
		this.fatherName = fatherName;
		this.motherName = motherName;
		this.dateOfBirth = dateOfBirth;
		this.college = college;
		this.course = course;
		this.branch = branch;
		this.semester = semester;
		this.section = section;
		this.classRollNo = classRollNo;
		this.intermediatePercent = intermediatePercent;
		this.highschoolPercent = highschoolPercent;
		this.admissionDate = admissionDate;

	}

	public static StudentPersonal getStudentPersonalObject() {

		// perform the networking here
		ExecutorService exec = Executors.newSingleThreadExecutor();
		Future<StudentPersonal> resultFromThread = exec.submit(StudentPersonal::getStudentPersonalFromServer);

		try {
			return resultFromThread.get();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static StudentPersonal getStudentPersonalFromServer() throws IOException, JSONException {

		String studentId = BottomNavigationActivity.staticStudentIdForFragments;
		URL backendPersonalUrl = new URL("http://" + Server.SOCKET_ADDRESS +
				"/personal?" + "studentId=" + studentId);

		HttpURLConnection httpURLConnection;
		InputStream inputStream;
		Scanner inputStreamReader;
		StringBuilder personalJsonResponse = new StringBuilder();

		httpURLConnection = (HttpURLConnection) backendPersonalUrl.openConnection();
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setConnectTimeout(10000);
		httpURLConnection.setReadTimeout(10000);
		httpURLConnection.connect();

		inputStream = httpURLConnection.getInputStream();
		inputStreamReader = new Scanner(inputStream);

		while (inputStreamReader.hasNext()) {
			personalJsonResponse.append(inputStreamReader.nextLine());
		}

		inputStreamReader.close();
		inputStream.close();
		httpURLConnection.disconnect();

		JSONObject root = new JSONObject(personalJsonResponse.toString());
		boolean resultsAvailable = root.getBoolean("resultsAvailable");
		String fatherName = root.getString("fatherName");
		String motherName = root.getString("motherName");
		String dateOfBirth = root.getString("dateOfBirth");
		String college = root.getString("college");
		String course = root.getString("course");
		String branch = root.getString("branch");
		String semester = root.getString("semester");
		String section = root.getString("section");
		String classRollNo = root.getString("classRollNo");
		String intermediatePercent = root.getString("intermediatePercent");
		String highschoolPercent = root.getString("highschoolPercent");
		String admissionDate = root.getString("admissionDate");

		StudentPersonal studentPersonalObject = null;

		if (resultsAvailable) {
			studentPersonalObject = new StudentPersonal(
					studentId,
					fatherName,
					motherName,
					dateOfBirth,
					college,
					course,
					branch,
					semester,
					section,
					classRollNo,
					intermediatePercent,
					highschoolPercent,
					admissionDate
			);
		}

		return studentPersonalObject;

	}

	@NonNull
	@Override
	public String toString() {
		return this.fatherName + " " + this.motherName + " " + this.dateOfBirth;
	}

}
