package com.sict.shopdt.ListNotificationAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sict.shopdt.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Notification> notifications;

    public Adapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
        View view = layoutInflater.inflate( R.layout.itemlistnotification, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle_listnoti.setText( notifications.get( position ).getTitle() );
        holder.tvContent_listnoti.setText( notifications.get( position ).getContext() );
        holder.tvDate_listnoti.setText( notifications.get( position ).getDate() );
        Glide.with( context.getApplicationContext() )
                .load( R.drawable.anh1anh6 ).centerCrop().optionalCircleCrop()
                .into( holder.imgNotification_listnoti );
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle_listnoti, tvContent_listnoti, tvDate_listnoti;
        ImageView imgNotification_listnoti;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            tvTitle_listnoti = (TextView) itemView.findViewById( R.id.tvTitle_listnoti );
            tvContent_listnoti = (TextView) itemView.findViewById( R.id.tvContent_listnoti );
            tvDate_listnoti = (TextView) itemView.findViewById( R.id.tvDate_listnoti );
            imgNotification_listnoti = (ImageView) itemView.findViewById( R.id.imgNotification_listnoti );

        }
    }
}
