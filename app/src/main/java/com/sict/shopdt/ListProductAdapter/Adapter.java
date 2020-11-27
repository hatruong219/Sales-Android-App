package com.sict.shopdt.ListProductAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.shopdt.Activity.ChitietSp;
import com.sict.shopdt.Api;
import com.sict.shopdt.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Product> products;

    public Adapter(Context context, ArrayList<Product> listProduct){
        this.context = context;
        this.products = listProduct;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
        View view = layoutInflater.inflate( R.layout.itemlistproduct, parent,false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        DecimalFormat decimalFormat = new DecimalFormat( "###,###,###" );
        String Giafomat = decimalFormat.format( products.get( position ).getPriceProduct() );
        holder.tvNameProduct_listprd.setText( products.get( position ).getNameProduct() );
        holder.tvCommentProduct_listprd.setText( "  ("+String.valueOf( products.get( position ).getCommentProduct() )+")");
        holder.tvPriceProduct_listprd.setText( Giafomat );
        holder.rbProductRating_listprd.setRating(products.get(position).getStarProduct());
        Picasso.with(context).load( Api.host+"public/hinhanh/sanpham/" +products.get( position).getImageProduct())
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgProduct_listprd);
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context.getApplicationContext(), ChitietSp.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ID_Sanpham", products.get( position ).getID_Sanpham());
                context.startActivity(intent);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct_listprd;
        TextView tvNameProduct_listprd,tvPriceProduct_listprd,tvCommentProduct_listprd;
        AppCompatRatingBar rbProductRating_listprd;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            imgProduct_listprd = (ImageView) itemView.findViewById( R.id.imgProduct_listprd );
            tvNameProduct_listprd = (TextView) itemView.findViewById( R.id.tvNameProduct_listprd );
            tvPriceProduct_listprd = (TextView) itemView.findViewById( R.id.tvPriceProduct_listprd );
            rbProductRating_listprd = (AppCompatRatingBar) itemView.findViewById( R.id.rbProductRating_listprd );
            tvCommentProduct_listprd = (TextView) itemView.findViewById( R.id.tvCommentProduct_listprd );
        }
    }
}
