package com.example.proyectov1.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectov1.R;

import java.util.ArrayList;

public class Adaptador_Carrito extends BaseAdapter {

    private Context context;
    private ArrayList<Carrito> ventaOnlineArrayList;

    public Adaptador_Carrito(Context context, ArrayList<Carrito> ventaOnlineArrayList) {
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
        Carrito item = ventaOnlineArrayList.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.items_lista_carrito, null);

        ImageView img_previwq = view.findViewById(R.id.img_previwq);
        TextView tv_titulo_carrito = view.findViewById(R.id.tv_titulo_carrito);
        TextView tv_cantidad_carrito= view.findViewById(R.id.tv_cantidad_carrito);
        TextView tv_precio_carrito = view.findViewById(R.id.tv_precio_carrito);

        img_previwq.setImageResource(R.drawable.shopping);
        tv_titulo_carrito.setText(item.getNombre_producto());
        tv_cantidad_carrito.setText("" + item.getCantidad());
        tv_precio_carrito.setText("$ " + item.getCosto());

        return view;
    }
}
