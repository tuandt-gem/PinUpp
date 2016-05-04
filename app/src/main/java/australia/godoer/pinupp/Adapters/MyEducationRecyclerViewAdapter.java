package australia.godoer.pinupp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.util.Map;

import australia.godoer.pinupp.Models.Education;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.CircularTransformation;
import australia.godoer.pinupp.Utils.Helper;
import australia.godoer.pinupp.Views.Profile.Sections.EducationFragment.OnListFragmentInteractionListener;
import australia.godoer.pinupp.Views.Profile.Sections.ProfileContentActivity;

public class MyEducationRecyclerViewAdapter extends RecyclerView.Adapter<MyEducationRecyclerViewAdapter.ViewHolder> {

    private final Map<Integer,Education.Degrees> degreesMap;
    private final OnListFragmentInteractionListener mListener;
    private final Context edu_cont;

    public MyEducationRecyclerViewAdapter(Map<Integer,Education.Degrees> items, OnListFragmentInteractionListener listener, Context context) {
        degreesMap = items;
        mListener = listener;
        edu_cont = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_education, parent, false);
        // handle click for adding FAB
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = position+1;
        Education.Degrees tempDegree = degreesMap.get(position+1);
        holder.DegreeItemView.setText(tempDegree.toString());

        if(tempDegree.getImage_uri().equalsIgnoreCase("")){
            Picasso.with(edu_cont)
                    .load(R.drawable.education_150_v2)
                    .fit()
                    .transform(new CircularTransformation())
                    .into(holder.uniLogo);

        }else{
            Picasso.with(edu_cont)
                    .load(Uri.parse(tempDegree.getImage_uri()))
                    .fit()
                    .transform(new CircularTransformation())
                    .into(holder.uniLogo);
        }

        holder.contentWrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onEducationListInteraction(holder.mItem, 1);
                }
                return false;
            }
        });

        holder.delWrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onEducationListInteraction(holder.mItem, 2);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return degreesMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView DegreeItemView;
        public final View delWrapper;
        public final View contentWrapper;
        public final ImageView uniLogo;
        public int mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            DegreeItemView = (TextView) view.findViewById(R.id.education_qualifi);
            delWrapper = view.findViewById(R.id.education_del_wrapper);
            contentWrapper = view.findViewById(R.id.education_content_wrapper);
            uniLogo = (ImageView) view.findViewById(R.id.education_deg_img);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + DegreeItemView.getText() + "'";
        }
    }
}
