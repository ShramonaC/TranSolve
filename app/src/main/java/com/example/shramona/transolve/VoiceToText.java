package com.example.shramona.transolve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AudioEffect;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.speech.tts.TextToSpeech;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import static android.R.attr.contextUri;
import static android.R.attr.port;
import static android.R.attr.value;
import static android.speech.tts.TextToSpeech.QUEUE_ADD;

public class VoiceToText extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {
    private final int SPEECH_RECOGNITION_CODE = 0;
    private final int SPEECH_RECOGNITION_CODE2 = 1;
    private final int SPEECH_RECOGNITION_CODE3 = 2;
    String arr[] = {"Bardhaman", "Shyambazar", "Belurmath", "Belurbazar", "Uttarpara", "mckvie",
            "barabazar", "Hatibagan", "Liluah", "Salkia", "Chingrighata", "Howrah Stn",
            "Park Street,Kolkata", "Esplanade,Kolkata", "Sreerampur", "Ballykhal", "Ballyghat", "Konnagar",
            "Ultadanga", "Sealdah", "Rabindra Sadan","Rishra","Bally","Ballykhal","Ballyghat","Park Circus"};
    String modeTrans[] = {"bus", "train", "cab"};
    double sim[] = new double[26];
    double sim2[] = new double[3];
    double len = 0.0;
    private TextView txtOutput, txtOutput2, txtOutput3;
    String src, dst, mode;
    private ImageButton btnMicrophone, btnMicrophone2, btnMicrophone3, help;
    private Button btnMicrophone4;
    private TextToSpeech tts;
    public static String IP;
    private int port = 50005;
    AudioRecord recorder;
    Boolean isAvailable = false;

