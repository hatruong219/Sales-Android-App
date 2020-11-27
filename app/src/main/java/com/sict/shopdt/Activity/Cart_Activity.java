package com.sict.shopdt.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.sict.shopdt.CartAdapter.Adapter;
import com.sict.shopdt.CartAdapter.ProductCart;
import com.sict.shopdt.ListProductAdapter.Product;
import com.sict.shopdt.MainActivity;
import com.sict.shopdt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart_Activity extends AppCompatActivity {
    RecyclerView recycleview_cart;
    Adapter adapter;
    Context context;
    TextView tvCartTotal;
    AppCompatButton btnCartPay;
    ArrayList<ProductCart> productCarts = new ArrayList<>(); 
    private static String Url_Cart = Api.host + "api/listcart";
    private static String Url_PayCart = Api.host + "api/paycart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cart_ );
        getSupportActionBar().hide();
        int ID_User = MainActivity.getMyData();
        Log.d( "gàydgyuasgdyasyudas", "onCreate: "+ID_User );

        //ánh xạ
        btnCartPay = (AppCompatButton) findViewById( R.id.btnCartPay );
        tvCartTotal = (TextView) findViewById( R.id.tvCartTotal ) ;
        recycleview_cart = (RecyclerView) findViewById( R.id.recycleview_cart );
        recycleview_cart.setLayoutManager( new LinearLayoutManager( this ) );

        getDataCart();

        btnCartPay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ID_User>0){
                    RequestQueue queue = Volley.newRequestQueue(getApplication());
                    StringRequest stringRequest = new StringRequest( Request.Method.POST,
                            Url_PayCart,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.d( "change cart", "onResponse: "+response );
                                        if ( jsonObject.getBoolean("status") ){
                                            Intent intent = new Intent( getApplication(), Cart_Activity.class );
                                            startActivity( intent );
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
                            int ID_User = MainActivity.getMyData();
                            parrams.put( "ID_User", String.valueOf( ID_User ) );
                            return parrams;
                        }
                    };
                    queue.add(stringRequest);
                }else {
                    Toast.makeText( getApplicationContext(), "Mời bạn đăng nhập hệ thống", Toast.LENGTH_SHORT );
                }
            }

        } );
    }
    private void getDataCart() {
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest( Request.Method.POST,
                Url_Cart,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int total=0;
                        Log.d( "cartcarrtcart", "onResponse: " + response.toString() );
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            JSONArray data = jsonObject.getJSONArray( "data" );
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject( i );
                                int giahdt = object.getInt( "Gia" )*object.getInt( "Soluongmua" );
                                total+=giahdt;
                                final ProductCart productCart = new ProductCart(
                                        object.getString( "Namesp" ),
                                        object.getString( "Nameth" ),
                                        giahdt,
                                        object.getString( "Image" ),
                                        object.getInt( "Soluongmua" ),
                                        object.getInt( "ID_Sanpham" )

                                );

                                productCarts.add( productCart );
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DecimalFormat decimalFormat = new DecimalFormat( "###,###,###" );
                        tvCartTotal.setText( decimalFormat.format(total)+" Vnđ" );

                        recycleview_cart.setHasFixedSize( true );
                        recycleview_cart.setLayoutManager(new LinearLayoutManager( context ));
                        adapter = new Adapter( getApplication(), productCarts );
                        recycleview_cart.setAdapter( (RecyclerView.Adapter) adapter );
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
                int ID_User = MainActivity.getMyData();
                parrams.put("ID_User", String.valueOf( ID_User ) );
                return parrams;
            }
        };
        queue.add(stringRequest);
    }
}