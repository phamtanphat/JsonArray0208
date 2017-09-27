package com.ptp.phamtanphat.jsonarray0208;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    TextView txtjson;
    Button btngetjson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtjson = (TextView) findViewById(R.id.textviewjson);
        btngetjson = (Button) findViewById(R.id.buttonjson);
        btngetjson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetJsonArray().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo2.json");
            }
        });
    }
    private class GetJsonArray extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObjectdanhsach = new JSONObject(s);

//                String danhsach = jsonObjectdanhsach.getString("danhsach");
//                Toast.makeText(MainActivity.this, danhsach, Toast.LENGTH_SHORT).show();

                JSONArray jsonArraydanhsach = jsonObjectdanhsach.getJSONArray("danhsach");
//                Toast.makeText(MainActivity.this, jsonArraydanhsach.length() + "", Toast.LENGTH_SHORT).show();
                for (int i = 0 ; i<jsonArraydanhsach.length();i++){
                    JSONObject jsonObject = jsonArraydanhsach.getJSONObject(i);

                    String khoahoc = jsonObject.getString("khoahoc");
                    txtjson.append(khoahoc + "\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
}
