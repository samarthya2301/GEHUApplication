package com.samarthya.gehuapplication.ui.dashboard;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class ExamResponse {

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
