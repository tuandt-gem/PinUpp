package australia.godoer.pinupp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.Map;
import australia.godoer.pinupp.Models.MySkills;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Views.Profile.Sections.EducationFragment;
import australia.godoer.pinupp.Views.Profile.Sections.SkillsFragment;
import australia.godoer.pinupp.Views.Profile.Sections.SkillsFragment.OnSkillListInteractionListener;

public class SkillsRecyclerViewAdapter extends RecyclerView.Adapter<SkillsRecyclerViewAdapter.ViewHolder> {

    private final Map<Integer, MySkills.Skill> skillMap;
    private final SkillsFragment.OnSkillListInteractionListener mListener;
    private final Context edu_cont;

    public SkillsRecyclerViewAdapter(Map<Integer, MySkills.Skill> items, OnSkillListInteractionListener listener, Context context) {
        skillMap = items;
        mListener = listener;
        edu_cont = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_skills, parent, false);
        // handle click for adding FAB
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = position+1;
        holder.SkillText.setText(skillMap.get(position + 1).getTitle());
        holder.SkillLevel.setText(skillMap.get(position + 1).getLevel());
        holder.seekbar.setProgress(skillMap.get(position+1).getSeek_val());

        holder.contentWrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onSkillListFragmentInteraction(holder.mItem, 1,0);
                }
                return false;
            }
        });

        holder.delWrapper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mListener) {
                    mListener.onSkillListFragmentInteraction(holder.mItem, 2,0);
                }
                return false;
            }
        });

        holder.seekbar.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return value * 10;
            }
        });
        holder.seekbar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                holder.SkillLevel.setText(String.valueOf(value * 10) + "%");
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                mListener.onSkillListFragmentInteraction(holder.mItem,3, holder.seekbar.getProgress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return skillMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView SkillText;
        public final TextView SkillLevel;
        public final View delWrapper;
        public final View contentWrapper;
        public final DiscreteSeekBar seekbar;
        public int mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            SkillText = (TextView) view.findViewById(R.id.skills_title);
            SkillLevel = (TextView) view.findViewById(R.id.skills_value);
            contentWrapper = view.findViewById(R.id.skills_content_wrapper);
            delWrapper = view.findViewById(R.id.skills_del_wrapper);
            seekbar = (DiscreteSeekBar) view.findViewById(R.id.skills_list_discretebar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + SkillText.getText() + "'";
        }
    }
}
