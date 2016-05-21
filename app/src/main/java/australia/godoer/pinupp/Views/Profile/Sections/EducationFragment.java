package australia.godoer.pinupp.Views.Profile.Sections;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.google.gson.Gson;

import australia.godoer.pinupp.Adapters.MyEducationRecyclerViewAdapter;
import australia.godoer.pinupp.Models.Education;
import australia.godoer.pinupp.Models.MySkills;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.Helper;
import australia.godoer.pinupp.Views.Profile.HomeActivity;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EducationFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;
    public static MyEducationRecyclerViewAdapter eduAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EducationFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education_list, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.education_quali_list);
        // Set the adapter
        if (recyclerView != null) {
            Context context = view.getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            eduAdapter = new MyEducationRecyclerViewAdapter(ProfileContentActivity.current_profile.getEducation().getDegreesList(), mListener, getContext());
            recyclerView.setAdapter(eduAdapter);
            final FloatingActionButton FAB = (FloatingActionButton) view.findViewById(R.id.education_fab);
            FAB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int eduSize = ProfileContentActivity.current_profile.getEducation().getDegreesList().size();
                    if (eduSize < 5) {
                        boolean wrapInScrollView = true;
                        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                                .title("New Education")
                                .customView(R.layout.education_dialog, wrapInScrollView)
                                .positiveText("Add Degree")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(MaterialDialog dialog, DialogAction which) {
                                        EditText titleTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_qualification_name_edit);
                                        EditText yearTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_qualification_year_edit);
                                        EditText instituteTxt = (EditText) dialog.getCustomView().findViewById(R.id.add_qualification_institution_edit);
                                        ProfileContentActivity.current_profile.getEducation().getDegreesList()
                                                .put(eduSize + 1, new Education.Degrees(titleTxt.getText().toString(), instituteTxt.getText().toString(), yearTxt.getText().toString(), ProfileContentActivity.upload_uri));
                                        ProfileContentActivity.upload_uri = "";
                                        recyclerView.getAdapter().notifyDataSetChanged();

                                        View customDialogView = dialog.getCustomView();
                                        // Logo
                                        final ImageView logo = (ImageView) customDialogView.findViewById(R.id.add_qualification_dialog_logo_img);
                                        logo.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        Log.e("@@@@@", "@@@@@1234");
                                                                        ProfileContentActivity.upload_img = logo;
                                                                        mListener.showEducationFileChooser();
                                                                    }
                                                                }
                                        );
                                    }
                                }).build();
                        View customDialogView = dialog.getCustomView();
                        // Logo
                        final ImageView logo = (ImageView) customDialogView.findViewById(R.id.add_qualification_dialog_logo_img);
                        logo.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        ProfileContentActivity.upload_img = logo;
                                                        mListener.showEducationFileChooser();
                                                    }
                                                }
                        );
                        dialog.show();
                    } else {
                        Helper.showMsg(getActivity().findViewById(R.id.container), "Can have only 5 items in free version", getResources().getColor(R.color.warnColor));
                    }
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onEducationListInteraction(int item, int action);

        void showEducationFileChooser();
    }
}
