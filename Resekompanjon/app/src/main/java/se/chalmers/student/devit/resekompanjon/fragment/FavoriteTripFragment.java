package se.chalmers.student.devit.resekompanjon.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import se.chalmers.student.devit.resekompanjon.R;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.BackendCommunicator;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoConnectionException;
import se.chalmers.student.devit.resekompanjon.backend.connectionBackend.NoJsonAvailableException;
import se.chalmers.student.devit.resekompanjon.backend.utils.JsonInfoExtract;
import se.chalmers.student.devit.resekompanjon.backend.utils.OnTaskCompleted;
import se.chalmers.student.devit.resekompanjon.backend.utils.readers.FavoriteHandler;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteTripFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteTripFragment extends Fragment implements View.OnClickListener, OnTaskCompleted {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_JSONOBJECT = "jsonObject";

    private int jsonIndex;
    private JsonObject jsonObject;
    private  boolean isFavorite;
    private BackendCommunicator comm;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param obj The JsonObject that this fragment should display.
     * @return A new instance of fragment FavoriteTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteTripFragment newInstance(JsonObject obj) {
        FavoriteTripFragment fragment = new FavoriteTripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JSONOBJECT, obj.getAsString());
        fragment.setArguments(args);
        return fragment;
    }

    public FavoriteTripFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(ARG_JSONOBJECT);
            this.jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
        }
        ImageButton favButton = (ImageButton) getView().findViewById(R.id.favouriteButton);
        favButton.setOnClickListener(this);
        updateImageIcon();
    }

    private void updateImageIcon() {
        FavoriteHandler handler = new FavoriteHandler(getActivity());
        JsonArray arr = handler.getTripArrayAsJson();

        String origin = ((TextView) getView().findViewById(R.id.textViewFromLocation)).getText().toString();
        String destination = ((TextView) getView().findViewById(R.id.textViewToLocation)).getText().toString();

        jsonObject = new JsonObject();
        jsonObject.addProperty("originName", origin);
        jsonObject.addProperty("endName", destination);
        if(handler.isFavorite(jsonObject)) {
            ImageButton favButton = (ImageButton) getView().findViewById(R.id.favouriteButton);
            favButton.setImageDrawable(getResources().getDrawable(R.drawable.favourite_untoggled));
        } else {
            this.jsonIndex = handler.getFavoriteIndex(jsonObject);
            isFavorite = true;
            ImageButton favButton = (ImageButton) getView().findViewById(R.id.favouriteButton);
            favButton.setImageDrawable(getResources().getDrawable(R.drawable.favourite_toggled));
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
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.equals(getView().findViewById(R.id.favouriteButton))) {
            updateImageIcon();
            FavoriteHandler handler = new FavoriteHandler(getActivity());
            if(isFavorite) {
                isFavorite=false;
                handler.removeFavorite(jsonIndex);
            } else {
                isFavorite=true;
                comm = new BackendCommunicator(getActivity(), this);
                try {
                    comm.getStationbyName(jsonObject.get("originName").getAsString());
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onTaskCompleted() {
        if(jsonObject.get("originID").getAsString().equals(null) ) {
            try{
                JsonObject fromBackend = comm.getApiData().getAsJsonObject();
                JsonInfoExtract infoExtract = new JsonInfoExtract(fromBackend);
                fromBackend = infoExtract.getStops();
                jsonObject.addProperty("originID", fromBackend.get("originID").getAsString());
            } catch (NoJsonAvailableException e) {
                Toast noConectionMessage = Toast.makeText(getActivity(), "Tyvärr så går det inte att söka med det innehållet!", Toast.LENGTH_LONG);
                noConectionMessage.show();
                e.printStackTrace();
            }

            try {
                comm.getStationbyName(jsonObject.get("endName").getAsString());
            } catch (NoConnectionException e) {
                e.printStackTrace();
            }
        } else {
            try{
                JsonObject fromBackend = comm.getApiData().getAsJsonObject();
                JsonInfoExtract infoExtract = new JsonInfoExtract(fromBackend);
                fromBackend = infoExtract.getStops();
                jsonObject.addProperty("endID", fromBackend.get("endID").getAsString());
                FavoriteHandler handler = new FavoriteHandler(getActivity());
                handler.addToFavoriteTrips(jsonObject.get("originName").getAsString(),
                        jsonObject.get("originID").getAsString(),
                        jsonObject.get("endName").getAsString(), jsonObject.get("endID").getAsString());
                jsonIndex = handler.getNumbOfFavorites()-1;
            } catch (NoJsonAvailableException e) {
                Toast noConectionMessage = Toast.makeText(getActivity()
                        , "Tyvärr så går det inte att söka med det innehållet!", Toast.LENGTH_LONG);
                noConectionMessage.show();
                e.printStackTrace();
            }

        }

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
