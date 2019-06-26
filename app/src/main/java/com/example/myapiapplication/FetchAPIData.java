package com.example.myapiapplication;

import android.content.Context;
import android.content.res.AssetManager;
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
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

public class FetchAPIData extends AsyncTask<Void, Void, Void> {
    StringBuilder data = new StringBuilder();
    StringBuilder dataParsed;
    String singleParsed = "";
    Context context;


    public FetchAPIData(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL(getConfigProperty("api.url"));
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection(); //connection
            InputStream inputStream = httpsURLConnection.getInputStream(); //to get streamed input
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            data = new StringBuilder();
            dataParsed = new StringBuilder();

            while(line != null){
                line = bufferedReader.readLine();
                data.append(line);
            }

            JSONArray jsonArray = new JSONArray(data.toString());
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                singleParsed = jsonObject.toString(2);
                dataParsed.append(singleParsed);
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

    /**
     * Helper to yield property details
     * @param propKey the supplied key value
     * @return Property value present in the app.properties file
     */
    private String getConfigProperty(String propKey){
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("app.properties");
            properties.load(inputStream);
            return properties.getProperty(propKey);
        }catch(IOException ex){
            return "";
        }
    }
}
