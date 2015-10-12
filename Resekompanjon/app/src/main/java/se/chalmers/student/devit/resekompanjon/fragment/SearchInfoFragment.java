package se.chalmers.student.devit.resekompanjon.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.chalmers.student.devit.resekompanjon.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STARTLOCATION = "startlocation";
    private static final String ARG_ENDLOCATION = "endlocation";
    private static final String ARG_TIME = "time";

    // TODO: Rename and change types of parameters
    private String mStartLocation;
    private String mEndLocation;
    private String mTime;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param startLocation This is the startpoint of the search.
     * @param endLocation This is the endpoint of the search.
     * @param time This is the date and time of the search.
     * @return A new instance of fragment SearchInfoFragment.
     */
    public static SearchInfoFragment newInstance(String startLocation, String endLocation
                                                 ,String time) {
        SearchInfoFragment fragment = new SearchInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STARTLOCATION, startLocation);
        args.putString(ARG_ENDLOCATION, endLocation);
        args.putString(ARG_TIME,time);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStartLocation = getArguments().getString(ARG_STARTLOCATION);
            mEndLocation = getArguments().getString(ARG_ENDLOCATION);
            mTime = getArguments().getString(ARG_TIME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_info, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
