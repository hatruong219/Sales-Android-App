package com.sict.shopdt;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;
import com.sict.shopdt.Activity.Cart_Activity;
import com.sict.shopdt.Fragment.CanhanFragment;
import com.sict.shopdt.Fragment.DanhmucFragment;
import com.sict.shopdt.Fragment.ThongbaoFragment;
import com.sict.shopdt.Fragment.TimkiemFragment;
import com.sict.shopdt.Fragment.TrangchuFragment;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private Toolbar toolbar;
    private static int ID_User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();
        toolbar = findViewById( R.id.toolbar );
        toolbar.setOnMenuItemClickListener( this );

        //menu bottom
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        toolbar.setTitle("Shop Teke");
        loadFragment(new TrangchuFragment());

        Intent intent = getIntent();
        ID_User = intent.getIntExtra("ID_User",0);

    }
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_giohang:
                Intent intent = new Intent( getApplicationContext(), Cart_Activity.class );
                startActivity( intent );
                return true;
            case R.id.toolbar_timkiem:
                Toast.makeText(this, "About button selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_Trangchu:
                    toolbar.setTitle("Shop Teke");
                    fragment = new TrangchuFragment();
                    loadFragment( (TrangchuFragment) fragment );
                    return true;
                case R.id.navigation_Timkiem:
                    toolbar.setTitle("Tim kiếm");
                    fragment = new TimkiemFragment();
                    loadFragment( (TimkiemFragment) fragment );
                    return true;
                case R.id.navigation_Thongbao:
                    toolbar.setTitle("Thông báo");
                    fragment = new ThongbaoFragment();
                    loadFragment( (ThongbaoFragment) fragment );
                    return true;
                case R.id.navigation_Canhan:
                    toolbar.setTitle("Cá nhân");
                    fragment = new CanhanFragment();
                    loadFragment( (CanhanFragment) fragment );
                    return true;
                default:
                    showLockTaskEscapeMessage();
            }
            return false;
        }
    };
    public static int getMyData() {
        return ID_User;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