    private int sampleRate = 30000; // 44100 for music

    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = AudioFormat.ENCODING_AC3;
    int minBufSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, AudioFormat.ENCODING_PCM_16BIT); //audioFormat
    private boolean status = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_to_text);
        tts = new TextToSpeech(this, this);
        txtOutput = (TextView) findViewById(R.id.txtSpeechInput);
        txtOutput2 = (TextView) findViewById(R.id.txtSpeechInput2);
        txtOutput3 = (TextView) findViewById(R.id.txtSpeechInput3);

        btnMicrophone = (ImageButton) findViewById(R.id.btnSpeak);
        btnMicrophone2 = (ImageButton) findViewById(R.id.btnSpeak2);
        btnMicrophone3 = (ImageButton) findViewById(R.id.btnSpeak3);
        btnMicrophone4 = (Button) findViewById(R.id.btnSpeak4);
        help = (ImageButton) findViewById(R.id.help);

        btnMicrophone.setOnClickListener(this);
        btnMicrophone2.setOnClickListener(this);
        btnMicrophone3.setOnClickListener(this);
        btnMicrophone4.setOnClickListener(this);
        help.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSpeak:
                startSpeechToText();
                break;

            case R.id.btnSpeak2:
                startSpeechToText2();
                break;
            case R.id.btnSpeak3:
                startSpeechToText3();
                break;
            case R.id.btnSpeak4:
                speakOut();
                break;
            case R.id.help:
                helpout();
                break;
            default:
                break;

        }
    }

    /**
     * Start speech to text intent. This opens up Google Speech Recognition API dialog box to listen the speech input.
     */
    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak out where you are...");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void startSpeechToText2() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak out where you want to go...");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE2);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void startSpeechToText3() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak how will you travel...");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE3);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Callback for speech recognition activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                try {
                    if (resultCode == RESULT_OK && null != data) {
                        ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        for (int in = 0; in < result.size(); in++) {
                            src = result.get(in);
                            for (int r = 0; r < arr.length; r++) {
                                String s1 = arr[r];
                                String s2 = src;
                                int[] costs = new int[s2.length() + 1];
                                for (int i = 0; i <= s1.length(); i++) {
                                    int lastValue = i;
                                    for (int j = 0; j <= s2.length(); j++) {
                                        if (i == 0)
                                            costs[j] = j;
                                        else {
                                            if (j > 0) {
                                                int newValue = costs[j - 1];
                                                if (s1.charAt(i - 1) != s2.charAt(j - 1))
                                                    newValue = Math.min(Math.min(newValue, lastValue),
                                                            costs[j]) + 1;
                                                costs[j - 1] = lastValue;
                                                lastValue = newValue;
                                            }
                                        }
                                    }
                                    if (i > 0) {
                                        costs[s2.length()] = lastValue;
                                        len = ((double) s1.length() - costs[s2.length()]) / (double) s1.length();
                                    }
                                }
                                sim[r] = len;
                            }
                            for (int i = 0; i < sim.length; i++) {
                                System.out.println(sim[i]);
                            }
                            double max = sim[0];
                            int pos = 0;
                            for (int i = 0; i < sim.length; i++) {
                                if (max <= sim[i]) {
                                    max = sim[i];
                                    pos = i;
                                }
                            }
                            src = arr[pos];
                            txtOutput.setText(src);
                        }
                    }
                } catch (ActivityNotFoundException a) {

                }

                break;
            }

            case SPEECH_RECOGNITION_CODE2: {
                try {
                    if (resultCode == RESULT_OK && null != data) {
                        ArrayList<String> result2 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        for (int in = 0; in < result2.size(); in++) {
                            dst = result2.get(in);
                            for (int r = 0; r < arr.length; r++) {
                                String s1 = arr[r];
                                String s2 = dst;
                                int[] costs = new int[s2.length() + 1];
                                for (int i = 0; i <= s1.length(); i++) {
                                    int lastValue = i;
                                    for (int j = 0; j <= s2.length(); j++) {
                                        if (i == 0)
                                            costs[j] = j;
                                        else {
                                            if (j > 0) {
                                                int newValue = costs[j - 1];
                                                if (s1.charAt(i - 1) != s2.charAt(j - 1))
                                                    newValue = Math.min(Math.min(newValue, lastValue),
                                                            costs[j]) + 1;
                                                costs[j - 1] = lastValue;
                                                lastValue = newValue;
                                            }
                                        }
                                    }
                                    if (i > 0) {
                                        costs[s2.length()] = lastValue;
                                        len = ((double) s1.length() - costs[s2.length()]) / (double) s1.length();
                                    }
                                }
                                sim[r] = len;
                            }
                            for (int i = 0; i < sim.length; i++) {
                                System.out.println(sim[i]);
                            }
                            double max = sim[0];
                            int pos = 0;
                            for (int i = 0; i < sim.length; i++) {
                                if (max <= sim[i]) {
                                    max = sim[i];
                                    pos = i;
                                }
                            }
                            dst = arr[pos];
                            txtOutput2.setText(dst);
                        }
                    }
                } catch (ActivityNotFoundException a) {

                }
                break;
            }


            case SPEECH_RECOGNITION_CODE3: {
                try {
                    if (resultCode == RESULT_OK && null != data) {
                        ArrayList<String> result3 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        for (int in = 0; in < result3.size(); in++) {
                            mode = result3.get(in);
                            for (int r = 0; r < modeTrans.length; r++) {
                                String s1 = modeTrans[r];
                                String s2 = mode;
                                int[] costs = new int[s2.length() + 1];
                                for (int i = 0; i <= s1.length(); i++) {
                                    int lastValue = i;
                                    for (int j = 0; j <= s2.length(); j++) {
                                        if (i == 0)
                                            costs[j] = j;
                                        else {
                                            if (j > 0) {
                                                int newValue = costs[j - 1];
                                                if (s1.charAt(i - 1) != s2.charAt(j - 1))
                                                    newValue = Math.min(Math.min(newValue, lastValue),
                                                            costs[j]) + 1;
                                                costs[j - 1] = lastValue;
                                                lastValue = newValue;
                                            }
                                        }
                                    }
                                    if (i > 0) {
                                        costs[s2.length()] = lastValue;
                                        len = ((double) s1.length() - costs[s2.length()]) / (double) s1.length();
                                    }
                                }
                                sim2[r] = len;
                            }
                            for (int i = 0; i < sim2.length; i++) {
                                System.out.println(sim2[i]);
                            }
                            double max = sim2[0];
                            int pos = 0;
                            for (int i = 0; i < sim2.length; i++) {
                                if (max <= sim2[i]) {
                                    max = sim2[i];
                                    pos = i;
                                }
                            }
                            mode = modeTrans[pos];
                            txtOutput3.setText(mode);
                        }
                    }
                } catch (ActivityNotFoundException a) {

                }
                break;
            }
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

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(new Locale("en", "US"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnMicrophone4.setEnabled(true);
                help.setEnabled(true);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {

        if (src != null && dst != null && mode != null) {
            String text = "You / are / going / from /" + src + "/ to / /" + dst + "/ and / mode of transport is / /" + mode +" / /Now, / Press find fare to know fare details";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

            Intent intent = new Intent(VoiceToText.this, MapsActivity.class);
            intent.putExtra("src", src);
            intent.putExtra("dst", dst);
            intent.putExtra("mode", mode);
            startActivity(intent);
        } else if (src == null && dst != null && mode != null) {
            String text = "You / are / going / to / /" + dst + "/ and / mode of transport is / /" + mode+"/ Now, / After filling source / Press find fare to know fare details";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            Intent intent = new Intent(VoiceToText.this, MapsActivity.class);
            intent.putExtra("dst", dst);
            intent.putExtra("mode", mode);
            startActivity(intent);
        } else {
            String text_null = "Please / fill out all / the details";
            tts.speak(text_null, TextToSpeech.QUEUE_FLUSH, null);

        }
    }

    private void helpout() {

        if (src != null && dst != null && mode != null) {
            String text = "Welcome// to Transolve facility. Here you need to fill up your destination and / mode  of transport first/ then / you " +
                    " can  choose to fill source/ or/ access / gps facility ." +
                    " then  press go button / to  enjoy our  system! Happy using";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        } else if (src == null && dst != null && mode != null) {
            String text = "Welcome// to Transolve facility. Since you have kept your source / blank, you are availing / gps facility ." +
                    " press go button / to  enjoy our  system! Happy using";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
        else {
            String text = "Welcome// to Transolve facility. Click on the microphone sign and start filling your transport details " +
                    " then  press go button / to  enjoy our  system! Happy using";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }



}


  /*  protected void createDatabase() {
        db = openOrCreateDatabase("TranSolve", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS trans(src INTEGER NOT NULL, dest VARCHAR NOT NULL,mode VARCHAR NOT NULL);");
    }

    protected void insertIntoDB() {
        if (src.equals("") || dst.equals("") || mode.equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
        String query = "INSERT INTO trans (source,destination,mode) VALUES('"+src+"', '"+dst+"', '"+mode+"');";
        db.execSQL(query);
        Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();
    }
    */
  public void startStreaming() {


      Thread streamThread = new Thread(new Runnable() {

          @Override
          public void run() {
              try {

                  final DatagramSocket socket = new DatagramSocket();
                  Log.d("VS", "Socket Created");

                  final byte[] buffer = new byte[minBufSize];

                  Log.d("VS","Buffer created of size " + minBufSize);


                  Log.d("IP Add", IP);

                  final InetAddress destination = InetAddress.getByName(IP.trim());
                  Log.d("VS", "Address retrieved");
                  //recorder= new AudioRecord()

                  recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,channelConfig, AudioFormat.ENCODING_PCM_16BIT,minBufSize*10); //audioFormat
                  Log.d("VS", "Recorder initialized");

                  if (isAvailable)
                      Log.d("Acoustic Echo Canceller", "Acoustic Echo Canceller is enabled");

                  if (isAvailable && android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                      AcousticEchoCanceler.create(recorder.getAudioSessionId());
                      NoiseSuppressor.create(recorder.getAudioSessionId());
                      AutomaticGainControl.create(recorder.getAudioSessionId());
                  }

                  recorder.startRecording();
                  Thread sendThread= new Thread (new Runnable() {
                      @Override
                      public void run() {

                          while(status)

                          {
                              final DatagramPacket packet;
                              //reading data from MIC into buffer
                              minBufSize = recorder.read(buffer, 0, buffer.length);

                              //putting buffer in the packet
                              packet = new DatagramPacket(buffer, buffer.length, destination, port);
                              try {
                                  socket.send(packet);
                              }
                              catch(IOException e){
                                  Log.e("VS", "IOException");
                              }
                              System.out.println("MinBufferSize: " + minBufSize);

                          }
                      }
                  });

                  sendThread.run();

              } catch(UnknownHostException e) {
                  Log.e("VS", "UnknownHostException");
              } catch (IOException e) {
                  e.printStackTrace();
                  Log.e("VS", "IOException");
              }
          }

      });
      streamThread.start();
  }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("No", null).show();
    }
}
