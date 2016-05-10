package australia.godoer.pinupp.Views.Profile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import australia.godoer.pinupp.Models.Profile;
import australia.godoer.pinupp.Models.User;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.CircularTransformation;
import australia.godoer.pinupp.Utils.Helper;
import australia.godoer.pinupp.Views.LoginActivity;

public class NewProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NewProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View  view = inflater.inflate(R.layout.fragment_new_profile, container, false);
        TextView username_txt = (TextView) view.findViewById(R.id.new_profile_list_name_msg);
        username_txt.setText(HomeActivity.current_user.getFullName());

        TextView msg_txt = (TextView) view.findViewById(R.id.new_profile_list_msg);
        if(HomeActivity.current_user.getPROFILE_MAP().size() == 0)
        msg_txt.setText("Let's create your first profile");
        else
        msg_txt.setText("");

        ImageView exp_img = (ImageView) view.findViewById(R.id.new_profile_image);
        Picasso.with(getContext())
                .load(R.drawable.my_info_400)
                .fit()
                .transform(new CircularTransformation())  // 10 is the radius
                .into(exp_img);

        Button getStartedBtn = (Button) view.findViewById(R.id.new_profile_get_started_btn);
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp_title = ((EditText) view.findViewById(R.id.new_profile_title_edit)).getText().toString();
                //TODO fix for map size and retrieval
                int temp_id = HomeActivity.current_user.getPROFILE_MAP().size()+1;
                if ( temp_title.length() > 0 ){
                    Profile new_profile = new Profile(temp_id,temp_title);
                    //saving the current user state
                    new_profile.getMyInfo().setFirstName(ParseUser.getCurrentUser().getString(LoginActivity.PARSE_LOGIN_FIRST_NAME_KEY));
                    new_profile.getMyInfo().setLastName(ParseUser.getCurrentUser().getString(LoginActivity.PARSE_LOGIN_LAST_NAME_KEY));
                    HomeActivity.current_user.getPROFILE_MAP().put(temp_id, new_profile);
                    ParseUser.getCurrentUser().put(User.PROFILE_MAP_KEY, new Gson().toJson(HomeActivity.current_user.getPROFILE_MAP()));
                    ParseUser.getCurrentUser().saveInBackground();
//                    ParseUser.getCurrentUser().put(LoginActivity.PARSE_LOGIN_PINUPP_USERPROFILE_KEY,new Gson().toJson(HomeActivity.current_user));
//                    ParseUser.getCurrentUser().saveEventually();
                    Bundle args = new Bundle();
                    args.putInt(HomeActivity.PROFILE_KEY,temp_id);
                    ProfileHomeFragment HomeProfileFrag = new ProfileHomeFragment();
                    HomeProfileFrag.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,HomeProfileFrag).commit();
                }else{
                    Helper.showMsg(view.findViewById(R.id.new_profile_relative_layout), "Please Enter a title!", getResources().getColor(R.color.warnColor));
                }
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
