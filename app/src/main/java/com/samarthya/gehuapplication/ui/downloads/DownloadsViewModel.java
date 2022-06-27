package com.samarthya.gehuapplication.ui.downloads;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.net.Uri;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.samarthya.gehuapplication.BottomNavigationActivity;
import com.samarthya.gehuapplication.Server;
import com.samarthya.gehuapplication.R;
import com.samarthya.gehuapplication.databinding.FragmentDownloadsBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class DownloadsViewModel extends ViewModel {

	private final MutableLiveData<String> downloadSuccessful;
	private final MutableLiveData<String> downloadUnuccessful;

	// constructor for view model class
	public DownloadsViewModel() {

		downloadSuccessful = new MutableLiveData<>();
		downloadUnuccessful = new MutableLiveData<>();

	}

	public boolean downloadRequiredFile(FragmentDownloadsBinding binding,
									 String fileType, String semesterSelectedFromSpinner) {

		View view = binding.getRoot();
		ExecutorService exec = Executors.newSingleThreadExecutor();
		AtomicBoolean isFileDownloaded = new AtomicBoolean(false);

		downloadSuccessful.setValue(view.getResources().getString(R.string.download_successful));
		downloadUnuccessful.setValue(view.getResources().getString(R.string.download_unsuccessful));

		Future<Boolean> isFileAvailable = exec.submit(() -> {

			try {
				isFileDownloaded.set
						(downloadRequiredFileUtil(view, fileType, semesterSelectedFromSpinner));
			} catch (FileNotFoundException e) {
				isFileDownloaded.set(false);
			}

			return isFileDownloaded.get();

		});

		boolean boolIsFileAvailable = false;

		try {
			boolIsFileAvailable = isFileAvailable.get();
		} catch (ExecutionException | InterruptedException ignored) {}

		return boolIsFileAvailable;

	}

	private boolean downloadRequiredFileUtil
			(View view, String fileType, String semesterSelectedFromSpinner)
			throws FileNotFoundException {

		File file = new File(view.getContext().getExternalFilesDir(null), fileType);

		Uri uri = Uri.parse(
				"http://" + Server.SOCKET_ADDRESS + "/downloads/" +
						fileType +
						"?studentId=" + BottomNavigationActivity.staticStudentIdForFragments +
						"&semester=" + semesterSelectedFromSpinner
		);

		boolean boolDownloadFileExistsOrNot = downloadFileExistsOrNot(uri.toString());

		if ( !boolDownloadFileExistsOrNot) {
			return false;
		}

		DownloadManager.Request request = new DownloadManager.Request(uri);
		request.setTitle(fileType.toUpperCase(Locale.ROOT) +
						" file for Semester " + semesterSelectedFromSpinner)
				.setDescription("Downloading " + fileType + " file...")
				.setNotificationVisibility
						(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
				.setDestinationUri(Uri.fromFile(file))
				.setAllowedOverMetered(true)
				.setAllowedOverRoaming(true);

		DownloadManager manager = (DownloadManager)
				view.getContext().getSystemService(DOWNLOAD_SERVICE);
		manager.enqueue(request);

		return true;

	}

	private boolean downloadFileExistsOrNot(String fileToCheckUrl) {

		try {

			URL url = new URL(fileToCheckUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setConnectTimeout(10000);
			httpURLConnection.setReadTimeout(10000);
			httpURLConnection.connect();
			httpURLConnection.getInputStream();
			httpURLConnection.disconnect();

		} catch (IOException e) {
			return false;
		}

		return true;

	}

	public LiveData<String> getDownloadSuccessfulText() {
		return downloadSuccessful;
	}

	public LiveData<String> getDownloadUnsuccessfulText() {
		return downloadUnuccessful;
	}

}