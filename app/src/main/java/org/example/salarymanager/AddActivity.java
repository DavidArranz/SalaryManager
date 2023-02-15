package org.example.salarymanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    EditText etMonto,etNombre,etDate;
    ImageView ivIcono;
    Button bSave;
    Switch sIngreso;
    public static final int REQUEST_CODE_IMAGE_SELECT = 101;
    boolean selected=false;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etMonto = findViewById(R.id.editTextNumberMonto);
        etNombre = findViewById(R.id.editTextNombre);
        etDate = findViewById(R.id.editTextDate);
        ivIcono = findViewById(R.id.imageViewIconSet);
        bSave = findViewById(R.id.buttonSave);
        sIngreso = findViewById(R.id.switchIngreso);
        /*Cuando se clica el Image View del selector de icono se realiza un intent para seleccionar una imagen
        * de la galeria*/
        ivIcono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), REQUEST_CODE_IMAGE_SELECT);
            }
        });
        /*al presionar el boton de guardar se recogen los datos y se envian como respuesta del inatent al MainActivity*/
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom,date;
                Double monto;
                nom = etNombre.getText().toString().toUpperCase();
                date = etDate.getText().toString();
                if(etMonto.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Monto es un campo obligatorio",Toast.LENGTH_SHORT).show();
                }else{
                    monto = Double.parseDouble(etMonto.getText().toString());
                    monto = Math.abs(monto);
                    if(!sIngreso.isChecked()){
                        monto = -monto;
                    }


                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("nombre",nom);
                        intent.putExtra("monto",monto);
                        intent.putExtra("date",date);
                        Bitmap bmSmall;
                        Bitmap bm;
                        if(selected){
                            bm = ((BitmapDrawable)ivIcono.getDrawable()).getBitmap();
                             bmSmall=Bitmap.createScaledBitmap(bm,70,70,false);

                        }else{
                            bm = null;
                            if(monto<0){
                                bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.flecha_izq)).getBitmap();
                            }else{
                                bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.derecha)).getBitmap();
                            }

                             bmSmall=Bitmap.createScaledBitmap(bm,70,70,false);
                        }
                        intent.putExtra("icon",(bmSmall));
                        setResult(RESULT_OK,intent);
                        finish();
                }
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    //respuesta del intent para escoger un icono
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_SELECT && resultCode == RESULT_OK) {
            //Recoge el Uri de la imagen
            Uri imageUri = data.getData();
            InputStream inputStream = null;
            if (null != imageUri) {
                ivIcono.setImageURI(imageUri);
            }
            selected = true;
        }
    }

    public void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                etDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }


}
