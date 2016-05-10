package australia.godoer.pinupp.Views.Profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import australia.godoer.pinupp.Models.Profile;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.Helper;
import australia.godoer.pinupp.Views.Profile.Sections.ProfileContentActivity;

public class ProfileHomeFragment extends Fragment {

    private Profile current_profile;
    TextView my_info_txt;
    TextView experience_txt;
    TextView education_txt;
    TextView job_txt;
    TextView skills_txt;
    Button mGenerateButton;

    public ProfileHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_profile_home, container, false);
        my_info_txt = ((TextView) view.findViewById(R.id.profile_home_my_info_txt));
        experience_txt = ((TextView) view.findViewById(R.id.profile_home_experience_txt));
        education_txt = ((TextView) view.findViewById(R.id.profile_home_education_txt));
        job_txt = ((TextView) view.findViewById(R.id.profile_home_job_list_txt));
        skills_txt = ((TextView) view.findViewById(R.id.profile_home_skills_txt));
        mGenerateButton = (Button) view.findViewById(R.id.new_profile_get_started_btn);

        if (getArguments() != null) {
            // specific profile
            current_profile = HomeActivity.current_user.getPROFILE_MAP().get(getArguments().get(HomeActivity.PROFILE_KEY));
        }else if(HomeActivity.current_user.getPROFILE_MAP().size() > 0){
            // get the latest added profile
            current_profile = HomeActivity.current_user.getPROFILE_MAP().get(HomeActivity.current_user.getPROFILE_MAP().size());
        }else{
            // no profiles ! somehow ended up here, create a new one!
            Helper.showMsg(getActivity().findViewById(R.id.new_profile_relative_layout), "Can't find that profile! Please create a new one", getResources().getColor(R.color.errorColor));
            NewProfileFragment newProfileFragment = new NewProfileFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container,newProfileFragment);
            transaction.commit();
        }

        final TextView titleTxt = ((TextView) view.findViewById(R.id.profile_home_title));
        titleTxt.setText(current_profile.title.toString());
        titleTxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               return Helper.showEditDialog(titleTxt, getContext(), "New title");
            }
        });
        //set all the images
        final ImageView my_info_img = (ImageView)view.findViewById(R.id.profile_home_my_info_img);
        //Helper.insertImage(my_info_img, R.drawable.my_info_400, getContext(), "#FFFF8800");
        Helper.insertImage(my_info_img, R.drawable.my_info_400, getContext());
        my_info_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContentActivity(0);
            }
        });
        final ImageView my_exp_img = (ImageView)view.findViewById(R.id.profile_home_experience_img);
        Helper.insertImage(my_exp_img,R.drawable.experencie_150,getContext());
        my_exp_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContentActivity(1);
            }
        });
        final ImageView my_edu_img = (ImageView)view.findViewById(R.id.profile_home_education_img);
        Helper.insertImage(my_edu_img,R.drawable.education_150_v2,getContext());
        my_edu_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContentActivity(2);
            }
        });
        final ImageView my_job_img = (ImageView)view.findViewById(R.id.profile_home_job_img);
        Helper.insertImage(my_job_img,R.drawable.job_list_150,getContext());
        my_job_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContentActivity(3);
            }
        });
        final ImageView my_skills_img = (ImageView)view.findViewById(R.id.profile_home_skills_img);
        Helper.insertImage(my_skills_img,R.drawable.skill_150,getContext());
        my_skills_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContentActivity(4);
            }
        });


        //listeners for sub headings

        my_info_txt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Helper.showEditDialog(my_info_txt, getContext(), "New title");
                //perf replace with mText return from dialog
                current_profile.getMyInfo().setTitle(my_info_txt.getText().toString());
                return true;
            }
        });

        experience_txt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Helper.showEditDialog(experience_txt, getContext(), "New title");
                current_profile.getExperiences().setTitle(experience_txt.getText().toString());
                return true;
            }
        });


        education_txt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Helper.showEditDialog(education_txt, getContext(), "New title");
                current_profile.getEducation().setTitle(education_txt.getText().toString());
                return true;
            }
        });


        job_txt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Helper.showEditDialog(job_txt, getContext(), "New title");
                current_profile.getMyJobs().setTitle(job_txt.getText().toString());
                return true;
            }
        });


        skills_txt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Helper.showEditDialog(skills_txt, getContext(), "New title");
                current_profile.getMyskills().setTitle(skills_txt.getText().toString());
                return true;
            }
        });

        mGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), PreviewOverallActivity.class)
                        .putExtra(HomeActivity.PROFILE_OBJ_KEY,new Gson().toJson(current_profile)));
            }
        });
        return view;
    }

    public void startContentActivity(int secPosition){
        current_profile.getMyInfo().setTitle(my_info_txt.getText().toString());
        current_profile.getEducation().setTitle(education_txt.getText().toString());
        current_profile.getMyskills().setTitle(skills_txt.getText().toString());
        current_profile.getExperiences().setTitle(experience_txt.getText().toString());
        current_profile.getMyJobs().setTitle(job_txt.getText().toString());
        Intent intent = new Intent(getContext(), ProfileContentActivity.class);
        intent.putExtra(HomeActivity.PROFILE_OBJ_KEY,new Gson().toJson(current_profile));
        intent.putExtra(ProfileContentActivity.SECTION_ACTIVE,Integer.valueOf(secPosition));
        startActivity(intent);
    }
}
