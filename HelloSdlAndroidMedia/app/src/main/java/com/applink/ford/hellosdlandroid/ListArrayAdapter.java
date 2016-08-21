package com.applink.ford.hellosdlandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eliasdaniel on 21/08/16.
 */
public class ListArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final int rowlayout;
    private final ArrayList<HashMap<String, Object>> dados;
    private int selecionado=-1;

    public ListArrayAdapter(Context context, int rowlayout, String[] values, ArrayList<HashMap<String, Object>> dados){
        super(context, rowlayout, values);
        this.rowlayout=rowlayout;
        this.context = context;
        this.values = values;
        this.dados = dados;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(this.rowlayout, parent, false);
        int pos = Integer.parseInt(values[position]);

        // Pessoa
        try {
            ((TextView) rowView.findViewById(R.id.nomePosto)).setText(this.dados.get(pos).get("nomePosto").toString());
            ((TextView) rowView.findViewById(R.id.gasolina)).setText("Gasolina: "+this.dados.get(pos).get("gasolina").toString());
            ((TextView) rowView.findViewById(R.id.alcool)).setText("Alcool: "+this.dados.get(pos).get("alcool").toString());
            ((TextView) rowView.findViewById(R.id.distancia)).setText("Distância: " + this.dados.get(pos).get("distancia").toString());
            /*if(dados.get(pos).get("sincronizado") != null && String.valueOf(dados.get(pos).get("sincronizado")).equals("1")){
                ((ImageView)rowView.findViewById(R.id.icon)).setImageDrawable(null);
            }*/
        }catch (Exception e){
        }
        return rowView;
    }
}