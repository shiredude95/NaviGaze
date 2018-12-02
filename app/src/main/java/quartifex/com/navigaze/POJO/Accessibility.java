
package quartifex.com.navigaze.POJO;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Accessibility {

    @SerializedName("accessibleWith")
    @Expose
    private AccessibleWith accessibleWith;
    @SerializedName("accessibleWithLocalized")
    @Expose
    private String accessibleWithLocalized;
    @SerializedName("areas")
    @Expose
    private List<Area> areas = null;
    @SerializedName("areasLocalized")
    @Expose
    private String areasLocalized;

    public AccessibleWith getAccessibleWith() {
        return accessibleWith;
    }

    public void setAccessibleWith(AccessibleWith accessibleWith) {
        this.accessibleWith = accessibleWith;
    }

    public String getAccessibleWithLocalized() {
        return accessibleWithLocalized;
    }

    public void setAccessibleWithLocalized(String accessibleWithLocalized) {
        this.accessibleWithLocalized = accessibleWithLocalized;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public String getAreasLocalized() {
        return areasLocalized;
    }

    public void setAreasLocalized(String areasLocalized) {
        this.areasLocalized = areasLocalized;
    }

}
