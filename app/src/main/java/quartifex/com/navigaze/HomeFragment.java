package quartifex.com.navigaze;

import android.content.Context;
import android.content.Intent;
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
	private OnFragmentInteractionListener mListener;
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
		}
	}

	private void decrementcurrentItemIndex() {
		if (currentItemIndex > 0) {
			currentItemIndex -= 1;
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

	}


	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_speed_dial:
				uiCallToCountDown(btnSpeedDial, new Intent(Intent.ACTION_CALL));
				break;
			case R.id.button_sos:
				uiCallToCountDown(btnSOS, new Intent(Intent.ACTION_CALL));
				break;
			case R.id.button_smart_home:
				uiCallToCountDown(btnSmartHome, new Intent(Intent.ACTION_CALL));
				break;
			case R.id.button_nearby:
				uiCallToCountDown(btnNearby, new Intent(Intent.ACTION_CALL));
				break;
			case R.id.button_media:
				uiCallToCountDown(btnMedia, new Intent(Intent.ACTION_CALL));
				break;
			case R.id.button_messages:
				uiCallToCountDown(btnMessage, new Intent(Intent.ACTION_CALL));
				break;
			case -1:
				idleClickNoTouch(v);
				break;
			default:
				Toast.makeText(getActivity(), "IDLE", Toast.LENGTH_SHORT).show();
				break;
		}
	}


	private void uiCallToCountDown(final FunButton button, Intent i) {

		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				//handle actual button clicks
				if (isCountdownRunning && mCountDownTimer != null) {
					isCountdownRunning = false;
					mCountDownTimer.cancel();
					for (View v1 : btnList) {

						if (v1 instanceof FunButton) ((FunButton) v1).setProgressBarVisibility(false);

					}
				}

				for (View v1 : btnList) {

					if (!(v1 instanceof FunButton))
						v1.setBackgroundColor(getResources().getColor(android.R.color.white));

				}

				Log.d("Randi", String.valueOf(Looper.myLooper() == Looper.getMainLooper()));
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
						Toast.makeText(getActivity(), button.getButtonText(), Toast.LENGTH_LONG).show();
						button.setProgressBarVisibility(false);
						isCountdownRunning = false;
					}
				};
				mCountDownTimer.start();
			}


		});
	}

	//handle idle clicks
	public void idleClick(View v) {

		if (isCountdownRunning && mCountDownTimer != null) {

			isCountdownRunning = false;
			mCountDownTimer.cancel();
			for (View v1 : btnList) {
				((FunButton) v1).setProgressBarVisibility(false);
			}
		}
		Toast.makeText(getActivity(), "IDLE", Toast.LENGTH_SHORT).show();

	}

	private void idleClickNoTouch(final View v) {

		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				//Cancel and hide all loaders
				if (isCountdownRunning && mCountDownTimer != null) {
					isCountdownRunning = false;
					mCountDownTimer.cancel();
					for (View v1 : btnList) {

						if (v1 instanceof FunButton)
							((FunButton) v1).setProgressBarVisibility(false);
						else {
							v1.setBackgroundColor(getResources().getColor(android.R.color.white));
						}
					}
				}


				v.setBackgroundColor(getResources().getColor(R.color.colorAccent));

			}
		});

//		Toast.makeText(getActivity(),"IDLE",Toast.LENGTH_SHORT).show();
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
