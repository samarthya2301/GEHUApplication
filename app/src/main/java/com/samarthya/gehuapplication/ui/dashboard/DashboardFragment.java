package com.samarthya.gehuapplication.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.samarthya.gehuapplication.BottomNavigationActivity;
import com.samarthya.gehuapplication.R;
import com.samarthya.gehuapplication.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

	private FragmentDashboardBinding binding;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		DashboardViewModel dashboardViewModel =
				new ViewModelProvider(this).get(DashboardViewModel.class);

		binding = FragmentDashboardBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		// getting hold of the components on the DashboardFragment
		final TextView tvDashboardForSemester = binding.tvDashboardForSemester;
		final Spinner spinnerSelectSemester = binding.spinnerSelectSemesterDashboard;

		// spinner adapter
		ArrayAdapter<CharSequence> spinnerSelectSemesterAdapter =
				ArrayAdapter.createFromResource(
						requireContext(),
						R.array.spinnerSelectSemester,
						android.R.layout.simple_spinner_item
				);

		// setting up the spinner
		spinnerSelectSemesterAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSelectSemester.setAdapter(spinnerSelectSemesterAdapter);

		// spinner item select listener
		spinnerSelectSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

				// toast to mark that the data fetching has started
				showCustomToast("Fetching Data, Please Wait!");

				String semesterSelected = adapterView.getItemAtPosition(position).toString();
				dashboardViewModel.getTvDashboardForSemester(root, semesterSelected)
						.observe(getViewLifecycleOwner(), tvDashboardForSemester::setText);

				// fetch the dashboard response object here
				DashboardResponseObject dashboardResponseObject = DashboardResponseObject
						.fetchDashboardResponse(
								BottomNavigationActivity.staticStudentIdForFragments,
								semesterSelected
						);

				setDashboardFieldValues(dashboardResponseObject, dashboardViewModel);

			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {}

		});

		return root;

	}
	
	private void setDashboardFieldValues(DashboardResponseObject dashboardResponseObject,
									DashboardViewModel dashboardViewModel
	) {

		boolean resultsAvailable = dashboardResponseObject.getResultsAvailable();
		AttendanceResponse attendanceResponse =	dashboardResponseObject.getAttendanceResponse();
		ExamResponse examResponse =	dashboardResponseObject.getExamResponse();
		FeeResponse feeResponse = dashboardResponseObject.getFeeResponse();

		if ( !resultsAvailable) {
			return;
		}

		// static fields for the DashboardFieldsCard
		DashboardFieldsCard.setDashboardFieldCardStatics(dashboardViewModel, this);
		// all card fields
		DashboardFieldsCard cvAttendanceField = new DashboardFieldsCard(
				binding.cvDashboardAttendance.dashboardField,
				binding.cvDashboardAttendance.dashboardKey1,
				binding.cvDashboardAttendance.dashboardKey2,
				binding.cvDashboardAttendance.dashboardValue1,
				binding.cvDashboardAttendance.dashboardValue2
		);
		DashboardFieldsCard cvExamField = new DashboardFieldsCard(
				binding.cvDashboardExam.dashboardField,
				binding.cvDashboardExam.dashboardKey1,
				binding.cvDashboardExam.dashboardKey2,
				binding.cvDashboardExam.dashboardValue1,
				binding.cvDashboardExam.dashboardValue2
		);
		DashboardFieldsCard cvFeeField = new DashboardFieldsCard(
				binding.cvDashboardFees.dashboardField,
				binding.cvDashboardFees.dashboardKey1,
				binding.cvDashboardFees.dashboardKey2,
				binding.cvDashboardFees.dashboardValue1,
				binding.cvDashboardFees.dashboardValue2
		);

		String attendanceValue1 = attendanceResponse.attendancePercent + "%";
		String attendanceValue2 = attendanceResponse.overallAttendancePercent + "%";
		cvAttendanceField.setFieldData(
				new DashboardValuesCard(
						"Attendance",
						"Current",
						"Overall",
						attendanceValue1,
						attendanceValue2
				)
		);

		String examValue1 = examResponse.sgpa + "/10.00";
		String examValue2 = examResponse.cgpa + "/10.00";
		cvExamField.setFieldData(
				new DashboardValuesCard(
						"Exam",
						"SGPA (current)",
						"CGPA (overall)",
						examValue1,
						examValue2
				)
		);

		String rupeeSymbol = getResources().getString(R.string.rupee_symbol);
		String feesValue1 = rupeeSymbol + feeResponse.balance + "";
		String feesValue2 = rupeeSymbol + feeResponse.paid + "/" + rupeeSymbol + feeResponse.dues;
		cvFeeField.setFieldData(
				new DashboardValuesCard(
						"Fees",
						"Balance Amount",
						"Paid/Dues",
						feesValue1,
						feesValue2
				)
		);

		// toast to mark data fetching has been done
		showCustomToast("Data has been Fetched!");

	}

	public void showCustomToast(String toastData) {
		Toast.makeText(this.getContext(), toastData, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		binding = null;

	}

}