package com.example.proyectov1;

public class Venta_Online {
    private int id;
    private int id_producto;
    private String nombre_producto;
    private double costo;
    private String fecha_venta;
    private int ID_USUARIO;
    private int CARRITO;

    public Venta_Online() {
    }
    public Venta_Online(int id, int id_producto, String nombre_producto, double costo, String fecha_venta, int ID_USUARIO, int CARRITO) {
        this.id = id;
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.costo = costo;
        this.fecha_venta = fecha_venta;
        this.ID_USUARIO = ID_USUARIO;
        this.CARRITO = CARRITO;
    }

    public int getId() {
        return id;
    }

    public int getId_producto() {
        return id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public double getCosto() {
        return costo;
    }

    public String getFecha_venta() {
        return fecha_venta;
    }

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public int getCARRITO() {
        return CARRITO;
    }
}
