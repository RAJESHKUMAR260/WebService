package com.example.webserviceapplication;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchData extends AsyncTask<Void,Void,Void>{
    private String data = "";
    private String dataParsed = "";
    private String singleParsed = "";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.androidhive.info/contacts/");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            while (line !=null) {
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for (int i=0;i<JA.length(); i++){
                JSONObject JO =(JSONObject) JA.getJSONObject(i);
                singleParsed =
                        "Id:" + JO.get("id") + "\n" +
                        "Name:" + JO.get("name") + "\n"+
                        "Email:" + JO.get("email") + "\n"+
                        "Address:" + JO.get("address") + "\n"+
                        "Gender:" + JO.get("gender") + "\n"+
                         "Phone:"  + JO.get ("phone") + "\n";
                JSONObject JO1 = JO.getJSONObject("phone");
                singleParsed =
                "Mobile:" + JO1.get("mobile") + "\n" +
                 "Office:" + JO1.get("office") + "\n" +
                 "Home:"  + JO1.get("home") + "\n";

                dataParsed = dataParsed + singleParsed;


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.data.setText(this.dataParsed);
    }
}
