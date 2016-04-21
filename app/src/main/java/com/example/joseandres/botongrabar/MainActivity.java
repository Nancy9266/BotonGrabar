package com.example.joseandres.botongrabar;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnCompletionListener {
    TextView tv1;
    //Declaramos un objeto de la clase MediaRecorder para grabar audio.
    MediaRecorder recorder;
    //Declaramos un objeto de la clase MediaPlayer para reproducir
    // el archivo de sonido generado
    MediaPlayer player;
    //Declaramos un objeto de la clase File que hace referencia al archivo que se creará.
    File archivo;
    //Declaramos las variables que harán referencia a los tres botones.
    Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//En el método onCreate obtenemos la referencia de los cuatro objetos
// creados en el archivo XML.
        tv1 = (TextView) this.findViewById(R.id.tv1);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    //Metodo para Grabar.
    public void grabar(View v) {
        recorder = new MediaRecorder();
        //definimos el micrófono como fuente de audio.
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File path = new File(Environment.getExternalStorageDirectory()
                .getPath());
        try {
            archivo = File.createTempFile("temporal", ".3gp", path);
        } catch (IOException e) {
        }
        recorder.setOutputFile(archivo.getAbsolutePath());
        //Llamamos al método prepare y finalmente al método start
        // para comenzar la grabación
        try {
            recorder.prepare();
        } catch (IOException e) {
        }
        recorder.start();
        tv1.setText("Grabando");
        btn1.setEnabled(false);
        btn2.setEnabled(true);
    }
         //Metodo Detener.
    public void detener(View v) {
        //Llamamos primero al método stop de la clase MediaRecorder y
        // liberamos los recursos consumidos llamando a release.
        recorder.stop();
        recorder.release();
        //Llamamos primero al método stop de la clase MediaRecorder y
        // liberamos los recursos consumidos llamando a release.
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        //Referenciamos el archivo a que debe reproducir.
        try {
            player.setDataSource(archivo.getAbsolutePath());
        } catch (IOException e) {
        }
        //llamamos al método prepare de la clase MediaPlayer.
        try {
            player.prepare();
        } catch (IOException e) {
        }
        btn1.setEnabled(true);
        btn2.setEnabled(false);
        btn3.setEnabled(true);
        tv1.setText("Listo para reproducir");
    }
//El método reproducir simplemente llama al método start de la clase MediaPlayer
// para iniciar la reproducción del archivo previamente grabado.
    public void reproducir(View v) {
        player.start();
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        tv1.setText("Reproduciendo");
    }
//El método onCompletion se ejecuta cuando termina de reproducirse el archivo de audio.
    public void onCompletion(MediaPlayer mp) {
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        tv1.setText("Listo");
    }
}