package quartifex.com.navigaze;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.face.Face;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import quartifex.com.navigaze.HttpWrapper.Network;
import quartifex.com.navigaze.POJO.Data;
import quartifex.com.navigaze.face.FaceDetectorActivity;
import quartifex.com.navigaze.features.NearbyFragment;



public class FaceActivity extends AppCompatActivity implements FaceDetectorActivity.FaceListener, HomeFragment.OnFragmentInteractionListener, Network.NetworkListener, BaseFragment.FragmentActionListener {

    private List<Float> leftEyeOpenProbs;
    private List<Float> rightEyeOpenProbs;
    private int FPS = 10;
    private int LEFT_EYE_OPEN_FLAG = 0;
    private int RIGHT_EYE_OPEN_FLAG = 1;
    private int BOTH_EYE_CLOSED = 2;
    private int NO_ACTION_FLAG = 3;
    private float MIN_PROBABILITY_THRESHOLD = 0.2f;
    private int currentAction = -1;
    private int prevAction = -1;

    HomeFragment homeFragment;
    private BaseFragment currentFragmet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leftEyeOpenProbs = new ArrayList<>();
        rightEyeOpenProbs = new ArrayList<>();
        displayFragment();

    }

    public void displayFragment() {
        homeFragment = HomeFragment.newInstance();
        currentFragmet = homeFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container_list, homeFragment).addToBackStack(null).commit();
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        homeFragment = (HomeFragment) fragmentManager.findFragmentById(R.id.fragment_container_list);
        if (homeFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(homeFragment);
        }
    }


    @Override
    public void getFace(Face face) {
        if (leftEyeOpenProbs.size() < FPS) {
            leftEyeOpenProbs.add(face.getIsLeftEyeOpenProbability());
            rightEyeOpenProbs.add(face.getIsRightEyeOpenProbability());
        } else {
            if (getAction(leftEyeOpenProbs, rightEyeOpenProbs) == LEFT_EYE_OPEN_FLAG) {
                updateCurrentPreviousFunction(LEFT_EYE_OPEN_FLAG);
            } else if (getAction(leftEyeOpenProbs, rightEyeOpenProbs) == RIGHT_EYE_OPEN_FLAG) {
                updateCurrentPreviousFunction(RIGHT_EYE_OPEN_FLAG);
			} else if (getAction(leftEyeOpenProbs,rightEyeOpenProbs) == BOTH_EYE_CLOSED) {
			    updateCurrentPreviousFunction(BOTH_EYE_CLOSED);
            }
			else {
			    updateCurrentPreviousFunction(NO_ACTION_FLAG);
            }
            leftEyeOpenProbs.clear();
            rightEyeOpenProbs.clear();
        }
    }

    private void updateCurrentPreviousFunction(int forAction) {
        currentAction = forAction;
        Log.d("Action update", "updateCurrentPreviousFunction:: current action::" + currentAction
                + "previous action::" + prevAction);
        if (currentAction != prevAction) {
            if (currentAction == LEFT_EYE_OPEN_FLAG) {
                currentFragmet.handleRightBlink();
            } else if (currentAction == RIGHT_EYE_OPEN_FLAG) {
                currentFragmet.handleLeftBlink();
            } else if (currentAction == BOTH_EYE_CLOSED) {
	            currentFragmet.handleBothOpenOrClose();
            } else {
                // NO ACTION
            }
        } else {
            return;
        }
        prevAction = currentAction;
    }

	private int getAction (List<Float> leftEyeOpenProbs, List<Float> rightEyeOpenProbs) {
		int leftEyeOpenCount = 0;
		int rightEyeOpenCount = 0;
		int bothEyeClosedCount = 0;
		for (int i = 0; i < leftEyeOpenProbs.size(); i++) {
//			Log.d("FACE DETECTIOn", "getAction: values"+leftEyeOpenProbs.get(i)+"/"+rightEyeOpenProbs.get(i));
			if (isFirstEyeOpen(leftEyeOpenProbs.get(i), rightEyeOpenProbs.get(i))) {
				leftEyeOpenCount++;
			} else if (isFirstEyeOpen(rightEyeOpenProbs.get(i), leftEyeOpenProbs.get(i))) {
				rightEyeOpenCount++;
			} else if (isBothEyeClosed(leftEyeOpenProbs.get(i), rightEyeOpenProbs.get(i))) {
			    bothEyeClosedCount++;
            }
		}

		Log.d("FACE DETECTIOn", "getAction: "+(FPS * 0.6)+"LEFT/RIGHT"+leftEyeOpenCount+"/"+rightEyeOpenCount);
		if (isSufficient(rightEyeOpenCount)) {
//			Log.d("FACE DETECTIOn", "getAction: RIght"+rightEyeOpenCount);
			return LEFT_EYE_OPEN_FLAG;
		}else if (isSufficient(leftEyeOpenCount)) {
//			Log.d("FACE DETECTIOn", "getAction: left"+leftEyeOpenCount);
			return RIGHT_EYE_OPEN_FLAG;
		}else if (isSufficient(bothEyeClosedCount)) {
            return BOTH_EYE_CLOSED;
        } else {
			return NO_ACTION_FLAG;
		}
	}


    private boolean isFirstEyeOpen(float firstEyeProb, float secondEyeProb) {
        Log.d("FACE DETECTIOn", "getAction: secondEYEProb ::" + secondEyeProb);
        return firstEyeProb >= (2 * secondEyeProb) && (firstEyeProb >= MIN_PROBABILITY_THRESHOLD);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void idleClick(View v) {
        currentFragmet.idleClick(v);
    }

    private boolean isBothEyeClosed (float firstEyeProb, float secondEyeProb) {
        Log.d("FACE DETECTIOn closed", "getAction: secondEYEProb ::"+secondEyeProb+"first-->"+firstEyeProb);
        return firstEyeProb <= 0.2f && secondEyeProb <= 0.2f;
    }

    private boolean isSufficient (int count) {
	    return count > FPS * 0.6;
    }


    @Override
    public void onActionClick(String action) {
        Toast.makeText(this, action, Toast.LENGTH_LONG).show();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (action) {
            case HomeFragment.SPEED_DIAL:
                break;
            case HomeFragment.SOS:
                break;
            case HomeFragment.MEDIA:
                break;
            case HomeFragment.MESSAGE:
                break;
            case HomeFragment.NEARBY:
                currentFragmet = new NearbyFragment();
                break;
            case HomeFragment.SMART_HOME:
                break;
        }

        fragmentTransaction.remove(homeFragment);
        fragmentTransaction.add(R.id.fragment_container_list, currentFragmet).commit();
    }

    @Override
    public void onBackAction() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(currentFragmet);
        currentFragmet = homeFragment;
        fragmentTransaction.add(R.id.fragment_container_list, currentFragmet).commit();
    }

    @Override
    public void onNetworkResultReceive(Object o) {
        if (o instanceof Data) {
            Data data = (Data) o;
            currentFragmet.updateView(data.getNodes());
            Toast.makeText(this, data.getNodes().size()+"", Toast.LENGTH_SHORT).show();
        }
    }
}
