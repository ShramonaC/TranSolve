package com.example.shramona.transolve;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dell on 01-May-17.
 */
public class ParseJSONTrain {
    public static String[] TrainNos;
    public static String[] TrainNames;
    public static String[] Departures;
    public static String[] Arrivals;
    public static String[] fares;

    public static final String JSON_ARRAY_TRAIN = "railgaris";
    public static final String KEY_TRAINNO = "TrainNo";
    public static final String KEY_TRAINNAME = "TrainName";
    public static final String KEY_DEPARTURE = "Departure";
    public static final String KEY_ARRIVAL = "Arrival";
    public static final String KEY_FARE = "fare";


    private JSONArray trains = null;

    private String jsonT;
int dist;
    public ParseJSONTrain(String jsont,int dist){
        this.jsonT = jsont;
        this.dist=dist;
    }

    protected void parseJSONTrain(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(jsonT);
            trains = jsonObject.getJSONArray(JSON_ARRAY_TRAIN);
            TrainNos = new String[trains.length()];
            TrainNames = new String[trains.length()];
            Departures = new String[trains.length()];
            Arrivals = new String[trains.length()];
            fares = new String[trains.length()];


            for(int i=0;i<trains.length();i++){
                JSONObject jo = trains.getJSONObject(i);
                TrainNos[i] = jo.getString(KEY_TRAINNO);
                double f=dist/30;
                f=(f+1)*5;
                fares[i]=Double.toString(f);
                TrainNames[i] = jo.getString(KEY_TRAINNAME);
                Departures[i] = jo.getString(KEY_DEPARTURE);
                Arrivals[i] = jo.getString(KEY_ARRIVAL);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}