
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restroom {

    @SerializedName("ratingForWheelchair")
    @Expose
    private long ratingForWheelchair;
    @SerializedName("ratingForWheelchairLocalized")
    @Expose
    private String ratingForWheelchairLocalized;

    public long getRatingForWheelchair() {
        return ratingForWheelchair;
    }

    public void setRatingForWheelchair(long ratingForWheelchair) {
        this.ratingForWheelchair = ratingForWheelchair;
    }

    public String getRatingForWheelchairLocalized() {
        return ratingForWheelchairLocalized;
    }

    public void setRatingForWheelchairLocalized(String ratingForWheelchairLocalized) {
        this.ratingForWheelchairLocalized = ratingForWheelchairLocalized;
    }

}
