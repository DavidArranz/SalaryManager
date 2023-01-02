package org.example.salarymanager;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Gasto implements Serializable {
    private String nombre;
    private double monto;
    private Bitmap icon;
    private String date;

    public Gasto(String n, double cant, String date, Bitmap icon){
        nombre=n;
        monto=cant;
        this.date = date;
        this.icon = icon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMonto() {return monto;}

    public void setMonto(double monto) {this.monto = monto;}

    public Bitmap getIcon() {return icon;}

    public void setIcon(Bitmap icon) {this.icon = icon;}

    public String getDate() {return date;}

    public void setDate(Date String) {this.date = date;}
}
