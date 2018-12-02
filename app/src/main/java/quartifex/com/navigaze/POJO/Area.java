
package quartifex.com.navigaze.POJO;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area {

    @SerializedName("entrances")
    @Expose
    private List<Entrance> entrances = null;
    @SerializedName("entrancesLocalized")
    @Expose
    private String entrancesLocalized;
    @SerializedName("restrooms")
    @Expose
    private List<Restroom> restrooms = null;
    @SerializedName("ratingSpacious")
    @Expose
    private long ratingSpacious;
    @SerializedName("restroomsLocalized")
    @Expose
    private String restroomsLocalized;
    @SerializedName("ratingSpaciousLocalized")
    @Expose
    private String ratingSpaciousLocalized;

    public List<Entrance> getEntrances() {
        return entrances;
    }

    public void setEntrances(List<Entrance> entrances) {
        this.entrances = entrances;
    }

    public String getEntrancesLocalized() {
        return entrancesLocalized;
    }

    public void setEntrancesLocalized(String entrancesLocalized) {
        this.entrancesLocalized = entrancesLocalized;
    }

    public List<Restroom> getRestrooms() {
        return restrooms;
    }

    public void setRestrooms(List<Restroom> restrooms) {
        this.restrooms = restrooms;
    }

    public long getRatingSpacious() {
        return ratingSpacious;
    }

    public void setRatingSpacious(long ratingSpacious) {
        this.ratingSpacious = ratingSpacious;
    }

    public String getRestroomsLocalized() {
        return restroomsLocalized;
    }

    public void setRestroomsLocalized(String restroomsLocalized) {
        this.restroomsLocalized = restroomsLocalized;
    }

    public String getRatingSpaciousLocalized() {
        return ratingSpaciousLocalized;
    }

    public void setRatingSpaciousLocalized(String ratingSpaciousLocalized) {
        this.ratingSpaciousLocalized = ratingSpaciousLocalized;
    }

}
