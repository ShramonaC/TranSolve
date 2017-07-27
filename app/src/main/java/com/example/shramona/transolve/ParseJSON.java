package com.example.shramona.transolve;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shramona on 02-05-2017.
 */

public class ParseJSON {
     public static String[] Busnums;
     public static String[] Types;
     public static String[] fares;
     public static String[] distances;
    public static int len;

    public static final String JSON_ARRAY = "results";
    public static final String KEY_BUSNUM = "Busnum";
    public static final String KEY_TYPE = "Type";
     public static final String KEY_FARE = "fare";
    public static final String KEY_DISTANCE = "distance";
    int dst;

    private JSONArray fare = null;

    private String json;

    public ParseJSON(String json,int dst){
        this.json = json;
        this.dst=dst;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            fare = jsonObject.getJSONArray(JSON_ARRAY);
            len=fare.length();
            Busnums = new String[fare.length()];
            Types = new String[fare.length()];
            fares = new String[fare.length()];
            //distances = new String[fare.length()];


            for(int i=0;i<fare.length();i++){
                JSONObject jo = fare.getJSONObject(i);
                Busnums[i] = jo.getString(KEY_BUSNUM);
                Types[i] = jo.getString(KEY_TYPE);
                if(Types[i].equals("Mini") && dst ==0)
                    fares[i]="7";
                else if(Types[i].equals("Mini") && ( dst >0 && dst<=3))
                    fares[i]="7";
                else if(Types[i].equals("Mini") && ( dst > 3 && dst<=6))
                    fares[i]="8";
                else if(Types[i].equals("Mini") && ( dst > 6 && dst<=13))
                    fares[i]="9";
                else if(Types[i].equals("Mini") && dst > 13 )
                    fares[i]="10";
                else if(Types[i].equals("Private") && dst==0)
                    fares[i]="6";
                else if(Types[i].equals("Private") && ( dst >= 0 && dst<=3))
                    fares[i]="6";
                else if(Types[i].equals("Private") && ( dst > 3 && dst<=12))
                    fares[i]="8";
                else if(Types[i].equals("Private") && dst > 12)
                    fares[i]="7";
                else if(Types[i].equals("State") && dst ==0)
                    fares[i]="7";
                else if(Types[i].equals("State") && ( dst >= 0 && dst<=3))
                    fares[i]="7";
                else if(Types[i].equals("State") && ( dst > 3 && dst<=12))
                    fares[i]="9";
                else if(Types[i].equals("State") && dst > 12)
                    fares[i]="15";
                else if (dst>12)
                    fares[i]="20";
                else
                    fares[i]="25";
                //  fares[i] = jo.getString(KEY_FARE);
             //   distances[i] = jo.getString(KEY_DISTANCE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
