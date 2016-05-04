package australia.godoer.pinupp.Views.Profile.Sections;


import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import australia.godoer.pinupp.Adapters.MyEducationRecyclerViewAdapter;
import australia.godoer.pinupp.Adapters.SkillsRecyclerViewAdapter;
import australia.godoer.pinupp.Models.MySkills;
import australia.godoer.pinupp.Models.User;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.Helper;
import australia.godoer.pinupp.Views.Profile.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SkillsFragment extends Fragment {

    private OnSkillListInteractionListener mListener;
    public static SkillsRecyclerViewAdapter skillAdapter;

    public SkillsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skills_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.skills_skill_list);
        // Set the adapter
        if (recyclerView != null) {
            Context context = view.getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            skillAdapter = new SkillsRecyclerViewAdapter(ProfileContentActivity.current_profile.getMyskills().getSkillList(), mListener, getContext());
            recyclerView.setAdapter(skillAdapter);
            final FloatingActionButton FAB = (FloatingActionButton) view.findViewById(R.id.skills_fab);
            FAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int skillSize = ProfileContentActivity.current_profile.getMyskills().getSkillList().size();
                    if(skillSize < 5){
                    boolean wrapInScrollView = true;
                    final int[] skill_lvl = new int[1];
                    MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                            .title("New Skill")
                            .customView(R.layout.skills_dialog, wrapInScrollView)
                            .positiveText("Add Skill")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog dialog, DialogAction which) {

                                    EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.skills_dialog_add_title_edit);
                                    TextView lvlTxt = (TextView) dialog.getCustomView().findViewById(R.id.skills_dialog_level_txt);
                                    ProfileContentActivity.current_profile.getMyskills().getSkillList()
                                            .put(skillSize + 1, new MySkills.Skill(titleTxt.getText().toString(), lvlTxt.getText().toString(), skill_lvl[0]));
                                    recyclerView.getAdapter().notifyDataSetChanged();
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
                            info.setText(String.valueOf(value * 10) + "%");
                            skill_lvl[0] = value;
                        }

                        @Override
                        public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                        }
                    });
                }
                    else{
                        Helper.showMsg(getActivity().findViewById(R.id.container),"Can have only 5 items in free version", getResources().getColor(R.color.warnColor));
                    }
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSkillListInteractionListener) {
            mListener = (OnSkillListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        User.updateProfile(ProfileContentActivity.current_profile);
        mListener = null;
    }

    public interface OnSkillListInteractionListener {
        // TODO: Update argument type and name
        void onSkillListFragmentInteraction(int item, int action, int newSeek);
    }

}
