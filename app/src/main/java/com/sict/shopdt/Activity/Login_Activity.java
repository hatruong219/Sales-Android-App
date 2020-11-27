package com.sict.shopdt.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.sict.shopdt.Api;
import com.sict.shopdt.Fragment.TrangchuFragment;
import com.sict.shopdt.MainActivity;
import com.sict.shopdt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {
    EditText edtLoginPhone, edtLoginPass;
    Button btnLogin;
    TextView tvRegister;
    Context context;
    private static String Url_login = Api.host+"api/dangnhap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login_ );
        getSupportActionBar().hide();
        edtLoginPhone = (EditText) findViewById( R.id.edtLoginPhone );
        edtLoginPass = (EditText) findViewById( R.id.edtLoginPass );
        btnLogin = (Button) findViewById( R.id.btnLogin );
        tvRegister = (TextView) findViewById( R.id.tvRegister );



        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mSdt = edtLoginPhone.getText().toString().trim();
                String mMatkhau = edtLoginPass.getText().toString().trim();
                if (!mSdt.isEmpty() || !mMatkhau.isEmpty()){
                    Login();
                }
                else {
                    edtLoginPhone.setError( "Số điện thoại không được để trống" );
                    edtLoginPass.setError( "Mật khẩu không được để trống" );

                }
            }
        } );

        tvRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplication(), Register_Activity.class );
                startActivity(intent);
            }
        } );

    }
    private void Login() {

        RequestQueue queue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ( jsonObject.getBoolean("status") ){
                        JSONObject object = jsonObject.getJSONObject("data");
                        int ID_User = object.getInt("ID_User");
                        String Phone = object.getString("Phone");
                        String Pass = object.getString("Pass");
                        String date = object.getString("created_at");
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công"+ID_User, Toast.LENGTH_SHORT).show();
                        Log.d( "ID_User", "onResponse: "+ID_User );
                        Intent intent = new Intent( getApplication(), MainActivity.class );
                        intent.putExtra("ID_User", ID_User);
                        intent.putExtra("Phone", Phone);
                        intent.putExtra("Pass", Pass);
                        intent.putExtra("date", date);
                        startActivity(intent);

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

                // get data from edittext
                String Phone = edtLoginPhone.getText().toString().trim();
                String Pass = edtLoginPass.getText().toString().trim();
                parrams.put("Phone", Phone);
                parrams.put("Pass", Pass);

                return parrams;
            }
        };
        queue.add(stringRequest);
    }

}
