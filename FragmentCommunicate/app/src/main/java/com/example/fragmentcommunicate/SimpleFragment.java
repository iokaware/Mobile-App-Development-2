package com.example.fragmentcommunicate;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragmentcommunicate.databinding.FragmentSimpleBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimpleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleFragment extends Fragment {

    public static FragmentSimpleBinding fragmentSimpleBinding;

    // The radio button choice has 3 states: 0 = yes, 1 = no,
    // 2 = default (no choice). Using all 3 states.
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;

    private static final String ARG_CHOICE = "choice";

    private int choice;

    private OnFragmentInteractionListener listener;

    interface OnFragmentInteractionListener {
        void onRadioButtonChoice(int choice);
    }

    public SimpleFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CHOICE, choice);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * This method checks if the hosting activity has implemented
     * the OnFragmentInteractionListener interface. If it does not,
     * an exception is thrown.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + getResources().getString(R.string.exception_message));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            choice = getArguments().getInt(ARG_CHOICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentSimpleBinding = FragmentSimpleBinding.inflate(inflater, container, false);
        return fragmentSimpleBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (choice != NONE) {
            fragmentSimpleBinding.radioGroup.check
                    (fragmentSimpleBinding.radioGroup.getChildAt(choice).getId());
        }

        // Set the radioGroup onCheckedChanged listener.
        fragmentSimpleBinding.radioGroup.setOnCheckedChangeListener(
                (group, checkedId) -> {
                    switch (checkedId) {
                        case R.id.radio_button_yes: // User chose "Yes".
                            fragmentSimpleBinding.fragmentHeader.setText(R.string.yes_message);
                            listener.onRadioButtonChoice(YES);
                            break;
                        case R.id.radio_button_no: // User chose "No".
                            fragmentSimpleBinding.fragmentHeader.setText(R.string.no_message);
                            listener.onRadioButtonChoice(NO);
                            break;
                    }
                });
    }
}