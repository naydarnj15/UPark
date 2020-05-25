package edu.tacoma.uw.guilbb.courseswebservicesapp;

import android.app.Activity;
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
                    mItem.getmCourseLongDesc() + "$/hr");

            shortdesc =((TextView) rootView.findViewById(R.id.item_detail_short_desc));
            shortdesc.setText(
                    "Handicap parking?: " + mItem.getmCourseShortDesc());

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

    public void updateHandicapParking(JSONObject updatedParkingArea)throws JSONException{
        shortdesc =((TextView) rootView.findViewById(R.id.item_detail_short_desc));
        shortdesc.setText("Handicap parking?: " + updatedParkingArea.getString(Course.SHORT_DESC));
    }

    public void updateAvailableParking(JSONObject updatedParkingArea) throws JSONException {
        prereqs = ((TextView) rootView.findViewById(R.id.item_detail_prereqs));
        prereqs.setText("Empty spots?: " + updatedParkingArea.getString(Course.PRE_REQS));
    }

    public void updateParkingAreas(JSONObject updatedParkingArea) throws JSONException {

        Log.e("ACTIVITY", "Json passed to Act: " + updatedParkingArea);
        //------------update
       

        String newHandicapAvail;
        String newAvail;
        String newHourlyCost;

        newHourlyCost = updatedParkingArea.getString(Course.LONG_DESC);
        newAvail = updatedParkingArea.getString(Course.PRE_REQS);
        newHandicapAvail = updatedParkingArea.getString(Course.SHORT_DESC);


        longdesc = ((TextView) rootView.findViewById(R.id.item_detail_long_desc));
        longdesc.setText(
                newHourlyCost + "$/hr");

        shortdesc =((TextView) rootView.findViewById(R.id.item_detail_short_desc));
        shortdesc.setText("Handicap parking?: " + newHandicapAvail);

        id = ((TextView) rootView.findViewById(R.id.item_detail_id));
        id.setText( mItem.getmCourseId());

        prereqs = ((TextView) rootView.findViewById(R.id.item_detail_prereqs));
        prereqs.setText("Empty spots?: " + newAvail);

        //---------------
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