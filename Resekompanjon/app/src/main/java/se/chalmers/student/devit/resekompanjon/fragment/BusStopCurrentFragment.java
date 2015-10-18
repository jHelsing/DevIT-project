package se.chalmers.student.devit.resekompanjon.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import se.chalmers.student.devit.resekompanjon.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BusStopCurrentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BusStopCurrentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusStopCurrentFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BUS_STOP = "busStop";
    private static final String FIRST_BUS_STOP = "firstBusStop";

    // TODO: Rename and change types of parameters
    private String mBusStop;
    private String mFirstBusStop;
    private ImageButton stopButton;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param busStop Parameter 1.
     * @param firstBusStop Parameter 2.
     * @return A new instance of fragment BusStopCurrentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusStopCurrentFragment newInstance(String busStop, String firstBusStop) {
        BusStopCurrentFragment fragment = new BusStopCurrentFragment();
        Bundle args = new Bundle();
        args.putString(BUS_STOP, busStop);
        args.putString(FIRST_BUS_STOP, firstBusStop);
        fragment.setArguments(args);
        return fragment;
    }

    public BusStopCurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBusStop = getArguments().getString(BUS_STOP);
            mFirstBusStop = getArguments().getString(FIRST_BUS_STOP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_bus_stop, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    public void onStart() {
        super.onStart();
        if(mBusStop.equals(mFirstBusStop)){
            ImageView busStopIcon = (ImageView) getView().findViewById(R.id.busStopIcon);
            busStopIcon.setImageResource(R.drawable.visited_stop);
        }
        TextView busStopTextView = (TextView) getView().findViewById(R.id.busStopName);
        switch (mBusStop){
            case "G�taplatsen":
                mBusStop = "Götaplatsen";
                break;
            case "Kungsportsplatsn":
                mBusStop = "Kungsportsplatsen";
                break;
            }
        busStopTextView.setText(mBusStop);
        stopButton = (ImageButton) getView().findViewById(R.id.stopButton);
        stopButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        //Sends to activity
        stopButton.setImageResource(R.drawable.stop_toggled);
        ((OnFragmentInteractionListener) getActivity()).onFragmentInteraction();
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
        public void onFragmentInteraction();
    }

}
