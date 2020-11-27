package com.sict.shopdt.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sict.shopdt.Api;
import com.sict.shopdt.MainActivity;
import com.sict.shopdt.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Map;

public class ChitietSp extends AppCompatActivity {
    private static String Url_Chitietsp = Api.host + "api/chitietsanpham";
    private static String Url_addproduct = Api.host + "api/addproduct";
    ImageView imgProduct;
    TextView tvNameProduct, tvCommentProduct, tvPriceProduct;
    AppCompatButton btnAddCart;
    AppCompatRatingBar rbProductRating;
    Context context;
    HtmlTextView tvDescriptionProduct;
    static String temp1,temp2, temp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chitiet_sp );
        getSupportActionBar().hide();
        Anhxa();
        Laydulieu();

        // thêm vào giỏ hàng
        int ID_User = MainActivity.getMyData();
        btnAddCart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ID_User >0 ){
                    RequestQueue queue = Volley.newRequestQueue(getApplication());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_addproduct, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if ( jsonObject.getBoolean("status") ){
                                    Toast.makeText(getApplicationContext(), "Đã thêm thành công sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(getApplicationContext(), "Thêm sản phẩm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Lỗi " + error, Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parrams = new HashMap<>();
                            Intent intent = getIntent();
                            String ID_Sanpham = String.valueOf( intent.getIntExtra("ID_Sanpham",1) );
                            parrams.put("ID_Sanpham", ID_Sanpham);
                            parrams.put( "ID_User", String.valueOf( ID_User ) );
                            return parrams;
                        }
                    };
                    queue.add(stringRequest);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Bạn chưa đăng nhập, vui lòng đăng nhập để mua sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        } );

        // xem bình luận về sản phẩm

        tvCommentProduct.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String ID_Sanpham = String.valueOf( intent.getIntExtra("ID_Sanpham",1) );
                Log.d( "2 click", "onClick: "+ID_Sanpham );
                Intent intent1 = new Intent( getApplicationContext(), Comment_Activity.class );
                intent1.putExtra("ID_Sanpham", ID_Sanpham);
                startActivity(intent1);
            }
        } );

    }
    public  void Laydulieu(){
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_Chitietsp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ( jsonObject.getBoolean("status") ){
                        JSONObject object = jsonObject.getJSONObject("data");
                        String Image = object.getString( "Image" );
                        String Namesp = object.getString( "Namesp" );
                        String Mota = object.getString( "Mota" );

                        //Fommat giá
                        int Gia = object.getInt( "Gia" );
                        DecimalFormat decimalFormat = new DecimalFormat( "###,###,###" );
                        String Giafomat = decimalFormat.format( Gia );
                        tvPriceProduct.setText( Giafomat +" Vnđ");


                        tvNameProduct.setText( Namesp );
                        tvDescriptionProduct.setHtml(Mota,new HtmlHttpImageGetter(tvDescriptionProduct, null, true));
                        Picasso.with(context).load( Api.host+"public/hinhanh/sanpham/"+Image)
                                .error(R.drawable.ic_launcher_background)
                                .into(imgProduct);
                        int comment = jsonObject.getInt( "comment" );
                        tvCommentProduct.setText( "( XEM "+comment+" NHẬN XÉT )" );

                        int star = jsonObject.getInt( "star" );
                        rbProductRating.setRating(star);
                    }else {
                        Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Lỗi " + error, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();
                Intent intent = getIntent();
                String ID_Sanpham = String.valueOf( intent.getIntExtra("ID_Sanpham",1) );
                parrams.put("ID_Sanpham", ID_Sanpham);
                return parrams;
            }
        };
        queue.add(stringRequest);
    }
    public void Anhxa(){
        imgProduct = (ImageView) findViewById( R.id.imgProduct );
        tvNameProduct = (TextView) findViewById( R.id.tvNameProduct );
        tvCommentProduct = (TextView) findViewById( R.id.tvCommentProduct );
        tvPriceProduct = (TextView) findViewById( R.id.tvPriceProduct );
        tvDescriptionProduct = (HtmlTextView) findViewById( R.id.tvDescriptionProduct );
        btnAddCart = (AppCompatButton) findViewById( R.id.btnAddCart );
        rbProductRating = (AppCompatRatingBar) findViewById( R.id.rbProductRating );
    }
}
