package com.example.fragmentexample2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.fragmentexample2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private boolean isFragmentDisplayed = false;

    private SimpleFragment simpleFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Instantiate the fragment.
        simpleFragment = SimpleFragment.newInstance();

        // Set the click listener for the button.
        binding.openButton.setOnClickListener(view -> {
            // Get the FragmentManager and start a transaction.
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction =
                    fragmentManager.beginTransaction();

            if (!isFragmentDisplayed) {
                displayFragment();
            } else {
                hideFragment();
            }
        });
    }

    /**
     * This method is called when the user clicks the button
     * to open the fragment.
     */
    private void displayFragment() {
        // Add the SimpleFragment.
        fragmentTransaction
                .add(R.id.fragment_container, simpleFragment)
                .addToBackStack(null)
                .commit();

        // Update the Button text.
        isFragmentDisplayed = true;
        // Set boolean flag to indicate fragment is open.
        binding.openButton.setText(R.string.close);
    }

    /**
     * This method is called when the user clicks the button to
     * close the fragment.
     */
    private void hideFragment() {
        // Check to see if the fragment is already showing.
        if (simpleFragment != null) {
            // Commit the transaction to remove the fragment.
            fragmentTransaction
                    .remove(simpleFragment)
                    .addToBackStack(null)
                    .commit();

            // Update the Button text.
            isFragmentDisplayed = false;
            // Set boolean flag to indicate fragment is closed.
            binding.openButton.setText(R.string.open);
        }
    }
}