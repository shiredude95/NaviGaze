package quartifex.com.navigaze;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



	    ActionBar actionbar = getSupportActionBar();
	   if(actionbar!=null){
	   	actionbar.hide();
	   }



	    Window window = getWindow();

	    // clear FLAG_TRANSLUCENT_STATUS flag:
	    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

	    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
	    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

	    // finally change the color
	    window.setStatusBarColor(getResources().getColor(android.R.color.black));


	    final Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
		    @Override
		    public void run() {
			    // Do something after 5s = 5000ms
			    Intent intent = new Intent(SplashActivity.this, FaceActivity.class);
			    startActivity(intent);
			    finish();
		    }
	    }, 2000);

    }

}
