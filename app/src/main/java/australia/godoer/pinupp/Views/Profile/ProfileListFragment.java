package australia.godoer.pinupp.Views.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import australia.godoer.pinupp.Adapters.MyProfileItemRecyclerViewAdapter;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.Helper;

public class ProfileListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProfileListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profileitem_list, container, false);
        // Set the adapter
            Context context = view.getContext();
        TextView usernameTxt = (TextView) view.findViewById(R.id.profile_list_name_msg);
        usernameTxt.setText(HomeActivity.current_user.getFullName());
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.profile_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyProfileItemRecyclerViewAdapter(HomeActivity.current_user.getPROFILE_MAP(), mListener));
        final FloatingActionButton FAB = (FloatingActionButton) view.findViewById(R.id.profile_list_fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HomeActivity.current_user.getPROFILE_MAP().size() < 2){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NewProfileFragment()).commit();
                }else
                    Helper.showMsg(getActivity().findViewById(R.id.profile_list_layout),"Sorry, you've reached the profile limit", getResources().getColor(R.color.warnColor));
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        mListener = (OnListFragmentInteractionListener) getActivity();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(int item);
    }
}
