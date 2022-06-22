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
	float sgpa;
	float cgpa;
	String msg;

	ExamResponse() {

		this.resultsAvailable = false;
		this.sgpa = 0;
		this.cgpa = 0;
		this.msg = "";

	}

	ExamResponse(boolean resultsAvailable, float sgpa, float cgpa, String msg, boolean isSgpa) {

		this.resultsAvailable = resultsAvailable;
		if (isSgpa) {
			this.sgpa = sgpa;
		} else {
			this.cgpa = cgpa;
		}
		this.msg = msg;

	}

	public void setSgpa(float sgpa) {
		this.sgpa = sgpa;
	}

	public void setCgpa(float cgpa) {
		this.cgpa = cgpa;
	}

	public static ExamResponse getExamResponse(JSONObject examJson, boolean isSgpa) {

		ExamResponse examResponse;

		try {
			examResponse = parseExamResponse(examJson, isSgpa);
		} catch (JSONException e) {

			e.printStackTrace();
			examResponse = new ExamResponse();

		}

		return examResponse;

	}

	private static ExamResponse parseExamResponse(JSONObject examJson, boolean isSgpa)
			throws JSONException {

		boolean resultsAvailable = examJson.getBoolean("resultsAvailable");
		float sgpa = 0;
		float cgpa = 0;
		if (isSgpa) {
			sgpa = (float) examJson.getDouble("sgpa");
		} else {
			cgpa = (float) examJson.getDouble("cgpa");
		}
		String msg = examJson.getString("msg");

		return new ExamResponse(resultsAvailable, sgpa, cgpa, msg, isSgpa);

	}

	@NonNull
	@Override
	public String toString() {
		return "SGPA: " + this.sgpa + " CGPA: " + this.cgpa + "\n";
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
	float attendancePercent = 0;
	float overallAttendancePercent = 0;
	String msg;

	AttendanceResponse() {

		this.resultsAvailable = false;
		this.daysAttended = 0;
		this.daysTotal = 0;
		this.attendancePercent = 0.0f;
		this.overallAttendancePercent = 0.0f;
		this.msg = "";

	}

	AttendanceResponse(boolean resultsAvailable, int daysAttended, int daysTotal,
					   float attendancePercent, float overallAttendancePercent, String msg,
					   boolean isOverall) {

		this.resultsAvailable = resultsAvailable;
		this.daysAttended = daysAttended;
		this.daysTotal = daysTotal;
		if (isOverall) {
			this.overallAttendancePercent = overallAttendancePercent;
		} else {
			this.attendancePercent = attendancePercent;
		}
		this.msg = msg;

	}

	public void setAttendancePercent(float attendancePercent) {
		this.attendancePercent = attendancePercent;
	}

	public void setOverallAttendancePercent(float overallAttendancePercent) {
		this.overallAttendancePercent = overallAttendancePercent;
	}
	public static AttendanceResponse getAttendanceResponse(JSONObject obj, boolean isOverall) {

		AttendanceResponse attendanceResponse;

		try {
			attendanceResponse = parseAttendanceResponse(obj, isOverall);
		} catch (JSONException e) {

			e.printStackTrace();
			attendanceResponse = new AttendanceResponse();

		}

		return attendanceResponse;

	}

	private static AttendanceResponse parseAttendanceResponse(JSONObject obj, boolean isOverall)
			throws JSONException {

		boolean resultsAvailable = obj.getBoolean("resultsAvailable");
		int daysAttended = obj.getInt("daysAttended");
		int daysTotal = obj.getInt("daysTotal");
		float attendancePercent = 0;
		float attendancePercentOverall = 0;
		if (isOverall) {
			attendancePercentOverall = (float) obj.getDouble("attendancePercentOverall");
		} else {
			attendancePercent = (float) obj.getDouble("attendancePercent");
		}
		String msg = obj.getString("msg");

		return new AttendanceResponse(
				resultsAvailable ,daysAttended, daysTotal, attendancePercent,
				attendancePercentOverall, msg, isOverall
		);

	}

	@NonNull
	@Override
	public String toString() {
		return "Attendance: " + this.attendancePercent + " Overall: " + this.overallAttendancePercent;
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

	public boolean getResultsAvailable() {
		return this.resultsAvailable;
	}

	public int getDashboardResponseSize() {
		return this.dashboardResponseSize;
	}

	public ExamResponse getExamResponse() {
		return this.examResponse;
	}

	public FeeResponse getFeeResponse() {
		return this.feeResponse;
	}

	public AttendanceResponse getAttendanceResponse() {
		return this.attendanceResponse;
	}

	public String getMsg() {
		return this.msg;
	}

	@NonNull
	@Override
	public String toString() {

		return ".\nResponse Size: " + this.dashboardResponseSize +
				"\nSgpa: " + this.examResponse.sgpa +
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
					if (examResponse == null) {
						examResponse = ExamResponse.getExamResponse(obj, true);
					} else {
						examResponse.setSgpa((float)obj.getDouble("sgpa"));
					}
					break;

				case "examOverall":
					if (examResponse == null) {
						examResponse = ExamResponse.getExamResponse(obj, false);
					} else {
						examResponse.setCgpa((float)obj.getDouble("cgpa"));
					}
					break;

				case "fee":
					feeResponse = FeeResponse.getFeeResponse(obj);
					break;

				case "attendance":
					if (attendanceResponse == null) {
						attendanceResponse = AttendanceResponse.getAttendanceResponse(obj, false);
					} else {
						attendanceResponse.setAttendancePercent((float)obj.getDouble("attendancePercent"));
					}
					break;

				case "attendanceOverall":
					if (attendanceResponse == null) {
						attendanceResponse = AttendanceResponse.getAttendanceResponse(obj, true);
					} else {
						attendanceResponse.setOverallAttendancePercent((float)obj.getDouble("attendancePercentOverall"));
					}
					break;

			}

		}

		return new DashboardResponseObject(
				resultsAvailable, dashboardResponseSize,
				examResponse, feeResponse, attendanceResponse, msg
		);

	}

	public static DashboardResponseObject fetchDashboardResponse
			(String studentId, String semester) {

		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		Future<DashboardResponseObject> dashboardResponseObjectFuture =
				singleThreadExecutor.submit(() -> {

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