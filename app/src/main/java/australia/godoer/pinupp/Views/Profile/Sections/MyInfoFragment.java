package australia.godoer.pinupp.Views.Profile.Sections;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.parse.ParseUser;

import java.util.Calendar;

import australia.godoer.pinupp.Models.MyInfo;
import australia.godoer.pinupp.Models.Profile;
import australia.godoer.pinupp.Models.User;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.Helper;
import australia.godoer.pinupp.Views.LoginActivity;
import australia.godoer.pinupp.Views.Profile.HomeActivity;
import australia.godoer.pinupp.Views.Profile.ProfileHomeFragment;


public class MyInfoFragment extends Fragment implements CalendarDatePickerDialogFragment.OnDateSetListener {

    private EditText date_txt;

    public MyInfoFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        date_txt = (EditText) view.findViewById(R.id.myinfo_date);
        final TextView title_txt = ((TextView) view.findViewById(R.id.myinfo_title));
        title_txt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return Helper.showEditDialog(title_txt, getContext(), "New title");
            }
        });
        final EditText fname_edit = (EditText)view.findViewById(R.id.myinfo_firstname_edit);
        final EditText lname_edit = (EditText)view.findViewById(R.id.myinfo_lastname_edit);
        final EditText mobile_edit = (EditText)view.findViewById(R.id.myinfo_mobile_edit);
        final EditText address_edit = (EditText)view.findViewById(R.id.myinfo_address_edit);
        final EditText email_edit = (EditText)view.findViewById(R.id.myinfo_email_edit);
        final EditText location_edit = (EditText)view.findViewById(R.id.myinfo_location_edit);
        final EditText web_edit = (EditText)view.findViewById(R.id.myinfo_website_edit);
        final EditText aboutme_edit = (EditText)view.findViewById(R.id.myinfo_aboutme_edit);
        final EditText available_in_edit = (EditText)view.findViewById(R.id.myinfo_available_in_edit);
        final EditText available_date_edit = (EditText)view.findViewById(R.id.myinfo_date);
        MyInfo current_myifo = ProfileContentActivity.current_profile.getMyInfo();
        if(current_myifo !=null){
            title_txt.setText(current_myifo.getTitle());
            fname_edit.setText(current_myifo.getFirstName());
            lname_edit.setText(current_myifo.getLastName());
            mobile_edit.setText(current_myifo.getMobileNo());
            email_edit.setText(current_myifo.getEmail());
            location_edit.setText(current_myifo.getLocation());
            web_edit.setText(current_myifo.getWebsite());
            aboutme_edit.setText(current_myifo.getAboutMe());
            available_in_edit.setText(current_myifo.getAvailableFrom());
            available_date_edit.setText(current_myifo.getAvailableDate());
        }else{
            fname_edit.setText(ParseUser.getCurrentUser().get(LoginActivity.PARSE_LOGIN_FIRST_NAME_KEY).toString());
            lname_edit.setText(ParseUser.getCurrentUser().get(LoginActivity.PARSE_LOGIN_LAST_NAME_KEY).toString());
            email_edit.setText(ParseUser.getCurrentUser().getEmail());
        }

        final Calendar now = Calendar.getInstance();
        now.set(2016,01,01);

//        date_txt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
//                        .setOnDateSetListener(MyInfoFragment.this)
//                        .setFirstDayOfWeek(Calendar.SUNDAY)
//                        .setPreselectedDate(2016, now.MONTH, now.DAY_OF_MONTH)
//                        .setThemeDark();
//                cdp.show(getActivity().getSupportFragmentManager(), "FRAG_TAG_DATE_PICKER");
//            }
//        });

        date_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                            .setOnDateSetListener(MyInfoFragment.this)
                            .setFirstDayOfWeek(Calendar.SUNDAY)
                            .setPreselectedDate(2016, now.MONTH, now.DAY_OF_MONTH)
                            .setThemeDark();
                    cdp.show(getActivity().getSupportFragmentManager(), "FRAG_TAG_DATE_PICKER");
                }
                return false;
            }
        });
        //TODO save mText on swipe away

        final FloatingActionButton FAB = (FloatingActionButton) view.findViewById(R.id.myinfo_fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileno = mobile_edit.getText().toString();
                if(android.util.Patterns.PHONE.matcher(mobileno).matches()){
                    MyInfo myInfo = new MyInfo(fname_edit.getText().toString(),lname_edit.getText().toString(),
                            mobileno,address_edit.getText().toString(), location_edit.getText().toString(),
                            email_edit.getText().toString(),web_edit.getText().toString(),aboutme_edit.getText().toString(),
                            available_in_edit.getText().toString(),available_date_edit.getText().toString());
                    ProfileContentActivity.current_profile.setMyInfo(myInfo);
                    User.updateProfile(ProfileContentActivity.current_profile);
                    Helper.showMsg(getActivity().findViewById(R.id.myinfo_coord_layout),"Profile Saved", getResources().getColor(R.color.successColor) );

                }else{
                    Helper.showMsg(getActivity().findViewById(R.id.myinfo_coord_layout),"Invalid Mobile no", getResources().getColor(R.color.warnColor));
                }
            }
       });
        return view;
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
     date_txt.setText(String.format("%02d", dayOfMonth)+ '/' + String.format("%02d", monthOfYear+1)+ '/' + String.format("%02d", year));
    }
}
