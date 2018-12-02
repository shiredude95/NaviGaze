
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Properties {

    @SerializedName("infoPageUrl")
    @Expose
    private String infoPageUrl;
    @SerializedName("originalId")
    @Expose
    private String originalId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("accessibility")
    @Expose
    private Accessibility accessibility;
    @SerializedName("sourceId")
    @Expose
    private String sourceId;
    @SerializedName("sourceImportId")
    @Expose
    private String sourceImportId;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("localizedCategory")
    @Expose
    private String localizedCategory;
    @SerializedName("description")
    @Expose
    private String description;

    public String getInfoPageUrl() {
        return infoPageUrl;
    }

    public void setInfoPageUrl(String infoPageUrl) {
        this.infoPageUrl = infoPageUrl;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Accessibility getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Accessibility accessibility) {
        this.accessibility = accessibility;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceImportId() {
        return sourceImportId;
    }

    public void setSourceImportId(String sourceImportId) {
        this.sourceImportId = sourceImportId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalizedCategory() {
        return localizedCategory;
    }

    public void setLocalizedCategory(String localizedCategory) {
        this.localizedCategory = localizedCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
