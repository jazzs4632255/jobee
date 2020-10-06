package com.example.android_vu_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_vu_assignment.adapters.AllJobsRecyclerAdapter;
import com.example.android_vu_assignment.adapters.AllRepliesRecyclerAdapter;
import com.example.android_vu_assignment.models.Post;
import com.example.android_vu_assignment.models.Reply;
import com.example.android_vu_assignment.utils.Utils;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DiscussionFormActivity extends AppCompatActivity implements View.OnClickListener {

    private String postReferenceKey;
    private ProgressBar progressLayout, progressLayout2;
    private Toolbar toolbar;
    private TextView headingTextView, statementTextView, postedOnTextView;
    private RecyclerView repliesRecyclerView;
    private MaterialButton replyButton;
    private AllRepliesRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_form);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Discussion Forum");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
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

        if (getIntent().getExtras() != null) {
            Post post = (Post) getIntent().getExtras().get("post");
            if (post != null) {
                postReferenceKey = post.getPostReference();
                populateUI(post);
            }
        }

        loadAllReplies();
    }

    private void initUI() {
        headingTextView = findViewById(R.id.headingTextView);
        statementTextView = findViewById(R.id.statementTextView);
        postedOnTextView = findViewById(R.id.postedOnTextView);
        replyButton = findViewById(R.id.replyButton);
        progressLayout = findViewById(R.id.progressLayout);
        repliesRecyclerView = findViewById(R.id.repliesRecyclerView);

        replyButton.setOnClickListener(this);
    }

    private void populateUI(Post post) {
        toolbar.setTitle(post.getTitle().isEmpty() ? "Discussion Forum" : post.getTitle());
        headingTextView.setText(post.getTitle());
        statementTextView.setText(post.getDescription());
        postedOnTextView.setText("Posted on : " + Utils.getTimeUTC(post.getTimestamp()));
    }

    @Override
    public void onClick(View view) {
        if (view == replyButton) {
            showReplyDialog();
        }
    }

    private void showReplyDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reply_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        final TextView replyEditText = dialog.findViewById(R.id.replyEditText);
        MaterialButton postButton = dialog.findViewById(R.id.postButton);
        progressLayout2 = dialog.findViewById(R.id.progressLayout2);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reply = replyEditText.getText().toString();
                if (reply.isEmpty()){
                    Toast.makeText(DiscussionFormActivity.this, "Please type reply before post.", Toast.LENGTH_SHORT).show();
                }

                postReplyToFirebasePost(reply, dialog);
            }
        });
        
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void postReplyToFirebasePost(String reply, final Dialog dialog) {
        progressLayout2.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts").child(postReferenceKey).child("replies");

        Reply postReply = new Reply(reply, System.currentTimeMillis());

        myRef.push().setValue(postReply).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressLayout2.setVisibility(View.GONE);
                Toast.makeText(DiscussionFormActivity.this, "Your post is posted successfully!", Toast.LENGTH_SHORT).show();
                if (dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
    }

    private void loadAllReplies() {
        //progressLayout.setVisibility(View.VISIBLE);
        repliesRecyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        final DatabaseReference postsQuery = FirebaseDatabase.getInstance().getReference()
                .child("posts").child(postReferenceKey).child("replies");
        postsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()){
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseRecyclerOptions<Reply> options
                = new FirebaseRecyclerOptions.Builder<Reply>()
                .setQuery(postsQuery, new SnapshotParser<Reply>() {
                    @NonNull
                    @Override
                    public Reply parseSnapshot(@NonNull DataSnapshot snapshot) {
                        String key = snapshot.getKey();
                       // progressLayout.setVisibility(View.GONE);
                        return  snapshot.getValue(Reply.class);
                    }
                }).build();
        adapter = new AllRepliesRecyclerAdapter(options, this);
        repliesRecyclerView.setAdapter(adapter);
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