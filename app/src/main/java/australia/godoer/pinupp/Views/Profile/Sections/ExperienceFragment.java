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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.parse.ParseUser;

import australia.godoer.pinupp.Adapters.MyExperienceRecyclerViewAdapter;
import australia.godoer.pinupp.Models.Experiences;
import australia.godoer.pinupp.Models.MySkills;
import australia.godoer.pinupp.Models.User;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.Helper;
import australia.godoer.pinupp.Views.Profile.HomeActivity;

public class ExperienceFragment extends Fragment {

    private OnExperienceListInteractionListener mListener;
    public static MyExperienceRecyclerViewAdapter expAdapter;

    public ExperienceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_experience_list, container, false);
        Button previewBtn = (Button) view.findViewById(R.id.exp_preview_chart);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.experience_list);
        // Set the adapter
        if (recyclerView != null) {
        // Set the adapter
            Context context = view.getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            expAdapter = new MyExperienceRecyclerViewAdapter(ProfileContentActivity.current_profile.getExperiences().getExperienceMap(), mListener);
            recyclerView.setAdapter(expAdapter);

            final TextView empty_txt = (TextView) view.findViewById(R.id.empty_experience_msg);
            if(ProfileContentActivity.current_profile.getExperiences().getExperienceMap().size() == 0){
                empty_txt.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerView.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, R.id.empty_experience_msg);
                recyclerView.setLayoutParams(params);
            }
            final FloatingActionButton FAB = (FloatingActionButton) view.findViewById(R.id.experience_fab);
            FAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int dynamic_size = ProfileContentActivity.current_profile.getExperiences().getExperienceMap().size();
                    if(dynamic_size < 5) {
                        boolean wrapInScrollView = true;
                        final String[] spin1_selected = new String[1];
                        final String[] spin2_selected = new String[1];
                        //final int[] spin1_pos = new int[1];
                        //final int[] spin2_pos = new int[1];
                        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
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
                                        //ProfileContentActivity.current_profile.getExperiences().total_weight += chart_weight;
                                        //Rollback for now maybe use spinner for future
                                        Experiences.Experience newExp = new Experiences.Experience(titleTxt.getText().toString(), chart_weight, value1.getText().toString(), value2.getText().toString(),
                                                val1 + " Years" + val2 + " Months");
                                        ProfileContentActivity.current_profile.getExperiences().getExperienceMap().put(dynamic_size + 1, newExp);
                                        empty_txt.setVisibility(View.GONE);
                                        recyclerView.getAdapter().notifyDataSetChanged();
                                    }
                                })
                                .show();
//                        final Spinner spin1 = (Spinner) dialog.getCustomView().findViewById(R.id.exp_spinner1);
//                        String spin_options[] = {"Years", "Months", "No", "#"};
//                        ArrayAdapter<String> spin1_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spin_options);
//                        spin1_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        spin1.setAdapter(spin1_adapter);
//                        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                spin1_selected[0] = spin1.getSelectedItem().toString();
//                                spin1_pos[0] = position;
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> parent) {
//
//                            }
//                        });
//                        final Spinner spin2 = (Spinner) dialog.getCustomView().findViewById(R.id.exp_spinner2);
//                        ArrayAdapter<String> spin2_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spin_options);
//                        spin2_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        spin2.setAdapter(spin1_adapter);
//                        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                spin2_selected[0] = spin2.getSelectedItem().toString();
//                                spin2_pos[0] = position;
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> parent) {
//
//                            }
//                        });
                    }else{
                        Helper.showMsg(getActivity().findViewById(R.id.container),"Can have only 5 items in free version", getResources().getColor(R.color.warnColor));
                    }
                }
            });

            previewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent previewIntent = new Intent(getContext(),PreviewChartActivity.class);
                    previewIntent.putExtra(HomeActivity.PROFILE_OBJ_KEY, new Gson().toJson(ProfileContentActivity.current_profile));
                    ParseUser.getCurrentUser().saveInBackground();
                    startActivity(previewIntent);
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExperienceListInteractionListener) {
            mListener = (OnExperienceListInteractionListener) context;
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

    public interface OnExperienceListInteractionListener {
        // TODO: Update argument type and name
        void onExpListInteraction(int item, int action);
    }
}
