package com.sict.shopdt.CartAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.shopdt.Activity.Changvalue;
import com.sict.shopdt.Activity.ChitietSp;
import com.sict.shopdt.Api;
import com.sict.shopdt.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Queue;
import java.util.zip.Inflater;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<ProductCart> productCarts;

    public Adapter(Context context, ArrayList<ProductCart> productCarts) {
        this.context = context;
        this.productCarts = productCarts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from( parent.getContext() );
        View view = layoutInflater.inflate( R.layout.itemcart, parent, false );

        
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat( "###,###,###" );
        String Giafomat = decimalFormat.format( productCarts.get( position ).getPriceProduct() );
        holder.tvCartPrice.setText( Giafomat );
        holder.tvCartName.setText( productCarts.get( position ).getNameProduct() );
        holder.tvCartBrand.setText( productCarts.get( position ).getNameBrand() );
        holder.tvCartQuantity.setText( Integer.toString( productCarts.get( position ).getQuantity() ) );

        holder.btnCartAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context.getApplicationContext(), Changvalue.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ID_Sanpham", productCarts.get( position ).getID_Sanpham());
                intent.putExtra( "ValueChange","addvalue" );
                context.startActivity(intent);
            }
        } );
        holder.btnCartSub.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context.getApplicationContext(), Changvalue.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ID_Sanpham", productCarts.get( position ).getID_Sanpham());
                intent.putExtra( "ValueChange","subvalue" );
                context.startActivity(intent);
            }
        } );
        holder.imgCartClear.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context.getApplicationContext(), Changvalue.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ID_Sanpham", productCarts.get( position ).getID_Sanpham());
                intent.putExtra( "ValueChange","delete" );
                context.startActivity(intent);
            }
        } );


        Picasso.with(context).load( Api.host+"public/hinhanh/sanpham/" +productCarts.get( position).getImageProduct())
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgCart);

        holder.imgCart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context.getApplicationContext(), ChitietSp.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ID_Sanpham", productCarts.get( position ).getID_Sanpham());
                context.startActivity(intent);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return productCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCart, imgCartClear;
        TextView tvCartName, tvCartBrand, tvCartPrice, tvCartQuantity;
        public Button btnCartSub, btnCartAdd;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            imgCart = (ImageView) itemView.findViewById( R.id.imgCart );
            imgCartClear = (ImageView) itemView.findViewById( R.id.imgCartClear );
            tvCartName = (TextView) itemView.findViewById( R.id.tvCartName );
            tvCartBrand = (TextView) itemView.findViewById( R.id.tvCartBrand );
            tvCartPrice = (TextView) itemView.findViewById( R.id.tvCartPrice );
            tvCartQuantity = (TextView) itemView.findViewById( R.id.tvCartQuantity );
            btnCartAdd = (Button) itemView.findViewById( R.id.btnCartAdd );
            btnCartSub = (Button) itemView.findViewById( R.id.btnCartSub);
        }
    }
}
