package quartifex.com.navigaze;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;


/**
 * A simple {@link} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {


	// TODO: Rename parameter arguments, choose names that match


	View rootView;
	private int timeConstant = 5;


	private boolean isCountdownRunning = false;

	private FunButton btnSpeedDial;
	private FunButton btnSOS;
	private FunButton btnSmartHome;
	private FunButton btnNearby;
	private FunButton btnMedia;
	private FunButton btnMessage;

	private List<View> btnList = new ArrayList<>();
	private CountDownTimer mCountDownTimer;

	public static final String SPEED_DIAL = "speed_dial";
	public static final String SOS = "sos";
	public static final String SMART_HOME = "smart_home";
	public static final String MEDIA = "media";
	public static final String MESSAGE = "message";
	public static final String NEARBY = "nearby";


	private int currentItemIndex = 0;

	public HomeFragment() {
		// Required empty public constructor
	}

	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	private void incrementcurrentItemIndex() {

		if (currentItemIndex < btnList.size() - 1) {
			currentItemIndex += 1;
		}else{
			currentItemIndex=0;
		}
	}

	private void decrementcurrentItemIndex() {
		if (currentItemIndex > 0) {
			currentItemIndex -= 1;
		}else {
			currentItemIndex=btnList.size()-1;
		}
	}

	private int getCurrentItemIndex() {
		return currentItemIndex;
	}

	@Override
	public View customFeatureFragment(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, parent, false);


		btnSpeedDial = rootView.findViewById(R.id.button_speed_dial);
		btnSOS = rootView.findViewById(R.id.button_sos);
		btnSmartHome = rootView.findViewById(R.id.button_smart_home);
		btnNearby = rootView.findViewById(R.id.button_nearby);
		btnMedia = rootView.findViewById(R.id.button_media);
		btnMessage = rootView.findViewById(R.id.button_messages);

		getAllViewsInList(rootView.findViewById(R.id.ll_home_fragment_container));
		setAllViewsOnClickListener();


		//Now specific components here (you can initialize Buttons etc)
		return rootView;
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
	    
		Log.d("CLOSED EYES", "handleBothOpenOrClose: CLOSSSSSEEE!!!!");
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_speed_dial:
				uiCallToCountDown(btnSpeedDial, SPEED_DIAL);
				break;
			case R.id.button_sos:
				uiCallToCountDown(btnSOS, SOS);
				break;
			case R.id.button_smart_home:
				uiCallToCountDown(btnSmartHome, SMART_HOME);
				break;
			case R.id.button_nearby:
				uiCallToCountDown(btnNearby, NEARBY);
				break;
			case R.id.button_media:
				uiCallToCountDown(btnMedia, MEDIA);
				break;
			case R.id.button_messages:
				uiCallToCountDown(btnMessage, MESSAGE);
				break;
			case -1:
				idleClickNoTouch(v);
				break;
			default:
				Toast.makeText(getActivity(), "IDLE", Toast.LENGTH_SHORT).show();
				break;
		}
	}


	private void uiCallToCountDown(final FunButton button, final String action) {

		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				flushButtonColorsOnIdle(null);
				button.setProgressBarVisibility(true);
				mCountDownTimer = new CountDownTimer(timeConstant * 1000, timeConstant * 10) {
					@Override
					public void onTick(long millisUntilFinished) {
						button.setProgressbarProgress((int) (((timeConstant * 1000 - millisUntilFinished) / (float) (timeConstant * 1000)) * 100));
						isCountdownRunning = true;
					}
					@Override
					public void onFinish() {
						button.setProgressbarProgress(100);
						button.setProgressBarVisibility(false);
						isCountdownRunning = false;
						((FaceActivity)getActivity()).onActionClick(action);
					}
				};
				mCountDownTimer.start();
			}


		});
	}


	private void flushButtonColorsOnIdle(@Nullable View v){
		if (isCountdownRunning && mCountDownTimer != null) {
			isCountdownRunning = false;
			mCountDownTimer.cancel();
			for (View v1 : btnList) {
				if (v1 instanceof FunButton) {
                    ((FunButton) v1).setProgressbarProgress(0);
				    ((FunButton) v1).setProgressBarVisibility(false);
                }
			}
		}
		for (View v1 : btnList) {
			if (!(v1 instanceof FunButton)) v1.setBackgroundColor(getResources().getColor(android.R.color.white));
		}
		if(v!=null) v.setBackgroundColor(getResources().getColor(R.color.lightColorAccent));
	}

	//handle idle clicks
	public void idleClick(View v) {flushButtonColorsOnIdle(v);}

	private void idleClickNoTouch(final View v) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//Cancel and hide all loaders
				flushButtonColorsOnIdle(v);
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


	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		void onFragmentInteraction(Uri uri);
	}
}
