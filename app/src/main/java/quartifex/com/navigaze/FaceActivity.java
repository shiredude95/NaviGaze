package quartifex.com.navigaze;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.vision.face.Face;

import androidx.appcompat.app.AppCompatActivity;
import quartifex.com.navigaze.face.FaceDetectorActivity;

public class FaceActivity extends AppCompatActivity implements FaceDetectorActivity.FaceListener {

    private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = findViewById(R.id.cameraText);
	}

    @Override
    public void getFace(Face face) {
        textView.setText(face.getIsLeftEyeOpenProbability() + ", " + face.getIsRightEyeOpenProbability());
    }
}
