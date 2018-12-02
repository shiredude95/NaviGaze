package quartifex.com.navigaze.features.news;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import quartifex.com.navigaze.BaseFragment;
import quartifex.com.navigaze.FaceActivity;
import quartifex.com.navigaze.FunButton;
import quartifex.com.navigaze.R;

public class NewsFragment extends BaseFragment implements View.OnClickListener{


	private boolean isCountdownRunning = false;

	private FunButton btnNext;


	private Button btnStable;


	private int timeConstant = 5;



	private List<View> btnList = new ArrayList<>();
	private CountDownTimer mCountDownTimer;

	private TextView newsTitle;
	private TextView newsDetail;

	private List<NewsItem> newsItemList;

	//empty constructor
	public NewsFragment(){ }

	View rootView;
	private int currentItemIndex=0;

	public static NewsFragment newInstance(){
		return new NewsFragment();
	}

	private void incrementcurrentItemIndex() {

		if (currentItemIndex < newsItemList.size() - 1) {
			currentItemIndex += 1;
		}else{
			currentItemIndex=0;
		}
	}

	private void decrementcurrentItemIndex() {
		if (currentItemIndex > 0) {
			currentItemIndex -= 1;
		}else {
			currentItemIndex=newsItemList.size()-1;
		}
	}

	private int getCurrentItemIndex() {
		return currentItemIndex;
	}


	@Override
	public View customFeatureFragment(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_news,parent,false);


		btnNext=rootView.findViewById(R.id.button_right_news);
		btnStable=rootView.findViewById(R.id.button_idle_news);


		newsTitle=rootView.findViewById(R.id.news_item_title);
		newsDetail=rootView.findViewById(R.id.news_item_content);


		getAllViewsInList(rootView.findViewById(R.id.ll_news_control_container));
		setAllViewsOnClickListener();

		return rootView;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		newsItemList=createNewsItems();
		newsTitle.setText(newsItemList.get(getCurrentItemIndex()).newsTitle);
		newsDetail.setText(newsItemList.get(getCurrentItemIndex()).newsContent);

	}







	private void setAllViewsOnClickListener() {
		for (View v : btnList) {
			v.setOnClickListener(this);
		}
	}



	@Override
	public void handleLeftBlink() {
		decrementcurrentItemIndex();
		btnList.get(getCurrentItemIndex()).callOnClick();
	}

	@Override
	public void handleRightBlink() {
		incrementcurrentItemIndex();
		btnList.get(getCurrentItemIndex()).callOnClick();
	}

	@Override
	public void handleBothOpenOrClose() {
		((FaceActivity) getActivity()).onBackAction();
	}



	@Override
	public void onClick(View v) {

		switch (v.getId()){
			case R.id.button_right_news:
				uiCallToCountDown(btnNext,"right");
				break;
			case R.id.button_idle_news:
				idleClickNoTouch(btnStable);
				break;

			default:break;

		}
	}





	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}


	@Override
	public void onDetach() {
		super.onDetach();
	}

	private void uiCallToCountDown(final FunButton button, final String action) {

		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				//handle actual button clicks
//				flushButtonColorsOnIdle(null);
				Log.d("Randi", String.valueOf(Looper.myLooper() == Looper.getMainLooper()));
				button.setProgressBarVisibility(true);

				btnStable.setBackgroundColor(getResources().getColor(android.R.color.white));
				btnStable.setTextColor(getResources().getColor(android.R.color.darker_gray));

				mCountDownTimer = new CountDownTimer(timeConstant * 1000, timeConstant * 10) {
					@Override
					public void onTick(long millisUntilFinished) {
						button.setProgressbarProgress((int) (((timeConstant * 1000 - millisUntilFinished) / (float) (timeConstant * 1000)) * 100));
						isCountdownRunning = true;
					}
					@Override
					public void onFinish() {


						//Next Button button update text view from here

						button.setProgressbarProgress(100);
						button.setProgressBarVisibility(false);
						isCountdownRunning = false;

						btnStable.setBackgroundColor(getResources().getColor(R.color.colorAccent));
						btnStable.setTextColor(getResources().getColor(android.R.color.white));



						nextAction();


					}
				};
				mCountDownTimer.start();
			}


		});
	}


	private void nextAction(){

	    incrementcurrentItemIndex();
	    newsTitle.setText(newsItemList.get(getCurrentItemIndex()).newsTitle);
	    newsDetail.setText(newsItemList.get(getCurrentItemIndex()).newsContent);

	}


	//only called from actual touch, for vision itched to hardcoded logic since only two buttons
	private void flushButtonColorsOnIdle(@Nullable View v){
		if (isCountdownRunning && mCountDownTimer != null) {
			isCountdownRunning = false;
			mCountDownTimer.cancel();
			for (View v1 : btnList) {
				if (v1 instanceof FunButton) ((FunButton) v1).setProgressBarVisibility(false);
			}
		}
		for (View v1 : btnList) {
			if (!(v1 instanceof FunButton)) v1.setBackgroundColor(getResources().getColor(android.R.color.white));
		}
		if(v!=null) v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
	}

	public void idleClick(View v) {flushButtonColorsOnIdle(v);}

	private void idleClickNoTouch(final View v) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//Cancel and hide all loaders
//				flushButtonColorsOnIdle(v);
				if (isCountdownRunning && mCountDownTimer != null) {
					isCountdownRunning = false;
					mCountDownTimer.cancel();
					for (View v1 : btnList) {
						if (v1 instanceof FunButton) ((FunButton) v1).setProgressBarVisibility(false);
					}
				}

				btnStable.setBackgroundColor(getResources().getColor(R.color.colorAccent));
				btnStable.setTextColor(getResources().getColor(android.R.color.white));
			}
		});
	}

	@Override
	public void getAllViewsInList(View view) {


		if (btnList.size() != 0)
			btnList.clear();

		ViewGroup viewGroup = (ViewGroup) view;
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			View v1 = viewGroup.getChildAt(i);
			btnList.add(v1);
		}
	}


	private List<NewsItem> createNewsItems() {

		List<NewsItem> newsItems = new ArrayList<>();

		newsItems.add(new NewsItem(0,getActivity().getResources().getString(R.string.art_title_1),getActivity().getResources().getString(R.string.art_content_1)));
		newsItems.add(new NewsItem(1,getActivity().getResources().getString(R.string.art_title_2),getActivity().getResources().getString(R.string.art_content_2)));
		newsItems.add(new NewsItem(2,getActivity().getResources().getString(R.string.art_title_3),getActivity().getResources().getString(R.string.art_content_3)));
		newsItems.add(new NewsItem(3,getActivity().getResources().getString(R.string.art_title_4),getActivity().getResources().getString(R.string.art_content_4)));
		newsItems.add(new NewsItem(4,getActivity().getResources().getString(R.string.art_title_6),getActivity().getResources().getString(R.string.art_content_6)));
		newsItems.add(new NewsItem(5,getActivity().getResources().getString(R.string.art_title_8),getActivity().getResources().getString(R.string.art_content_8)));
		newsItems.add(new NewsItem(6,getActivity().getResources().getString(R.string.art_title_9),getActivity().getResources().getString(R.string.art_content_9)));



		return newsItems;
	}




}
