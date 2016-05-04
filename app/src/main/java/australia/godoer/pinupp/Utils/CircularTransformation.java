package australia.godoer.pinupp.Utils;

import android.graphics.*;
import com.squareup.picasso.Transformation;

/**
 * Transforms an image into a circle representation. Such as a avatar.
 */
public class CircularTransformation implements Transformation
{
    //#RRGGBB
    //#AARRGGBB
//private int BORDER_COLOR = Color.parseColor("#FFFF8800");
private final int BORDER_RADIUS = -1;

    public CircularTransformation() {
       // this.BORDER_COLOR = Color.parseColor(border_color);
    }

    @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;

//            // Prepare the background
//            Paint paintBg = new Paint();
//            //paintBg.setColor(BORDER_COLOR);
//            paintBg.setAntiAlias(true);
//
//            // Draw the background circle
//            canvas.drawCircle(r, r, r, paintBg);

            // Draw the image smaller than the background so a little border will be seen
            canvas.drawCircle(r, r, r - BORDER_RADIUS, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
}