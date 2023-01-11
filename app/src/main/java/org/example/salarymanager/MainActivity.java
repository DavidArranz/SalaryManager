package org.example.salarymanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "Felizitacion";
    private double objetivo,salario,monto;
    AdapterGastos adapter;
    ArrayList<Gasto> gastos;
    RecyclerView rv;
    ActionBar ab;
    Button bAdd;
    ImageButton bEdit;
    TextView tvObjetivo,tvSalario,tvMonto;
    FileManager fm;
    int currentMonth;
    boolean objFlag=true;



    //metodo que recive las respuestas de los intents
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //Respuesta de la creracion de un gasto
        if (resultCode == RESULT_OK && requestCode == 1) {
            String nom,sdate;
            double monto_gasto;
            Bitmap icon;
            //recepcion de los datos del gasto
            nom=intent.getStringExtra("nombre");
            sdate=intent.getStringExtra("date");
            monto_gasto=intent.getDoubleExtra("monto",0.0);
            icon=intent.getParcelableExtra("icon");
            //creacion del gasto
            Gasto gasto = new Gasto(nom,monto_gasto,sdate,icon);
            gastos.add(gasto);
            //se notifica al adaptador el nuevo gasto
            adapter.notifyItemInserted(gastos.size()-1);
            //se vuelve a guardar la lista de gastos actualizada
            fm.saveData(gastos);
            //se actualiza el monto
            monto = monto+monto_gasto;
            //se guarda la nueva cantidad
            fm.saveData(String.valueOf(monto),"monto");
            //se establece la nueva cantidad en la interfaz
            tvMonto.setText(String.valueOf(monto)+"€");
            //si objFlag es true (todabia no se habia rebasado el objetivo)
            //y el monto es ahora menor que el ovjetivo
            //se crea el popup
            //y se pone el Flag a false para que no se vuelva a ejecutar
            if(objFlag && monto<objetivo){
                createPopUp();
                objFlag = false;
                fm.saveData(Boolean.toString(objFlag),"flag");
            }

        }
        //respuesta de la modificacion de parametros
        if (resultCode == RESULT_OK && requestCode == 2) {
            String obj,sal;
            obj = intent.getStringExtra("objetivo");
            sal =  intent.getStringExtra("salario");
            //se guardan los nuevos parametros en sus ficheros
            fm.saveData(obj,"objetivo");
            fm.saveData(sal,"salario");
            //se guardan como double para operar con ellos
            objetivo=Double.parseDouble(obj);
            salario=Double.parseDouble(sal);
            monto=salario;
            tvObjetivo.setText(objetivo+"€");
            tvSalario.setText(salario+"€");
            tvMonto.setText(salario+"€");


        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMonto = findViewById(R.id.textViewMonto);
        tvObjetivo = findViewById(R.id.textViewObjetivo);
        tvSalario = findViewById(R.id.textViewEditSalario);
        bEdit=findViewById(R.id.imageButtonEdit);
        bAdd = findViewById(R.id.buttonAdd);
        rv = findViewById(R.id.recyclerViewGastos);
        //layout del reciclerview
        rv.setLayoutManager(new LinearLayoutManager(this));


        //escondo la ActionBar tansolo por movivos esteticos
        ab = getSupportActionBar();
        if(ab!=null){
            ab.hide();
        }
        //Se crea el FileManager (mirar la clase para mas informacion)
         fm = new FileManager(getApplicationContext());

         //Recogida de informacion de los ficheros
        gastos = fm.getGastos();
        adapter = new AdapterGastos(gastos);
        //selecion del adapter para el ReciclerView
        rv.setAdapter(adapter);
        salario = fm.getSalario();
        objetivo = fm.getObjetivo();
        monto=fm.getMonto();
        objFlag = fm.getFlag();
        currentMonth = fm.getCurrentMonth();

        tvSalario.setText(String.valueOf(salario));
        tvObjetivo.setText(String.valueOf(objetivo));
        tvMonto.setText(String.valueOf(monto));





        //Listener para añadir un movimiento
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(tvSalario.getText().toString());
                if(tvSalario.getText().equals("0.0")){
                    Toast.makeText(getApplicationContext(),"Primero introduce un salario",Toast.LENGTH_SHORT).show();
                }else{
                resetMonth(currentMonth);
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivityForResult(intent,1);
            }}
        });
        //Listener para editar los valores de monto objetivo y salario
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                startActivityForResult(intent,2);
            }
        });

    }

    //metodo que crea el PopUp para avisar de que queda poco dinero del salario de este mes
    private void createPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Queda poco dinero del salario de este mes.");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void resetMonth(int previousMonth){
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        //si el mes a cambiado
        if (currentMonth != previousMonth) {
            if(monto >= objetivo && currentMonth != -1) {
                notification();
            }
            //resetea el monto al salario actual
            monto = salario;
            fm.saveData(String.valueOf(monto),"monto");
            tvMonto.setText(String.valueOf(monto));
            //guarda el nuevo mes
            fm.saveData(String.valueOf(currentMonth),"mes");
            //resetea el limite objetivo
            objFlag=true;
            fm.saveData(String.valueOf(objFlag),"flag");
            //vacia la lista de gastos
            gastos.clear();
            //notifica al adapter
            adapter.notifyDataSetChanged();

        }

    }

    private void notification() {
        //intent que abre la aplicacion
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Creacion de la notificacion
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.fuegos_artificiales)
                .setContentTitle("Felizidades!")
                .setContentText("LO HAS CONSEGUIDO!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(getApplicationContext(),R.color.tema));
        createNotificationChannel();
        // Enseñar la notificacion
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            String description = "";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}