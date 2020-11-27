package com.sict.shopdt.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sict.shopdt.Activity.ChitietSp;
import com.sict.shopdt.Activity.ListProduct_Activity;
import com.sict.shopdt.Api;
import com.sict.shopdt.R;
import com.sict.shopdt.Trangchu.Adapter;
import com.sict.shopdt.Trangchu.Sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangchuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangchuFragment extends Fragment {
    Context context;
    RecyclerView recyclerView_dt;
    RecyclerView recyclerView_mt;
    RecyclerView recyclerView_lk;
    View view;
    ViewFlipper viewFlipper_dt;
    ViewFlipper viewFlipper_mt;
    ViewFlipper viewFlipper_lk;
    TextView tvSelectAll1;
    TextView tvSelectAll2;
    TextView tvSelectAll3;
    com.sict.shopdt.Trangchu.Adapter adapter;
    ArrayList<Sanpham> sanphams_dt = new ArrayList<>( );
    ArrayList<Sanpham> sanphams_mt = new ArrayList<>( );
    ArrayList<Sanpham> sanphams_lk = new ArrayList<>( );
    private static String Json_urldt = Api.host + "api/trangchu_dienthoai";
    private static String Json_urlmt = Api.host + "api/trangchu_maytinh";
    private static String Json_urllk = Api.host + "api/trangchu_linkkien";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrangchuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangchuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrangchuFragment newInstance(String param1, String param2) {

        TrangchuFragment fragment = new TrangchuFragment();
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
        view = inflater.inflate( R.layout.fragment_trangchu, container, false );
        //điện thoại
        recyclerView_dt = view.findViewById( R.id.recycleview_trangchudt );
        viewFlipper_dt = view.findViewById(R.id.viewlipper_dt);
        ActionViewFlipper_dt();
        laydulieu_dt();
        //máy tính
        recyclerView_mt = view.findViewById( R.id.recycleview_trangchumt );
        viewFlipper_mt = view.findViewById(R.id.viewlipper_mt);
        ActionViewFlipper_mt();
        laydulieu_mt();
        //linh kiện
        recyclerView_lk = view.findViewById( R.id.recycleview_trangchulk );
        viewFlipper_lk = view.findViewById(R.id.viewlipper_lk);
        ActionViewFlipper_lk();
        laydulieu_lk();


        tvSelectAll1 = view.findViewById( R.id.tvSelectAll1 );
        tvSelectAll2 = view.findViewById( R.id.tvSelectAll2 );
        tvSelectAll3 = view.findViewById( R.id.tvSelectAll3 );


        tvSelectAll1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( view.getContext(), ListProduct_Activity.class );
                intent.putExtra("ID_Loaihang", 1);
                view.getContext().startActivity(intent);
            }
        } );
        tvSelectAll2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( view.getContext(), ListProduct_Activity.class );
                intent.putExtra("ID_Loaihang", 2);
                view.getContext().startActivity(intent);
            }
        } );
        tvSelectAll3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( view.getContext(), ListProduct_Activity.class );
                intent.putExtra("ID_Loaihang", 3);
                view.getContext().startActivity(intent);
            }
        } );
        // Inflate the layout for this fragment
        return view;
    }


// ĐIỆN THOẠI

    //lay du lieu api
    private void laydulieu_dt() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, Json_urldt, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject spObject = response.getJSONObject( i );
                        Sanpham sanpham_dt = new Sanpham();
                        sanpham_dt.setID_Sanpham( spObject.getInt( "ID_Sanpham" ) );
                        sanpham_dt.setAnhsp( spObject.getString( "Image" ).toString() );
                        sanpham_dt.setTensp( spObject.getString( "Namesp" ).toString() );
                        int giasp = spObject.getInt( "Gia" );
                        sanpham_dt.setGiasp(giasp);

                        sanphams_dt.add( sanpham_dt );


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //xét layout cho các item
                recyclerView_dt.setHasFixedSize( true );
                recyclerView_dt.setLayoutManager( new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false) );
                adapter = new Adapter( getActivity(), sanphams_dt );
                recyclerView_dt.setAdapter( (RecyclerView.Adapter) adapter );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "tag","onErrorResponse"+error.getMessage() );
            }
        } );
        queue.add( jsonArrayRequest );
    }


    //slide quang cao
    private void ActionViewFlipper_dt() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add( "https://i2.wp.com/www.techtimes.vn/wp-content/uploads/2019/02/Galaxy-s10e-techtimes.jpg" );
        mangquangcao.add( "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSJa_en7hEctFtTNGzezwiLOXQP6JiyJ09tXw&usqp=CAU" );
        mangquangcao.add( "https://livestream.vn/wp-content/uploads/2018/10/c2-kinh-doanh-dien-thoai-va-phu-kien-2.jpg" );
        mangquangcao.add( "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSsEyMtOtFuotRZ1Ra0Qn89py7_ujHGPhWq2Q&usqp=CAU" );
        for (int i=0; i<mangquangcao.size(); i++){
            ImageView imageview = new ImageView(getActivity());
            Glide.with( this )
                    .load( mangquangcao.get( i ) )
                    .apply( new RequestOptions()
                    .transform( new CenterCrop(), new RoundedCorners( 16 ) ) )
                    .into( imageview );
            imageview.setScaleType( ImageView.ScaleType.FIT_XY );
            viewFlipper_dt.addView(imageview);
        }
        viewFlipper_dt.setFlipInterval(5000);
        viewFlipper_dt.setAutoStart(true);
    }

