package se.chalmers.student.devit.resekompanjon;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.support.v4.util.TimeUtils;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchBoxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author Jonathan. Revisited by Amar.
 * @version 0.1
 */
public class SearchBoxFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private DatePickerDialog travelDatePicker;
    private SimpleDateFormat travelDateFormatter;

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnSearchFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void onStart() {
        super.onStart();
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
            initDefaultValues();
            initDatePicker();
            initTimePicker();

        } catch (ClassCastException e) {
            throw new ClassCastException( getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    private void initDefaultValues() {
        View view = this.getView();
        Resources res = this.getResources();
        String currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":"
                + Calendar.getInstance().get(Calendar.MINUTE);
        TextView tv = (TextView) view.findViewById(R.id.editTextTravelTime);
        tv.setText(currentTime);

        String currentLocation = res.getString(R.string.current_location);
        tv = (TextView) view.findViewById(R.id.editTextTipStartLocation);
        tv.setHint(currentLocation);

        String endLocation = res.getString(R.string.default_end_location);
        tv = (TextView) view.findViewById(R.id.editTextTipEndLocation);
        tv.setHint(endLocation);

    }

    private void initDatePicker(){
        travelDateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        final EditText editDate = (EditText) this.getView().findViewById(R.id.editTextTravelDate);
        editDate.setInputType(InputType.TYPE_NULL);

        editDate.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        travelDatePicker = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,monthOfYear,dayOfMonth);
                editDate.setText(travelDateFormatter.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

    }

    private void initTimePicker(){
        travelTimeFormatter = new SimpleDateFormat("HH:mm");

        final EditText editTime = (EditText) this.getView().findViewById(R.id.editTextTravelTime);
        editTime.setInputType(InputType.TYPE_NULL);

        editTime.setOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        travelTimePicker = new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newTime = Calendar.getInstance();
                newTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newTime.set(Calendar.MINUTE, minute);
                editTime.setText(travelTimeFormatter.format(newTime.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(v == this.getView().findViewById(R.id.editTextTravelDate)){
            travelDatePicker.show();
        } else if(v == this.getView().findViewById(R.id.editTextTravelTime)){
            travelTimePicker.show();
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
        public void OnSearchFragmentInteraction(Uri uri);
    }

}
