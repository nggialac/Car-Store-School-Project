package com.example.de_tai_di_dong.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.adapter.ItemClickListener;
import com.example.de_tai_di_dong.adapter.SanPhamAdapter;
import com.example.de_tai_di_dong.getData.CallAPI;
import com.example.de_tai_di_dong.getData.GetData_Product;
import com.example.de_tai_di_dong.model.SanPham;
import com.google.android.material.navigation.NavigationView;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    Toolbar toolbar;
    ArrayList<SanPham> arraySanPham;
    SanPhamAdapter adapterSP;
    ListView listsanpham;
    EditText etSearch;
    //navigation
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    int idsp=0;
    int idKH=0;
    NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idKH=getIntent().getIntExtra("idUser",0);
        drawerLayout = findViewById(R.id.drawer_layout);
        mToggle= new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //NavigationView nvDrawer =findViewById(R.id.nv);
        navigationView = (NavigationView)findViewById(R.id.nvmenu);
        navigationView.setNavigationItemSelectedListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.donhang:
                    if(idKH ==0) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        //startActivityForResult(intent, 111);
                    }else {
                        Intent intent = new Intent(MainActivity.this, ThanhToan_HoaDonActivity.class);
                        intent.putExtra("idKH",idKH);
                        startActivity(intent);
                    }
                    break;
                case R.id.banchay:
                    Intent intent = new Intent(MainActivity.this, StatisActivity.class);
                    intent.putExtra("idUser",idKH);
                    startActivity(intent);
                    break;
                default:
                    break;

            }
            return false;
        });
        setControl();
        /*etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              //  adapterSP.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        setEvent();
        requestSanPham();

    }

    private void requestSanPham() {
        GetData_Product service =  CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<SanPham>> call = service.getAllSanPham();
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                showSanPham(response.body());

            }
            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }

        });
    }

    private void setEvent() {

    }

    private void setControl() {
        arraySanPham= new ArrayList<>();
        listsanpham = findViewById(R.id.listSanPham);
        //etSearch=findViewById(R.id.etSearch);
    }

    private void showSanPham(List<SanPham> listSanPham){
        Log.d("listSanPham", listSanPham.toString());
        adapterSP = new SanPhamAdapter(this,(ArrayList<SanPham>) listSanPham,this);
        listsanpham.setAdapter(adapterSP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        //getMenuInflater().inflate(R.menu.draw_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menugiohang) {
            if(idKH == 0) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                intent.putExtra("idKH",idKH);
                startActivity(intent);
            }
        }
        if (item.getItemId() == R.id.account) {
            if(idKH ==0) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                //startActivityForResult(intent, 111);
            }else {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("idUser",idKH);
                startActivity(intent);
            }
        }
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //sự kiện click vào item
    @Override
    public void ClickItem(int idSP) {
        idsp= idSP;
        Intent intent =new Intent(MainActivity.this, SanPhamActivity.class);
        intent.putExtra("idSP",idsp);
        intent.putExtra("idKH",idKH);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if(resultCode== Activity.RESULT_OK && requestCode ==111){
            idKH=getIntent().getIntExtra("idUser",0);
        }*/
    }
}