//MÁY TÍNH
    //lấy dữ liệu máy tính
    private void laydulieu_mt() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, Json_urlmt, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d( "TAG", "onResponse: " + response.toString() );
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject spObject = response.getJSONObject( i );
                        Sanpham sanpham_mt = new Sanpham();
                        sanpham_mt.setID_Sanpham( spObject.getInt( "ID_Sanpham" ) );
                        sanpham_mt.setAnhsp( spObject.getString( "Image" ).toString() );
                        sanpham_mt.setTensp( spObject.getString( "Namesp" ).toString() );
                        int giasp = spObject.getInt( "Gia" );
                        sanpham_mt.setGiasp(giasp);

                        sanphams_mt.add( sanpham_mt );


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //xét layout cho các item
                recyclerView_mt.setHasFixedSize( true );
                recyclerView_mt.setLayoutManager( new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false) );
                adapter = new Adapter( getActivity(), sanphams_mt );
                recyclerView_mt.setAdapter( (RecyclerView.Adapter) adapter );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "tag","onErrorResponse"+error.getMessage() );
            }
        } );
        queue.add( jsonArrayRequest );
    }


    //slide quang cao máy tính
    private void ActionViewFlipper_mt() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add( "https://znews-photo.zadn.vn/w660/Uploaded/fsmyy/2018_05_15/ea3ec008effd01a358ec.jpg" );
        mangquangcao.add( "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRyKFb3kwIbOfz0YyF5zus87giqO8CpQHC5Tg&usqp=CAU" );
        mangquangcao.add( "https://pic.pikbest.com/00/00/97/61s888piCri7.jpg-1.jpg!bw700" );
        mangquangcao.add( "https://sa.tinhte.vn/2016/05/3704044_image001.jpg" );
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageview = new ImageView( getActivity() );
            Glide.with( this )
                    .load( mangquangcao.get( i ) )
                    .apply( new RequestOptions()
                            .transform( new CenterCrop(), new RoundedCorners( 16 ) ) )
                    .into( imageview );
            imageview.setScaleType( ImageView.ScaleType.FIT_XY );
            viewFlipper_mt.addView( imageview );
        }
        viewFlipper_mt.setFlipInterval( 5000 );
        viewFlipper_mt.setAutoStart( true );
    }


//LINH KIỆN

    private void laydulieu_lk() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, Json_urllk, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject spObject = response.getJSONObject( i );
                        Sanpham sanpham_lk = new Sanpham();
                        sanpham_lk.setID_Sanpham( spObject.getInt( "ID_Sanpham" ) );
                        sanpham_lk.setAnhsp( spObject.getString( "Image" ).toString() );
                        sanpham_lk.setTensp( spObject.getString( "Namesp" ).toString() );
                        int giasp = spObject.getInt( "Gia" );
                        sanpham_lk.setGiasp(giasp);
                        sanphams_lk.add( sanpham_lk );


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //xét layout cho các item
                recyclerView_lk.setHasFixedSize( true );
                recyclerView_lk.setLayoutManager( new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false) );
                adapter = new Adapter( getActivity(), sanphams_lk );
                recyclerView_lk.setAdapter( (RecyclerView.Adapter) adapter );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "tag","onErrorResponse"+error.getMessage() );
            }
        } );
        queue.add( jsonArrayRequest );
    }


    //slide quang cao
    private void ActionViewFlipper_lk() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add( "https://livestream.vn/wp-content/uploads/2018/10/f2-kinh-doanh-dien-thoai-va-phu-kien-2-1.jpg" );
        mangquangcao.add( "https://file.hstatic.net/1000266242/collection/59267369_413705799419604_8867213200978870272_n_52f8722ef78f4903a3efe5272bf5d69c_master.jpg" );
        mangquangcao.add( "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSiW2XPq_zzZCjeSQjLvgnCR_R-kZHl_ykNzw&usqp=CAU" );
        mangquangcao.add( "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSeZiskU30Q9CaU7hbbDHVJd63n-sR2Ezb7ig&usqp=CAU" );
        for (int i=0; i<mangquangcao.size(); i++){
            ImageView imageview = new ImageView(getActivity());
            Glide.with( this )
                    .load( mangquangcao.get( i ) )
                    .apply( new RequestOptions()
                            .transform( new CenterCrop(), new RoundedCorners( 16 ) ) )
                    .into( imageview );
            imageview.setScaleType( ImageView.ScaleType.FIT_XY );
            viewFlipper_lk.addView(imageview);
        }
        viewFlipper_lk.setFlipInterval(5000);
        viewFlipper_lk.setAutoStart(true);
    }
}
