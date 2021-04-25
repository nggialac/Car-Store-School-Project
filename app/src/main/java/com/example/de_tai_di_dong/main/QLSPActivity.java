package com.example.de_tai_di_dong.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.adapter.CarItemListener;
import com.example.de_tai_di_dong.adapter.ItemClickListener;
import com.example.de_tai_di_dong.adapter.QLSPAdapter;
import com.example.de_tai_di_dong.adapter.StatisAdapter;
import com.example.de_tai_di_dong.getData.CallAPI;
import com.example.de_tai_di_dong.getData.GetData_Product;
import com.example.de_tai_di_dong.model.ResultLogin;
import com.example.de_tai_di_dong.model.SanPham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLSPActivity extends AppCompatActivity implements ItemClickListener, CarItemListener {

    int idsp = 0;
    int idKH = 0;
    ArrayList<SanPham> arraySanPham;
    QLSPAdapter adapterSP;
    ListView listsanpham;

    @Override
    public void ClickItem(int idSP) {
        idsp = idSP;
        Intent intent = new Intent(QLSPActivity.this, ActivitySuasanpham.class);
        intent.putExtra("idSP", idsp);
        intent.putExtra("idKH", idKH);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlsp);
        idKH = getIntent().getIntExtra("idUser", 0);
        setControl();
        setEvent();
        requestSanPham();
    }

    private void setEvent() {

    }

    private void setControl() {
        arraySanPham = new ArrayList<>();
        listsanpham = findViewById(R.id.listSanPham);
    }

    private void showSanPham(List<SanPham> listSanPham) {
        Log.d("listSanPham", listSanPham.toString());
        adapterSP = new QLSPAdapter(this, (ArrayList<SanPham>) listSanPham, this, this);
        listsanpham.setAdapter(adapterSP);
    }

    private void requestSanPham() {
        GetData_Product service = CallAPI.getRetrofitInstance().create(GetData_Product.class);
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

    @Override
    public void onDeleteCartItem(int idKH, int position) {
        GetData_Product service = CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ResultLogin> call = service.deleteProduct(idKH);
        call.enqueue(new Callback<ResultLogin>() {
            @Override
            public void onResponse(Call<ResultLogin> call, Response<ResultLogin> response) {
                if (response.body().getResult() == 1) {
                    Toast.makeText(QLSPActivity.this, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                    requestSanPham();
                } else {
                    Toast.makeText(QLSPActivity.this, "Không xóa được!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResultLogin> call, Throwable t) {

            }
        });

    }
}
