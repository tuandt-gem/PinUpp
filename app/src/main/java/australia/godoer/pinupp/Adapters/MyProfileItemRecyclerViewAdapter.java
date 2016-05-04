package australia.godoer.pinupp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import australia.godoer.pinupp.Models.Profile;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Views.Profile.ProfileListFragment.OnListFragmentInteractionListener;

public class MyProfileItemRecyclerViewAdapter extends RecyclerView.Adapter<MyProfileItemRecyclerViewAdapter.ViewHolder> {

    private final Map<Integer,Profile> mValues;
    private final OnListFragmentInteractionListener mListener;

//    public MyProfileItemRecyclerViewAdapter(List<Profile> items, OnListFragmentInteractionListener listener) {
//        mValues = items;
//        mListener = listener;
//    }Map<Integer, Profile> ITEM_MAP

    public MyProfileItemRecyclerViewAdapter(Map<Integer, Profile> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

        @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_profileitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = position+1;
        holder.mIdView.setText(String.valueOf(position+1));
        holder.mContentView.setText(mValues.get((position+1)).title);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public int mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_id);
            mContentView = (TextView) view.findViewById(R.id.item_title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
