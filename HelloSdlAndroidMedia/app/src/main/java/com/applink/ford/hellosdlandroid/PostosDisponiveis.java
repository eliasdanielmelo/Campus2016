package com.applink.ford.hellosdlandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class PostosDisponiveis extends Activity {

    ListView listview;
    ListArrayAdapter listArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postos_disponiveis);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_postos_disponiveis, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.context=this;
        carregaLista();
    }

    public void carregaLista(){
        listview = (ListView) findViewById(R.id.listview);
        final ArrayList<HashMap<String, Object>> dados = Util.getListaPosto();

        listArrayAdapter = new ListArrayAdapter(this, R.layout.list_postos, getValues(dados), dados);
        listview.setAdapter(listArrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                view.setSelected(true);
                listArrayAdapter.notifyDataSetChanged();
                //PK da posicao clicada
                final String item = (String) parent.getItemAtPosition(position);

                //abre a activity de cadastro
                exibeDadosPosto(dados.get(Integer.valueOf(item)));
            }

        });
    }

    public String[] getValues(ArrayList<HashMap<String, Object>> dados){
        String[] values = new String[dados.size()];
        for(int i=0; i<dados.size(); i++){
            values[i]=String.valueOf(i);
        }
        return values;
    }

    public void exibeDadosPosto(HashMap<String, Object> posto){
        Intent intent = new Intent(this, DadosPosto.class);
        for (String key : posto.keySet()) {
            intent.putExtra(key, posto.get(key).toString());
        }
        startActivity(intent);
        //startActivity(new Intent(PostosDisponiveis.this, DadosPosto.class));
    }
}
