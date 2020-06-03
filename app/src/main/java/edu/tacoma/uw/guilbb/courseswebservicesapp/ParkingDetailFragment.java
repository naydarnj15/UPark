package edu.tacoma.uw.guilbb.courseswebservicesapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import edu.tacoma.uw.guilbb.courseswebservicesapp.model.Course;


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ParkingListActivity}
 * in two-pane mode (on tablets) or a {@link ParkingDetailActivity}
 * on handsets.
 */
public class ParkingDetailFragment extends Fragment {

    private static final String TAG = "DisplayMessageActivity";
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Course mItem;
    private Handler mHandler;


    Button yesButton ;
    View.OnClickListener yesButtonOnClickListener;
    View myRootView;
    TextView longdesc;
    TextView shortdesc;
    TextView id;
    TextView prereqs;
    Fragment thisFragment;
    View rootView;
    List<Course> parkingAreaList;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ParkingDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (Course) getArguments().getSerializable(ARG_ITEM_ID);

            thisFragment = this;
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout)
                    activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getmCourseId());


            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the course content as text in a TextView.
        if (mItem != null) {
            longdesc = ((TextView) rootView.findViewById(R.id.item_detail_long_desc));
            longdesc.setText(
                    mItem.getmCourseLongDesc() + "$ / hr");

            shortdesc =((TextView) rootView.findViewById(R.id.item_detail_short_desc));
            if (mItem.getmCourseShortDesc().equals("Yes")){
                // Accessible spots available
                shortdesc.setText("Accessible Parking Available");
            } else{
                //No spots available
                shortdesc.setText("Accessible Parking Full");
            }


            id = ((TextView) rootView.findViewById(R.id.item_detail_id));
            id.setText(
                    mItem.getmCourseId());

            prereqs = ((TextView) rootView.findViewById(R.id.item_detail_prereqs));
            prereqs.setText("Empty spots?: " +
                    mItem.getmCoursePrereqs());

        }
        final View theRootView = rootView;
        myRootView = rootView;

        ((ParkingDetailActivity)getActivity()).setFragmentRefreshListener(new ParkingDetailActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler = new Handler();
                mHandler.post(mUpdate);



            }
        });
        rootView = theRootView;


        return rootView;
    }

    /**
     * This method updates the text that displays weather there is handicap parking available
     * @param updatedParkingArea    A json object with the updated handicap availability info, this
     *                              should be coming from ParkingDetailActivity
     * @throws JSONException        If there is something wrong with the json passed in as a parameter
     *                              then it will throw an exception
     */
    public void updateHandicapParking(JSONObject updatedParkingArea)throws JSONException{
        shortdesc =((TextView) rootView.findViewById(R.id.item_detail_short_desc));
        ImageView icon = ((ImageView) rootView.findViewById(R.id.accessible_icon));

        if (updatedParkingArea.getString(Course.SHORT_DESC).equals("Yes")){
            // Accessible spots available
            shortdesc.setText("Accessible Parking Available");
            icon.setColorFilter(Color.argb(255, 0, 255, 0));

        } else{
            //No spots available
            shortdesc.setText("Accessible Parking Full");
            icon.setColorFilter(Color.argb(255, 255, 0, 0));
        }

    }

    /**
     * This method updates the text that displays weather there is any parking available
     * @param updatedParkingArea    A json object with the updated parking availability info, this
     *                              should be coming from ParkingDetailActivity
     * @throws JSONException        If there is something wrong with the json passed in as a parameter
     *                              then it will throw an exception
     */
    public void updateAvailableParking(JSONObject updatedParkingArea) throws JSONException {
        prereqs = ((TextView) rootView.findViewById(R.id.item_detail_prereqs));
        ImageView icon = ((ImageView) rootView.findViewById(R.id.parking_icon));
        if (updatedParkingArea.getString(Course.PRE_REQS).equals("Yes")){
            // Accessible spots available
            prereqs.setText("Parking Available");
            icon.setColorFilter(Color.argb(255, 0, 255, 0));

        } else{
            //No spots available
            prereqs.setText("Parking Full");
            icon.setColorFilter(Color.argb(255, 255, 0, 0));
        }
    }






    private Runnable mUpdate = new Runnable() {
        public void run() {

            if (mItem != null) {
                /*longdesc.setText( mItem.getmCourseLongDesc()+ "$/hr");

                shortdesc.setText("Handicap parking???: " +
                        mItem.getmCourseShortDesc());

                id.setText(
                        mItem.getmCourseId());

                prereqs.setText(
                        "Empty spots?: " +mItem.getmCoursePrereqs());



                getFragmentManager().beginTransaction().detach(thisFragment).attach(thisFragment).commit();*/

            }
            /*Toast.makeText(getActivity().getApplicationContext(), "REFRESH!!" + mItem.getmCoursePrereqs(), Toast.LENGTH_SHORT).show();*/



        }
    };


}