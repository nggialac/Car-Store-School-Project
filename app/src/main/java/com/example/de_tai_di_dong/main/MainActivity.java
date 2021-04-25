package com.example.de_tai_di_dong.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
    ArrayList<SanPham> origin;
    SanPhamAdapter adapterSP;
    ListView listsanpham;
    EditText etSearch;
    //navigation
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private SwipeRefreshLayout swipeRefreshLayout;
    int idsp = 0;
    int idKH = 0;
    NavigationView navigationView;
    Spinner spinner;
    String[] spinnerString;
    boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idKH = getIntent().getIntExtra("idUser", 0);
        drawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //SPINNER
        spinner = findViewById(R.id.spinnerMain);
        spinnerString = new String[]{"all", "honda", "yamaha"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerString);
        spinner.setAdapter(adapter);

        //SWIPE
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestSanPham();
                if(update){
                    //swipeRefreshLayout.setRefreshing(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 1750);
                    update = false;
                }
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //NavigationView nvDrawer =findViewById(R.id.nv);
        navigationView = (NavigationView) findViewById(R.id.nvmenu);
        navigationView.setNavigationItemSelectedListener((menuItem) -> {
            switch (menuItem.getItemId()) {
                case R.id.donhang:
                    if (idKH == 0) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        //startActivityForResult(intent, 111);
                    } else {
                        Intent intent = new Intent(MainActivity.this, ThanhToan_HoaDonActivity.class);
                        intent.putExtra("idKH", idKH);
                        startActivity(intent);
                    }
                    break;
                case R.id.banchay:
                    Intent intent = new Intent(MainActivity.this, StatisActivity.class);
                    intent.putExtra("idUser", idKH);
                    startActivity(intent);
                    break;
                case R.id.quanLy:
                    if (idKH == 0) {
                        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent2);
                        //startActivityForResult(intent, 111);
                    } else {
                        Intent intent1 = new Intent(MainActivity.this, QLSPActivity.class);
                        intent1.putExtra("idUser", idKH);
                        startActivity(intent1);

                    }
                    break;
                case R.id.themsanpham :
                    if (idKH == 0) {
                        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent2);
                        //startActivityForResult(intent, 111);
                    } else {
                        Intent intent2 = new Intent(MainActivity.this, ActivityThemSanPham.class);
                        startActivity(intent2);

                    }
                    break;
                default:
                    break;

            }
            return false;
        });
        setControl();
        requestSanPham();
        origin = new ArrayList<>();
        setEvent();

    }

    private void requestSanPham() {
        GetData_Product service = CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<SanPham>> call = service.getAllSanPham();
        call.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                Log.d("arrmovie", response.toString());
                //show san pham
                origin = response.body();
                showSanPham(response.body());
                setEvent();
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Log.d("arrmovie", t.toString());
            }


        });
        update = true;
    }

    private void setEvent() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //Object item = parent.getItemAtPosition(pos);
                if (pos == 0) {
                    showSanPham(origin);
                }
                if (pos == 1) {
                    ArrayList<SanPham> sp = new ArrayList<SanPham>();
                    for (SanPham spp : origin) {
                        if (spp.getProGroupId() == 1) {
                            sp.add(spp);
                        }
                    }
                    showSanPham(sp);
                }
                if (pos == 2) {
                    ArrayList<SanPham> sp = new ArrayList<SanPham>();
                    for (SanPham spp : origin) {
                        if (spp.getProGroupId() == 2) {
                            sp.add(spp);
                        }
                    }
                    showSanPham(sp);
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setControl() {
        arraySanPham = new ArrayList<>();
        listsanpham = findViewById(R.id.listSanPham);
        //etSearch=findViewById(R.id.etSearch);
    }

    private void showSanPham(List<SanPham> listSanPham) {
        Log.d("listSanPham", listSanPham.toString());
        adapterSP = new SanPhamAdapter(this, (ArrayList<SanPham>) listSanPham, this);
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
            if (idKH == 0) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                intent.putExtra("idKH", idKH);
                startActivity(intent);
            }
        }
        if (item.getItemId() == R.id.account) {
            if (idKH == 0) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                //startActivityForResult(intent, 111);
            } else {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("idUser", idKH);
                startActivity(intent);
            }
        }
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //sự kiện click vào item
    @Override
    public void ClickItem(int idSP) {
        idsp = idSP;
        Intent intent = new Intent(MainActivity.this, SanPhamActivity.class);
        intent.putExtra("idSP", idsp);
        intent.putExtra("idKH", idKH);
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
