package com.samarthya.gehuapplication.ui.dashboard;

import androidx.annotation.NonNull;

import org.json.JSONArray;
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

class ExamResponse {

	boolean resultsAvailable;
	float cgpa;
	String msg;

	ExamResponse() {

		this.resultsAvailable = false;
		this.cgpa = 0;
		this.msg = "";

	}

	ExamResponse(boolean resultsAvailable, float cgpa, String msg) {

		this.resultsAvailable = resultsAvailable;
		this.cgpa = cgpa;
		this.msg = msg;

	}

	public static ExamResponse getExamResponse(JSONObject obj) {

		ExamResponse examResponse;

		try {
			examResponse = parseExamResponse(obj);
		} catch (JSONException e) {

			e.printStackTrace();
			examResponse = new ExamResponse();

		}

		return examResponse;

	}

	private static ExamResponse parseExamResponse(JSONObject obj) throws JSONException {

		boolean resultsAvailable = obj.getBoolean("resultsAvailable");
		float cgpa = (float) obj.getDouble("cgpa");
		String msg = obj.getString("msg");

		return new ExamResponse(resultsAvailable, cgpa, msg);

	}

}

class FeeResponse {

	boolean resultsAvailable;
	int dues;
	int paid;
	int balance;
	String msg;

	FeeResponse() {

		this.resultsAvailable = false;
		this.dues = 0;
		this.paid = 0;
		this.balance = 0;
		this.msg = "";

	}

	FeeResponse(boolean resultsAvailable, int dues, int paid, int balance, String msg) {

		this.resultsAvailable = resultsAvailable;
		this.dues = dues;
		this.paid = paid;
		this.balance = balance;
		this.msg = msg;

	}

	public static FeeResponse getFeeResponse(JSONObject obj) {

		FeeResponse feeResponse;

		try {
			feeResponse = parseFeeResponse(obj);
		} catch (JSONException e) {

			e.printStackTrace();
			feeResponse = new FeeResponse();

		}

		return feeResponse;

	}

	private static FeeResponse parseFeeResponse(JSONObject obj) throws JSONException {

		boolean resultsAvailable = obj.getBoolean("resultsAvailable");
		int dues = obj.getInt("dues");
		int paid = obj.getInt("paid");
		int balance = obj.getInt("balance");
		String msg = obj.getString("msg");

		return new FeeResponse(resultsAvailable, dues, paid, balance, msg);

	}

}

class AttendanceResponse {

	boolean resultsAvailable;
	int daysAttended;
	int daysTotal;
	float attendancePercent;
	String msg;

	AttendanceResponse() {

		this.resultsAvailable = false;
		this.daysAttended = 0;
		this.daysTotal = 0;
		this.attendancePercent = 0.0f;
		this.msg = "";

	}

	AttendanceResponse(boolean resultsAvailable, int daysAttended, int daysTotal,
				float attendancePercent, String msg) {

		this.resultsAvailable = resultsAvailable;
		this.daysAttended = daysAttended;
		this.daysTotal = daysTotal;
		this.attendancePercent = attendancePercent;
		this.msg = msg;

	}

	public static AttendanceResponse getAttendanceResponse(JSONObject obj) {

		AttendanceResponse attendanceResponse;

		try {
			attendanceResponse = parseAttendanceResponse(obj);
		} catch (JSONException e) {

			e.printStackTrace();
			attendanceResponse = new AttendanceResponse();

		}

		return attendanceResponse;

	}

	private static AttendanceResponse parseAttendanceResponse(JSONObject obj) throws JSONException {

		boolean resultsAvailable = obj.getBoolean("resultsAvailable");
		int daysAttended = obj.getInt("daysAttended");
		int daysTotal = obj.getInt("daysTotal");
		float attendancePercent = (float) obj.getDouble("attendancePercent");
		String msg = obj.getString("msg");

		return new AttendanceResponse
				(resultsAvailable ,daysAttended, daysTotal, attendancePercent, msg);

	}

}

public class DashboardResponseObject {

