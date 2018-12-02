
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartiallyAccessibleWith {

    @SerializedName("wheelchair")
    @Expose
    private Boolean wheelchair;
    @SerializedName("wheelchairLocalized")
    @Expose
    private String wheelchairLocalized;

    public Boolean getWheelchair() {
        return wheelchair;
    }

    public void setWheelchair(Boolean wheelchair) {
        this.wheelchair = wheelchair;
    }

    public String getWheelchairLocalized() {
        return wheelchairLocalized;
    }

    public void setWheelchairLocalized(String wheelchairLocalized) {
        this.wheelchairLocalized = wheelchairLocalized;
    }

}
