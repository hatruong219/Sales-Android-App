package com.sict.shopdt.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.sict.shopdt.MainActivity;
import com.sict.shopdt.Manhinhcho;
import com.sict.shopdt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Changvalue extends AppCompatActivity {
    private static String Url_ChangeCart= Api.host + "api/changecart";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_changvalue );
        getSupportActionBar().hide();
        getDataCart();


    }

    private void getDataCart() {
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest( Request.Method.POST,
                Url_ChangeCart,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d( "change cart", "onResponse: "+response );
                            if ( jsonObject.getBoolean("status") ){
                                Intent intent = new Intent( getApplication(), Cart_Activity.class );
                                startActivity( intent );
                                Changvalue.this.finish();

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
                Intent intent = getIntent();
                String ValueChange = intent.getStringExtra("ValueChange");
                String ID_Sanpham = String.valueOf( intent.getIntExtra("ID_Sanpham",1) );
                parrams.put("ID_Sanpham", ID_Sanpham);
                parrams.put( "ID_User", String.valueOf( ID_User ) );
                parrams.put("ValueChange", ValueChange);
                return parrams;
            }
        };
        queue.add(stringRequest);
    }
}