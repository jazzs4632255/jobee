package com.example.android_vu_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android_vu_assignment.models.Post;
import com.example.android_vu_assignment.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostJobActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText titleInputLayout, descriptionInputLayout;
    private Spinner spinner;
    private MaterialButton postJobButton;
    String[] inquiryTypes;
    private ProgressBar progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Post Something!");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initUI();
        inquiryTypes = new String[]{"Please Select One", "Job", "Accomodation", "Discussion"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, inquiryTypes);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void initUI() {
        progressLayout = findViewById(R.id.progressLayout);
        spinner = findViewById(R.id.dropdownTextInput);
        titleInputLayout = findViewById(R.id.titleInputLayout);
        descriptionInputLayout = findViewById(R.id.descriptionInputLayout);
        postJobButton = findViewById(R.id.postButton);

        postJobButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == postJobButton){
            if (checkValidationPass()) {
               postJobToFirebase();
            }
        }
    }

    private void postJobToFirebase() {
        progressLayout.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts");
        String type = inquiryTypes[spinner.getSelectedItemPosition()];
        String title = titleInputLayout.getText().toString();
        String description = descriptionInputLayout.getText().toString();
        String postedBy = "Admin";
        long timeStamp = System.currentTimeMillis();
        Post post = new Post(type,title,description,postedBy,timeStamp);
        myRef.push().setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(PostJobActivity.this, "Your post is posted successfully!", Toast.LENGTH_SHORT).show();

                //Go back to home
                startActivity(new Intent(PostJobActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private boolean checkValidationPass(){
        View coordinatorLayout = findViewById(android.R.id.content);
        if(spinner.getSelectedItemPosition() == 0){
            Utils.showSnackBarMessage(coordinatorLayout,"Please select inquiry type of post.");
            return  false;
        }
        if (titleInputLayout.getText().toString().isEmpty()){
            Utils.showSnackBarMessage(coordinatorLayout,"Please title of post.");
            return  false;
        }
        else if (descriptionInputLayout.getText().toString().isEmpty()){
            Utils.showSnackBarMessage(coordinatorLayout,"Please provide description of post.");
            return false;
        }
        return  true;
    }
}