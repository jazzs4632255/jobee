package com.example.android_vu_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android_vu_assignment.adapters.AllJobsRecyclerAdapter;
import com.example.android_vu_assignment.models.Post;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AllJobsActivity extends AppCompatActivity {

    private AllJobsRecyclerAdapter adapter;
    private RecyclerView allJobsRecyclerView;
    private ProgressBar progressLayout;
    private TextView noRecordFoundTextView;
    private boolean isFromDiscussion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jobs);

        Toolbar toolbar = findViewById(R.id.toolbar);
        allJobsRecyclerView = findViewById(R.id.allJobsRecyclerView);
        progressLayout = findViewById(R.id.progressLayout);
        noRecordFoundTextView = findViewById(R.id.noRecordFoundTextView);

        toolbar.setTitle("Jobs / Accomodation / Discussions");
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

        if (getIntent().getExtras() != null){

           isFromDiscussion = getIntent().getExtras().getBoolean("isFromDiscussion", false);
        }

        loadAllJobs();
    }

    private void loadAllJobs() {
        progressLayout.setVisibility(View.VISIBLE);
        allJobsRecyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        final DatabaseReference postsQuery = FirebaseDatabase.getInstance().getReference()
                .child("posts");

        postsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()){
                    progressLayout.setVisibility(View.GONE);
                    noRecordFoundTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseRecyclerOptions<Post> options
                = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(postsQuery, new SnapshotParser<Post>() {
                    @NonNull
                    @Override
                    public Post parseSnapshot(@NonNull DataSnapshot snapshot) {
                        String key = snapshot.getKey();
                        progressLayout.setVisibility(View.GONE);
                        noRecordFoundTextView.setVisibility(View.GONE);
                        Post post = snapshot.getValue(Post.class);
                        post.setPostReference(key);
                        return post;
                    }
                }).build();
        adapter = new AllJobsRecyclerAdapter(options, this);
        allJobsRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}