package org.example.salarymanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddActivity extends AppCompatActivity {
    EditText etMonto,etFecha,etNombre;
    ImageView ivIcono;
    Bitmap image = null;
    Button bSave;
    public static final int REQUEST_CODE_IMAGE_SELECT = 1;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etMonto = findViewById(R.id.editTextNumberMonto);
        etFecha = findViewById(R.id.editTextDate);
        etNombre = findViewById(R.id.editTextNombre);
        ivIcono = findViewById(R.id.imageViewIconSet);
        bSave = findViewById(R.id.buttonSave);

        ivIcono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE_SELECT);
            }
        });
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom,date;
                Double monto;
                nom = etNombre.getText().toString();
                date = etFecha.getText().toString();
                monto = Double.parseDouble(etMonto.getText().toString());
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("nombre",nom);
                intent.putExtra("monto",monto);
                intent.putExtra("date",date);
                if(image == null){
                    Drawable drawable = getResources().getDrawable(R.drawable.flecha_izq);
                    image = ((BitmapDrawable)drawable).getBitmap();
                }
                intent.putExtra("icon",image);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_SELECT && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image = BitmapFactory.decodeStream(inputStream);
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
