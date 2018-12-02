
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForWheelchairUsers {

    @SerializedName("isAvailable")
    @Expose
    private Boolean isAvailable;
    @SerializedName("isAvailableLocalized")
    @Expose
    private String isAvailableLocalized;

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getIsAvailableLocalized() {
        return isAvailableLocalized;
    }

    public void setIsAvailableLocalized(String isAvailableLocalized) {
        this.isAvailableLocalized = isAvailableLocalized;
    }

}
