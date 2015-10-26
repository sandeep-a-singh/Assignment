package app.weather.sunny.yahooweather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import animators.adapters.AlphaInAnimationAdapter;
import animators.adapters.ScaleInAnimationAdapter;
import networking.VolleyCall;

public class Weather extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    TextView region =(TextView) findViewById(R.id.region);
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<WeatherHolder> data_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String city =getIntent().getExtras().getString("cities");

        String tag_json_obj = "json_obj_req";


        VolleyCall.getInstance().getRequestQueue().cancelAll(tag_json_obj);
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+city+"%2C%20IN%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response is", response.toString());
                        pDialog.hide();
                        JSONObject query_result = response.optJSONObject("query");
                        JSONObject results = query_result.optJSONObject("results");
                        JSONObject channel = results.optJSONObject("channel");
                        JSONObject loc = channel.optJSONObject("location");
                        JSONObject item = channel.optJSONObject("item");

                        String title = null;

                        try {
                            title = item.getString("title");
                            Log.d("Title","title "+title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Title", "title Not found ");
                        }
                        Log.d("Title", "title " + title);

                        region.setText(title);
                        int count =query_result.optInt("count");
                        Log.d("Yahoo Weather",String.valueOf(count));


                        String data = "";
                        data_list =new ArrayList<>();

                        try {
                            //Get the instance of JSONArray that contains JSONObjects
                            JSONArray jsonArray = item.optJSONArray("forecast");

                            //Iterate the jsonArray and print the info of JSONObjects
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                WeatherHolder weather_data=new WeatherHolder();

                                String code = jsonObject.optString("code").toString();
                                weather_data.setCode(code);
                                String text = jsonObject.optString("text").toString();
                                weather_data.setText(text);
                                String date = jsonObject.optString("date").toString();
                                weather_data.setDate(date);
                                String high = jsonObject.optString("high").toString();
                                weather_data.setHigh("Max:"+high+"F");
                                String low = jsonObject.optString("low").toString();
                                weather_data.setLow("Min:" + low + "F");

                                data += "Node"+i+" : \n code= "+ code +" \n text= "+ text +" \n date= "+ date +" \n ";


                                data_list.add(weather_data);
                            }

                        } catch (JSONException e) {e.printStackTrace();}








try {
    mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewxml);

    // use this setting to improve performance if you know that changes
    // in content do not change the layout size of the RecyclerView
    mRecyclerView.setHasFixedSize(true);

    // use a linear layout manager
    mLayoutManager = new LinearLayoutManager(getApplicationContext());

    mRecyclerView.setLayoutManager(mLayoutManager);
    mAdapter = new Weather_Adapter(data_list,getApplicationContext());

    AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
    ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
    scaleAdapter.setFirstOnly(false);
    scaleAdapter.setInterpolator(new OvershootInterpolator());
    mRecyclerView.setAdapter(scaleAdapter);

}catch (Exception e)
{
    e.printStackTrace();
}







                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Response error", "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        VolleyCall.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
}
}