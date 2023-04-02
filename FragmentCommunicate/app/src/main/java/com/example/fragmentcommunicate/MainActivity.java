package com.example.fragmentcommunicate;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.fragmentcommunicate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener{

    public static ActivityMainBinding activityMainBinding;

    private boolean isFragmentDisplayed = false;

    // The radio button choice default is 2 = (no choice).
    // Initialize the radio button choice to the default.
    private int radioButtonChoice = 2;
    private SimpleFragment simpleFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        // Instantiate the fragment.
        simpleFragment = SimpleFragment.newInstance(radioButtonChoice);

        // Set the click listener for the button.
        activityMainBinding.openButton.setOnClickListener(view -> {
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
        activityMainBinding.openButton.setText(R.string.close);
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
            activityMainBinding.openButton.setText(R.string.open);
        }
    }

    /**
     * This method keeps in memory the radio button choice the user selected
     * and displays a Toast to show it.
     *
     * @param choice The user's radio button choice.
     */
    @Override
    public void onRadioButtonChoice(int choice) {
        // Keep the radio button choice to pass it back to the fragment.
        radioButtonChoice = choice;
        // Show a Toast with the radio button choice.
        Toast.makeText(this, "Choice is " + choice,
                LENGTH_SHORT).show();
    }
}