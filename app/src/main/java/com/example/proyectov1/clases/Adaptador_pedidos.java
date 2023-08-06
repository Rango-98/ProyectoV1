package com.example.proyectov1.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proyectov1.R;

import java.util.ArrayList;

public class Adaptador_pedidos extends BaseAdapter {

    private Context context;
    private ArrayList<Pedidos> pedidosArrayList;

    public Adaptador_pedidos(Context context, ArrayList<Pedidos> pedidosArrayList) {
        this.context = context;
        this.pedidosArrayList = pedidosArrayList;
    }

    @Override
    public int getCount() {
        return pedidosArrayList.size();
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
         Pedidos itemPedido = pedidosArrayList.get(i);

        view = LayoutInflater.from(context).inflate(R.layout.items_lista_pedido, null);

        TextView tv_codigo_pedido, tv_status_pedido;

        tv_codigo_pedido = view.findViewById(R.id.tv_codigo_pedido);
        tv_status_pedido = view.findViewById(R.id.tv_status_pedido);

        tv_codigo_pedido.setText("Codigo para recoger el pedido: "+ itemPedido.getCodigo_compra());
        if(itemPedido.getStatus() == 0){
            tv_status_pedido.setText("Pendiente");
        }else{
            tv_status_pedido.setText("Completo");
        }


        return view;
    }
}
