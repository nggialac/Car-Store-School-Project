package com.example.de_tai_di_dong.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.getData.CallAPI;
import com.example.de_tai_di_dong.getData.GetDataUser;
import com.example.de_tai_di_dong.getData.GetData_Product;
import com.example.de_tai_di_dong.model.ResultLogin;
import com.example.de_tai_di_dong.model.SanPham;
import com.example.de_tai_di_dong.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
        int idKH=0;
        TextView tvInfoUser;
        Button btnChangeInfo , btnSingout, buttonDoiMK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        idKH=getIntent().getIntExtra("idUser",idKH);
        setControl();
        setEvent();
    }

    private void setEvent() {
        GetDataUser service =  CallAPI.getRetrofitInstance().create(GetDataUser.class);
        Call<ArrayList<User>> call = service.getInfoUser(idKH);
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                assert response.body() != null;
                String info="Name:\t\t\t\t"+ response.body().get(0).getName()+"\nEmail:\t\t\t\t"+response.body().get(0).getEmail()+"\nPhone:\t\t\t\t"+response.body().get(0).getPhone()+"\nUserName:\t\t\t\t"+response.body().get(0).getUserName()+"\nAddress:\t\t\t\t"+response.body().get(0).getAddress()+"\n";
                tvInfoUser.setText(info);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
        btnSingout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(UserActivity.this,MainActivity.class);
                intent.putExtra("idKH",0);
                Toast.makeText(UserActivity.this,"Bạn đăng xuất thành công !!!",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
//        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(UserActivity.this,ChangeInfo.class);
//                intent.putExtra("idKH",idKH);
//                 startActivity(intent);
//            }
//        });
//        buttonDoiMK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogChangPass();
//            }
//        });

    }
//    private void DialogChangPass(){
//        final Dialog dialog =new Dialog(this);
//        dialog.setContentView(R.layout.dialog_change_pass);
//        dialog.setTitle("Thay đổi mật khẩu ");
//        dialog.setCanceledOnTouchOutside(false);
//        // ánh xạ
//        final EditText passcu= dialog.findViewById(R.id.editpassCu);
//        final EditText passmoi=dialog.findViewById(R.id.editpassMoi);
//        final EditText passconfirm= dialog.findViewById(R.id.editpassConfirm);
//        final  Button btnTD = dialog.findViewById(R.id.buttonPass);
//        final  Button  btnT= dialog.findViewById(R.id.button_btnT);
//        btnTD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String pass_cu=passcu.getText().toString();
//                String pass_moi=passmoi.getText().toString();
//                String pass_conf=passconfirm.getText().toString();
//                if(pass_cu.length() == 0){
//                    passcu.setError("Chưa nhập mật khẩu cũ");
//                    passcu.requestFocus();
//                    return;
//                }
//                if(pass_moi.length() == 0){
//                    passmoi.setError("Chưa nhập mật khẩu mới");
//                    passmoi.requestFocus();
//                    return;
//                }
//                if(pass_conf.length() == 0){
//                    passconfirm.setError("Chưa nhập mật khẩu cũ");
//                    passconfirm.requestFocus();
//                    return;
//                }
//                if(!pass_moi.equals(pass_conf)){
//                    Toast.makeText(UserActivity.this,"Mật khẩu mới trùng với mật khẩu nhập lại!!",Toast.LENGTH_LONG).show();
//                    return;
//                }
//                GetDataUser service =CallAPI.getRetrofitInstance().create(GetDataUser.class);
//                Call<ArrayList<ResultLogin>> call = service.getChangePass(idKH,pass_cu,pass_moi);
//                call.enqueue(new Callback<ArrayList<ResultLogin>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<ResultLogin>> call, Response<ArrayList<ResultLogin>> response) {
//                        assert response.body() != null;
//                        int res=response.body().get(0).getResult();
//                        if(res == 1)
//                            Toast.makeText(UserActivity.this,"Thay đổi thành công!!",Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(UserActivity.this,"Thay đổi thất bại vì mật khẩu cũ không chính xác!!",Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<ResultLogin>> call, Throwable t) {
//
//                    }
//                });
//            }
//        });
//        btnT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }

    private void setControl() {
        tvInfoUser =findViewById(R.id.tvInfoUser);
        btnChangeInfo=findViewById(R.id.btnChangeInfo);
        btnSingout= findViewById(R.id.btnSingout);
        buttonDoiMK= findViewById(R.id.buttonDoiMK);

    }
}
