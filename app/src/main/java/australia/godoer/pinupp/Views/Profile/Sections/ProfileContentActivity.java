package australia.godoer.pinupp.Views.Profile.Sections;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.google.gson.Gson;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.squareup.picasso.Picasso;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import australia.godoer.pinupp.Models.Education;
import australia.godoer.pinupp.Models.Experiences;
import australia.godoer.pinupp.Models.Jobs;
import australia.godoer.pinupp.Models.MySkills;
import australia.godoer.pinupp.Models.Profile;
import australia.godoer.pinupp.Models.User;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.CircularTransformation;
import australia.godoer.pinupp.Utils.Helper;
import australia.godoer.pinupp.Views.Profile.HomeActivity;

public class ProfileContentActivity extends AppCompatActivity implements EducationFragment.OnListFragmentInteractionListener,
        SkillsFragment.OnSkillListInteractionListener, ExperienceFragment.OnExperienceListInteractionListener,
        JobItemFragment.OnJobListFragmentInteractionListener, CalendarDatePickerDialogFragment.OnDateSetListener {

    private static final int FILE_CODE = 211;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static Profile current_profile;
    public static final String SECTION_ACTIVE = "section_active";
    private int sec_active;
    public static ImageView upload_img;
    public static String upload_uri = "";
    private EditText job_date_txt;
    private String month_add_job;
    private String year_add_job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_content);
        if(getIntent() != null){
            if(getIntent().hasExtra(HomeActivity.PROFILE_OBJ_KEY))
                current_profile = new Gson().fromJson(getIntent().getStringExtra(HomeActivity.PROFILE_OBJ_KEY), Profile.class);
            if(getIntent().hasExtra(SECTION_ACTIVE))
                sec_active = getIntent().getIntExtra(SECTION_ACTIVE,0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(sec_active);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            User.updateProfile(current_profile);
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSkillListFragmentInteraction(final int item, int action, int newSeekVal) {
        if (action == 1){
            //show the dialog
            boolean wrapInScrollView = true;
            final int[] skill_lvl = new int[1];
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title("Update Skill")
                    .customView(R.layout.skills_dialog, wrapInScrollView)
                    .positiveText("Update")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            int skillSize = ProfileContentActivity.current_profile.getMyskills().getSkillList().size();
                            EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.skills_dialog_add_title_edit);
                            TextView lvlTxt = (TextView) dialog.getCustomView().findViewById(R.id.skills_dialog_level_txt);
                            MySkills.Skill temp = ProfileContentActivity.current_profile.getMyskills().getSkillList().get(item);
                            temp.setTitle(titleTxt.getText().toString());
                            temp.setLevel(lvlTxt.getText().toString());
                            temp.setSeek_val(skill_lvl[0]);
                            SkillsFragment.skillAdapter.notifyDataSetChanged();
                        }
                    })
                    .show();

            View dialogCustomView = dialog.getCustomView();
            final TextView info = (TextView) dialogCustomView.findViewById(R.id.skills_dialog_level_txt);
            final DiscreteSeekBar discreteSeekBar1 = (DiscreteSeekBar) dialogCustomView.findViewById(R.id.skills_discretebar);
            discreteSeekBar1.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
                @Override
                public int transform(int value) {
                    return value * 10;
                }
            });
            discreteSeekBar1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                @Override
                public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                    info.setText( String.valueOf(value *10 ) + "%");
                    skill_lvl[0] = value;
                }

                @Override
                public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                }
            });
            EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.skills_dialog_add_title_edit);
            titleTxt.setText(current_profile.getMyskills().getSkillList().get(item).getTitle());
            TextView lvlTxt = (TextView) dialog.getCustomView().findViewById(R.id.skills_dialog_level_txt);
            lvlTxt.setText(current_profile.getMyskills().getSkillList().get(item).getTitle());

        }else if(action == 2){
            //delete the skill
            current_profile.getMyskills().getSkillList().remove(item);
            SkillsFragment.skillAdapter.notifyDataSetChanged();
            Helper.showMsg(findViewById(R.id.container),"Skill has been deleted", getResources().getColor(R.color.warnColor));
        }else{
            current_profile.getMyskills().getSkillList().get(item).setSeek_val(newSeekVal);
            current_profile.getMyskills().getSkillList().get(item).setLevel(String.valueOf(newSeekVal *10 ) + "%");
        }
    }

    @Override
    public void onExpListInteraction(final int item, int action) {

        if(action == 1){
            boolean wrapInScrollView = true;
//            final String[] spin1_selected = new String[1];
//            final String[] spin2_selected = new String[1];
//            final int[] spin1_pos = new int[1];
//            final int[] spin2_pos = new int[1];
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title("New Experience")
                    .customView(R.layout.experience_dialog, wrapInScrollView)
                    .positiveText("Add Experience")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.exp_dialog_add_title_edit);
                            EditText value1 = (EditText) dialog.getCustomView().findViewById(R.id.exp_dialog_year_edit);
                            EditText value2 = (EditText) dialog.getCustomView().findViewById(R.id.exp_dialog_month_edit);

                            String val1 = value1.getText().toString();
                            String val2 = value2.getText().toString();
                            int years;
                            int months;
                            if(val1.length() == 0){
                                years = 0;
                                val1 = String.valueOf(0);
                            }else{
                                years = Integer.parseInt(val1)*12;
                            }

                            if(val2.length() == 0){
                                months = 0;
                                val2 = String.valueOf(0);
                            }else
                                months = Integer.parseInt(val2);
                            float chart_weight = Float.valueOf(years+months);
                            Experiences.Experience updateExp = ProfileContentActivity.current_profile.getExperiences().getExperienceMap().get(item);
                            updateExp.setTitle(titleTxt.getText().toString());
                            updateExp.setChart_weight(chart_weight);
                            //Rollback for now maybe use spinner for future
                            //updateExp.setTotalValStr(value1.getText().toString() + " " + spin1_selected[0] + " " + value2.getText().toString() + " " + spin2_selected[0]);
                            updateExp.setTotalValStr(val1 + " Years" + val2 + " Months");
                            updateExp.setValue1(value1.getText().toString());
                            updateExp.setValue2(value2.getText().toString());
                            //hardcoding years and months for reverting back to textview
                            //updateExp.setSpin1_pos(spin1_pos[0]);
                            //updateExp.setSpin2_pos(spin2_pos[0]);
                            ExperienceFragment.expAdapter.notifyDataSetChanged();
                        }
                    })
                    .show();
            Experiences.Experience toUpdateExp = ProfileContentActivity.current_profile.getExperiences().getExperienceMap().get(item);
            EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.exp_dialog_add_title_edit);
            titleTxt.setText(toUpdateExp.getTitle());
            EditText value1 = (EditText) dialog.getCustomView().findViewById(R.id.exp_dialog_year_edit);
            EditText value2 = (EditText) dialog.getCustomView().findViewById(R.id.exp_dialog_month_edit);
            value1.setText(toUpdateExp.getValue1());
            value2.setText(toUpdateExp.getValue2());

