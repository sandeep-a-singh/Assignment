package app.weather.sunny.yahooweather;

/**
 * Created by sunny on 26/10/15.
 */
public class WeatherHolder {
    String date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String code;
    String text;
    String high;
    String low;




    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }






    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
