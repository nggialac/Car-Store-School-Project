package com.example.de_tai_di_dong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.de_tai_di_dong.R;
import com.example.de_tai_di_dong.main.QLSPActivity;
import com.example.de_tai_di_dong.model.SanPham;

import java.util.ArrayList;

public class QLSPAdapter extends BaseAdapter {
    private Context context;
    private ItemClickListener clickItem;
    private ArrayList<SanPham> list_SanPham;
    private  CarItemListener deleteClick;
    public QLSPAdapter(Context context,ArrayList<SanPham> listSanPham,ItemClickListener clickItem,CarItemListener deleteClick) {
        this.context=context;
        this.list_SanPham= listSanPham;
        this.clickItem=clickItem;
        this.deleteClick = deleteClick;
    }

    public QLSPAdapter(QLSPActivity qlspActivity, ArrayList<SanPham> listSanPham, QLSPActivity qlspActivity1) {
    }

    @Override
    public int getCount() {
        if (list_SanPham == null)
            return 0;
        else
            return list_SanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private  class ViewHolder{
        ImageView imgHinh;
        TextView tenHinh;
        TextView gia;
        LinearLayout list;
        TextView sl;
        ImageView image;
    }

    @Override
    public View getView(int position, @Nullable View view, @Nullable ViewGroup parent) {
        QLSPAdapter.ViewHolder holder;
        if( view == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qlsp, parent, false);
            holder = new QLSPAdapter.ViewHolder();
            holder.imgHinh= view.findViewById(R.id.imgSanPham);
            holder.tenHinh=view.findViewById(R.id.tvSanPham);
            holder.gia=view.findViewById(R.id.tvPrice);
            holder.list=view.findViewById(R.id.list);
            holder.sl = view.findViewById(R.id.tvQuanlity);
            holder.image = view.findViewById(R.id.imgDelete);
            view.setTag(holder);
        }else holder = (QLSPAdapter.ViewHolder)view.getTag();

        final SanPham sanPham=list_SanPham.get(position);

        Glide.with(context).load(sanPham.getImage1()).into(holder.imgHinh);
        holder.tenHinh.setText(sanPham.getName());
        holder.gia.setText(sanPham.getPrice()+"");
        holder.sl.setText(sanPham.getQuantity()+"");
        holder.list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem.ClickItem(sanPham.getId());
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick.onDeleteCartItem(sanPham.getId(),position);
            }
        });
        return view;
    }
}
