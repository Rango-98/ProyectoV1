package com.example.proyectov1.clases;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectov1.R;
import com.example.proyectov1.clases.Producto;

import java.util.ArrayList;
import java.util.Base64;

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

        ImageView img_previwq = view.findViewById(R.id.img_previwq);
        TextView tv_titulo_producto = view.findViewById(R.id.tv_titulo_producto);
        TextView tv_precio = view.findViewById(R.id.tv_precio);

        try{
            if (!item.getFoto().equals("sin foto") && !item.getFoto().equals("null")) {
                byte[] bytes = Base64.getDecoder().decode(item.getFoto());
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img_previwq.setImageBitmap(bitmap);
            } else {

            }

        }catch(Exception e){
            System.err.println(e);
        }

        img_previwq .setImageResource(R.drawable.shopping);
        tv_titulo_producto.setText(item.getNombre() + " - " + item.getModelo() );
        tv_precio.setText("$ " + item.getPrecio_venta() + "\n"
                + "Marca: " + item.getMarca() + "\n"
                + "Codigo de barras: " + item.getCodigo_barras());

        return view ;
    }
}
