package com.samarthya.gehuapplication.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;

public class HomeViewModel extends ViewModel {

	// student id
	private final MutableLiveData<String> mldStudentId;

	// student contact
	private final MutableLiveData<String> mldPersonalEmail;
	private final MutableLiveData<String> mldCollegeEmail;
	private final MutableLiveData<String> mldPhoneOne;
	private final MutableLiveData<String> mldPhoneTwo;
	private final MutableLiveData<String> mldStudentImageUrl;
	private final MutableLiveData<String> mldStudentName;

	// student personal
	private final MutableLiveData<String> mldFatherName;
	private final MutableLiveData<String> mldMotherName;
	private final MutableLiveData<String> mldDateOfBirth;
	private final MutableLiveData<String> mldCollege;
	private final MutableLiveData<String> mldCourse;
	private final MutableLiveData<String> mldBranch;
	private final MutableLiveData<String> mldSemester;
	private final MutableLiveData<String> mldSection;
	private final MutableLiveData<String> mldClassRollNo;
	private final MutableLiveData<String> mldIntermediatePercent;
	private final MutableLiveData<String> mldHighschoolPercent;
	private final MutableLiveData<String> mldAdmissionDate;

	public HomeViewModel() {

		StudentContact studentContactObject = null;
		StudentPersonal studentPersonalObject = null;

		try {

			studentContactObject = StudentContact.getStudentContactObject();
			studentPersonalObject = StudentPersonal.getStudentPersonalObject();
			Log.d("xxx", "HomeViewModel: " + studentPersonalObject);

		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		// student id
		mldStudentId = new MutableLiveData<>();

		// student contact
		mldPersonalEmail = new MutableLiveData<>();
		mldCollegeEmail = new MutableLiveData<>();
		mldPhoneOne = new MutableLiveData<>();
		mldPhoneTwo = new MutableLiveData<>();
		mldStudentImageUrl = new MutableLiveData<>();
		mldStudentName = new MutableLiveData<>();

		// student personal
		mldFatherName = new MutableLiveData<>();
		mldMotherName = new MutableLiveData<>();
		mldDateOfBirth = new MutableLiveData<>();
		mldCollege = new MutableLiveData<>();
		mldCourse = new MutableLiveData<>();
		mldBranch = new MutableLiveData<>();
		mldSemester = new MutableLiveData<>();
		mldSection = new MutableLiveData<>();
		mldClassRollNo = new MutableLiveData<>();
		mldIntermediatePercent = new MutableLiveData<>();
		mldHighschoolPercent = new MutableLiveData<>();
		mldAdmissionDate = new MutableLiveData<>();

		assert studentContactObject != null;
		assert studentPersonalObject != null;

		// setting the student id
		mldStudentId.setValue(studentContactObject.studentId);

		// setting the student contact details
		mldPersonalEmail.setValue(studentContactObject.personalEmail);
		mldCollegeEmail.setValue(studentContactObject.collegeEmail);
		mldPhoneOne.setValue("+91–" + studentContactObject.phoneOne);
		mldPhoneTwo.setValue("+91–" + studentContactObject.phoneTwo);
		mldStudentImageUrl.setValue(studentContactObject.studentImageUrl);
		mldStudentName.setValue(studentContactObject.studentName);

		// setting the student personal details
		mldFatherName.setValue(":   " + studentPersonalObject.fatherName);
		mldMotherName.setValue(":   " + studentPersonalObject.motherName);
		mldDateOfBirth.setValue(":   " + studentPersonalObject.dateOfBirth);
		mldCollege.setValue(":   " + studentPersonalObject.college);
		mldCourse.setValue(":   " + studentPersonalObject.course);
		mldBranch.setValue(":   " + studentPersonalObject.branch);
		mldSemester.setValue(":   " + studentPersonalObject.semester);
		mldSection.setValue(":   " + studentPersonalObject.section);
		mldClassRollNo.setValue(":   " + studentPersonalObject.classRollNo);
		mldIntermediatePercent.setValue(":   " + studentPersonalObject.intermediatePercent);
		mldHighschoolPercent.setValue(":   " + studentPersonalObject.highschoolPercent);
		mldAdmissionDate.setValue(":   " + studentPersonalObject.admissionDate);

	}

	public LiveData<String> getStudentId() {
		return mldStudentId;
	}

	public LiveData<String> getPersonalEmail() {
		return mldPersonalEmail;
	}

	public LiveData<String> getCollegeEmail() {
		return mldCollegeEmail;
	}

	public LiveData<String> getPhoneOne() {

		if (Objects.equals(mldPhoneOne.getValue(), "")) {
			return getPhoneTwo();
		}

		return mldPhoneOne;
	}

	public LiveData<String> getPhoneTwo() {
		return mldPhoneTwo;
	}

	public LiveData<String> getStudentImageUrl() {
		return mldStudentImageUrl;
	}

	public LiveData<String> getStudentName() {
		return mldStudentName;
	}

	public LiveData<String> getFatherName() {
		return mldFatherName;
	}

	public LiveData<String> getMotherName() {
		return mldMotherName;
	}

	public LiveData<String> getDateOfBirth() {
		return mldDateOfBirth;
	}

	public LiveData<String> getCollege() {
		return mldCollege;
	}

	public LiveData<String> getCourse() {
		return mldCourse;
	}

	public LiveData<String> getBranch() {
		return mldBranch;
	}

	public LiveData<String> getSemester() {
		return mldSemester;
	}

	public LiveData<String> getSection() {
		return mldSection;
	}

	public LiveData<String> getClassRollNo() {
		return mldClassRollNo;
	}

	public LiveData<String> getIntermediatePercent() {
		return mldIntermediatePercent;
	}

	public LiveData<String> getHighschoolPercent() {
		return mldHighschoolPercent;
	}

	public LiveData<String> getAdmissionDate() {
		return mldAdmissionDate;
	}


}