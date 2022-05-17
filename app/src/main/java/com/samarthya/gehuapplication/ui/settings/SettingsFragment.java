package com.samarthya.gehuapplication.ui.settings;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.samarthya.gehuapplication.R;
import com.samarthya.gehuapplication.databinding.FragmentSettingsBinding;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SettingsFragment extends Fragment {

	private FragmentSettingsBinding binding;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {

		SettingsViewModel settingsViewModel =
				new ViewModelProvider(this).get(SettingsViewModel.class);

		binding = FragmentSettingsBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		// hidden elements, only appear when clicking the change password button
		final LinearLayout containerLLNewPassword = binding.containerLLNewPassword;
		final LinearLayout containerLLConfirmPassword = binding.containerLLConfirmPassword;
		final TextView tvPasswordChangedSuccessfully = binding.tvPasswordChangedSuccessfully;

		// both the buttons
		final Button btnChangePassword = binding.btnChangePassword;
		final Button btnLogout = binding.btnLogout;

		// enter new password views
		final ImageView ivNewPasswordSetVisible = binding.ivNewPassrwordSetVisible;
		final EditText etNewPassword = binding.etNewPassword;

		// confirm new password views
		final ImageView ivConfirmPasswordSetVisible = binding.ivConfirmPasswordSetVisible;
		final EditText etConfirmPassword = binding.etConfirmPassword;

		// hide and show new password logic
		AtomicBoolean isNewPasswordVisible = new AtomicBoolean(false);
		ivNewPasswordSetVisible.setOnClickListener(view -> {

			if (isNewPasswordVisible.get()) {

				ivNewPasswordSetVisible.setImageResource(R.drawable.make_password_visible);
				isNewPasswordVisible.set(false);
				etNewPassword.setTransformationMethod
						(PasswordTransformationMethod.getInstance());  // show password

			} else {

				ivNewPasswordSetVisible.setImageResource(R.drawable.make_password_invisible);
				isNewPasswordVisible.set(true);
				etNewPassword.setTransformationMethod
						(HideReturnsTransformationMethod.getInstance());  // hide password

			}

		});

		// hide and show confirm password logic
		AtomicBoolean isConfirmPasswordVisible = new AtomicBoolean(false);
		ivConfirmPasswordSetVisible.setOnClickListener(view -> {

			if (isConfirmPasswordVisible.get()) {

				ivConfirmPasswordSetVisible.setImageResource(R.drawable.make_password_visible);
				isConfirmPasswordVisible.set(false);
				etConfirmPassword.setTransformationMethod
						(PasswordTransformationMethod.getInstance());  // show password

			} else {

				ivConfirmPasswordSetVisible.setImageResource(R.drawable.make_password_invisible);
				isConfirmPasswordVisible.set(true);
				etConfirmPassword.setTransformationMethod
						(HideReturnsTransformationMethod.getInstance());  // hide password

			}

		});

		AtomicBoolean isSubmitEnabled = new AtomicBoolean(false);
		btnChangePassword.setOnClickListener(view -> {
			
			if (!isSubmitEnabled.get()) {
				
				containerLLNewPassword.setVisibility(View.VISIBLE);
				containerLLConfirmPassword.setVisibility(View.VISIBLE);
				tvPasswordChangedSuccessfully.setVisibility(View.GONE);
				btnChangePassword.setText(R.string.submit);
				isSubmitEnabled.set(true);

			} else {

				String newPassword = etNewPassword.getText().toString();
				String confirmPassword = etConfirmPassword.getText().toString();

				if (newPassword.isEmpty() ||
						confirmPassword.isEmpty() ||
						!newPassword.equals(confirmPassword)
				) {
					
					Toast.makeText(requireContext(),
							"Passwords Do Not Match!", Toast.LENGTH_SHORT)
							.show();

					return;
					
				}

				ExecutorService exec = Executors.newSingleThreadExecutor();
				Handler handler = new Handler(Looper.getMainLooper());
				AtomicBoolean passwordChangedSuccessfully = new AtomicBoolean(false);

				exec.execute(() -> {

					try {
						passwordChangedSuccessfully.set(settingsViewModel.postNewPassword(newPassword));
					} catch (IOException | JSONException e) {
						// the passwordChangedSuccessfully will remain false if error is thrown
						e.printStackTrace();
					}

					handler.post(() -> {

						if (passwordChangedSuccessfully.get()) {

							// set the textview as successful here only
							tvPasswordChangedSuccessfully.setVisibility(View.VISIBLE);
							containerLLNewPassword.setVisibility(View.GONE);
							containerLLConfirmPassword.setVisibility(View.GONE);
							btnChangePassword.setText(R.string.change_password);
							etNewPassword.setText("");
							etConfirmPassword.setText("");
							isSubmitEnabled.set(false);

						} else {

							// set the password change as unsuccessful with livedata
							settingsViewModel.getPasswordChangeFailureMessage().observe(
									getViewLifecycleOwner(),
									tvPasswordChangedSuccessfully::setText
							);

						}

					});

				});

			}

		});

		// destroy the activity and continue with MainActivity from onResume()
		btnLogout.setOnClickListener(view -> requireActivity().finish());

		return root;

	}

	@Override
	public void onDestroyView() {

		super.onDestroyView();
		binding = null;

	}

}