package com.example.shramona.transolve;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shramona.transolve.R;

/**
 * Created by Shramona on 02-05-2017.
 */

public class CustomList extends ArrayAdapter<String> {


    private Activity context;
    private String[] Busnums;
    private String[] Types;
    private String[] fares;
  //  private String[] distances;

    public CustomList(Activity context, String[] Busnums, String[] Types,String[] fares){
        super(context, R.layout.listview, Busnums);
        this.context = context;
        this.Busnums = Busnums;
        this.Types = Types;
        this.fares = fares;
//         this.distances = distances;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.listview, null, true);
        TextView textViewF = (TextView) listViewItem.findViewById(R.id.fare);
        TextView textViewR = (TextView) listViewItem.findViewById(R.id.route);
        TextView textViewD = (TextView) listViewItem.findViewById(R.id.distance);

        textViewF.setText(Busnums[position]);
        textViewR.setText(Types[position]);
        textViewD.setText(fares[position]);

        //   textViewD.setText(distances[position]);

        return listViewItem;
    }

}
