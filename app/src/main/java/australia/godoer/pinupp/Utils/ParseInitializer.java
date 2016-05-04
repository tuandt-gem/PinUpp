package australia.godoer.pinupp.Utils;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by naveen on 2/22/2016.
 */
public class ParseInitializer extends Application {

    private static final String APPLICATION_ID = "PkVC0BvRy98NoSYM3yMNUWzWaxZzphtfxuz8dugz";
    private static final String CLIENT_KEY = "zwzagUgSZWhhZdFs9JTugckVACKjvHhlFkRQr8Y1";

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
    }
}
