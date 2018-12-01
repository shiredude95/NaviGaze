package quartifex.com.navigaze;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FunButton extends LinearLayout {

	private TextView funButtonText;
	private ProgressBar progressBar;


	public FunButton(Context context, AttributeSet attrs){

		super(context,attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.FunButton,0,0);
		String buttonText = a.getString(R.styleable.FunButton_buttonText);
		a.recycle();

		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);

		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_fun_button,this,true);

		funButtonText = (TextView)getChildAt(0);
		funButtonText.setText(buttonText);

		progressBar = (ProgressBar)getChildAt(1);
	}

	public FunButton(Context context){
		this(context,null);
	}

	public void setProgressBarVisibility(boolean visibile){

		progressBar.setVisibility(visibile?VISIBLE:INVISIBLE);
	}

	public void setFunButtonText(String buttonLabel){

		funButtonText.setText(buttonLabel);
	}


	public void setProgressbarProgress(int progress){
		progressBar.setProgress(progress);
	}

	public String getButtonText(){

		return this.funButtonText.getText().toString();
	}

	public void setButtonTextColor(int i){

		this.funButtonText.setTextColor(i);
	}


}
