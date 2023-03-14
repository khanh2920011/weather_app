package com.example.weather;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class weather_adapter extends BaseAdapter {
    Context context;
    int layout;
    List<weather> list;

    public weather_adapter(Context context, int layout, List<weather> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    class ViewHolder {
        TextView txtngay;
        TextView txttrangthai;
        ImageView imgicon;
        TextView txtmax;
        TextView txtmin;

    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
            if(convertView == null){
                viewholder = new ViewHolder();
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(layout, null);
                viewholder.txtngay = convertView.findViewById(R.id.txtngay);
                viewholder.txttrangthai = convertView.findViewById(R.id.txttrangthai);
                viewholder.imgicon = convertView.findViewById(R.id.imgicon);
                viewholder.txtmax = convertView.findViewById(R.id.txtmax);
                viewholder.txtmin = convertView.findViewById(R.id.txtmin);
                convertView.setTag(viewholder);
            }
            else {
                viewholder = (ViewHolder) convertView.getTag();
            }

            weather weather = list.get(i);
            viewholder.txtngay.setText(weather.getNgay());
            viewholder.txttrangthai.setText(weather.getTrang_thai());
            Picasso.get().load(weather.getUrl_icon()).into(viewholder.imgicon);
            viewholder.txtmax.setText(weather.getMax());
            viewholder.txtmin.setText(weather.getMin());
        return convertView;
    }
}
