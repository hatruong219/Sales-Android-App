package com.sict.shopdt.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WriteComment_Activity extends AppCompatActivity {
    ImageView imgWriteComment;
    TextView tvProductComment;
    AppCompatButton btnAddComment;
    AppCompatRatingBar rbWriteComment;
    EditText edtWriteComment;
    Context context;
    private static String Url_Addcomment = Api.host + "api/addcomment";
    private static String Url_Inforoncomment = Api.host + "api/inforoncomment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_write_comment_ );
        getSupportActionBar().hide();
        Anhxa();

        int ID_User = MainActivity.getMyData();
        btnAddComment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ID_User >0 ){
                    RequestQueue queue = Volley.newRequestQueue(getApplication());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_Addcomment, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if ( jsonObject.getBoolean("status") ){
                                    Toast.makeText(getApplicationContext(), "Đã thêm thành công đánh giá về sản phẩm", Toast.LENGTH_SHORT).show();
                                    Intent intent = getIntent();
                                    String ID_Sanpham = intent.getStringExtra( "ID_Sanpham" );

                                    Intent intent1 = new Intent( getApplicationContext(), Comment_Activity.class );
                                    intent1.putExtra( "ID_Sanpham", ID_Sanpham );
                                    startActivity( intent1 );

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
                            int star = (int) rbWriteComment.getRating();
                            String Noidung = String.valueOf( edtWriteComment.getText());
                            DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
                            dateFormatter.setLenient(false);
                            Date today = new Date();
                            String date = dateFormatter.format(today);

                            Intent intent = getIntent();
                            String ID_Sanpham = intent.getStringExtra( "ID_Sanpham" );
                            parrams.put("ID_Sanpham", ID_Sanpham);
                            parrams.put( "ID_User", String.valueOf( ID_User ) );
                            parrams.put( "Capbac", String.valueOf( star ) );
                            parrams.put( "Noidung", Noidung );
                            parrams.put( "Ngaybl", date );

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

    }

    private void Anhxa() {
        edtWriteComment = (EditText) findViewById( R.id.edtWriteComment );
        btnAddComment = (AppCompatButton) findViewById( R.id.btnAddComment );
        rbWriteComment = (AppCompatRatingBar) findViewById( R.id.rbWriteComment );
    }
}