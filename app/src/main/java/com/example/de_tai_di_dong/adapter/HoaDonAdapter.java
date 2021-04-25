package com.example.de_tai_di_dong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.model.Orders;

import java.util.ArrayList;

public class HoaDonAdapter extends BaseAdapter {
    private ArrayList<Orders> listOrders;
    private Context context;
    private ItemClickListener clickItem;
    private int idKH;
    public HoaDonAdapter( ArrayList<Orders>listOrders, Context context, int idKH,ItemClickListener clickItem){
        this.context=context;
        this.listOrders=listOrders;
        this.idKH=idKH;
        this.clickItem = clickItem;
    }
    @Override
    public int getCount() {
        return listOrders.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        EditText name;
        EditText date;
        EditText phone;
        EditText totalPrice;
        EditText checkDate;
        EditText orderBy;
        ConstraintLayout listHD;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final  ViewHolder holder;
        if( view == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don, parent, false);
            holder = new ViewHolder();
            holder.name= view.findViewById(R.id.nameHD);
            holder.date=view.findViewById(R.id.ngayHD);
            holder.phone=view.findViewById(R.id.phoneHD);
            holder.totalPrice=view.findViewById(R.id.totalPrice);
            holder.checkDate =view.findViewById(R.id.editTextCheckDate);
            //holder.orderBy=view.findViewById(R.id.edtOrderBy);
            holder.listHD=view.findViewById(R.id.listHD);
            view.setTag(holder);
        }else holder=(HoaDonAdapter.ViewHolder)view.getTag();
        final  Orders orders = listOrders.get(position);
        holder.date.setText(orders.getBuyingDay()+"");
        holder.name.setText(orders.getName());
        holder.phone.setText(orders.getPhone());
        holder.checkDate.setText(orders.getCheckDate()+"");
        //holder.orderBy.setText(orders.getUserId() + "");
        holder.totalPrice.setText(orders.getTotalPrice()+"");
        holder.listHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.ClickItem(orders.getId());
            }
        });
        return view;
    }
}
