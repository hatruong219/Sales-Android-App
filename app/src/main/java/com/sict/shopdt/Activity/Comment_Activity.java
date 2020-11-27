package com.sict.shopdt.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sict.shopdt.Api;
import com.sict.shopdt.ListComment.Adapter;
import com.sict.shopdt.ListComment.Comment;
import com.sict.shopdt.ListProductAdapter.Product;
import com.sict.shopdt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Comment_Activity extends AppCompatActivity {
    RecyclerView recycleview_comment;
    AppCompatButton btnWriteComment;
    View view;
    Adapter adapter;
    Context context;
    ArrayList<Comment> comments = new ArrayList<>(  );
    private static String Url_listcomment = Api.host + "api/listcomment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_comment_ );
        getSupportActionBar().hide();


        //ánh xạ
        btnWriteComment = (AppCompatButton) findViewById( R.id.btnWriteComment );
        recycleview_comment = (RecyclerView) findViewById( R.id.recycleview_comment );
        recycleview_comment.setLayoutManager( new LinearLayoutManager( this ) );
        getDataListComment();

        btnWriteComment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String ID_Sanpham = intent.getStringExtra( "ID_Sanpham" );

                Intent intent1 = new Intent( getApplicationContext(), WriteComment_Activity.class );
                intent1.putExtra( "ID_Sanpham", ID_Sanpham );
                startActivity( intent1 );
            }
        } );

    }

    private void getDataListComment() {
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest( Request.Method.POST,
                Url_listcomment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            JSONArray data = jsonObject.getJSONArray( "data" );
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject( i );
                                final Comment comment = new Comment(
                                        object.getString( "Name" ),
                                        object.getInt( "Capbac" ),
                                        object.getString( "Ngaybl" ),
                                        object.getString( "Noidung" ),
                                        object.getString( "Anhdaidien" )
                                );


                                comments.add( comment );
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        recycleview_comment.setHasFixedSize( true );
                        recycleview_comment.setLayoutManager(new LinearLayoutManager( context ));
                        adapter = new Adapter( getApplication(), comments );
                        recycleview_comment.setAdapter( (RecyclerView.Adapter) adapter );
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
                String ID_Sanpham = intent.getStringExtra( "ID_Sanpham" );
                parrams.put("ID_Sanpham", ID_Sanpham);
                return parrams;
            }
        };
        queue.add(stringRequest);
    }

}