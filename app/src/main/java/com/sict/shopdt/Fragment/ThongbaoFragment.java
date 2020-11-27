package com.sict.shopdt.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sict.shopdt.Api;
import com.sict.shopdt.ListNotificationAdapter.Adapter;
import com.sict.shopdt.ListNotificationAdapter.Notification;
import com.sict.shopdt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongbaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongbaoFragment extends Fragment {
    Context context;
    RecyclerView recyclerView_listnoti;
    View view;
    ArrayList<Notification> notifications = new ArrayList<>( );
    com.sict.shopdt.ListNotificationAdapter.Adapter adapter;
    private static String Json_urllistnoti = Api.host + "api/listnoti";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongbaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongbaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongbaoFragment newInstance(String param1, String param2) {
        ThongbaoFragment fragment = new ThongbaoFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_thongbao, container, false );
        recyclerView_listnoti = (RecyclerView) view.findViewById( R.id.recycleview_listnotification );
        laydulieu();
        // Inflate the layout for this fragment
        return view;
    }

    private void laydulieu() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, Json_urllistnoti, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject spObject = response.getJSONObject( i );
                        Notification notification = new Notification();
                        notification.setTitle( spObject.getString( "Tieude" ).toString() );
                        notification.setContext( spObject.getString( "Noidung" ).toString() );
                        notification.setDate( spObject.getString( "created_at" ).toString() );

                        notifications.add( notification );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //xét layout cho các item
                recyclerView_listnoti.setHasFixedSize( true );
                recyclerView_listnoti.setLayoutManager(new LinearLayoutManager(context));
                adapter = new Adapter( getActivity(), notifications );
                recyclerView_listnoti.setAdapter( (RecyclerView.Adapter) adapter );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "tag","onErrorResponse"+error.getMessage() );
            }
        } );
        queue.add( jsonArrayRequest );
    }
}
