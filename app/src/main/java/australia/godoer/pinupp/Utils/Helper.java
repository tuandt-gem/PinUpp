package australia.godoer.pinupp.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import australia.godoer.pinupp.R;

/**
 * Created by naveen on 2/27/2016.
 */
public class Helper {


    public static void showMsg(View viewId, String msg, int msgColor) {
        Snackbar snackbar = Snackbar.make( viewId, msg, Snackbar.LENGTH_SHORT);
        View view1 = snackbar.getView();
//        view1.setBackgroundColor(Color.);
        TextView tv = (TextView) view1.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(msgColor);
        snackbar.show();
    }

    public static void insertImage(ImageView imgview, int drawable,Context context){
        Picasso.with(context)
                .load(drawable)
                .fit()
                .transform(new CircularTransformation())
                .into(imgview);
    }

    public static boolean showEditDialog(final TextView txtUpdate,Context context, String msg){

        new MaterialDialog.Builder(context)
                .title("Your input")
                .content("Please enter the new Value")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(msg, msg, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        txtUpdate.setText(input.toString());
                    }
                }).show();
        return true;
    }
}
