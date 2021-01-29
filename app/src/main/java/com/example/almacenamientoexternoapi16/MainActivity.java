package com.example.almacenamientoexternoapi16;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText et_nombre, et_contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        et_contenido = (EditText)findViewById(R.id.txt_contenido);
    }



    //Este metodo permite almacenar la información, yo lo imagino como una carpeta la carpeta es el txt_nombre y los archivos dentro de ella son lo que escribo en txt_contenido
    public void Guardar (View view){

        String nombre = et_nombre.getText().toString();
        String contenido = et_contenido.getText().toString();

        try {
            File tarjetaSD = Environment.getExternalStorageDirectory(); //Hacemos que el programa detecte el almacenamiento externo del dispositivo en el que esta y lo asigna bajo la variable tarjetaSD
            Toast.makeText(this, tarjetaSD.getPath(), Toast.LENGTH_LONG).show(); //Enseña el path en el que se encuentra la variable tarjetaSD

            File rutaArchivo = new File(tarjetaSD.getPath(), nombre);
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(rutaArchivo.getName(), Activity.MODE_PRIVATE));

            archivo.write(contenido);
            archivo.flush();
            archivo.close();
            Toast.makeText(this, "DATOS GUARDADOS", Toast.LENGTH_SHORT).show(); //

            et_nombre.setText("");
            et_contenido.setText("");
        }catch (IOException e){
            Toast.makeText(this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        }

    }



    //Permite buscar las "carpetas" que creamos y nos muestra su contenido
    public void Consultar(View view){
        String nombre = et_nombre.getText().toString();

        try {
            File tarjetaSD = Environment.getExternalStorageDirectory();
            File rutaArchivo = new File(tarjetaSD.getPath(), nombre);
            InputStreamReader archivo = new InputStreamReader(openFileInput(rutaArchivo.getName()));

            BufferedReader leerArchivo = new BufferedReader(archivo);

            String linea = leerArchivo.readLine();
            String contenidoCompleto="";
            while (linea != null){
                contenidoCompleto = contenidoCompleto + linea + "\n";
                linea = leerArchivo.readLine();
            }
            leerArchivo.close();
            archivo.close();
            et_contenido.setText(contenidoCompleto);
        }catch (IOException e){
            Toast.makeText(this, "No se pudo leer el archivo", Toast.LENGTH_SHORT).show();
        }

    }
}