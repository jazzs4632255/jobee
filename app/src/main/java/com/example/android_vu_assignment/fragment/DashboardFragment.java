package com.example.android_vu_assignment.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.android_vu_assignment.PostJobActivity;
import com.example.android_vu_assignment.R;
import com.google.android.material.button.MaterialButton;

public class DashboardFragment extends Fragment implements View.OnClickListener{

    private static final String EXTRA_TEXT = "text";
    private View view;
    private MaterialButton askForHelpButton, wantToHelpButton;

    public static DashboardFragment createFor(String text) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        askForHelpButton = view.findViewById(R.id.askForHelpButton);
        wantToHelpButton = view.findViewById(R.id.wantToHelpButton);

        askForHelpButton.setOnClickListener(this);
        wantToHelpButton.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
    }

    @Override
    public void onClick(View view) {
        if (view == askForHelpButton || view == wantToHelpButton){
            gotoPostJobActivity();
        }
    }

    private void gotoPostJobActivity() {
        startActivity(new Intent(getActivity(), PostJobActivity.class));
    }
}