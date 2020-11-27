package com.sict.shopdt.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sict.shopdt.Api;
import com.sict.shopdt.ListProductAdapter.Adapter;
import com.sict.shopdt.ListProductAdapter.Product;
import com.sict.shopdt.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListProduct_Activity extends AppCompatActivity {
    RecyclerView recycleview_listproduct;
    View view;
    Adapter adapter;
    Context context;
    ArrayList<Product> products = new ArrayList<>( );
    private static String Json_urllistproduct = Api.host + "api/listproduct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_product_ );
        getSupportActionBar().hide();

        //ánh xạ
        recycleview_listproduct = (RecyclerView) findViewById( R.id.recycleview_listproduct );
        recycleview_listproduct.setLayoutManager( new LinearLayoutManager( this ) );
        getDataListProduct();

    }
    private void getDataListProduct() {
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Json_urllistproduct,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            JSONArray data = jsonObject.getJSONArray( "data" );
                            JSONObject star = jsonObject.getJSONObject( "star" );
                            JSONObject comment = jsonObject.getJSONObject( "comment" );
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject( i );
                                final Product product = new Product(
                                        object.getString( "Namesp" ),
                                        object.getInt( "Gia" ),
                                        object.getString( "Image" ),
                                        (int) star.getInt( String.valueOf( object.getInt( "ID_Sanpham" ) ) ),
                                        object.getInt( "ID_Sanpham" ),
                                        (int) comment.getInt( String.valueOf( object.getInt( "ID_Sanpham" ) ) )
                                );

                                Log.d( "TAG", "onResponse: " + product.getNameProduct()+" va "+ product.getCommentProduct()+" vaf "+product.getStarProduct() );

                                products.add( product );
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        recycleview_listproduct.setHasFixedSize( true );
                        recycleview_listproduct.setLayoutManager(new LinearLayoutManager( context ));
                        adapter = new Adapter( getApplication(), products );
                        recycleview_listproduct.setAdapter( (RecyclerView.Adapter) adapter );
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
                            String ID_Loaihang = String.valueOf( intent.getIntExtra("ID_Loaihang",0) );
                            parrams.put("ID_Loaihang", ID_Loaihang);
                            return parrams;
                        }
                    };
                    queue.add(stringRequest);
                }
    }