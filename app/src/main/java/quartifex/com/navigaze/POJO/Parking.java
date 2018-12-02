
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parking {

    @SerializedName("forWheelchairUsers")
    @Expose
    private ForWheelchairUsers forWheelchairUsers;
    @SerializedName("forWheelchairUsersLocalized")
    @Expose
    private String forWheelchairUsersLocalized;

    public ForWheelchairUsers getForWheelchairUsers() {
        return forWheelchairUsers;
    }

    public void setForWheelchairUsers(ForWheelchairUsers forWheelchairUsers) {
        this.forWheelchairUsers = forWheelchairUsers;
    }

    public String getForWheelchairUsersLocalized() {
        return forWheelchairUsersLocalized;
    }

    public void setForWheelchairUsersLocalized(String forWheelchairUsersLocalized) {
        this.forWheelchairUsersLocalized = forWheelchairUsersLocalized;
    }

}
