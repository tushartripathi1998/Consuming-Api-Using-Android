package com.example.myapiapplication;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String data = "";
    String dataParsed = "";
    String singleParsed = "";

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("https://api.myjson.com/bins/xuykl");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection(); //connection
            InputStream inputStream = httpsURLConnection.getInputStream(); //to get streamed input
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while(line != null){
                line = bufferedReader.readLine();
                data = data+line;
            }

            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                singleParsed = "Name:" + jsonObject.get("name")+"\n"+
                               "Password:" + jsonObject.get("password")+"\n"+
                               "Contact:" + jsonObject.get("contact")+"\n"+
                               "Country:" + jsonObject.get("country")+"\n";

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
    protected void onPostExecute(Void aVoid) {  //on ui work, and showing the data
        super.onPostExecute(aVoid);
        MainActivity.data.setText(dataParsed);
    }
}
