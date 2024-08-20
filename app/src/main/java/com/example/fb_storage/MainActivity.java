package com.example.fb_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.File;
import android.graphics.BitmapFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener referencias a los elementos de la interfaz
        Button buttonDownload = findViewById(R.id.button_download);
        ImageView imageView = findViewById(R.id.image_view);

        // Crear una referencia a la imagen en Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/tu_imagen.jpg");

        buttonDownload.setOnClickListener(view -> {
            // Crear un archivo temporal para guardar la imagen descargada
            File localFile = null;
            try {
                localFile = File.createTempFile("images", "jpg");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Descargar la imagen
            File finalLocalFile = localFile;
            storageRef.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Si la descarga es exitosa, cargar la imagen en el ImageView
                        Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath());
                        imageView.setImageBitmap(bitmap);
                    })
                    .addOnFailureListener(exception -> {
                        // Manejar errores en la descarga
                        // ...
                    });
        });
    }
}