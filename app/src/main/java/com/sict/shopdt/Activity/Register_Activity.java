package com.sict.shopdt.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register_Activity extends AppCompatActivity {
    EditText edtRegisterName, edtRegisterPass, edtRegisterPhone, getEdtRegisterConfirmPass;
    RadioButton rbtnRegisterMale, rbtnRegisterFemale;

    AppCompatButton btnRegister;
    private static String Url_Register = (String) Api.host+"api/dangky";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_register);
        getSupportActionBar().hide();
        edtRegisterPhone = (EditText) findViewById( R.id.edtRegisterPhone );
        edtRegisterName = (EditText) findViewById( R.id.edtRegisterName );
        edtRegisterPass = (EditText) findViewById( R.id.edtRegisterPass );
        btnRegister = (AppCompatButton) findViewById( R.id.btnRegister );
        rbtnRegisterMale = (RadioButton) findViewById( R.id.rbtRegisterMale );
        rbtnRegisterFemale = (RadioButton) findViewById( R.id.rbtRegisterFemale );
        btnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtRegisterPhone.getText().toString().trim();
                String name = edtRegisterName.getText().toString().trim();
                String pass = edtRegisterPass.getText().toString().trim();
                    if (!phone.isEmpty() || !pass.isEmpty() || !name.isEmpty()){
                        Register();
                    }
                    else {
                        edtRegisterPhone.setError( "Vui lòng nhập đầy đủ thông tin" );
                        edtRegisterName.setError( "Vui lòng nhập đầy đủ thông tin" );
                        edtRegisterPass.setError( "Vui lòng nhập đầy đủ thông tin" );
                        getEdtRegisterConfirmPass.setError( "Vui lòng nhập đầy đủ thông tin" );

                    }

            }
        } );

    }

    // hàm xử lý register
    private void Register() {

        RequestQueue queue = Volley.newRequestQueue(getApplication());
        StringRequest stringRequest = new StringRequest( Request.Method.POST, Url_Register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ( jsonObject.getBoolean("status") ){
                        Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent( getApplication(),Login_Activity.class );
                        startActivity( intent );

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
                String Name = edtRegisterName.getText().toString().trim();
                String Phone = edtRegisterPhone.getText().toString().trim();
                String Pass = edtRegisterPass.getText().toString().trim();
                String Gender = "Nũ";
                if (rbtnRegisterMale.isChecked()){
                    Gender = "Nam";
                }
                parrams.put("Name", Name);
                parrams.put("Phone", Phone);
                parrams.put("Pass", Pass);
                parrams.put("Gender", Gender);

                return parrams;
            }
        };
        queue.add(stringRequest);
    }
}