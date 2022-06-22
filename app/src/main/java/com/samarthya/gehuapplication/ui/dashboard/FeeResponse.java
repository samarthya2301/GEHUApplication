package com.samarthya.gehuapplication.ui.dashboard;

import org.json.JSONException;
import org.json.JSONObject;

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
