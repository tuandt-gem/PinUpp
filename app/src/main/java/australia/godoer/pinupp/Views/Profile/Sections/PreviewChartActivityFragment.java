package australia.godoer.pinupp.Views.Profile.Sections;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import australia.godoer.pinupp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PreviewChartActivityFragment extends Fragment {

    public PreviewChartActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview_chart, container, false);
    }
}
