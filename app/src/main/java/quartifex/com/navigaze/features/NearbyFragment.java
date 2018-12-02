package quartifex.com.navigaze.features;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import quartifex.com.navigaze.BaseFragment;
import quartifex.com.navigaze.FaceActivity;
import quartifex.com.navigaze.HttpWrapper.Network;
import quartifex.com.navigaze.POJO.Area;
import quartifex.com.navigaze.POJO.Entrance;
import quartifex.com.navigaze.POJO.Feature;
import quartifex.com.navigaze.POJO.Node;
import quartifex.com.navigaze.POJO.ResponseObject;
import quartifex.com.navigaze.PlaceView;
import quartifex.com.navigaze.R;

public class NearbyFragment extends BaseFragment  {


    // TODO: Rename parameter arguments, choose names that match


    View rootView;


    private PlaceView place1;
    private PlaceView place2;
    private PlaceView place3;
    private PlaceView place4;
    private PlaceView place5;
    //    private FunButton btnMessage;
    private LinearLayout linearLayout;

//    private List<PlaceView> placeList = new ArrayList<>();
    private CountDownTimer mCountDownTimer;


    public static final String P1 = "p1";
    public static final String P2 = "p2";
    public static final String P3 = "p3";
    public static final String P4 = "p4";
    public static final String P5 = "p5";




    private int currentItemIndex = 0;

    public NearbyFragment() {
        // Required empty public constructor
    }

    public static NearbyFragment newInstance() {
        return new NearbyFragment();
    }

//    private void incrementcurrentItemIndex() {
//
//        if (currentItemIndex < placeList.size() - 1) {
//            currentItemIndex += 1;
//        }
//    }
//
//    private void decrementcurrentItemIndex() {
//        if (currentItemIndex > 0) {
//            currentItemIndex -= 1;
//        }
//    }
//
//    private int getCurrentItemIndex() {
//        return currentItemIndex;
//    }

    @Override
    public View customFeatureFragment(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_nearby, parent, false);


		String url = "https://accessibility-cloud.freetls.fastly.net/place-infos.json?excludeSourceIds=LiBTS67TjmBcXdEmX&x=9651&y=12311&z=15&appToken=27be4b5216aced82122d7cf8f69e4a07&locale=en_US&includePlacesWithoutAccessibility=0";
		new Network(getContext(), ResponseObject.class).execute(url);
//
//        place1 = rootView.findViewById(R.id.place_v1);
//        place2 = rootView.findViewById(R.id.place_v2);
//        place3 = rootView.findViewById(R.id.place_v3);
//        place4 = rootView.findViewById(R.id.place_v4);
//        place5 = rootView.findViewById(R.id.place_v5);


//	    getAllViewsInList(rootView.findViewById(R.id.ll_nearby_fragment_container));
		linearLayout=(LinearLayout)rootView.findViewById(R.id.ll_nearby_fragment_container);
        //Now specific components here (you can initialize Buttons etc)
        return rootView;
    }





    @Override
    public void handleLeftBlink() {
//        decrementcurrentItemIndex();
//        placeList.get(getCurrentItemIndex()).callOnClick();
    }

    @Override
    public void handleRightBlink() {
//        incrementcurrentItemIndex();
//        placeList.get(getCurrentItemIndex()).callOnClick();
    }

    @Override
    public void handleBothOpenOrClose() {
        ((FaceActivity) getActivity()).onBackAction();
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
    public void getAllViewsInList(View view) {


//        if (placeList.size() != 0)
//            placeList.clear();
//
//        ViewGroup viewGroup = (ViewGroup) view;
//        for (int i = 0; i < viewGroup.getChildCount(); i++) {
//            PlaceView v1 = (PlaceView)viewGroup.getChildAt(i);
//            placeList.add(v1);
//        }
    }

    @Override
    public <k> void updateView(List<k> nodes) {
        try {
            for(int i=0;i<nodes.size();i++){

            	PlaceView placeView=new PlaceView(getContext());
	            placeView.setPlaceTitle(getPlaceName(((Feature) nodes.get(i))));
	            placeView.setPlaceDetail(getOtherDetails(((Feature) nodes.get(i))));
				linearLayout.addView(placeView);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getPlaceName(Feature node){

    	return node.getProperties().getName();
    }

    private String dividerDot="\u00B7";
    private String space3="   ";

    private String getOtherDetails(Feature node){



    	String address=node.getProperties().getAddress();


		String isWheelChair="Wheelchair Accessible: ";
		String wcValue=node.getProperties().getAccessibility().getAccessibleWith().getWheelchairLocalized();

		String wc2append="false";
		if(wcValue!=null && wcValue.length()>0){
			wc2append="true";
		}
		isWheelChair+=wc2append;


		StringBuilder rating= new StringBuilder("Rating: ");
		String ratingValue="N/A";


		List<Area> areas = node.getProperties().getAccessibility().getAreas();

		boolean ratingFound=false;

		if(areas!=null && areas.size()>0){
			for(Area area:areas){
				if(!ratingFound){
					for(Entrance entrance:area.getEntrances()){
						if(entrance.getRatingForWheelchair()>0){
							ratingValue=String.valueOf(entrance.getRatingForWheelchair());
							ratingFound=true;
							break;
						}
					}
				}
			}

		}

		StringBuilder resultBuilder=new StringBuilder();
		resultBuilder.append(address);
		resultBuilder.append("\n");

		resultBuilder.append(dividerDot);
		resultBuilder.append(rating+ratingValue);
		resultBuilder.append(space3);

		resultBuilder.append(dividerDot);
		resultBuilder.append(isWheelChair);
		resultBuilder.append(space3);

		return resultBuilder.toString();



    }




}
