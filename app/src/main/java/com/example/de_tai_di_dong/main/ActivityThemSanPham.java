package com.example.de_tai_di_dong.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.getData.CallAPI;
import com.example.de_tai_di_dong.getData.GetData_Product;
import com.example.de_tai_di_dong.model.Product;
import com.example.de_tai_di_dong.model.ResultLogin;
import com.example.de_tai_di_dong.model.SanPham;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityThemSanPham extends AppCompatActivity {
    Spinner proGroup;
    EditText editTextName , editTextStock , editTextNote ,editTextPrice ;
    Button btnInsert , btnBack, btnChoseImage;
    ImageView imgView;
    Uri imageData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        setControl();
        setEvent();
    }
    private void setEvent() {
        Product sp = new Product();
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



        btnChoseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
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
                sp.setPrice(Price);
                sp.setStock(Stock);
                sp.setDescription(decs);
                GetData_Product service = CallAPI.getRetrofitInstance().create(GetData_Product.class);
                Call<ResultLogin> call = service.addProduct(sp);
                call.enqueue(new Callback<ResultLogin>() {
                    @Override
                    public void onResponse(Call<ResultLogin> call, Response<ResultLogin> response) {
                        if(response.body().getResult() == 1){
                            AlertDialog.Builder b = new AlertDialog.Builder(ActivityThemSanPham.this);
                            b.setTitle("Xác nhận");
                            b.setMessage("Thêm sản phẩm thành công !!");
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
        btnChoseImage = (Button) findViewById(R.id.buttonAnh);
        imgView =  (ImageView) findViewById(R.id.imageShow);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode ==  RESULT_OK  && data !=null){
             imageData =  data.getData();
            imgView.setImageURI(imageData);
        }
    }
}