package com.example.proyectov1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador_Carrito extends BaseAdapter {

    private Context context;
    private ArrayList<Venta_Online> ventaOnlineArrayList;

    public Adaptador_Carrito(Context context, ArrayList<Venta_Online> ventaOnlineArrayList) {
        this.context = context;
        this.ventaOnlineArrayList = ventaOnlineArrayList;
    }

    @Override
    public int getCount() {
        return ventaOnlineArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Venta_Online item = ventaOnlineArrayList.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.view_producto, null);

        TextView txtTituloProducto = view.findViewById(R.id.txtTituloProducto);
        TextView txtProducto = view.findViewById(R.id.txtProducto);
        TextView txtTotalItems = view.findViewById(R.id.txtTotalItems);

        txtTituloProducto.setText(item.getNombre_producto());
        txtProducto.setText("" + item.getId_producto());
        txtTotalItems.setText("$ " + item.getCosto());

        return view;
    }
}
