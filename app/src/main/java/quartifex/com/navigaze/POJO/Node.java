
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class Node {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("node_type")
    @Expose
    private NodeType nodeType;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("wheelchair")
    @Expose
    private String wheelchair;
    @SerializedName("wheelchair_description")
    @Expose
    private String wheelchairDescription;
    @SerializedName("street")
    @Expose
    private Object street;
    @SerializedName("housenumber")
    @Expose
    private Object housenumber;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("postcode")
    @Expose
    private Object postcode;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("name")
    @Expose
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getWheelchair() {
        return wheelchair;
    }

    public void setWheelchair(String wheelchair) {
        this.wheelchair = wheelchair;
    }

    public String getWheelchairDescription() {
        return wheelchairDescription;
    }

    public void setWheelchairDescription(String wheelchairDescription) {
        this.wheelchairDescription = wheelchairDescription;
    }

    public Object getStreet() {
        return street;
    }

    public void setStreet(Object street) {
        this.street = street;
    }

    public Object getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(Object housenumber) {
        this.housenumber = housenumber;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getPostcode() {
        return postcode;
    }

    public void setPostcode(Object postcode) {
        this.postcode = postcode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return getId() + " " + getName() + " " + getCategory();
    }
}
