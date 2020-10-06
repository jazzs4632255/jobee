package com.example.android_vu_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android_vu_assignment.models.Post;
import com.example.android_vu_assignment.utils.Utils;

public class JobDetailsActivity extends AppCompatActivity {

    private TextView titleTextView, typeTextView, descriptionTextView, postedByTextView, timeTextView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Job Details");
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

        if (getIntent().getExtras() != null){
            Post post = (Post)getIntent().getExtras().get("post");
            if(post != null){
                populateUI(post);
            }
        }
    }

    private void initUI() {
        titleTextView = findViewById(R.id.titleTextView);
        typeTextView = findViewById(R.id.typeTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        postedByTextView = findViewById(R.id.postedByTextView);
        timeTextView = findViewById(R.id.timeTextView);
    }

    private void populateUI(Post post) {
        toolbar.setTitle(post.getTitle().isEmpty() ? "Job Details" : post.getTitle());
        titleTextView.setText(post.getTitle());
        typeTextView.setText("Inquiry Type : "+ post.getType());
        descriptionTextView.setText(post.getDescription());
        postedByTextView.setText(post.getPostedBy());
        timeTextView.setText(Utils.getTimeUTC(post.getTimestamp()));
    }
}