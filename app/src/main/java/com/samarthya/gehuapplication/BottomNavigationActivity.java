package com.samarthya.gehuapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.samarthya.gehuapplication.databinding.ActivityBottomNavigationBinding;

public class BottomNavigationActivity extends AppCompatActivity {

	public static String staticStudentIdForFragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Intent intentFromMainActivity = getIntent();
		staticStudentIdForFragments = intentFromMainActivity.getStringExtra("studentId");

		com.samarthya.gehuapplication.databinding.ActivityBottomNavigationBinding binding =
				ActivityBottomNavigationBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		findViewById(R.id.nav_view);

		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_home,
				R.id.navigation_dashboard,
				R.id.navigation_downloads,
				R.id.navigation_settings
		).build();

		NavController navController = Navigation.findNavController(this, R.id.navigationHostFragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(binding.navView, navController);

	}

}