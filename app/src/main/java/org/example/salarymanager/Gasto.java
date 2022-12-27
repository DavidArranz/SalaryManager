package org.example.salarymanager;

public class Gasto {
    private String nombre;
    private long cantidad;

    public Gasto(String n, long cant){
        nombre=n;
        cantidad=cant;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }
}