	boolean resultsAvailable;
	int dashboardResponseSize;
	ExamResponse examResponse;
	FeeResponse feeResponse;
	AttendanceResponse attendanceResponse;
	String msg;

	DashboardResponseObject() {

		this.resultsAvailable = false;
		this.dashboardResponseSize = 0;
		this.examResponse = new ExamResponse();
		this.feeResponse = new FeeResponse();
		this.attendanceResponse = new AttendanceResponse();
		this.msg = "";

	}

	DashboardResponseObject(boolean resultsAvailable, int dashboardResponseSize,
							ExamResponse examResponse, FeeResponse feeResponse,
							AttendanceResponse attendanceResponse, String msg) {

		this.resultsAvailable = resultsAvailable;
		this.dashboardResponseSize = dashboardResponseSize;
		this.examResponse = examResponse;
		this.feeResponse = feeResponse;
		this.attendanceResponse = attendanceResponse;
		this.msg = msg;

	}

	@NonNull
	@Override
	public String toString() {

		return ".\nResponse Size: " + this.dashboardResponseSize +
				"\nCgpa: " + this.examResponse.cgpa +
				"\nFee Paid: " + this.feeResponse.paid +
				"\nAttendance: " + this.attendanceResponse.attendancePercent +
				"\nMessage: " + this.msg;

	}

	private static DashboardResponseObject fetchDashboardResponseUtil
			(String studentId, String semester) throws IOException, JSONException {

		URL dashboardResponseUrl = new URL("http://192.168.43.100:3000/dashboard?studentId=" +
				studentId + "&semester=" + semester);

		HttpURLConnection httpURLConnection;
		InputStream inputStream;
		Scanner inputStreamReader;
		StringBuilder dashboardJsonResponse = new StringBuilder();

		httpURLConnection = (HttpURLConnection) dashboardResponseUrl.openConnection();
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setConnectTimeout(10000);
		httpURLConnection.setReadTimeout(10000);
		httpURLConnection.connect();

		inputStream = httpURLConnection.getInputStream();
		inputStreamReader = new Scanner(inputStream);

		while (inputStreamReader.hasNext()) {
			dashboardJsonResponse.append(inputStreamReader.nextLine());
		}

		inputStreamReader.close();
		inputStream.close();
		httpURLConnection.disconnect();

		JSONObject root = new JSONObject(dashboardJsonResponse.toString());
		boolean resultsAvailable = root.getBoolean("resultsAvailable");
		int dashboardResponseSize = root.getInt("dashboardResponseSize");
		JSONArray dashboardResponse = root.getJSONArray("dashboardResponse");  // all fields
		String msg = root.getString("msg");

		// objects for dashboardResponse
		ExamResponse examResponse = null;
		FeeResponse feeResponse = null;
		AttendanceResponse attendanceResponse = null;

		for (int i = 0; i < dashboardResponseSize; i++) {

			JSONObject obj = (JSONObject) dashboardResponse.get(i);
			String dashboardField = obj.getString("dashboardField");

			switch (dashboardField) {

				case "exam":
					examResponse = ExamResponse.getExamResponse(obj);
					break;

				case "fee":
					feeResponse = FeeResponse.getFeeResponse(obj);
					break;

				case "attendance":
					attendanceResponse = AttendanceResponse.getAttendanceResponse(obj);
					break;

			}

		}

		return new DashboardResponseObject(
				resultsAvailable, dashboardResponseSize,
				examResponse, feeResponse, attendanceResponse, msg
		);

	}

	public static DashboardResponseObject fetchDashboardResponse(String studentId, String semester) {

		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		Future<DashboardResponseObject> dashboardResponseObjectFuture = singleThreadExecutor.submit(() -> {

			try {
				return fetchDashboardResponseUtil(studentId, semester);
			} catch (JSONException | IOException e) {

				e.printStackTrace();
				return new DashboardResponseObject();

			}

		});

		try {
			return dashboardResponseObjectFuture.get();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		return new DashboardResponseObject();

	}

}