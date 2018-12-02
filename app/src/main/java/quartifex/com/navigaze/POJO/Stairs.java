
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stairs {

    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("countLocalized")
    @Expose
    private String countLocalized;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCountLocalized() {
        return countLocalized;
    }

    public void setCountLocalized(String countLocalized) {
        this.countLocalized = countLocalized;
    }

}
