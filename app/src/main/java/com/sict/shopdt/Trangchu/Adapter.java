package com.sict.shopdt.Trangchu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.shopdt.Activity.ChitietSp;
import com.sict.shopdt.Api;
import com.sict.shopdt.MainActivity;
import com.sict.shopdt.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private Context context;
        ArrayList<Sanpham> sanphams;

    public Adapter(Context context, ArrayList<Sanpham> sanphams) {
        this.context = context;
        this.sanphams = sanphams;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
       View view = layoutInflater.inflate( R.layout.item_trangchu, parent, false );
       return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        DecimalFormat decimalFormat = new DecimalFormat( "###,###,###" );
        String Giafomat = decimalFormat.format( sanphams.get( position ).getGiasp() );
        holder.tensp.setText( sanphams.get( position ).getTensp() );
        holder.giasp.setText( Giafomat );
        Picasso.with(context).load( Api.host+"public/hinhanh/sanpham/" +sanphams.get( position).getAnhsp())
                .error(R.drawable.ic_launcher_background)
                .into(holder.anhsp);
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, ChitietSp.class );
                intent.putExtra("ID_Sanpham", sanphams.get( position ).getID_Sanpham());
                context.startActivity(intent);
                }
        } );
    }

    @Override
    public int getItemCount() {
        return sanphams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView anhsp;
        TextView tensp,giasp;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            anhsp = (ImageView) itemView.findViewById( R.id.anhsp );
            tensp = (TextView) itemView.findViewById( R.id.tensp );
            giasp = (TextView) itemView.findViewById( R.id.giasp );
        }
    }

}
