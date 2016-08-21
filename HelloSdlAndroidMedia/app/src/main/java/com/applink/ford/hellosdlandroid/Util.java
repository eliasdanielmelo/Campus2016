package com.applink.ford.hellosdlandroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by eliasdaniel on 21/08/16.
 */
public class Util {

    public static Activity context;
    public static String TAG = "TESTE_FORD";
    public static String controller = "http://sistema.sic7.com.br/controller/sistema/repositorio.controller.php";

    public static void postData(String controller, RequestParams post, AsyncHttpResponseHandler asyncHttpResponseHandler){
        AsyncHttpClient client = new AsyncHttpClient();
        if(Util.context != null){
            PersistentCookieStore myCookieStore = new PersistentCookieStore(Util.context);
            client.setCookieStore(myCookieStore);
        }
        Log.d(Util.TAG + " URL:", controller);
        Log.d(Util.TAG + " POST:", post.toString());
        if(asyncHttpResponseHandler == null){
            //asyncHttpResponseHandler não definido, cria um limpo (o cliente http precisa)
            asyncHttpResponseHandler = new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    Util.trataRetornoStatic(responseBody, Util.context);
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                }
            };
        }
        client.post(controller, post, asyncHttpResponseHandler);
    }

    public static Boolean trataRetornoStatic(byte[] responseBody, Context contextParam){
        String dadosString = null;
        try {
            dadosString = new String(responseBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Util.msg(Util.context, "Erro retorno byte para string utf8", false, Toast.LENGTH_LONG);
        }
        Boolean retorno=false;
        try {
            JSONObject dados = new JSONObject(dadosString);
            String msg = (String) dados.get("msg");
            Util.msg(Util.context, msg, true, Toast.LENGTH_LONG);
            retorno=true;
        } catch (JSONException e) {
            if(false && (dadosString != null) && (dadosString.split("\n").length > 0)){
                String linhas[] = dadosString.split("\n");
                Log.e(Util.TAG + " retorno NÃO é JSON", "");
                for (int i=0; i<linhas.length; i++){
                    Log.e(Util.TAG, linhas[i]);
                }
            }else{
                Log.e(Util.TAG + " retorno NÃO é JSON", dadosString);
            }

            //e.printStackTrace();
        }
        return retorno;
    }

    public static HashMap<String, Object> convertHashMap(byte[] responseBody){
        try {
            String decoded = new String(responseBody, "UTF8");
            return convertHashMap(decoded);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
    public static HashMap<String, Object> convertHashMap(String s){
        return convertHashMap(s, false);
    }
    public static HashMap<String, Object> convertHashMap(String s, boolean debug){
        HashMap<String, Object> hm = new HashMap();
        try {
            JSONObject json = new JSONObject(s);
            if(debug){
                Log.e(Util.TAG, "convertHashMap JSON: "+json);
            }
            Iterator<String> keys = json.keys();
            while (keys.hasNext()){
                String key = (String) keys.next();
                if(debug){
                    Log.e(Util.TAG, "convertHashMap HashMap key: "+key);
                }
                hm.put(key, json.get(key).toString());
            }
        } catch (JSONException e) {
            if(debug){
                e.printStackTrace();
            }
        }
        //tenta converter os dados do retorno
        if(hm.containsKey("dados")){
            HashMap<String, Object> dados = Util.convertHashMap(hm.get("dados").toString());
            if(dados.size() > 0){
                hm.put("dados", dados);
            }
        }
        if(debug){
            Log.e(Util.TAG, "convertHashMap HashMap pronto: "+hm.toString());
        }
        return hm;
    }

    public static void msg(final Activity context, final String msg, final boolean status, final int tempo){
        context.runOnUiThread(new Runnable() {
                                  public void run() {
//                                      Toast.makeText(context.getApplicationContext(), msg, tempo).show();
                                      Toast toast = Toast.makeText(context.getApplicationContext(), msg, tempo);
                                      View view = toast.getView();
                                      if (status) {
                                          //view.setBackgroundResource(R.drawable.backgroundverde);
                                      } else {
                                          //view.setBackgroundResource(R.drawable.backgroundvermelho);
                                      }
                                      TextView text = (TextView) view.findViewById(android.R.id.message);
                                      text.setTextColor(Color.BLACK);
                                      toast.show();
                                  }
                              }
        );
    }

    public static ArrayList<HashMap<String, Object>> getListaPosto(){
        //via get
        /*RequestParams post = new RequestParams();
        post.add("objeto", "nada");
        Util.postData("http://sistema.sic7.com.br/controller/sistema/repositorio.controller.php", post, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                if(Util.trataRetornoStatic(responseBody, Util.context)){
                    HashMap<String, Object> data = Util.convertHashMap(responseBody);
                    Log.e(Util.TAG, data.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });*/

        //dados teste
        final ArrayList<HashMap<String, Object>> dados = new ArrayList<>();
        dados.add(new HashMap<String, Object>() {{
            put("nomePosto", "POSTO 1");
            put("gasolina", "5.50");
            put("alcool", "3.50");
            put("avaliacao", "5");
            put("lat","-8.036196");
            put("log","-34.8706157");
            put("distancia","0m");
        }});
        dados.add(new HashMap<String, Object>() {{
            put("nomePosto", "POSTO 2");
            put("gasolina", "5.50");
            put("alcool", "3.50");
            put("avaliacao", "5");
            put("lat","-8.041401");
            put("log","-34.882975");
            put("distancia","50km");
        }});
        return dados;
    }

}
