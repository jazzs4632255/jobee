package com.example.android_vu_assignment.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_vu_assignment.DiscussionFormActivity;
import com.example.android_vu_assignment.JobDetailsActivity;
import com.example.android_vu_assignment.R;
import com.example.android_vu_assignment.models.Post;
import com.example.android_vu_assignment.utils.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AllJobsRecyclerAdapter extends FirebaseRecyclerAdapter<Post, AllJobsRecyclerAdapter.AllJobsViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private Context mContext;

    public AllJobsRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);

    }

    public AllJobsRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Post> options, Context mContext) {
        super(options);
        this.mContext = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull AllJobsViewHolder holder, int position, @NonNull final Post post) {

        holder.titleTextView.setText(post.getTitle());
        holder.typeTextView.setText("Inquiry Type : " + post.getType());
        holder.descriptionTextView.setText(post.getDescription());
        holder.postedByTextView.setText(post.getPostedBy());
        holder.timeTextView.setText(Utils.getTimeUTC(post.getTimestamp()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (post.getType().equalsIgnoreCase("Discussion")) {
                    intent = new Intent(mContext, DiscussionFormActivity.class);
                } else {
                    intent = new Intent(mContext, JobDetailsActivity.class);
                }
                intent.putExtra("post", post);
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public AllJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_post_item, parent, false);
        return new AllJobsViewHolder(view);
    }

    static class AllJobsViewHolder
            extends RecyclerView.ViewHolder {
        TextView titleTextView, typeTextView, descriptionTextView, postedByTextView, timeTextView;

        public AllJobsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            postedByTextView = itemView.findViewById(R.id.postedByTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
