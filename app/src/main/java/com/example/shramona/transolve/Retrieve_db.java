package com.example.shramona.transolve;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.Locale;


public class Retrieve_db extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private String src, dst, mode, dist;
    private ListView listView;
    private static String JSON_URL_BUS;
    private static String JSON_URL_TRAIN;

    public int index, distance;

    private ImageButton listen;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_db);
        listView = (ListView) findViewById(R.id.listView);
        listen = (ImageButton) findViewById(R.id.listen);
        tts = new TextToSpeech(this, this);

       try {     /* Get values from Intent */
           Intent intent = getIntent();
           src = intent.getStringExtra("src");
           dst = intent.getStringExtra("dst");
           mode = intent.getStringExtra("mode");
           dist = intent.getStringExtra("dist");
           if(dist!=null){
               distance = (int) Math.round(Double.parseDouble((dist.substring(0, dist.indexOf(".")))));
           }
           else {
               distance=0;
           }
           if(mode.equals("train") && dst.equals("Howrah Stn"))
           {
               dst = "Howrah";
           }
           else if(mode.equals("train") && src.equals("Howrah Stn"))
           {
               dst = "Howrah";
           }
           int xyz = dst.indexOf(" ");
           if(xyz>0)
           {
               String dst2 = dst.substring(0, (xyz)) + "%20" + dst.substring(xyz + 1, dst.length());
               dst = dst2;
           }
           int mno = src.indexOf(" ");

           if(mno>0){

               String src2 = src.substring(0, (mno)) + "%20" + src.substring(mno + 1, src.length());
               src = src2;
           }


           if (mode.equals("bus")) {
               JSON_URL_BUS = "https://voiceenquiry.herokuapp.com/getdata/";
               sendRequestBus();
           }
           //sendRequestTrain();
           else if (mode.equals("train")) {
               JSON_URL_TRAIN = "http://aquaman.herokuapp.com/gettrains/";
               sendRequestTrain();
           }
           else if(mode.equals("cab"))
               sendRequestCab();

           listen.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
                 output();
               }
           });
       }catch (ActivityNotFoundException a) {
       }
    }

    private void sendRequestCab() {
        Intent i = new Intent(this, CustomCab.class);
        i.putExtra("dist",distance);
        this.startActivity(i);
    }


    private void sendRequestBus() {
        String JSON_URL_BUS = "https://voiceenquiry.herokuapp.com/getdata/" + src + "/" + dst;
        //System.out.println("hello "+ src +" , "+dst);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL_BUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSONBus(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Retrieve_db.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(Retrieve_db.this, "No bus available in this route", Toast.LENGTH_LONG).show();
                        String abc= "No bus available in this route";
                        tts.speak(abc, TextToSpeech.QUEUE_FLUSH, null);
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSONBus(String json) {
        ParseJSON pj = new ParseJSON(json, distance);
        pj.parseJSON();

        CustomList cl = new CustomList(this, ParseJSON.Busnums, ParseJSON.Types, ParseJSON.fares);
        listView.setAdapter(cl);
        }

   public void output() {

       String abc;
       if (mode.equals("bus")) {
           int size = ParseJSON.fares.length;
           for (int i = 0; i < size; i++) {


               String busno = ParseJSON.Busnums[i];
               String type = ParseJSON.Types[i];
               String fare = ParseJSON.fares[i];
               abc = " Available Bus Number /" + busno + "/ / whose / Fare is /" + fare+" and / Bus / Type / is /" + type;
               tts.speak(abc, TextToSpeech.QUEUE_ADD, null);

           }
       } else if (mode.equals("train")) {
           int size = ParseJSONTrain.Departures.length;
           for (int i = 0; i < size; i++) {
               String trainno = ParseJSONTrain.TrainNos[i];
               String trainname = ParseJSONTrain.TrainNames[i];
               String departure = ParseJSONTrain.Departures[i];
               String arrival = ParseJSONTrain.Arrivals[i];
               String fare = ParseJSONTrain.fares[i];
               abc = " Available Train / name  / is /" + trainname + "Whose fare is "+fare+"/ which will arrive at / /"+arrival+"/ and Departured" +
                       " from  /" + departure;
               tts.speak(abc, TextToSpeech.QUEUE_ADD, null);
           }
       }
       else if (mode.equals("cab")) {
        //   setContentView(R.layout.listviewcab);

       }}



        /*if (Busnums != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    tts = new TextToSpeech(Retrieve_db.this, new TextToSpeech.OnInitListener() {

                        @Override
                        public void onInit(int status) {
                            //And edited Here
                            if (status == TextToSpeech.SUCCESS) {
                                int result = tts.setLanguage(new Locale("en", "US"));
                                if (result == TextToSpeech.LANG_MISSING_DATA
                                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                    Log.e("TTS", "This Language is not supported");
                                } else {
                                    for (o = 0; o < l; o++) {
                                        String toSpeak = "Bus number is" + Busnums[o] + "fare is" + fares[o] + "and type of bus is" + Types[o];
                                        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                }
                            } else {
                                Log.e("TTS", "Initilization Failed!");
                            }
                        }
                    });
                }
            });
        }
        else {
            String toSpeak = "There is no data";
            tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }
        }*/



    private void sendRequestTrain() {
        String JSON_URL_TRAIN="http://aquaman.herokuapp.com/gettrains/"+ src + "/" + dst;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL_TRAIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSONTrain(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Retrieve_db.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(Retrieve_db.this, "No train available in this route", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSONTrain(String jsonT) {
        ParseJSONTrain pj = new ParseJSONTrain(jsonT,distance);
        pj.parseJSONTrain();
        CustomListTrain clt = new CustomListTrain(this, ParseJSONTrain.TrainNos, ParseJSONTrain.TrainNames, ParseJSONTrain.Departures, ParseJSONTrain.Arrivals,ParseJSONTrain.fares);
        listView.setAdapter(clt);
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

}
