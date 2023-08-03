package com.example.proyectov1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador_producto extends BaseAdapter {

    private ArrayList<Producto> arreglo_producto;
    private Context context;

    public Adaptador_producto(Context context, ArrayList<Producto> arreglo_producto) {
        this.context = context;
        this.arreglo_producto = arreglo_producto;
    }

    @Override
    public int getCount() {
        return arreglo_producto.size();
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
        Producto item = arreglo_producto.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.view_producto, null);

        TextView txtTituloProducto = view.findViewById(R.id.txtTituloProducto);
        TextView txtProducto = view.findViewById(R.id.txtProducto);
        TextView txtTotalItems = view.findViewById(R.id.txtTotalItems);

        txtTituloProducto.setText(item.getNombre());
        txtProducto.setText("$ " + item.getPrecio_proveedor());
        txtTotalItems.setText("$ " + item.getPrecio_venta());

        return view ;
    }
}
