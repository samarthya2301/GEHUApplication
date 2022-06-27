package com.samarthya.gehuapplication.ui.dashboard;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samarthya.gehuapplication.R;

public class DashboardViewModel extends ViewModel {

	private final MutableLiveData<String> tvDashboardForSemester;

	private final MutableLiveData<String> dashboardAttendance;
	private final MutableLiveData<String> dashboardAttendanceKey1;
	private final MutableLiveData<String> dashboardAttendanceValue1;
	private final MutableLiveData<String> dashboardAttendanceKey2;
	private final MutableLiveData<String> dashboardAttendanceValue2;

	private final MutableLiveData<String> dashboardExam;
	private final MutableLiveData<String> dashboardExamKey1;
	private final MutableLiveData<String> dashboardExamValue1;
	private final MutableLiveData<String> dashboardExamKey2;
	private final MutableLiveData<String> dashboardExamValue2;

	private final MutableLiveData<String> dashboardFees;
	private final MutableLiveData<String> dashboardFeesKey1;
	private final MutableLiveData<String> dashboardFeesValue1;
	private final MutableLiveData<String> dashboardFeesKey2;
	private final MutableLiveData<String> dashboardFeesValue2;

	public DashboardViewModel() {

		tvDashboardForSemester = new MutableLiveData<>();

		dashboardAttendance = new MutableLiveData<>();
		dashboardAttendanceKey1 = new MutableLiveData<>();
		dashboardAttendanceValue1 = new MutableLiveData<>();
		dashboardAttendanceKey2 = new MutableLiveData<>();
		dashboardAttendanceValue2 = new MutableLiveData<>();

		dashboardExam = new MutableLiveData<>();
		dashboardExamKey1 = new MutableLiveData<>();
		dashboardExamValue1 = new MutableLiveData<>();
		dashboardExamKey2 = new MutableLiveData<>();
		dashboardExamValue2 = new MutableLiveData<>();

		dashboardFees = new MutableLiveData<>();
		dashboardFeesKey1 = new MutableLiveData<>();
		dashboardFeesValue1 = new MutableLiveData<>();
		dashboardFeesKey2 = new MutableLiveData<>();
		dashboardFeesValue2 = new MutableLiveData<>();

	}

	public LiveData<String> getTvDashboardForSemester(@NonNull View view, String semester) {

		String fullText = view.getResources().getString(R.string.dashboard_for_semester)
				+ " "
				+ semester;

		tvDashboardForSemester.setValue(fullText);
		return tvDashboardForSemester;

	}

	public LiveData<String> getDashboardAttendance(String value) {
		dashboardAttendance.setValue(value);
		return dashboardAttendance;
	}

	public LiveData<String> getDashboardAttendanceKey1(String value) {
		dashboardAttendanceKey1.setValue(value);
		return dashboardAttendanceKey1;
	}

	public LiveData<String> getDashboardAttendanceValue1(String value) {
		dashboardAttendanceValue1.setValue(value);
		return dashboardAttendanceValue1;
	}

	public LiveData<String> getDashboardAttendanceKey2(String value) {
		dashboardAttendanceKey2.setValue(value);
		return dashboardAttendanceKey2;
	}

	public LiveData<String> getDashboardAttendanceValue2(String value) {
		dashboardAttendanceValue2.setValue(value);
		return dashboardAttendanceValue2;
	}

	public LiveData<String> getDashboardExam(String value) {
		dashboardExam.setValue(value);
		return dashboardExam;
	}

	public LiveData<String> getDashboardExamKey1(String value) {
		dashboardExamKey1.setValue(value);
		return dashboardExamKey1;
	}

	public LiveData<String> getDashboardExamValue1(String value) {
		dashboardExamValue1.setValue(value);
		return dashboardExamValue1;
	}

	public LiveData<String> getDashboardExamKey2(String value) {
		dashboardExamKey2.setValue(value);
		return dashboardExamKey2;
	}

	public LiveData<String> getDashboardExamValue2(String value) {
		dashboardExamValue2.setValue(value);
		return dashboardExamValue2;
	}

	public LiveData<String> getDashboardFees(String value) {
		dashboardFees.setValue(value);
		return dashboardFees;
	}

	public LiveData<String> getDashboardFeesKey1(String value) {
		dashboardFeesKey1.setValue(value);
		return dashboardFeesKey1;
	}

	public LiveData<String> getDashboardFeesValue1(String value) {
		dashboardFeesValue1.setValue(value);
		return dashboardFeesValue1;
	}

	public LiveData<String> getDashboardFeesKey2(String value) {
		dashboardFeesKey2.setValue(value);
		return dashboardFeesKey2;
	}

	public LiveData<String> getDashboardFeesValue2(String value) {
		dashboardFeesValue2.setValue(value);
		return dashboardFeesValue2;
	}

}