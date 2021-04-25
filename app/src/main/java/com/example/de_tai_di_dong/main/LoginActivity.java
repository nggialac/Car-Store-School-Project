package com.example.de_tai_di_dong.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.getData.CallAPI;
import com.example.de_tai_di_dong.getData.GetDataUser;
import com.example.de_tai_di_dong.model.ResultLogin;
import com.example.de_tai_di_dong.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etTenDN, etPass;
    Button btnDN, btnT;
    int idUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        setEvent();

    }

    private void setEvent() {
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = etTenDN.getText().toString();
                String pass = etPass.getText().toString();
                // bắt ngoại lệ chưa nhập thông tin
                if (ten.length() == 0) {
                    etTenDN.setError("Chưa nhập tên tài khoản");
                    etTenDN.requestFocus();
                    return;
                }
                if (pass.length() == 0) {
                    etPass.setError("Chưa nhập mật khẩu");
                    etPass.requestFocus();
                    return;
                }
                //Toast.makeText(getApplicationContext(), "OK vao dang nhap", Toast.LENGTH_LONG).show();

                GetDataUser service = CallAPI.getRetrofitInstance().create(GetDataUser.class);
                Call<ResultLogin> call = service.getLogin(ten, pass);
                call.enqueue(new Callback<ResultLogin>() {
                    @Override
                    public void onResponse(Call<ResultLogin> call, Response<ResultLogin> response) {
                        assert response.body() != null;
                        idUser = response.body().getResult();
                        //GetDataUser service =  CallAPI.getRetrofitInstance().create(GetDataUser.class);

                        if (response.body().getResult() != -1) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("idUser", idUser);
                            startActivity(intent);
                            // setResult(Activity.RESULT_OK,intent);
                            finish();

                        } else {
                            etTenDN.requestFocus();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultLogin> call, Throwable t) {

                    }
                });
            }

        });

//        btnDK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogLogin();
//            }
//        });
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //intent.putExtra("idUser",idUser);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        etTenDN = findViewById(R.id.txtTenDN);
        etPass = findViewById(R.id.txtPass);
        btnDN = findViewById(R.id.btnDN);
        //btnDK = findViewById(R.id.btnDK);
        //btnThoat = findViewById(R.id.btnThoat);
        btnT = findViewById(R.id.btnT);
    }
}
