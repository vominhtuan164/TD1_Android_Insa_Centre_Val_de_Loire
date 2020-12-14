package com.example.td1_asl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ListeActivity extends AppCompatActivity {

    /**
     * Par défault, si on ne lance pas le script python:
     * python simple-testing-server.py
     * L'interface affiche toujours les listes des items pour toutes des persones
     * "Café", "Lait", "Fromage", "CocaCola"
     */
    String[] meals = {"Café", "Lait", "Fromage", "CocaCola"};
    String[] items = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);
        TextView testname = findViewById(R.id.nameLoginUtilisateur);
        testname.setText(getIntent().getStringExtra("testname"));
        listItems(meals);
        String url = "http://10.0.2.2:8003/courses";
        new GetJSONTask(url).execute();
    }

    /**
     * Exercice 13, 14: Request REST
     * Tâche asynchronous réalise la requête GET récupérent des données JSON
     * et sauvegardent dans la String Array items
     */
    private class GetJSONTask extends AsyncTask<String, JSONObject, String> {

        private String url;

        /**
         * L'addresse IP de simulateur est 10.0.2.2, par défault
         */
        public GetJSONTask(String url) {
            this.url = url;
        }

        @Override
        protected String doInBackground(String... arg0) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(this.url);
            String result = " vide ";
            HttpResponse response;
            try {
                response = httpclient.execute(httpget);
                // Examine the response status
                //Log.i()
                Log.i("Praeda", "katkout" + response.getStatusLine().toString());
                // Get hold of the response entity
                HttpEntity entity = response.getEntity();
                Log.d("ent", "Entity:" + entity);
                // If the response does not enclose an entity, there is no need
                // to worry about connection release
                if (entity != null) {

                    // A Simple JSON Response Read
                    InputStream instream = entity.getContent();
                    result = convertStreamToString(instream);
                    // now you have the string representation of the HTML request
                    instream.close();
                }
            } catch (Exception e) {
                Log.d("erreur", "errreur:" + e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                showData(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * convertStreamToString convert de données Stream à String
     *
     * @param InputStream
     * @return String
     */
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * getStringArray recevoit un JSONArray et retourne un String Array
     *
     * @param jsonArray
     * @return String[]
     */
    public static String[] getStringArray(JSONArray jsonArray) {
        String[] stringArray = null;
        if (jsonArray != null) {
            int length = jsonArray.length();
            stringArray = new String[length];
            for (int i = 0; i < length; i++) {
                stringArray[i] = jsonArray.optString(i);
            }
        }
        return stringArray;
    }

    /**
     * Affiches chaque élément dans un String Array
     *
     * @param String[] list
     */
    public void listItems(final String[] list) {
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.activity_listview, list);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent appInfo = new Intent(ListeActivity.this, AchatActivity.class);
                        appInfo.putExtra("achat_name", list[position]);
                        appInfo.putExtra("name", getIntent().getStringExtra("testname"));
                        startActivity(appInfo);
                    }
                });
    }

    private void showData(String json) throws JSONException {

        JSONObject jsonObj = new JSONObject(json);
        String checkName = getIntent().getStringExtra("testname");
        if (checkName.equals("Yezan")) {
            items = getStringArray(jsonObj.getJSONArray("Yezan"));
            listItems(items);
        } else if (checkName.equals("Amine")) {
            items = getStringArray(jsonObj.getJSONArray("Amine"));
            listItems(items);
        } else if (checkName.equals("Alice")) {
            items = getStringArray(jsonObj.getJSONArray("Alice"));
            listItems(items);
        } else listItems(meals);
    }
}
