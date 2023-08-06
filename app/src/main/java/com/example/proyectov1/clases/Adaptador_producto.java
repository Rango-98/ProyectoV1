package com.example.proyectov1.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proyectov1.R;
import com.example.proyectov1.clases.Producto;

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
        view = LayoutInflater.from(context).inflate(R.layout.items_lista_producto, null);

        TextView tv_titulo_producto = view.findViewById(R.id.tv_titulo_producto);
        TextView tv_precio = view.findViewById(R.id.tv_precio);

        tv_titulo_producto.setText(item.getNombre());
        tv_precio.setText("$ " + item.getPrecio_venta());

        return view ;
    }
}
