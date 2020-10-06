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
import com.example.android_vu_assignment.models.Reply;
import com.example.android_vu_assignment.models.Reply;
import com.example.android_vu_assignment.utils.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AllRepliesRecyclerAdapter extends FirebaseRecyclerAdapter<Reply, AllRepliesRecyclerAdapter.AllJobsViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private Context mContext;

    public AllRepliesRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Reply> options) {
        super(options);

    }

    public AllRepliesRecyclerAdapter(@NonNull FirebaseRecyclerOptions<Reply> options, Context mContext) {
        super(options);
        this.mContext = mContext;
    }

    @Override
    protected void onBindViewHolder(@NonNull AllJobsViewHolder holder, int position, @NonNull final Reply reply) {

        holder.replyTextView.setText(reply.getReply());
        holder.timeTextView.setText(Utils.getTimeUTC(reply.getTimestamp()));
    }

    @NonNull
    @Override
    public AllJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_reply_item, parent, false);
        return new AllJobsViewHolder(view);
    }

    static class AllJobsViewHolder
            extends RecyclerView.ViewHolder {
        TextView replyTextView, timeTextView;

        public AllJobsViewHolder(@NonNull View itemView) {
            super(itemView);
            replyTextView = itemView.findViewById(R.id.replyTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
