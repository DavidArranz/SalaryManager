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
//esta clase esta dedicada al manejo de la informacion en los ficheros.
public class FileManager {
    private File file_gastos,file_objetivo,file_salario,file_monto,file_obj_flag,file_mes;

    public FileManager(Context context){
        file_gastos = new File(context.getFilesDir(), "rvGastos.bin");
        file_objetivo = new File(context.getFilesDir(), "objetivo.bin");
        file_salario = new File(context.getFilesDir(), "salario.bin");
        file_monto = new File(context.getFilesDir(),"monto.bin");
        file_obj_flag = new File(context.getFilesDir(),"flag.bin");
        file_mes = new File(context.getFilesDir(),"mes.bin");
    }
    //metodo que devuelve del fichero gastos el arraylist de gastos,
    //en caso de no existir devuelve un arraylist vacio;
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
    //metodo que devuelve el salario
    public double getSalario(){
        if(file_salario.exists()&& file_salario.length()!=0){
            return readDouble(file_salario);
        }else{
            return 0;
        }
    }
    //metodo que devuelve el monto objetivo
    public double getObjetivo(){
        if(file_objetivo.exists()&& file_objetivo.length()!=0){
            return readDouble(file_objetivo);
        }else{
            return 0;
        }
    }
    //metodo que devuelve el monto actual
    public double getMonto(){
        if(file_monto.exists() && file_monto.length()!=0){
            return readDouble(file_monto);
        }else{
            return 0;
        }
    }
    //metodo que devuelve un flag que indica si el monto ovjetivo ya fue revasado
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
    //metodo que se usa para leer un double de un fichero
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
    //metodo que guarda un arraylist en el fichero gastos
    public void saveData(ArrayList data){

        try {
            file_gastos.createNewFile();
            FileOutputStream outputStream = outputStream = new FileOutputStream(file_gastos,false);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //metodo que guarda un string en un fichero dependiendo del string que se pase como segundo parametro
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
            case "mes":
                file = file_mes;
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

    public int getCurrentMonth() {
        if (file_mes.exists() && file_mes.length() != 0) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                FileInputStream fis = new FileInputStream(file_mes);
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
            int value = Integer.parseInt(valueString);
            return value;
        } else {
            return -1;
        }
    }
}
