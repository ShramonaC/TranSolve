package com.example.shramona.transolve;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Shramona on 23-05-2017.
 */
public class CustomListTrain extends ArrayAdapter<String> {


    private Activity context;
    private String[] TrainNos;
    private String[] TrainNames;
    private String[] Departures;
    private String[] Arrivals;
    private String[] fares;

    public CustomListTrain(Activity context, String[] TrainNos, String[] TrainNames,String[] Departures, String[] Arrivals,String[] fares){
        super(context, R.layout.listviewtrain, TrainNos);
        this.context = context;
        this.TrainNos = TrainNos;
        this.TrainNames = TrainNames;
        this.Departures = Departures;
        this.Arrivals = Arrivals;
        this.fares=fares;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.listviewtrain, null, true);
        TextView textViewTno = (TextView) listViewItem.findViewById(R.id.trainno);
        TextView textViewTnum = (TextView) listViewItem.findViewById(R.id.trainname);
        TextView textViewDept = (TextView) listViewItem.findViewById(R.id.departure);
        TextView textViewArr = (TextView) listViewItem.findViewById(R.id.arrival);
        TextView textViewfa = (TextView) listViewItem.findViewById(R.id.fare);

        textViewTno.setText(TrainNos[position]);
        textViewTnum.setText(TrainNames[position]);
        textViewDept.setText(Departures[position]);
        textViewArr.setText(Arrivals[position]);
        textViewfa.setText(fares[position]);

        return listViewItem;
    }

}