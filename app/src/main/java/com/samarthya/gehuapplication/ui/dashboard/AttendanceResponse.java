package com.samarthya.gehuapplication.ui.dashboard;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

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
		return "Attendance: " +
				this.attendancePercent +
				" Overall: " +
				this.overallAttendancePercent;
	}

}
