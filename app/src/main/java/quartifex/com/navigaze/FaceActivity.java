package quartifex.com.navigaze;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.vision.face.Face;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import quartifex.com.navigaze.face.FaceDetectorActivity;

public class FaceActivity extends AppCompatActivity implements FaceDetectorActivity.FaceListener {

	private TextView textView;
	private List<Float> leftEyeOpenProbs;
	private List<Float> rightEyeOpenProbs;
	private int FPS = 10;
	private int LEFT_EYE_OPEN_FLAG = 0;
	private int RIGHT_EYE_OPEN_FLAG = 1;
	private int NO_ACTION_FLAG = 2;
	private float MIN_PROBABILITY_THRESHOLD = 0.2f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		leftEyeOpenProbs = new ArrayList<>();
		rightEyeOpenProbs = new ArrayList<>();
		textView = findViewById(R.id.cameraText);
	}

	@Override
	public void getFace(Face face) {
//		String result = "NO ACTION";
		if (leftEyeOpenProbs.size() < FPS) {
			leftEyeOpenProbs.add(face.getIsLeftEyeOpenProbability());
			rightEyeOpenProbs.add(face.getIsRightEyeOpenProbability());
		}else {
			if (getAction(leftEyeOpenProbs, rightEyeOpenProbs) == LEFT_EYE_OPEN_FLAG) {
//				result = "RIGHT EYE CLOSED";
				textView.setText("RIGHT EYE CLOSED");
			}else if (getAction(leftEyeOpenProbs, rightEyeOpenProbs) == RIGHT_EYE_OPEN_FLAG) {
//				result = "LEFT EYE CLOSED";
				textView.setText("LEFT EYE CLOSED");
			} else {
//				result = "NO ACTION";
				textView.setText("NO ACTION");
			}
			leftEyeOpenProbs.clear();
			rightEyeOpenProbs.clear();
		}
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				textView.setText(result);
//			}
//		});

//        textView.setText(face.getIsLeftEyeOpenProbability() + ", " + face.getIsRightEyeOpenProbability());
	}

	private int getAction (List<Float> leftEyeOpenProbs, List<Float> rightEyeOpenProbs) {
		int leftEyeOpenCount = 0;
		int rightEyeOpenCount = 0;
		for (int i = 0; i < leftEyeOpenProbs.size(); i++) {
			Log.d("FACE DETECTIOn", "getAction: values"+leftEyeOpenProbs.get(i)+"/"+rightEyeOpenProbs.get(i));
			if (isFirstEyeOpen(leftEyeOpenProbs.get(i), rightEyeOpenProbs.get(i))) {
				leftEyeOpenCount++;
			} else if (isFirstEyeOpen(rightEyeOpenProbs.get(i), leftEyeOpenProbs.get(i))) {
				rightEyeOpenCount++;
			}
		}
		Log.d("FACE DETECTIOn", "getAction: "+(FPS * 0.6)+"LEFT/RIGHT"+leftEyeOpenCount+"/"+rightEyeOpenCount);
		if (rightEyeOpenCount > (FPS * 0.6)) {
			Log.d("FACE DETECTIOn", "getAction: RIght"+rightEyeOpenCount);
			return LEFT_EYE_OPEN_FLAG;
		}else if (leftEyeOpenCount > (FPS * 0.6)) {
			Log.d("FACE DETECTIOn", "getAction: left"+leftEyeOpenCount);
			return RIGHT_EYE_OPEN_FLAG;
		}else {
			return NO_ACTION_FLAG;
		}
	}

	private boolean isFirstEyeOpen (float firstEyeProb, float secondEyeProb) {
		Log.d("FACE DETECTIOn", "getAction: secondEYEProb ::"+secondEyeProb);
		return firstEyeProb >= (2 * secondEyeProb) && (firstEyeProb >= MIN_PROBABILITY_THRESHOLD);
	}
}
