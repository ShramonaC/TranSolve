package com.example.shramona.transolve;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Shramona on 01-06-2017.
 */

public class CustomCab extends Activity implements TextToSpeech.OnInitListener {

    private int distance;
    TextView dst1, dst2, dst3, fare1, fare2, fare3;
    public double ubergo, uberx, uberxl;
    private ImageButton listen;
    private TextToSpeech tts;
    String cabfare1,cabfare2,cabfare3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewcab);
        listen = (ImageButton) findViewById(R.id.listen);
        tts = new TextToSpeech(this, this);
        dst1 = (TextView) findViewById(R.id.distance1);
        dst2 = (TextView) findViewById(R.id.distance2);
        dst3 = (TextView) findViewById(R.id.distance3);
        fare1 = (TextView) findViewById(R.id.fare1);
        fare2 = (TextView) findViewById(R.id.fare2);
        fare3 = (TextView) findViewById(R.id.fare3);
        Intent i = getIntent();
        distance = i.getIntExtra("dist", 0);
        if (distance <= 4) {
            ubergo = 62.4;
            dst1.setText(Integer.toString(distance));
             cabfare1= String.format("%.2f", ubergo);
            fare1.setText(cabfare1);
        } else {
            ubergo = 62.4 + ((distance - 4) * 1.6 + (distance - 4) * 7.4);

            dst1.setText(Integer.toString(distance));
             cabfare1= String.format("%.2f", ubergo);
            fare1.setText(cabfare1);
        }
        if (distance <= 4) {
            uberx = 67.7;
            dst2.setText(Integer.toString(distance));
            cabfare2= String.format("%.2f", uberx);
            fare2.setText(cabfare2);
        } else {
            uberx = 67.7 + (distance * 1.6) + (distance * 9.5);
            //String.format("%.2f",uberx);
            dst2.setText(Integer.toString(distance));
            cabfare2= String.format("%.2f", uberx);
            fare2.setText(cabfare2);
        }
        //  tts.speak(abc, TextToSpeech.QUEUE_ADD, null);
        if (distance <= 4) {
            uberxl = 78.9;
            dst3.setText(Integer.toString(distance));
           cabfare3 = String.format("%.2f", uberxl);
            fare3.setText(cabfare3);
        } else {
            uberxl = 78.9 + (distance * 2.1) + (distance * 15.9);
            dst3.setText(Integer.toString(distance));
            cabfare3= String.format("%.2f", uberxl);
            fare3.setText(cabfare3);
        }

        listen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                output();
            }
        });

}
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(new Locale("en", "US"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                listen.setEnabled(true);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    public void output() {

        String abc,abc1,abc2;




                abc = " Available Cab / name  / is /ubergo whose fare is"+ cabfare1+" and / distance / is " +distance +"/ kilo meter";
        abc1 = " Available Cab / name  / is /uber X whose fare is"+ cabfare2+" and / distance / is " +distance+"/ kilo meter";
        abc2 = " Available Cab / name  / is /uber XL whose fare is"+ cabfare3+" and / distance / is " +distance+"/ kilo meter";
                tts.speak(abc, TextToSpeech.QUEUE_ADD, null);
        tts.speak(abc1, TextToSpeech.QUEUE_ADD, null);
        tts.speak(abc2, TextToSpeech.QUEUE_ADD, null);


}
}



//         this.distances = distances;


