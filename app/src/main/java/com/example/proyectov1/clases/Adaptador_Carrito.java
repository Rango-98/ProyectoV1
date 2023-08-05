package com.example.proyectov1.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proyectov1.R;
import com.example.proyectov1.clases.Venta_Online;

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

        TextView tv_titulo_carrito = view.findViewById(R.id.tv_titulo_carrito);
        TextView tv_cantidad_carrito= view.findViewById(R.id.tv_cantidad_carrito);
        TextView tv_precio_carrito = view.findViewById(R.id.tv_precio_carrito);

        tv_titulo_carrito.setText(item.getNombre_producto());
        tv_cantidad_carrito.setText("" + item.getId_producto());
        tv_precio_carrito.setText("$ " + item.getCosto());

        return view;
    }
}
