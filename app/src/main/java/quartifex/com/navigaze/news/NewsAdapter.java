package quartifex.com.navigaze.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import quartifex.com.navigaze.R;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

	private LayoutInflater inflater;
	private List<NewsItem> newsItems;

	public NewsAdapter(Context context,List<NewsItem> newsItem){

		this.inflater=LayoutInflater.from(context);
		this.newsItems=newsItem;
	}


	@NonNull
	@Override
	public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(inflater.inflate(R.layout.news_item,parent,false));
	}

	@Override
	public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

		NewsItem newsItem = newsItems.get(position);
		holder.titleText.setText(newsItem.newsTitle);
		holder.contentText.setText(newsItem.newsContent);

	}

	@Override
	public int getItemCount() {
		return newsItems.size();
	}


	public List<NewsItem> getNewsItems(){
		return newsItems;
	}

	public class ViewHolder extends RecyclerView.ViewHolder {


		TextView titleText;
		TextView contentText;

		ViewHolder(@NonNull View itemView) {
			super(itemView);
			this.titleText=itemView.findViewById(R.id.news_item_title);
			this.contentText=itemView.findViewById(R.id.news_item_content);
		}
	}

}