//            final Spinner spin1 = (Spinner) dialog.getCustomView().findViewById(R.id.exp_spinner1);
//            String spin_options[] = {"Years","Months","No","#"};
//            ArrayAdapter<String> spin1_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spin_options);
//            spin1_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spin1.setAdapter(spin1_adapter);
//            spin1.setSelection(toUpdateExp.getSpin1_pos());
//            spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    spin1_selected[0] = spin1.getSelectedItem().toString();
//                    spin1_pos[0] = position;
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
//            final Spinner spin2 = (Spinner) dialog.getCustomView().findViewById(R.id.exp_spinner2);
//            ArrayAdapter<String> spin2_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spin_options);
//            spin2_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spin2.setAdapter(spin1_adapter);
//            spin2.setSelection(toUpdateExp.getSpin2_pos());
//            spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    spin2_selected[0] = spin2.getSelectedItem().toString();
//                    spin2_pos[0] = position;
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
        }else{
            current_profile.getExperiences().getExperienceMap().remove(item);
            ExperienceFragment.expAdapter.notifyDataSetChanged();
            Helper.showMsg(findViewById(R.id.container),"Experience has been deleted", getResources().getColor(R.color.warnColor));
        }

    }

    @Override
    public void onEducationListInteraction(final int item, int action) {
       if(action == 1){
           boolean wrapInScrollView = true;
           MaterialDialog dialog = new MaterialDialog.Builder(this)
                   .title("Update Degree")
                   .customView(R.layout.education_dialog, wrapInScrollView)
                   .positiveText("Update")
                   .onPositive(new MaterialDialog.SingleButtonCallback() {
                       @Override
                       public void onClick(MaterialDialog dialog, DialogAction which) {
                           EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_qualification_name_edit);
                           EditText yearTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_qualification_year_edit);
                           EditText instituteTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_qualification_institution_edit);
                           Education.Degrees temp = current_profile.getEducation().getDegreesList().get(item);
                           temp.setQualification(titleTxt.getText().toString());
                           temp.setYear(yearTxt.getText().toString());
                           temp.setInstitute(instituteTxt.getText().toString());
                           EducationFragment.eduAdapter.notifyDataSetChanged();
                       }
                   })
                   .show();
           EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_qualification_name_edit);
           EditText yearTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_qualification_year_edit);
           EditText instituteTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_qualification_institution_edit);
           Education.Degrees temp = current_profile.getEducation().getDegreesList().get(item);
           titleTxt.setText(temp.getQualification());
           yearTxt.setText(temp.getYear());
           instituteTxt.setText(temp.getInstitute());
       }else{
           current_profile.getEducation().getDegreesList().remove(item);
           EducationFragment.eduAdapter.notifyDataSetChanged();
           Helper.showMsg(findViewById(R.id.container), "Qualification has been deleted", getResources().getColor(R.color.warnColor));
       }
    }

    @Override
    public void onJobListInteraction(final int item, int action) {
        if(action == 1){
            boolean wrapInScrollView = true;
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title("New Job")
                    .customView(R.layout.jobs_dialog, wrapInScrollView)
                    .positiveText("Add Job")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            int jobSize = ProfileContentActivity.current_profile.getMyJobs().getJob_list().size();
                            EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_job_name_edit);
                            EditText companyTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_job_company_name_edit);
                            Jobs.Job current_job = ProfileContentActivity.current_profile.getMyJobs().getJob_list().get(item);
                            current_job.setCompany_name(companyTxt.getText().toString());
                            current_job.setTitle(companyTxt.getText().toString());
                            current_job.setImage_uri(upload_uri);
                            current_job.setMonth(month_add_job);
                            current_job.setYear(year_add_job);
                            JobItemFragment.jobListAdapter.notifyDataSetChanged();
                        }
                    })
                    .show();

            View customDialogView = dialog.getCustomView();
            final ImageView logo = (ImageView) customDialogView.findViewById(R.id.job_dialog_logo_img);
            logo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ProfileContentActivity.upload_img = logo;
                                            showFileChooser();
                                        }
                                    }
            );

            final Calendar now = Calendar.getInstance();
            now.set(2016,01,01);
            job_date_txt = (EditText) customDialogView.findViewById(R.id.add_job_date);
            job_date_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                            .setOnDateSetListener(ProfileContentActivity.this)
                            .setFirstDayOfWeek(Calendar.SUNDAY)
                            .setPreselectedDate(2016, now.MONTH, now.DAY_OF_MONTH)
                            .setThemeDark();
                    cdp.show(getSupportFragmentManager(), "FRAG_TAG_DATE_PICKER");
                }
            });
            Jobs.Job current_job = ProfileContentActivity.current_profile.getMyJobs().getJob_list().get(item);
            EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_job_name_edit);
            EditText companyTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_job_company_name_edit);
            job_date_txt.setText(current_job.getMonth() + " " + current_job.getYear());
            titleTxt.setText(current_job.getTitle());
            companyTxt.setText(current_job.getCompany_name());
                Picasso.with(getApplicationContext())
                        .load(Uri.parse(current_job.getImage_uri()))
                        .fit()
                        .transform(new CircularTransformation())
                        .into(upload_img);
        }else{
            current_profile.getMyJobs().getJob_list().remove(item);
            JobItemFragment.jobListAdapter.notifyDataSetChanged();
            Helper.showMsg(findViewById(R.id.container), "Job has been deleted", getResources().getColor(R.color.warnColor));
        }
    }


    public void showFileChooser() {
        // This always works
        Intent i = new Intent(getApplicationContext(), FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, FILE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                // For JellyBean and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                {
                    ClipData clip = data.getClipData();
                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            Uri uri = clip.getItemAt(i).getUri();
                        }
                    }
                }
            } else {
                upload_uri = data.getData().toString();
                Picasso.with(getApplicationContext())
                        .load(Uri.parse(upload_uri))
                        .fit()
                        .transform(new CircularTransformation())
                        .into(upload_img);
            }
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, monthOfYear);
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        month_add_job = month_date.format(cal.getTime());
        //month_add_job = new DateFormatSymbols().getMonths()[monthOfYear];
        year_add_job = String.valueOf(year);//String.format("%02d", year);
        job_date_txt.setText(month_add_job + '/' + year_add_job);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0){
                return new MyInfoFragment();
            }else if(position == 1){
                return new ExperienceFragment();
            }else if(position == 2){
                return new EducationFragment();
            }else if(position == 3){
                return new JobItemFragment();
            }else if(position == 4){
                return new SkillsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return current_profile.getMyInfo().getTitle();
                case 1:
                    return current_profile.getExperiences().getTitle();
                case 2:
                    return current_profile.getEducation().getTitle();
                case 3:
                    return current_profile.getMyJobs().getTitle();
                case 4:
                    return current_profile.getMyskills().getTitle();
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        User.updateProfile(current_profile);
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        User.updateProfile(current_profile);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        User.updateProfile(current_profile);
        super.onStop();
    }
}
