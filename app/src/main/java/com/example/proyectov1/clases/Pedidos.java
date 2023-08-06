package com.example.proyectov1.clases;

public class Pedidos {
    private int id_pedido;
    private int codigo_compra;
    private  int status;
    private int id_usuario;
    private int codigo_carrito;

    public Pedidos() {
    }

    public Pedidos(int id_pedido, int codigo_compra, int status, int id_usuario, int codigo_carrito) {
        this.id_pedido = id_pedido;
        this.codigo_compra = codigo_compra;
        this.status = status;
        this.id_usuario = id_usuario;
        this.codigo_carrito = codigo_carrito;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public int getCodigo_compra() {
        return codigo_compra;
    }

    public int getStatus() {
        return status;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public int getCodigo_carrito() {
        return codigo_carrito;
    }
}
