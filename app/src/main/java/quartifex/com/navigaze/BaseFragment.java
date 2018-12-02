package quartifex.com.navigaze;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import quartifex.com.navigaze.POJO.Node;

public abstract class BaseFragment extends Fragment {


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return customFeatureFragment(inflater,container,savedInstanceState);
	}


	public abstract View customFeatureFragment(LayoutInflater inflater,ViewGroup parent, Bundle savedInstanceState);

	//TODO:listener callback methods for eye listener and interface
	public abstract void handleLeftBlink();

	public abstract void handleRightBlink();

	public abstract void handleBothOpenOrClose();

	public abstract void getAllViewsInList(View v);

	public <k extends Object> void updateView(List<k> objects){}

}
