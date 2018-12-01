package quartifex.com.navigaze;

import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.vision.face.Face;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Headers;
import quartifex.com.navigaze.HttpWrapper.Network;
import quartifex.com.navigaze.POJO.Conditions;
import quartifex.com.navigaze.POJO.Data;
import quartifex.com.navigaze.POJO.Node;
import quartifex.com.navigaze.face.FaceDetectorActivity;

public class FaceActivity extends AppCompatActivity implements FaceDetectorActivity.FaceListener, Network.NetworkListener {

    private TextView textView;
    private FrameLayout frameLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = findViewById(R.id.cameraText);
		frameLayout = findViewById(R.id.container);

		String url = "http://wheelmap.org/api/nodes?api_key=-DFwtnoyB15aUuEvHq1d&bbox=13.341,52.505,13.434,52.523&per_page=10&wheelchair=yes";
		new Network(this, Data.class).execute(url);
	}

    @Override
    public void getFace(Face face) {
//        textView.setText(face.getIsLeftEyeOpenProbability() + ", " + face.getIsRightEyeOpenProbability());
    }

	@Override
	public void onNetworkResultReceive(Object o) {
		if(o == null) return;

		try{
            Data data = (Data)o;
            List<Node> nodes = data.getNodes();
            String val = nodes.size() + "\n";
            val += nodes.get(0).getName() + " " + nodes.get(0).getWheelchair();
            textView.setText(val);
        } catch (Exception e){
		    e.printStackTrace();
        }
	}
}
