
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessibleWith {

    @SerializedName("wheelchair")
    @Expose
    private boolean wheelchair;
    @SerializedName("wheelchairLocalized")
    @Expose
    private String wheelchairLocalized;

    public boolean isWheelchair() {
        return wheelchair;
    }

    public void setWheelchair(boolean wheelchair) {
        this.wheelchair = wheelchair;
    }

    public String getWheelchairLocalized() {
        return wheelchairLocalized;
    }

    public void setWheelchairLocalized(String wheelchairLocalized) {
        this.wheelchairLocalized = wheelchairLocalized;
    }

}
