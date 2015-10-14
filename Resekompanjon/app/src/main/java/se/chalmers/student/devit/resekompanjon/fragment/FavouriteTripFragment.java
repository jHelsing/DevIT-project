package se.chalmers.student.devit.resekompanjon.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import se.chalmers.student.devit.resekompanjon.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavouriteTripFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteTripFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_JSONOBJECT = "jsonObject";

    // TODO: Rename and change types of parameters
    private JsonObject jsonObject;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param obj The JsonObject that this fragment should display.
     * @return A new instance of fragment FavouriteTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteTripFragment newInstance(JsonObject obj) {
        FavouriteTripFragment fragment = new FavouriteTripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JSONOBJECT, obj.getAsString());
        fragment.setArguments(args);
        return fragment;
    }

    public FavouriteTripFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(ARG_JSONOBJECT);
            this.jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_trip, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
