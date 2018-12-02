
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entrance {

    @SerializedName("stairs")
    @Expose
    private Stairs stairs;
    @SerializedName("stairsLocalized")
    @Expose
    private String stairsLocalized;
    @SerializedName("ratingForWheelchair")
    @Expose
    private long ratingForWheelchair;
    @SerializedName("ratingForWheelchairLocalized")
    @Expose
    private String ratingForWheelchairLocalized;

    public Stairs getStairs() {
        return stairs;
    }

    public void setStairs(Stairs stairs) {
        this.stairs = stairs;
    }

    public String getStairsLocalized() {
        return stairsLocalized;
    }

    public void setStairsLocalized(String stairsLocalized) {
        this.stairsLocalized = stairsLocalized;
    }

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
