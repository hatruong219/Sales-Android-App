package com.sict.shopdt.ListComment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sict.shopdt.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Comment> comments;


    public Adapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
        View view = layoutInflater.inflate( R.layout.itemcomment, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rbComment.setRating(comments.get(position).getStarComment());
        holder.tvCommentName.setText( comments.get( position ).getNameUserComment() );
        holder.tvCommentDate.setText( comments.get( position ).getDateComment() );
        holder.tvCommentContent.setText( comments.get( position ).getContentComment() );
        Glide.with( context.getApplicationContext() )
                .load( R.drawable.anhdemodemo ).centerCrop().optionalCircleCrop()
                .into( holder.imgCommentUser );
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCommentUser;
        TextView tvCommentName, tvCommentDate, tvCommentContent;
        AppCompatRatingBar rbComment;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            imgCommentUser = (ImageView) itemView.findViewById( R.id.imgCommentUser );
            tvCommentName = (TextView) itemView.findViewById( R.id.tvCommentName );
            tvCommentDate = (TextView) itemView.findViewById( R.id.tvCommentDate );
            tvCommentContent = (TextView) itemView.findViewById( R.id.tvCommentContent );
            rbComment = (AppCompatRatingBar) itemView.findViewById( R.id.rbComment );


        }
    }
}
