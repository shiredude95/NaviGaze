package quartifex.com.navigaze.features.news;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class NewsDiffCallback extends DiffUtil.Callback {

	private final List<NewsItem> oldList;
	private final List<NewsItem> newList;

	public NewsDiffCallback(List<NewsItem> newList,List<NewsItem> oldList){

		this.oldList=oldList;
		this.newList=newList;
	}





	@Override
	public int getOldListSize() {
		return oldList.size();
	}

	@Override
	public int getNewListSize() {
		return newList.size();
	}

	@Override
	public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
		return oldList.get(oldItemPosition).id==oldList.get(newItemPosition).id;
	}

	@Override
	public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

		NewsItem newNewsItem = oldList.get(oldItemPosition);
		NewsItem oldNewsItem = newList.get(newItemPosition);
		return oldNewsItem.newsTitle.equals(newNewsItem.newsTitle)
				&&oldNewsItem.newsContent.equals(newNewsItem.newsContent);
	}
}
