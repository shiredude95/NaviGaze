
package quartifex.com.navigaze.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("num_pages")
    @Expose
    private Integer numPages;
    @SerializedName("item_count")
    @Expose
    private Integer itemCount;
    @SerializedName("item_count_total")
    @Expose
    private Integer itemCountTotal;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getItemCountTotal() {
        return itemCountTotal;
    }

    public void setItemCountTotal(Integer itemCountTotal) {
        this.itemCountTotal = itemCountTotal;
    }

}
