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
						attendanceResponse = AttendanceResponse
								.getAttendanceResponse(obj, false);
					} else {
						attendanceResponse.setAttendancePercent
								((float)obj.getDouble("attendancePercent"));
					}
					break;

				case "attendanceOverall":
					if (attendanceResponse == null) {
						attendanceResponse = AttendanceResponse
								.getAttendanceResponse(obj, true);
					} else {
						attendanceResponse.setOverallAttendancePercent
								((float)obj.getDouble("attendancePercentOverall"));
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