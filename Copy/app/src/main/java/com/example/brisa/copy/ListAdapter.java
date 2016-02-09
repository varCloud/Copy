package com.example.brisa.copy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChristianPR on 06/02/2016.
 */
public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<ItemCopia> items;

    public ListAdapter(Context context, List<ItemCopia> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.list_item,null);
        }

        ImageView ivItem = (ImageView) rowView.findViewById(R.id.ivItem);
        TextView lblTipoCopia = (TextView) rowView.findViewById(R.id.lblTipoCopia);
        TextView lblCostoCopia = (TextView) rowView.findViewById(R.id.lblCostoCopia);
        TextView lblCantidadCopias = (TextView) rowView.findViewById(R.id.lblCantidadCopias);

        ItemCopia item = this.items.get(position);
        lblTipoCopia.setText(item.getTipoCopia());
        ivItem.setImageResource(item.getImagen());
        lblCostoCopia.setText(item.getCosto());
        lblCantidadCopias.setText("Cantidad: "+item.getCantidadCopias());

        return rowView;
    }
}
