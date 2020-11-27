package com.sict.shopdt.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sict.shopdt.Activity.Aboutme;
import com.sict.shopdt.Activity.Login_Activity;
import com.sict.shopdt.Api;
import com.sict.shopdt.MainActivity;
import com.sict.shopdt.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CanhanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CanhanFragment extends Fragment {
    View view;
    private static String Url_Canhan = Api.host + "api/canhan";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CanhanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CanhanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CanhanFragment newInstance(String param1, String param2) {
        CanhanFragment fragment = new CanhanFragment();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    LinearLayout lnlNoLogin, lnlHaveLogin,lnlAboutme1;
    LinearLayout lnlAboutme2, lnlInformationofMe,lnlBillofMe,lnlCommentofMe;
    LinearLayout lnlLoginLogout;
    AppCompatButton btnLogout;
    ImageView imgHaveLogin;
    TextView tvNameUser, tvPhoneUser, tvGenderUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_canhan, container, false );

        lnlHaveLogin = (LinearLayout) view.findViewById( R.id.lnlHaveLogin );
        lnlNoLogin = (LinearLayout) view.findViewById( R.id.lnlNoLogin );
        lnlAboutme1 = (LinearLayout) view.findViewById( R.id.lnlAboutme1 );
        lnlAboutme2 = (LinearLayout) view.findViewById( R.id.lnlAboutme2 );
        lnlLoginLogout = (LinearLayout) view.findViewById( R.id.lnlLoginLogout );
        lnlBillofMe = (LinearLayout) view.findViewById( R.id.lnlBillofMe );
        lnlCommentofMe = (LinearLayout) view.findViewById( R.id.lnlCommentofMe );
        lnlInformationofMe = (LinearLayout) view.findViewById( R.id.lnlInformationofMe );
        btnLogout = (AppCompatButton) view.findViewById( R.id.btnLogout );
        imgHaveLogin = (ImageView) view.findViewById( R.id.imgHaveLogin );
        tvNameUser = (TextView) view.findViewById( R.id.tvNameUser );
        tvPhoneUser = (TextView) view.findViewById( R.id.tvPhoneUser );
        tvGenderUser = (TextView) view.findViewById( R.id.tvGenderUser );



        MainActivity activity = (MainActivity) getActivity();
        int ID_User = activity.getMyData();
        Log.d( "dsgfgds", "onCreateView: ban dau "+activity.getMyData() );


        //da dang nhap
        if(ID_User >0 ){
            lnlNoLogin.setVisibility( view.GONE );
            Laydulieu(ID_User);
            btnLogout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            } );

            lnlInformationofMe.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            } );
            lnlBillofMe.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            } );
            lnlCommentofMe.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            } );

            lnlAboutme2.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getActivity(), Aboutme.class );
                    startActivity( intent );
                }
            } );
        }
        //chua dang nhap account
        else {
            lnlHaveLogin.setVisibility( view.GONE );
            lnlLoginLogout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(), Login_Activity.class );
                    startActivity( intent );
                }
            } );


            //about me
            lnlAboutme1.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getActivity(), Aboutme.class );
                    startActivity( intent );
                }
            } );
        }

        return view;
    }

    public  void Laydulieu(int ID_User){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest( Request.Method.POST, Url_Canhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ( jsonObject.getBoolean("status") ){
                        JSONObject object = jsonObject.getJSONObject("data");
                        String Name = object.getString( "Name" );
                        String Phone = object.getString( "Phone" );
                        String Gender = object.getString( "Gender" );

                        tvNameUser.setText( Name );
                        tvPhoneUser.setText( Phone );
                        tvGenderUser.setText( Gender );
                    }else {
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi " + error, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();

                parrams.put("ID_User", String.valueOf( ID_User ) );
                return parrams;
            }
        };
        queue.add(stringRequest);
    }
}
