package com.topsportsnews.app.ServiceConnections;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.topsportsnews.activities.R;
import com.topsportsnews.app.models.SportsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omkardokur on 1/14/16.
 */
public class SportsAsyncTask extends AsyncTask<String, Void, List<SportsItem>> {
    private ProgressDialog progressDialog;
    private ResultsCallback resultCallback;
    private Context context;
    private List<SportsItem> sportsItemList;
    private static final String TAG = "RecyclerViewExample";
    public SportsAsyncTask(ResultsCallback resultCallback,Context context) {
        this.context = context;
        this.resultCallback =resultCallback;

    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected List<SportsItem> doInBackground(String... params) {

        HttpURLConnection urlConnection;
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            int statusCode = urlConnection.getResponseCode();

            // 200 represents HTTP OK
            if (statusCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                parseResult(response.toString());

            }
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return sportsItemList;
    }

    @Override
    protected void onPostExecute(List<SportsItem> sportsItemList) {
        progressDialog.dismiss();
        if(sportsItemList!=null)
        { resultCallback.onSuccess(sportsItemList);}
        else
        {
            resultCallback.onFailure(context.getResources().getString(R.string.failure_to_load));
        }


    }


    private List<SportsItem> parseResult(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray resultsList = jsonObject.getJSONArray("results");
            sportsItemList = new ArrayList<>();

            for (int i = 0; i < resultsList.length(); i++) {
                JSONObject resultsListJSONObject =resultsList.getJSONObject(i);
                SportsItem item = new SportsItem();
                item.setTitle(resultsListJSONObject.getString("title"));
                item.setDescription(resultsListJSONObject.getString("abstract"));
                item.setByline(resultsListJSONObject.getString("byline"));
                item.setPublishedDate(resultsListJSONObject.getString("published_date"));

                JSONArray mediaList = resultsListJSONObject.getJSONArray("media");
                JSONObject mediaObject = mediaList.getJSONObject(0);
                item.setImageCaption(mediaObject.getString("caption"));
                JSONArray mediaMetaData = mediaObject.getJSONArray("media-metadata");
                JSONObject mediaMetaDataObject = mediaMetaData.getJSONObject(2);
                item.setThumbnail(mediaMetaDataObject.getString("url"));

                sportsItemList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sportsItemList;
    }
}
