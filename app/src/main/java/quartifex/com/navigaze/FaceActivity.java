package quartifex.com.navigaze;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.vision.face.Face;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import quartifex.com.navigaze.face.FaceDetectorActivity;

public class FaceActivity extends AppCompatActivity implements FaceDetectorActivity.FaceListener,HomeFragment.OnFragmentInteractionListener {


	private List<Float> leftEyeOpenProbs;
	private List<Float> rightEyeOpenProbs;
	private int FPS = 10;
	private int LEFT_EYE_OPEN_FLAG = 0;
	private int RIGHT_EYE_OPEN_FLAG = 1;
	private int NO_ACTION_FLAG = 2;
	private float MIN_PROBABILITY_THRESHOLD = 0.2f;


	HomeFragment homeFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		leftEyeOpenProbs = new ArrayList<>();
		rightEyeOpenProbs = new ArrayList<>();
		displayFragment();



	}

	public void displayFragment(){
		homeFragment = HomeFragment.newInstance();
		FragmentManager fragmentManager=getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container_list,homeFragment).addToBackStack(null).commit();
	}

	public void closeFragment(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		homeFragment = (HomeFragment)fragmentManager.findFragmentById(R.id.fragment_container_list);
		if(homeFragment!=null){
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.remove(homeFragment);
		}
	}



	public boolean flagOn=true;
	@Override
	public void getFace(Face face) {
		if (leftEyeOpenProbs.size() < FPS) {
			leftEyeOpenProbs.add(face.getIsLeftEyeOpenProbability());
			rightEyeOpenProbs.add(face.getIsRightEyeOpenProbability());
		}else {
			if ((getAction(leftEyeOpenProbs, rightEyeOpenProbs) == LEFT_EYE_OPEN_FLAG) && flagOn) {
				flagOn=false;
				homeFragment.handleRightBlink();
			}else if (getAction(leftEyeOpenProbs, rightEyeOpenProbs) == RIGHT_EYE_OPEN_FLAG) {
				homeFragment.handleLeftBlink();
			} else {
				homeFragment.handleBothOpenOrClose();
			}
			leftEyeOpenProbs.clear();
			rightEyeOpenProbs.clear();
		}
	}

	private int getAction (List<Float> leftEyeOpenProbs, List<Float> rightEyeOpenProbs) {
		int leftEyeOpenCount = 0;
		int rightEyeOpenCount = 0;
		for (int i = 0; i < leftEyeOpenProbs.size(); i++) {
//			Log.d("FACE DETECTIOn", "getAction: values"+leftEyeOpenProbs.get(i)+"/"+rightEyeOpenProbs.get(i));
			if (isFirstEyeOpen(leftEyeOpenProbs.get(i), rightEyeOpenProbs.get(i))) {
				leftEyeOpenCount++;
			} else if (isFirstEyeOpen(rightEyeOpenProbs.get(i), leftEyeOpenProbs.get(i))) {
				rightEyeOpenCount++;
			}
		}
		Log.d("FACE DETECTIOn", "getAction: "+(FPS * 0.6)+"LEFT/RIGHT"+leftEyeOpenCount+"/"+rightEyeOpenCount);
		if (rightEyeOpenCount > (FPS * 0.6)) {
//			Log.d("FACE DETECTIOn", "getAction: RIght"+rightEyeOpenCount);
			return LEFT_EYE_OPEN_FLAG;
		}else if (leftEyeOpenCount > (FPS * 0.6)) {
//			Log.d("FACE DETECTIOn", "getAction: left"+leftEyeOpenCount);
			return RIGHT_EYE_OPEN_FLAG;
		}else {
			return NO_ACTION_FLAG;
		}
	}

	private boolean isFirstEyeOpen (float firstEyeProb, float secondEyeProb) {
		Log.d("FACE DETECTIOn", "getAction: secondEYEProb ::"+secondEyeProb);
		return firstEyeProb >= (2 * secondEyeProb) && (firstEyeProb >= MIN_PROBABILITY_THRESHOLD);
	}

	@Override
	public void onFragmentInteraction(Uri uri) {

	}

	public void idleClick(View v) {
		homeFragment.idleClick(v);
	}
}
