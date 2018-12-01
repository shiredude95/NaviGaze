package quartifex.com.navigaze.HttpWrapper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Divyavijay Sahay on 7/7/2018.
 */
public class Network extends AsyncTask<String, Void, Object> {

    private NetworkListener networkListener;
    private Class jsonClass;
    private Context context;

    public Network(Context context, Class jsonClass) {
        this.networkListener = (NetworkListener) context;
        this.jsonClass = jsonClass;
        this.context = context;
    }

    public interface NetworkListener {
        void onNetworkResultReceive(Object o);
    }

    @Override
    protected Object doInBackground(String... strings) {
        Request request = new Request.Builder().url(strings[0]).build();

        try {

            Response response = (new OkHttpClient()).newCall(request).execute();

            if (response.isSuccessful()) {
                String json = response.body().string();
                Gson gson = new GsonBuilder().create();
                return gson.fromJson(json, jsonClass);
            } else {
                Log.e("Network", "Network Error !!" + response.code());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        networkListener.onNetworkResultReceive(jsonClass.cast(o));
    }
}
