package quartifex.com.navigaze.features;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quartifex.com.navigaze.BaseFragment;
import quartifex.com.navigaze.FaceActivity;
import quartifex.com.navigaze.FunButton;
import quartifex.com.navigaze.HttpWrapper.Network;
import quartifex.com.navigaze.POJO.Data;
import quartifex.com.navigaze.R;

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    View rootView;
    private int timeConstant = 5;

    private boolean isCountdownRunning = false;

    private FunButton btnh1;
    private FunButton btnh2;
    private FunButton btnh3;
    private FunButton btnh4;
    private FunButton btnh5;

    private List<View> btnList = new ArrayList<>();
    private CountDownTimer mCountDownTimer;

    private String caretaker = "8572696933";


    private int currentItemIndex = 0;

    public MessageFragment() {
        // Required empty public constructor
    }

    public static MessageFragment newInstance() {
        return new MessageFragment();
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
        rootView = inflater.inflate(R.layout.fragment_message, parent, false);

        btnh1 = rootView.findViewById(R.id.button_h1);
        btnh2 = rootView.findViewById(R.id.button_h2);
        btnh3 = rootView.findViewById(R.id.button_h3);
        btnh4 = rootView.findViewById(R.id.button_h4);
        btnh5 = rootView.findViewById(R.id.button_h5);

        getAllViewsInList(rootView.findViewById(R.id.ll_message_fragment_container));
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
        ((FaceActivity)getActivity()).onBackAction();
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
            case R.id.button_h1:
                sendMessage(btnh1);
                break;
            case R.id.button_h2:
                sendMessage(btnh2);
                break;
            case R.id.button_h3:
                sendMessage(btnh3);
                break;
            case R.id.button_h4:
                sendMessage(btnh4);
                break;
            case R.id.button_h5:
                sendMessage(btnh5);
                break;
            case -1:
                idleClickNoTouch(v);
                break;
            default:
                Toast.makeText(getActivity(), "IDLE", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void sendMessage(final FunButton button) {

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

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(caretaker, null, button.getButtonText(), null, null);
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
                if (v1 instanceof FunButton) ((FunButton) v1).setProgressBarVisibility(false);
            }
        }
        for (View v1 : btnList) {
            if (!(v1 instanceof FunButton)) v1.setBackgroundColor(getResources().getColor(android.R.color.white));
        }
        if(v!=null) v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    //handle idle clicks
    public void idleClick(View v) {flushButtonColorsOnIdle(v);}

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

}
