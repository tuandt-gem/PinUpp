package australia.godoer.pinupp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import australia.godoer.pinupp.Models.Experiences;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Views.Profile.Sections.ExperienceFragment.OnExperienceListInteractionListener;

public class MyExperienceRecyclerViewAdapter extends RecyclerView.Adapter<MyExperienceRecyclerViewAdapter.ViewHolder> {

    private final Map<Integer, Experiences.Experience> mValues;
    private final OnExperienceListInteractionListener mListener;

    public MyExperienceRecyclerViewAdapter(Map<Integer,Experiences.Experience> items, OnExperienceListInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_experience, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = position+1;
        holder.mIdView.setText(String.valueOf(position+1));
        holder.mContentView.setText(mValues.get(position+1).toString());

        holder.contentWrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onExpListInteraction(holder.mItem, 1);
                }
                return false;
            }
        });

        holder.delWrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onExpListInteraction(holder.mItem, 2);
                }
                return false;
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
        public final View delWrapper;
        public final View contentWrapper;
        public int mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            delWrapper = view.findViewById(R.id.exp_del_wrapper);
            contentWrapper = view.findViewById(R.id.exp_content_wrapper);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
