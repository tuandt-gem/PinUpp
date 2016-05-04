package australia.godoer.pinupp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import australia.godoer.pinupp.Models.Jobs;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.CircularTransformation;
import australia.godoer.pinupp.Views.Profile.Sections.JobItemFragment.OnJobListFragmentInteractionListener;
import australia.godoer.pinupp.Views.Profile.Sections.ProfileContentActivity;

import java.util.HashMap;


public class MyJobItemRecyclerViewAdapter extends RecyclerView.Adapter<MyJobItemRecyclerViewAdapter.ViewHolder> {

    private final HashMap<Integer,Jobs.Job> mValues;
    private final OnJobListFragmentInteractionListener mListener;
    private final Context jobItemContext;

    public MyJobItemRecyclerViewAdapter(HashMap<Integer,Jobs.Job> items, OnJobListFragmentInteractionListener listener, Context jobFragContext) {
        mValues = items;
        mListener = listener;
        jobItemContext = jobFragContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_jobitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Jobs.Job temp_job = mValues.get(position+1);
        holder.mItem = temp_job;
        holder.jobItemYear.setText(temp_job.getMonth() + System.getProperty("line.separator") + temp_job.getYear());
        holder.jobItemTitle1.setText(temp_job.getTitle());
        holder.companyTitle.setText(temp_job.getCompany_name());
        if(temp_job.getImage_uri().equalsIgnoreCase("")){
            Picasso.with(jobItemContext)
                    .load(R.drawable.job_list_150)
                    .fit()
                    .transform(new CircularTransformation())
                    .into(holder.companyLogo);

        }else{
            Picasso.with(jobItemContext)
                    .load(Uri.parse(temp_job.getImage_uri()))
                    .fit()
                    .transform(new CircularTransformation())
                    .into(holder.companyLogo);
        }

        holder.contentWrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onJobListInteraction(position+1, 1);
                }
                return false;
            }
        });

        holder.delWrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onJobListInteraction(position+1, 2);
                }
                return false;
            }
        });

        holder.companyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener!=null){
                    ProfileContentActivity.upload_img = holder.companyLogo;
                    mListener.showFileChooser();
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
        public final TextView jobItemYear;
        public final TextView jobItemTitle1;
        public final TextView companyTitle;
        public final ImageView companyLogo;
        public final View delWrapper;
        public final View contentWrapper;
        public Jobs.Job mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            jobItemYear = (TextView) view.findViewById(R.id.job_item_date);
            jobItemTitle1 = (TextView) view.findViewById(R.id.job_item_title1);
            companyTitle = (TextView) view.findViewById(R.id.job_item_title2);
            companyLogo = (ImageView) view.findViewById(R.id.job_item_company_img);
            delWrapper = view.findViewById(R.id.job_del_wrapper);
            contentWrapper = view.findViewById(R.id.job_content_wrapper);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + jobItemTitle1.getText() + "'" +  companyTitle.getText() + "'";
        }
    }
}
