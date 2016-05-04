package australia.godoer.pinupp.Views.Profile.Sections;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import australia.godoer.pinupp.Models.Experiences;
import australia.godoer.pinupp.Models.Profile;
import australia.godoer.pinupp.R;
import australia.godoer.pinupp.Views.Profile.HomeActivity;

public class PreviewChartActivity extends AppCompatActivity {

    private Profile current_profile;
    public static final int[] COLOR_COLL = {
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53),
            Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80),
            Color.rgb(192, 255, 140), Color.rgb(255, 247, 140), Color.rgb(255, 208, 140),
            Color.rgb(140, 234, 255), Color.rgb(255, 140, 157)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_chart);
        if(getIntent() != null){
            current_profile = new Gson().fromJson(getIntent().getStringExtra(HomeActivity.PROFILE_OBJ_KEY),Profile.class);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //configure the pie chart
        PieChart exp_chart = (PieChart) findViewById(R.id.exp_pie_chart);
        exp_chart.setUsePercentValues(true);
        exp_chart.setDescription("Your Experience breakdown");

        exp_chart.setDrawHoleEnabled(true);
        exp_chart.setHoleRadius(5);
        exp_chart.setTransparentCircleRadius(6);

        exp_chart.setRotationEnabled(true);
        final Legend leg = exp_chart.getLegend();
        leg.setWordWrapEnabled(true);
        leg.setTextSize(12f);

        final Map<Integer,Experiences.Experience> temp_map = current_profile.getExperiences().getExperienceMap();
        ArrayList<String> exp_titles = new ArrayList<>();
        int[] color_arr = new int[temp_map.size()];
        ArrayList<Entry> y_vals = new ArrayList<Entry>();
        ArrayList<String> x_vals = new ArrayList<>();
        for(int i=1;i<=temp_map.size();i++){
            exp_titles.add(temp_map.get(i).getTitle());
            y_vals.add(new Entry(Math.round(temp_map.get(i).getChart_weight()),i-1));
            color_arr[i-1] = COLOR_COLL[i-1];
            x_vals.add(" ");
        }
        leg.setCustom(color_arr, exp_titles.toArray(new String[0]));

        exp_chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;

                TextView infoTXt = (TextView) findViewById(R.id.preview_chart_info_txt);
                infoTXt.setText(temp_map.get(e.getXIndex()+1).toString());
                //infoTXt.setText(leg.getLabel(e.getXIndex()) + " - " + e.getVal() + "%");
            }

            @Override
            public void onNothingSelected() {

            }
        });

        PieDataSet dataSet = new PieDataSet(y_vals, " ");
        dataSet.setSliceSpace(2);
        dataSet.setSelectionShift(8);
        dataSet.setColors(color_arr);

        PieData data = new PieData(x_vals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);

        exp_chart.setData(data);
        exp_chart.highlightValues(null);
        exp_chart.invalidate();
    }

}
