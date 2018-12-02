package quartifex.com.navigaze;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PlaceView extends LinearLayout {

	private TextView titleView;
	private TextView detailView;


	public PlaceView(Context context, AttributeSet attributeSet) {
		super(context,attributeSet);

		TypedArray a = context.obtainStyledAttributes(attributeSet,R.styleable.PlaceView,0,0);
		String placeTitle = a.getString(R.styleable.PlaceView_placeTitle);
		String placeDetail = a.getString(R.styleable.PlaceView_placeDetail);

		a.recycle();

		setOrientation(LinearLayout.VERTICAL);
		setGravity(Gravity.CENTER_VERTICAL);

		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_place_view,this,true);

		titleView=(TextView)getChildAt(0);
		titleView.setText(placeTitle);

		detailView=(TextView)getChildAt(1);
		detailView.setText(placeDetail);

	}

	public PlaceView(Context context){
		this(context,null);
	}


	public void setPlaceTitle(String title){

		titleView.setText(title);
	}

	public void setPlaceDetail(String detail){

		detailView.setText(detail);
	}




















}






















