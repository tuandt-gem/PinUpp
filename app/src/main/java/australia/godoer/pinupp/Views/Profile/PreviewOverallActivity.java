package australia.godoer.pinupp.Views.Profile;

import com.google.gson.Gson;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import australia.godoer.pinupp.Models.Education;
import australia.godoer.pinupp.Models.Experiences;
import australia.godoer.pinupp.Models.Jobs;
import australia.godoer.pinupp.Models.MyInfo;
import australia.godoer.pinupp.Models.MySkills;
import australia.godoer.pinupp.Models.Profile;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Utils.StringUtils;

/**
 * Created by neo on 5/5/2016.
 */
public class PreviewOverallActivity extends AppCompatActivity {
    // Views
    private LinearLayout mJobHistoryLayout;
    private LinearLayout mContentLayout;
    private LinearLayout mSkillSetLayout;
    private LinearLayout mEducationalLayout;

    // Profile
    private Profile mCurrentProfile;
    public static final int[] COLOR_COLL = {
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53),
            Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80),
            Color.rgb(192, 255, 140), Color.rgb(255, 247, 140), Color.rgb(255, 208, 140),
            Color.rgb(140, 234, 255), Color.rgb(255, 140, 157)
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_preview_overall);

        mContentLayout = (LinearLayout) findViewById(R.id.preview_overall_content_ll);
        mSkillSetLayout = (LinearLayout) findViewById(R.id.preview_overall_skill_set_ll);
        mEducationalLayout = (LinearLayout) findViewById(R.id.preview_overall_educational_ll);

        mJobHistoryLayout = (LinearLayout) findViewById(R.id.content_job_history_ll);

        // Get profile
        if (getIntent() != null) {
            mCurrentProfile = new Gson().fromJson(getIntent().getStringExtra(HomeActivity.PROFILE_OBJ_KEY), Profile.class);
        }

        if (mCurrentProfile == null) {
            showInitErrorDialog();
            return;
        }

        bindContent();

        // Button behavior
        findViewById(R.id.preview_overall_save_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getBitmapFromView(mContentLayout);

                File folder = new File(Environment.getExternalStorageDirectory() + "/PinUpp/");
                if (!folder.exists()) {
                    folder.mkdir();
                }

                SimpleDateFormat format = new SimpleDateFormat("yyyMMdd");

                String fileName = "PinUpp_"
                        + mCurrentProfile.getMyInfo().getFirstName()
                        + mCurrentProfile.getMyInfo().getLastName()
                        + format.format(new Date())
                        + ".jpg";
                saveBitmap(bitmap, folder.getAbsolutePath() + "/" + fileName);
            }
        });
    }

    private void bindContent() {
        bindProfile();
        bindJobHistory();
        bindExperience();
        bindSkillSet();
        bindEducational();
    }

    private void bindProfile() {
        MyInfo myInfo =  mCurrentProfile.getMyInfo();

        ((TextView) findViewById(R.id.preview_overall_available_in_tv))
                .setText(myInfo.getAvailableDate());
        ((TextView) findViewById(R.id.preview_overall_available_from_tv))
                .setText(myInfo.getAvailableFrom());
        ((TextView) findViewById(R.id.preview_overall_name_tv))
                .setText(myInfo.getFirstName() + " " + myInfo.getLastName());
        ((TextView) findViewById(R.id.preview_overall_position_tv))
                .setText(mCurrentProfile.title);
        ((TextView) findViewById(R.id.preview_overall_address_tv))
                .setText(myInfo.getAddress());
        ((TextView) findViewById(R.id.preview_overall_phone_tv))
                .setText(myInfo.getMobileNo());
        ((TextView) findViewById(R.id.preview_overall_email_tv))
                .setText(myInfo.getEmail());
        ((TextView) findViewById(R.id.preview_overall_link_tv))
                .setText(myInfo.getWebsite());
        ((TextView) findViewById(R.id.preview_overall_desc_tv))
                .setText(myInfo.getAboutMe());
    }

    private void bindEducational() {
        TreeNode root = TreeNode.root();
        for (Map.Entry<Integer, Education.Degrees> entry : mCurrentProfile.getEducation().getDegreesList().entrySet()) {
            Education.Degrees degrees = entry.getValue();
            TreeNode child = new TreeNode(degrees).setViewHolder(new EducationalNodeHolder(this));
            root.addChild(child);
        }



        AndroidTreeView tView = new AndroidTreeView(this, root);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mEducationalLayout.addView(tView.getView(), params);
    }

    private void bindSkillSet() {
        TreeNode root = TreeNode.root();

        for (Map.Entry<Integer, MySkills.Skill> entry : mCurrentProfile.getMyskills().getSkillList().entrySet()) {
            MySkills.Skill skill = entry.getValue();
            TreeNode child = new TreeNode(skill).setViewHolder(new SkillNodeHolder(this));
            root.addChild(child);
        }

        AndroidTreeView tView = new AndroidTreeView(this, root);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mSkillSetLayout.addView(tView.getView(), params);
    }

    private void bindExperience() {
        //configure the pie chart
        PieChart expChart = (PieChart) findViewById(R.id.preview_overall_exp_pie_chart);
        expChart.setUsePercentValues(true);
        expChart.setDescription("");

        expChart.setDrawHoleEnabled(true);
        expChart.setHoleRadius(5);
        expChart.setTransparentCircleRadius(6);

        expChart.setRotationEnabled(true);
        final Legend legend = expChart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setTextSize(12f);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

        final Map<Integer, Experiences.Experience> tempMap = mCurrentProfile.getExperiences().getExperienceMap();
        ArrayList<String> expTitles = new ArrayList<>();
        int[] colorArr = new int[tempMap.size()];
        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 1; i <= tempMap.size(); i++) {
            expTitles.add(tempMap.get(i).getTitle());
            yVals.add(new Entry(Math.round(tempMap.get(i).getChart_weight()), i - 1));
            colorArr[i - 1] = COLOR_COLL[i - 1];
            xVals.add(" ");
        }
        legend.setCustom(colorArr, expTitles.toArray(new String[0]));

        expChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;

                TextView infoTXt = (TextView) findViewById(R.id.preview_chart_info_txt);
                infoTXt.setText(tempMap.get(e.getXIndex() + 1).toString());
                //infoTXt.setText(leg.getLabel(e.getXIndex()) + " - " + e.getVal() + "%");
            }

            @Override
            public void onNothingSelected() {

            }
        });

        PieDataSet dataSet = new PieDataSet(yVals, " ");
        dataSet.setSliceSpace(2);
        dataSet.setSelectionShift(8);
        dataSet.setColors(colorArr);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);

        expChart.setData(data);
        expChart.highlightValues(null);
        expChart.invalidate();
    }

    private void bindJobHistory() {
        TreeNode root = TreeNode.root();
        Jobs jobs = mCurrentProfile.getMyJobs();
        for (Map.Entry<Integer, Jobs.Job> entry : jobs.getJob_list().entrySet()) {
            Jobs.Job job = entry.getValue();
            TreeNode child = new TreeNode(job)
                    .setViewHolder(new JobNodeHolder(this));
            root.addChild(child);
        }

        AndroidTreeView tView = new AndroidTreeView(this, root);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mJobHistoryLayout.addView(tView.getView(), params);
    }

    private class JobNodeHolder extends TreeNode.BaseNodeViewHolder<Jobs.Job> {

        public JobNodeHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, Jobs.Job value) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.layout_job_node, null, false);
            // Set date job
            String date = value.getMonth() + "\n" + value.getYear();
            ((TextView) view.findViewById(R.id.job_node_date_tv))
                    .setText(date);
            // Set company
            ((TextView) view.findViewById(R.id.job_node_company_tv))
                    .setText(value.getCompany_name());
            //Set icon
            TextView iconTv = (TextView) view.findViewById(R.id.job_node_icon_tv);
            ImageView iconIv = (ImageView) view.findViewById(R.id.job_node_icon_iv);
            String uri = value.getImage_uri();
            if (!StringUtils.isEmpty(uri)) {
                iconIv.setVisibility(View.VISIBLE);
                iconTv.setVisibility(View.GONE);
                iconIv.setImageURI(Uri.parse(uri));
            } else {
                iconIv.setVisibility(View.GONE);
                iconTv.setVisibility(View.VISIBLE);

                GradientDrawable bgShape = (GradientDrawable) iconTv.getBackground();
                int color = getRandomColor();
                Log.e("@@@", "color " + color);
                bgShape.setColor(color);

                String company = value.getCompany_name();
                if (!StringUtils.isEmpty(company)) {
                    iconTv.setText(company.substring(0, 1).toUpperCase());
                }
            }

            // Set position
            ((TextView) view.findViewById(R.id.job_node_position_tv))
                    .setText(value.getTitle());
            return view;
        }

    }

    private class SkillNodeHolder extends TreeNode.BaseNodeViewHolder<MySkills.Skill> {

        public SkillNodeHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, MySkills.Skill value) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.layout_skill_node, null, false);

            // Set title
            ((TextView) view.findViewById(R.id.skill_node_title_tv))
                    .setText(value.getTitle());

            // Set progress bar
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.skill_node_progress);
            progressBar
                    .setProgress(value.getSeek_val() * 10);

