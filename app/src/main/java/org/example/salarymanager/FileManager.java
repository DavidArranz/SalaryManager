package org.example.salarymanager;

import android.content.Context;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManager {
    private File file_gastos,file_objetivo,file_salario,file_monto,file_obj_flag;

    public FileManager(Context context){
        file_gastos = new File(context.getFilesDir(), "rvGastos.bin");
        file_objetivo = new File(context.getFilesDir(), "objetivo.bin");
        file_salario = new File(context.getFilesDir(), "salario.bin");
        file_monto = new File(context.getFilesDir(),"monto.bin");
        file_obj_flag = new File(context.getFilesDir(),"flag.bin");
    }
    public ArrayList<Gasto> getGastos(){
        ArrayList<Gasto> gastos = new ArrayList<>();
        if(file_gastos.exists() && file_gastos.length()!=0){
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file_gastos));
                gastos = (ArrayList<Gasto>) inputStream.readObject();
                inputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return gastos;
    }
    public double getSalario(){
        if(file_salario.exists()&& file_salario.length()!=0){
            return readDouble(file_salario);
        }else{
            return 0;
        }
    }
    public double getObjetivo(){
        if(file_objetivo.exists()&& file_objetivo.length()!=0){
            return readDouble(file_objetivo);
        }else{
            return 0;
        }
    }
    public double getMonto(){
        if(file_monto.exists() && file_monto.length()!=0){
            return readDouble(file_monto);
        }else{
            return 0;
        }
    }
    public boolean getFlag(){
        if (file_obj_flag.exists() && file_obj_flag.length()!=0) {
            // Lee el flag del fichero
            try (BufferedReader reader = new BufferedReader(new FileReader(file_obj_flag))) {
                return Boolean.parseBoolean(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    private Double readDouble(File file){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            FileInputStream fis = new FileInputStream(file);
            int i;
            i = fis.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = fis.read();
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String valueString = byteArrayOutputStream.toString();
        double value = Double.parseDouble(valueString);
        return value;

    }
    public void saveData(ArrayList data){


        try {
            FileOutputStream outputStream = outputStream = new FileOutputStream(file_gastos,false);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveData(String data,String str){
        File file = null;
        switch(str){
            case "monto":
                file = file_monto;
                break;
                case "objetivo":
                    file = file_objetivo;
                break;
            case "flag":
                file = file_obj_flag;
                break;
            case "salario":
                file = file_salario;
                break;
        }
        try {
            FileOutputStream outputStream = outputStream = new FileOutputStream(file,false);


            outputStream.write(data.getBytes());
            outputStream.close();

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
