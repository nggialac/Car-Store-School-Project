package com.example.de_tai_di_dong.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.getData.CallAPI;
import com.example.de_tai_di_dong.getData.GetData_Product;
import com.example.de_tai_di_dong.model.Product;
import com.example.de_tai_di_dong.model.ResultLogin;
import com.example.de_tai_di_dong.model.SanPham;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySuasanpham extends AppCompatActivity {

    int id;
    Spinner proGroup;
    EditText editTextName , editTextStock , editTextNote ,editTextPrice ;
    Button btnInsert , btnBack;
    String img="";
    ImageView imgView;
    Button add;
    //Product sp = new Product();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        setControl();
        id = getIntent().getIntExtra("idSP", 0);
        setEvent();
        RequestProduct(id);
    }
    private void RequestProduct(int id){
        final Product[] sp = new Product[1];
        GetData_Product service = CallAPI.getRetrofitInstance().create(GetData_Product.class);
        Call<ArrayList<Product>> call = service.getProductNew(id);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                sp[0] = response.body().get(0);
                String info = "Tên sản phẩm :      \t" + response.body().get(0).getName() + "\nCòn Lại :      \t" + response.body().get(0).getStock();
                info += "\nGiá :      \t" + response.body().get(0).getPrice();
                editTextName.setText( response.body().get(0).getName());
                editTextNote.setText(response.body().get(0).getDescription());
                editTextPrice.setText(String.valueOf(response.body().get(0).getPrice()));
                editTextStock.setText(String.valueOf(response.body().get(0).getStock()));
                proGroup.setSelection(response.body().get(0).getProGroupId() - 1);
                img = response.body().get(0).getImage1();
                Glide.with(ActivitySuasanpham.this).load(response.body().get(0).getImage1()).into(imgView);
//                tvInfoProduct.setText(info);
//                Glide.with(SanPhamActivity.this).load(response.body().get(0).getImage1()).into(img_sp);
//                if (response.body().get(0).getStock() == 0) {
//                    btnChonMua.setEnabled(false);
//                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }
    private void setEvent() {
        Product sp = new Product();
        sp.setId(id);

        String[] items = new String[] { "Honda", "Yamaha"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        proGroup.setAdapter(adapter);
        proGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int k =  position;
                Log.d("postion" , position + "" );
                sp.setProGroupId(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add.setEnabled(false);




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                int Stock = 0;
                String decs = editTextNote.getText().toString();
                int Price= 0;
                if(name.length() == 0){
                    editTextName.setError("Chưa nhập tên sản phảm ");
                    editTextName.requestFocus();
                    return;
                }
                if(editTextStock.getText().toString().length() == 0){
                    editTextStock.setError("Chua nhập số lượng sản phẩm");
                    editTextStock.requestFocus();
                    return;
                }
                else
                {   Stock = Integer.parseInt(editTextStock.getText().toString());
                    if(Stock < 0){
                        editTextStock.setError("Số lượng sản phẩm phải lớn hơn 0");
                        editTextStock.requestFocus();
                        return;
                    }
                }

                if(decs.length() == 0 ){
                    editTextNote.setError("Chưa nhập mô tả sản phẩm");
                    editTextNote.requestFocus();
                    return;
                }
                if(editTextPrice.getText().toString().length() == 0){
                    editTextPrice.setError("Chưa nhập giá sản phẩm");
                    editTextPrice.requestFocus();
                    return;
                }
                else
                {
                    Price = Integer.parseInt(editTextPrice.getText().toString());
                    if(Stock < 0){
                        editTextPrice.setError("Gía sản phẩm phải lớn hơn 0");
                        editTextPrice.requestFocus();
                        return;
                    }
                }

                sp.setName(name);
                sp.setStock(Stock);
                sp.setPrice(Price);
                sp.setDescription(decs);
                sp.setImage1(img);
                GetData_Product service = CallAPI.getRetrofitInstance().create(GetData_Product.class);
                Call<ResultLogin> call = service.putProduct(sp);
                call.enqueue(new Callback<ResultLogin>() {
                    @Override
                    public void onResponse(Call<ResultLogin> call, Response<ResultLogin> response) {
                        if(response.body().getResult() == 1){
                            AlertDialog.Builder b = new AlertDialog.Builder(ActivitySuasanpham.this);
                            b.setTitle("Xác nhận");
                            b.setMessage("Sửa sản phẩm thành công !!");
                            b.setPositiveButton("Ðóng", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            AlertDialog al = b.create();
                            al.show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResultLogin> call, Throwable t) {
                        Log.d("Err" , t +"");
                    }
                });
            }
        });

    }

    private void setControl() {
        proGroup  =  (Spinner) findViewById(R.id.spinnerGroup);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextStock = (EditText) findViewById(R.id.editTextStock);
        editTextNote = (EditText) findViewById(R.id.editTextNote);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        btnInsert =(Button) findViewById(R.id.buttonTT);
        btnBack =(Button) findViewById(R.id.buttonThoat);
        imgView = (ImageView) findViewById(R.id.imageShow);
        add = (Button) findViewById(R.id.buttonAnh);
    }

}