//            LayerDrawable layers =
//                    (LayerDrawable) ContextCompat.getDrawable(PreviewOverallActivity.this, R.drawable.progress_bar_states);
            LayerDrawable layers = (LayerDrawable) progressBar.getProgressDrawable();
            int color = getRandomColor();
            GradientDrawable shapeBg = (GradientDrawable) (layers.findDrawableByLayerId(android.R.id.background));
            ClipDrawable shapeProgress = (ClipDrawable) (layers.findDrawableByLayerId(android.R.id.progress));

//            shapeBg.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            shapeBg.setStroke(2, color);
            shapeProgress.setColorFilter(color, PorterDuff.Mode.SRC_IN);

            Log.e("@@@", "color " + color);
            progressBar.setBackgroundDrawable(layers);

            // Set level
            ((TextView) view.findViewById(R.id.skill_node_percentage_tv))
                    .setText(value.getLevel());
            return view;
        }

    }

    private class EducationalNodeHolder extends TreeNode.BaseNodeViewHolder<Education.Degrees> {

        public EducationalNodeHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, Education.Degrees value) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.layout_educational_node, null, false);

            //Set content
            String degrees = value.getQualification()
                    + "," + value.getInstitute()
                    + "," + value.getYear();
            ((TextView) view.findViewById(R.id.educational_node_degress_tv))
                    .setText(degrees);

            //Set icon
            TextView iconTv = (TextView) view.findViewById(R.id.educational_node_icon_tv);
            ImageView iconIv = (ImageView) view.findViewById(R.id.educational_node_icon_iv);
            String uri = value.getImage_uri();
            if (!StringUtils.isEmpty(uri)) {
                iconIv.setVisibility(View.VISIBLE);
                iconTv.setVisibility(View.GONE);
                iconIv.setImageURI(Uri.parse(uri));
            } else {
                iconIv.setVisibility(View.GONE);
                iconTv.setVisibility(View.VISIBLE);

                GradientDrawable bgShape = (GradientDrawable) iconTv.getBackground();
                int color = getRandomColor();
                bgShape.setColor(color);

                String institute = value.getInstitute();
                if (!StringUtils.isEmpty(institute)) {
                    iconTv.setText(institute.substring(0, 1).toUpperCase());
                }
            }
            return view;
        }

    }


    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private void saveBitmap(Bitmap bmp, String filePath) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
            showSaveErrorDialog();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                showSaveSuccessDialog();
            } catch (IOException e) {
                e.printStackTrace();
                showSaveErrorDialog();
            }
        }
    }

    private void showInitErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Can not get your profile. Try again.");
        builder.setTitle("Error");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }

    private void showSaveErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Save image error. Try again.");
        builder.setTitle("Error");
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    private void showSaveSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your profile image is saved in PinUpp folder. Enjoy.");
        builder.setTitle("Success");
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    private int getRandomColor() {
        Random random = new Random();
        int colorPos = random.nextInt(COLOR_COLL.length);
        return COLOR_COLL[colorPos];
    }
}
