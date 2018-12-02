
package quartifex.com.navigaze.POJO;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseObject {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("featureCount")
    @Expose
    private long featureCount;
    @SerializedName("totalFeatureCount")
    @Expose
    private long totalFeatureCount;
    @SerializedName("related")
    @Expose
    private Related related;
    @SerializedName("features")
    @Expose
    private List<Feature> features = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getFeatureCount() {
        return featureCount;
    }

    public void setFeatureCount(long featureCount) {
        this.featureCount = featureCount;
    }

    public long getTotalFeatureCount() {
        return totalFeatureCount;
    }

    public void setTotalFeatureCount(long totalFeatureCount) {
        this.totalFeatureCount = totalFeatureCount;
    }

    public Related getRelated() {
        return related;
    }

    public void setRelated(Related related) {
        this.related = related;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

}
