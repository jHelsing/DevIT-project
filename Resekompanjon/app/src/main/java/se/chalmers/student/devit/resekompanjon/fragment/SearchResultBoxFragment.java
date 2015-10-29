package se.chalmers.student.devit.resekompanjon.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.chalmers.student.devit.resekompanjon.R;
import se.chalmers.student.devit.resekompanjon.backend.utils.json.SearchResultTripSummary;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchResultBoxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchResultBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultBoxFragment extends Fragment {
    private static final String ARG_TRIP = "Trip";

    private SearchResultTripSummary trip;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param trip The SearchResaultTrips instance associated with this list item.
     * @return A new instance of fragment SearchResultBoxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultBoxFragment newInstance(SearchResultTripSummary trip) {
        SearchResultBoxFragment fragment = new SearchResultBoxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TRIP, trip.toString());
        fragment.setArguments(args);
        return fragment;
    }

    public SearchResultBoxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result_box, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
