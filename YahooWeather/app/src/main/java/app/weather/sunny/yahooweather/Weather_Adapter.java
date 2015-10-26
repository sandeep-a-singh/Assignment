package app.weather.sunny.yahooweather;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunny on 26/10/15.
 */
public class Weather_Adapter extends RecyclerView.Adapter<Weather_Adapter.ViewHolder> {
    private List<WeatherHolder> dataset;

Context context;


    public Weather_Adapter(List<WeatherHolder> dataset, Context context) {
        this.dataset = dataset;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView weather_icon;
        public TextView weather_date;
        public TextView weather_max_temp;
        public TextView weather_min_temp;
        public TextView weather_climate;

        public ViewHolder(View v) {
            super(v);
            weather_icon=(ImageView) v.findViewById(R.id.weather_image);
            weather_max_temp = (TextView) v.findViewById(R.id.maximum_temp);
            weather_min_temp = (TextView) v.findViewById(R.id.minimum_temp);
            weather_climate = (TextView) v.findViewById(R.id.climate);

            weather_date = (TextView) v.findViewById(R.id.tv_date);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forecast, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.weather_date.setText(dataset.get(position).getDate());
        holder.weather_min_temp.setText(dataset.get(position).getLow());

        String Image_Path = "icon"+dataset.get(position).getCode();


        holder.weather_max_temp.setText(dataset.get(position).getHigh());
        holder.weather_climate.setText(dataset.get(position).getText());
        System.out.println("On Bind");

       int id = context.getResources().getIdentifier(Image_Path, "drawable", context.getPackageName());

       Drawable image =context.getResources().getDrawable(id);
       holder.weather_icon.setBackground(image);
 }

    @Override
    public int getItemCount() {
        return dataset.size();
    }




}