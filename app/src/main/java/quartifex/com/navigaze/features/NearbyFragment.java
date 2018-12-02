package quartifex.com.navigaze.features;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quartifex.com.navigaze.BaseFragment;
import quartifex.com.navigaze.FaceActivity;
import quartifex.com.navigaze.FunButton;
import quartifex.com.navigaze.HomeFragment;
import quartifex.com.navigaze.HttpWrapper.Network;
import quartifex.com.navigaze.POJO.Data;
import quartifex.com.navigaze.POJO.Node;
import quartifex.com.navigaze.R;

public class NearbyFragment extends BaseFragment implements View.OnClickListener {


    // TODO: Rename parameter arguments, choose names that match


    View rootView;
    private HomeFragment.OnFragmentInteractionListener mListener;
    private int timeConstant = 5;


    private boolean isCountdownRunning = false;

    private FunButton btnSpeedDial;
    private FunButton btnSOS;
    private FunButton btnSmartHome;
    private FunButton btnNearby;
    private FunButton btnMedia;
    private FunButton btnMessage;
    private LinearLayout linearLayout;

    private List<View> btnList = new ArrayList<>();
    private CountDownTimer mCountDownTimer;

    public static final String SPEED_DIAL = "speed_dial";
    public static final String SOS = "sos";
    public static final String SMART_HOME = "smart_home";
    public static final String MEDIA = "media";
    public static final String MESSAGE = "message";
    public static final String NEARBY = "nearby";


    private int currentItemIndex = 0;

    public NearbyFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_nearby, parent, false);


        String url = "http://wheelmap.org/api/nodes?api_key=-DFwtnoyB15aUuEvHq1d&bbox=13.341,52.505,13.434,52.523&per_page=10&wheelchair=yes";
        new Network(getContext(), Data.class).execute(url);

        btnSpeedDial = rootView.findViewById(R.id.button_speed_dial);
        btnSOS = rootView.findViewById(R.id.button_sos);
        btnSmartHome = rootView.findViewById(R.id.button_smart_home);
        btnNearby = rootView.findViewById(R.id.button_nearby);
        btnMedia = rootView.findViewById(R.id.button_media);
        btnMessage = rootView.findViewById(R.id.button_messages);
        linearLayout = rootView.findViewById(R.id.ll_nearby_fragment_container);
        linearLayout.setVisibility(View.INVISIBLE);

        getAllViewsInList(rootView.findViewById(R.id.ll_nearby_fragment_container));
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
        if (context instanceof HomeFragment.OnFragmentInteractionListener) {
            mListener = (HomeFragment.OnFragmentInteractionListener) context;
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
                        button.setProgressBarVisibility(false);
                        isCountdownRunning = false;
                        ((FaceActivity)getActivity()).onActionClick(action);
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

    @Override
    public <k> void updateView(List<k> nodes) {
        try{
            btnSpeedDial.setFunButtonText(((Node)nodes.get(0)).getName());
            btnSOS.setFunButtonText(((Node)nodes.get(1)).getName());
            btnSmartHome.setFunButtonText(((Node)nodes.get(2)).getName());
            btnNearby.setFunButtonText(((Node)nodes.get(3)).getName());
            btnMedia.setFunButtonText(((Node)nodes.get(4)).getName());
            btnMessage.setFunButtonText(((Node)nodes.get(5)).getName());
            linearLayout.setVisibility(View.VISIBLE);
            Log.d("NEARBY", ((Node)nodes.get(0)).toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
