package com.samarthya.gehuapplication.ui.dashboard;

import android.widget.TextView;

public class DashboardFieldsCard {

	// required static fields
	static DashboardViewModel dashboardViewModel;
	static DashboardFragment dashboardFragment;

	// non static fields
	TextView tvDashboardField;
	TextView tvDashboardKey1;
	TextView tvDashboardKey2;
	TextView tvDashboardValue1;
	TextView tvDashboardValue2;

	public DashboardFieldsCard(TextView tvDashboardField,
							   TextView tvDashboardKey1,
							   TextView tvDashboardKey2,
							   TextView tvDashboardValue1,
							   TextView tvDashboardValue2) {

		this.tvDashboardField = tvDashboardField;
		this.tvDashboardKey1 = tvDashboardKey1;
		this.tvDashboardKey2 = tvDashboardKey2;
		this.tvDashboardValue1 = tvDashboardValue1;
		this.tvDashboardValue2 = tvDashboardValue2;

	}

	public static void setDashboardFieldCardStatics
			(DashboardViewModel dashboardViewModel, DashboardFragment dashboardFragment) {

		DashboardFieldsCard.dashboardViewModel = dashboardViewModel;
		DashboardFieldsCard.dashboardFragment = dashboardFragment;

	}

	// function to set live data
	public void setFieldData(DashboardValuesCard dashboardValuesCard) {

		switch (dashboardValuesCard.dashboardField) {

			case "Attendance":
				setFieldDataAttendance(dashboardValuesCard);
				break;

			case "Exam":
				setFieldDataExam(dashboardValuesCard);
				break;

			case "Fees":
				setFieldDataFees(dashboardValuesCard);
				break;

		}

	}

	private void setFieldDataAttendance(DashboardValuesCard dashboardValuesCard) {

		// heading
		dashboardViewModel.getDashboardAttendance(dashboardValuesCard.dashboardField)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardField::setText);

		// keys
		dashboardViewModel.getDashboardAttendanceKey1(dashboardValuesCard.dashboardKey1)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardKey1::setText);
		dashboardViewModel.getDashboardAttendanceKey2(dashboardValuesCard.dashboardKey2)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardKey2::setText);

		// values
		dashboardViewModel.getDashboardAttendanceValue1(dashboardValuesCard.dashboardValue1)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardValue1::setText);
		dashboardViewModel.getDashboardAttendanceValue2(dashboardValuesCard.dashboardValue2)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardValue2::setText);

	}

	private void setFieldDataExam(DashboardValuesCard dashboardValuesCard) {

		// heading
		dashboardViewModel.getDashboardExam(dashboardValuesCard.dashboardField)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardField::setText);

		// keys
		dashboardViewModel.getDashboardExamKey1(dashboardValuesCard.dashboardKey1)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardKey1::setText);
		dashboardViewModel.getDashboardExamKey2(dashboardValuesCard.dashboardKey2)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardKey2::setText);

		// values
		dashboardViewModel.getDashboardExamValue1(dashboardValuesCard.dashboardValue1)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardValue1::setText);
		dashboardViewModel.getDashboardExamValue2(dashboardValuesCard.dashboardValue2)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardValue2::setText);

	}

	private void setFieldDataFees(DashboardValuesCard dashboardValuesCard) {

		// heading
		dashboardViewModel.getDashboardFees(dashboardValuesCard.dashboardField)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardField::setText);

		// keys
		dashboardViewModel.getDashboardFeesKey1(dashboardValuesCard.dashboardKey1)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardKey1::setText);
		dashboardViewModel.getDashboardFeesKey2(dashboardValuesCard.dashboardKey2)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardKey2::setText);

		// values
		dashboardViewModel.getDashboardFeesValue1(dashboardValuesCard.dashboardValue1)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardValue1::setText);
		dashboardViewModel.getDashboardFeesValue2(dashboardValuesCard.dashboardValue2)
				.observe(dashboardFragment.getViewLifecycleOwner(), this.tvDashboardValue2::setText);

	}

}
