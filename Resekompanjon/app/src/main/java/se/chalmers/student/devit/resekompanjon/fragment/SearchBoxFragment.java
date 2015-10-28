package se.chalmers.student.devit.resekompanjon.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import se.chalmers.student.devit.resekompanjon.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSearchButtonClick} interface
 * to handle interaction events.
 * Use the {@link SearchBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author Jonathan. Revisited by Amar.
 * @version 0.1
 */
public class SearchBoxFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnSearchButtonClick mListener;

    private Calendar newDate;
    private DatePickerDialog travelDatePicker;
    private SimpleDateFormat travelDateFormatter;

    private  Calendar newTime;
    private TimePickerDialog travelTimePicker;
    private SimpleDateFormat travelTimeFormatter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchBoxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchBoxFragment newInstance(String param1, String param2) {
        SearchBoxFragment fragment = new SearchBoxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchBoxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_box, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onStart() {
        super.onStart();
        try {
            mListener = (OnSearchButtonClick) getActivity();
            initDatePicker();
            initTimePicker();
            initDefaultValues();
            this.getView().findViewById(R.id.buttonSearchTrip).setOnClickListener(this);
            this.getView().findViewById(R.id.editTextTipStartLocation).setOnFocusChangeListener(this);
            this.getView().findViewById(R.id.editTextTipEndLocation).setOnFocusChangeListener(this);

        } catch (ClassCastException e) {
            throw new ClassCastException( getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    private void initDefaultValues() {
        View view = this.getView();
        Resources res = this.getResources();

        EditText eT= (EditText) view.findViewById(R.id.editTextTravelTime);
        eT.setText(travelTimeFormatter.format(newTime.getTime()));

        eT = (EditText) view.findViewById(R.id.editTextTravelDate);
        eT.setText(travelDateFormatter.format(newDate.getTime()));

        String currentLocation = res.getString(R.string.current_location);
        eT = (EditText) view.findViewById(R.id.editTextTipStartLocation);
        eT.setHint(currentLocation);

        String endLocation = res.getString(R.string.default_end_location);
        eT = (EditText) view.findViewById(R.id.editTextTipEndLocation);
        eT.setHint(endLocation);

    }

    private void initDatePicker(){
        travelDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        final EditText editDate = (EditText) this.getView().findViewById(R.id.editTextTravelDate);
        editDate.setInputType(InputType.TYPE_NULL);

        editDate.setOnClickListener(this);

        newDate = Calendar.getInstance();

        travelDatePicker = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year,monthOfYear,dayOfMonth);
                editDate.setText(travelDateFormatter.format(newDate.getTime()));
            }
        },newDate.get(Calendar.YEAR),newDate.get(Calendar.MONTH),newDate.get(Calendar.DAY_OF_MONTH));

    }

    private void initTimePicker(){
        travelTimeFormatter = new SimpleDateFormat("HH:mm");

        final EditText editTime = (EditText) this.getView().findViewById(R.id.editTextTravelTime);
        editTime.setInputType(InputType.TYPE_NULL);

        editTime.setOnClickListener(this);

        newTime = Calendar.getInstance();

        travelTimePicker = new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                newTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newTime.set(Calendar.MINUTE, minute);
                editTime.setText(travelTimeFormatter.format(newTime.getTime()));
            }
        }, newTime.get(Calendar.HOUR_OF_DAY), newTime.get(Calendar.MINUTE),true);
    }

    public void initSavedValues(String startLocation,String endLocation, String date, String time){
        View view = this.getView();

        EditText eT= (EditText) view.findViewById(R.id.editTextTravelTime);
        eT.setText(time);

        eT = (EditText) view.findViewById(R.id.editTextTravelDate);
        eT.setText(date);

        eT = (EditText) view.findViewById(R.id.editTextTipStartLocation);
        eT.setText(startLocation);

        eT = (EditText) view.findViewById(R.id.editTextTipEndLocation);
        eT.setText(endLocation);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(v == this.getView().findViewById(R.id.buttonSearchTrip)){
            String startLocation = ((EditText) this.getView().findViewById(R.id.editTextTipStartLocation))
                    .getText().toString();
            String endLocation = ((EditText) this.getView().findViewById(R.id.editTextTipEndLocation))
                    .getText().toString();
            String date = ((EditText)this.getView().findViewById(R.id.editTextTravelDate))
                    .getText().toString();
            String time = ((EditText) this.getView().findViewById(R.id.editTextTravelTime))
                    .getText().toString();

            boolean canSearch = true;

            if(startLocation.isEmpty() || startLocation.equals(null)){
                EditText editTextStartLocation = ((EditText) this.getView().findViewById(R.id.editTextTipStartLocation));
                editTextStartLocation.getBackground().setColorFilter(getResources().getColor(R.color.redwarning)
                        , PorterDuff.Mode.SRC_ATOP);

                canSearch = false;
            }

            if(endLocation.isEmpty() || endLocation.equals(null)){
                EditText editTextEndLocation = ((EditText) this.getView().findViewById(R.id.editTextTipEndLocation));
                editTextEndLocation.getBackground().setColorFilter(getResources().getColor(R.color.redwarning)
                        , PorterDuff.Mode.SRC_ATOP);

                canSearch = false;
            }

            if(canSearch){
                mListener.onSearchButtonClick(startLocation, endLocation, date, time);
            }
            
        } else if(v == this.getView().findViewById(R.id.editTextTravelTime)){
            travelTimePicker.show();
        } else if(v == this.getView().findViewById(R.id.editTextTravelDate)){
            travelDatePicker.show();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus){
            InputMethodManager iMM = (InputMethodManager)getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            iMM.hideSoftInputFromWindow(this.getView().getWindowToken(), 0);
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
    public interface OnSearchButtonClick {
        public void onSearchButtonClick(String startLocation, String endLocation, String date, String time);
    }

}
