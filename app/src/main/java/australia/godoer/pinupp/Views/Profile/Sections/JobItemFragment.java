package australia.godoer.pinupp.Views.Profile.Sections;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import australia.godoer.pinupp.Adapters.MyJobItemRecyclerViewAdapter;
import australia.godoer.pinupp.Models.Jobs;
import australia.godoer.pinupp.R;

public class JobItemFragment extends Fragment implements CalendarDatePickerDialogFragment.OnDateSetListener {

    private OnJobListFragmentInteractionListener mListener;
    private EditText date_txt;
    private String month_add_job;
    private String year_add_job;
    public static MyJobItemRecyclerViewAdapter jobListAdapter;


    public JobItemFragment(){
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobitem_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.jobs_list);
        // Set the adapter
        if (recyclerView != null) {
            Context context = view.getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            jobListAdapter = new MyJobItemRecyclerViewAdapter(ProfileContentActivity.current_profile.getMyJobs().getJob_list(), mListener, getContext());
            recyclerView.setAdapter(jobListAdapter);
            final FloatingActionButton FAB = (FloatingActionButton) view.findViewById(R.id.jobs_fab);
            FAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean wrapInScrollView = true;
                    MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                            .title("New Job")
                            .customView(R.layout.jobs_dialog, wrapInScrollView)
                            .positiveText("Add Job")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                    int jobSize = ProfileContentActivity.current_profile.getMyJobs().getJob_list().size();
                                    EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_job_name_edit);
                                    EditText companyTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_job_company_name_edit);
                                    ProfileContentActivity.current_profile.getMyJobs().getJob_list()
                                            .put(jobSize + 1, new Jobs.Job(titleTxt.getText().toString(), companyTxt.getText().toString(),
                                                    month_add_job, year_add_job,ProfileContentActivity.upload_uri ));
                                    ProfileContentActivity.upload_uri="";
                                    recyclerView.getAdapter().notifyDataSetChanged();
                                }
                            })
                            .show();

                    View customDialogView = dialog.getCustomView();
                    final ImageView logo = (ImageView) customDialogView.findViewById(R.id.job_dialog_logo_img);
                    logo.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    ProfileContentActivity.upload_img = logo;
                                                    mListener.showFileChooser();
                                                }
                                            }
                    );

                    final Calendar now = Calendar.getInstance();
                    now.set(2016,01,01);
                    date_txt = (EditText) customDialogView.findViewById(R.id.add_job_date);
                    date_txt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                                    .setOnDateSetListener(JobItemFragment.this)
                                    .setFirstDayOfWeek(Calendar.SUNDAY)
                                    .setPreselectedDate(2016, now.MONTH, now.DAY_OF_MONTH)
                                    .setThemeDark();
                            cdp.show(getActivity().getSupportFragmentManager(), "FRAG_TAG_DATE_PICKER");
                        }
                    });
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnJobListFragmentInteractionListener) {
            mListener = (OnJobListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, monthOfYear);
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        month_add_job = month_date.format(cal.getTime());
        //month_add_job = new DateFormatSymbols().getMonths()[monthOfYear];
        year_add_job = String.valueOf(year);//String.format("%02d", year);
        date_txt.setText(month_add_job + ' ' + year_add_job);
    }

    public interface OnJobListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onJobListInteraction(int item, int action);
        void showFileChooser();
    }
}
