package quartifex.com.navigaze.features;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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
    private int timeConstant = 5;


    private boolean isCountdownRunning = false;

    private FunButton btnn1;
    private FunButton btnn2;
    private FunButton btnn3;
    private FunButton btnn4;
    private FunButton btnn5;
    //    private FunButton btnMessage;
    private LinearLayout linearLayout;

    private List<View> btnList = new ArrayList<>();
    private CountDownTimer mCountDownTimer;

    public static final String P1 = "p1";
    public static final String P2 = "p2";
    public static final String P3 = "p3";
    public static final String P4 = "p4";
    public static final String P5 = "p5";
    public static final String P6 = "p6";


    private int currentItemIndex = 0;

    public NearbyFragment() {
        // Required empty public constructor
    }

    public static NearbyFragment newInstance() {
        return new NearbyFragment();
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

        btnn1 = rootView.findViewById(R.id.button_p1);
        btnn2 = rootView.findViewById(R.id.button_p2);
        btnn3 = rootView.findViewById(R.id.button_p3);
        btnn4 = rootView.findViewById(R.id.button_p4);
        btnn5 = rootView.findViewById(R.id.button_p5);
//        btnMessage = rootView.findViewById(R.id.button_messages);
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
        ((FaceActivity) getActivity()).onBackAction();
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
            case R.id.button_p1:
                uiCallToCountDown(btnn1, P1);
                break;
            case R.id.button_p2:
                uiCallToCountDown(btnn2, P2);
                break;
            case R.id.button_p3:
                uiCallToCountDown(btnn3, P3);
                break;
            case R.id.button_p4:
                uiCallToCountDown(btnn4, P4);
                break;
            case R.id.button_p5:
                uiCallToCountDown(btnn5, P5);
                break;
//            case R.id.button_messages:
//                uiCallToCountDown(btnMessage, MESSAGE);
//                break;
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
                        if (v1 instanceof FunButton)
                            ((FunButton) v1).setProgressBarVisibility(false);

                    }
                }

                for (View v1 : btnList) {

                    if (!(v1 instanceof FunButton))
                        v1.setBackgroundColor(getResources().getColor(android.R.color.white));

                }

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
                        //TODO action

                    }
                };
                mCountDownTimer.start();
            }


        });
    }

    private void flushButtonColorsOnIdle(@Nullable View v) {
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
        if (v != null) v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    //handle idle clicks
    public void idleClick(View v) {
        flushButtonColorsOnIdle(v);
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
        try {
            btnn1.setFunButtonText(getTextValue(((Node) nodes.get(0))));
            btnn2.setFunButtonText(getTextValue(((Node) nodes.get(1))));
            btnn3.setFunButtonText(getTextValue(((Node) nodes.get(2))));
            btnn4.setFunButtonText(getTextValue(((Node) nodes.get(4))));
            btnn5.setFunButtonText(getTextValue(((Node) nodes.get(5))));
//            btnMessage.setFunButtonText(((Node) nodes.get(5)).getName());
            linearLayout.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTextValue(Node node) {
        String value = node.getName();
        value += "\n" + node.getCategory().getIdentifier().replaceAll("_", " ") + ", " + node.getNodeType().getIdentifier().replaceAll("_", " ");
        return value;
    }

}
