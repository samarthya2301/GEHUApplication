package com.samarthya.gehuapplication.ui.downloads;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.samarthya.gehuapplication.R;
import com.samarthya.gehuapplication.databinding.FragmentDownloadsBinding;

public class DownloadsFragment extends Fragment {

	private FragmentDownloadsBinding binding;
	private DownloadsViewModel downloadsViewModel;
	private TextView tvFileDownloadStatus;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		downloadsViewModel =
				new ViewModelProvider(this).get(DownloadsViewModel.class);

		binding = FragmentDownloadsBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		// download status TextView
		tvFileDownloadStatus = binding.tvFileDownloadStatus;

		// semester selected from the spinner
		final String[] semesterSelectedFromSpinner = new String[1];

		// setting up the spinner
		final Spinner spinnerSelectSemester = binding.spinnerSelectSemesterDownloads;
		ArrayAdapter<CharSequence> spinnerSelectSemesterAdapter =
				ArrayAdapter.createFromResource(
						requireContext(),
						R.array.spinnerSelectSemester,
						android.R.layout.simple_spinner_item
				);

		spinnerSelectSemesterAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSelectSemester.setAdapter(spinnerSelectSemesterAdapter);

		spinnerSelectSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected
					(AdapterView<?> adapterView, View view, int position, long id) {

				// value of the selected item from the spinner
				semesterSelectedFromSpinner[0] = adapterView.getItemAtPosition(position).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {}

		});

		// button - Exam Results
		final Button btnDownloadExamResult = binding.btnDownloadExamResult;
		btnDownloadExamResult.setOnClickListener(view -> {

			boolean isFileAvailable = downloadsViewModel.downloadRequiredFile
					(binding, "exam", semesterSelectedFromSpinner[0]);

			setTvDownloadStatus(isFileAvailable, view);

		});

		// button - Fee Receipts
		final Button btnDownloadFeeReceipt = binding.btnDownloadFeeReceipt;
		btnDownloadFeeReceipt.setOnClickListener(view -> {

			boolean isFileAvailable = downloadsViewModel.downloadRequiredFile
					(binding, "fee", semesterSelectedFromSpinner[0]);

			setTvDownloadStatus(isFileAvailable, view);

		});

		// button - Notices and Updates
		final Button btnDownloadNoticeUpdate = binding.btnDownloadNoticeUpdate;
		btnDownloadNoticeUpdate.setOnClickListener(view -> {

			Uri uri = Uri.parse("https://www.gehu.ac.in/content/gehu/en/notice-updates.html");
			Intent showNoticesAndUpdates = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(showNoticesAndUpdates);

		});

		return root;

	}

	private void setTvDownloadStatus(boolean isFileAvailable, View view) {

		tvFileDownloadStatus.setVisibility(View.VISIBLE);

		if (isFileAvailable) {

			tvFileDownloadStatus.setTextColor
					(view.getResources().getColor(R.color.success, null));
			downloadsViewModel.getDownloadSuccessfulText()
					.observe(getViewLifecycleOwner(), tvFileDownloadStatus::setText);

		} else {

			tvFileDownloadStatus.setTextColor
					(view.getResources().getColor(R.color.error, null));
			downloadsViewModel.getDownloadUnsuccessfulText()
					.observe(getViewLifecycleOwner(), tvFileDownloadStatus::setText);

		}

		AlphaAnimation tvDownloadStatusAnimation = new AlphaAnimation(1.0f, 1.0f);
		tvDownloadStatusAnimation.setStartOffset(1);
		tvDownloadStatusAnimation.setDuration(5000);
		tvDownloadStatusAnimation.setAnimationListener(new Animation.AnimationListener() {

			@Override public void onAnimationStart(Animation animation) {}

			@Override
			public void onAnimationEnd(Animation animation) {
				tvFileDownloadStatus.setVisibility(View.GONE);
			}

			@Override public void onAnimationRepeat(Animation animation) {}

		});

		tvFileDownloadStatus.setAnimation(tvDownloadStatusAnimation);

	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		binding = null;

	}

}