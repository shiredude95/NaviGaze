package quartifex.com.navigaze.features;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import quartifex.com.navigaze.BaseFragment;
import quartifex.com.navigaze.FaceActivity;
import quartifex.com.navigaze.FunButton;
import quartifex.com.navigaze.R;

public class SmartHomeFragment extends BaseFragment implements View.OnClickListener {

    View rootView;
    private int timeConstant = 5;

    private boolean isCountdownRunning = false;

    private FunButton btnsh1;
    private FunButton btnsh2;
    private FunButton btnsh3;
    private FunButton btnsh4;
    private FunButton btnsh5;

    TextToSpeech t1;
    public static final String TURN_ON = "Turn on the lights";
    public static final String TURN_OFF = "Turn off the lights";
    public static final String INIT_STRING = "Ok google, talk to Navigaze helper.";

    private List<View> btnList = new ArrayList<>();
    private CountDownTimer mCountDownTimer;

    private int currentItemIndex = 0;

    public SmartHomeFragment() {
        // Required empty public constructor
    }

    public static SmartHomeFragment newInstance() {
        return new SmartHomeFragment();
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
        rootView = inflater.inflate(R.layout.fragment_smart_home, parent, false);

        btnsh1 = rootView.findViewById(R.id.button_sh1);
        btnsh2 = rootView.findViewById(R.id.button_sh2);
        btnsh3 = rootView.findViewById(R.id.button_sh3);
        btnsh4 = rootView.findViewById(R.id.button_sh4);
        btnsh5 = rootView.findViewById(R.id.button_sh5);

        getAllViewsInList(rootView.findViewById(R.id.ll_message_fragment_container));
        setAllViewsOnClickListener();

        t1=new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    t1.setSpeechRate(0.7f);
                    t1.speak(INIT_STRING, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

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
            case R.id.button_sh1:
                sendMessage(btnsh1);
                break;
            case R.id.button_sh2:
                sendMessage(btnsh2);
                break;
            case R.id.button_sh3:
                sendMessage(btnsh3);
                break;
            case R.id.button_sh4:
                sendMessage(btnsh4);
                break;
            case R.id.button_sh5:
                sendMessage(btnsh5);
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
                        t1.speak(button.getButtonText(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                };
                mCountDownTimer.start();
            }


        });

    }


    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
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
