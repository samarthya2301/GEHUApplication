package com.samarthya.gehuapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.samarthya.gehuapplication.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

	private FragmentHomeBinding binding;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		HomeViewModel homeViewModel =
				new ViewModelProvider(this).get(HomeViewModel.class);

		binding = FragmentHomeBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		// student id
		final TextView tvStudentId = binding.tvStudentId;

		// student contact details
		final ImageView ivStudentImage = binding.ivStudentImage;
		final TextView tvStudentName = binding.tvStudentName;
		final TextView tvStudentPersonalEmail = binding.tvStudentPersonalEmail;
		final TextView tvStudentCollegeEmail = binding.tvStudentCollegeEmail;
		final TextView tvStudentPhoneNumber = binding.tvStudentPhoneNumber;

		// student personal details
		final TextView tvFatherName = binding.tvFatherName;
		final TextView tvMotherName = binding.tvMotherName;
		final TextView tvDOB = binding.tvDOB;
		final TextView tvCollege = binding.tvCollege;
		final TextView tvCourse = binding.tvCourse;
		final TextView tvBranch = binding.tvBranch;
		final TextView tvSemester = binding.tvSemester;
		final TextView tvSection = binding.tvSection;
		final TextView tvClassRollNo = binding.tvClassRollNo;
		final TextView tvIntermediate = binding.tvIntermediate;
		final TextView tvHighschool = binding.tvHighschool;
		final TextView tvAdmissionDate = binding.tvAdmissionDate;

		// student name
		homeViewModel.getStudentName().observe(getViewLifecycleOwner(), tvStudentName::setText);

		// student id
		homeViewModel.getStudentId().observe(getViewLifecycleOwner(), tvStudentId::setText);

		// student personal email
		homeViewModel.getPersonalEmail().observe(getViewLifecycleOwner(), tvStudentPersonalEmail::setText);

		// student college email
		homeViewModel.getCollegeEmail().observe(getViewLifecycleOwner(), tvStudentCollegeEmail::setText);

		// student phone number
		homeViewModel.getPhoneOne().observe(getViewLifecycleOwner(), tvStudentPhoneNumber::setText);

		// student image url and setting it with glide into the imageview
		String studentImageUrl = homeViewModel.getStudentImageUrl().getValue();
		Glide.with(requireContext())
				.load(studentImageUrl)
				.into(ivStudentImage);

		// personal student details
		homeViewModel.getFatherName().observe(getViewLifecycleOwner(), tvFatherName::append);
		homeViewModel.getMotherName().observe(getViewLifecycleOwner(), tvMotherName::append);
		homeViewModel.getDateOfBirth().observe(getViewLifecycleOwner(), tvDOB::append);
		homeViewModel.getCollege().observe(getViewLifecycleOwner(), tvCollege::append);
		homeViewModel.getCourse().observe(getViewLifecycleOwner(), tvCourse::append);
		homeViewModel.getBranch().observe(getViewLifecycleOwner(), tvBranch::append);
		homeViewModel.getSemester().observe(getViewLifecycleOwner(), tvSemester::append);
		homeViewModel.getSection().observe(getViewLifecycleOwner(), tvSection::append);
		homeViewModel.getClassRollNo().observe(getViewLifecycleOwner(), tvClassRollNo::append);
		homeViewModel.getIntermediatePercent().observe(getViewLifecycleOwner(), tvIntermediate::append);
		homeViewModel.getHighschoolPercent().observe(getViewLifecycleOwner(), tvHighschool::append);
		homeViewModel.getAdmissionDate().observe(getViewLifecycleOwner(), tvAdmissionDate::append);

		return root;

	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		binding = null;

	}

}