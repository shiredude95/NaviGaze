
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Related {

    @SerializedName("licenses")
    @Expose
    private Licenses licenses;

    public Licenses getLicenses() {
        return licenses;
    }

    public void setLicenses(Licenses licenses) {
        this.licenses = licenses;
    }

}
