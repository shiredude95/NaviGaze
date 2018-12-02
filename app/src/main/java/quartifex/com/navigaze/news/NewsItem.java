package quartifex.com.navigaze.news;

public class NewsItem {


	public String newsTitle;
	public String newsContent;
	public int id;

	public NewsItem(int id,String newsTitle,String newsContent){

		this.id=id;
		this.newsTitle=newsTitle;
		this.newsContent=newsContent;
	}


}
