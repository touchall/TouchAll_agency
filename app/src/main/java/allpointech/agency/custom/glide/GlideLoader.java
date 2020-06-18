package allpointech.agency.custom.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;


/**
 * Created by user on 2016-03-09.
 */
public class GlideLoader {

    private static int getDefaultImage() {
        return 0;
    }

    public static void loadView(Context context, String img_url, ImageView imgView) {
        Glide.with(context)
                .load(img_url)
                .thumbnail(0.6f)
                .into(imgView);

    }

    public static void loadView(Context context, String img_url, int default_img_res_id, ImageView imgView) {
        Glide.with(context)
                .load(img_url)
                .error(default_img_res_id)
                .thumbnail(0.6f)
                .into(imgView);

    }

    public static void loadView(Context context, String img_url, ImageView imgView, RequestListener<String, GlideDrawable> listener) {
        Glide.with(context)
                .load(img_url)
                .error(getDefaultImage())
                .listener(listener)
                .into(imgView);
    }

    public static void loadCircleView(Context context, String img_url, ImageView imgView) {
        Glide.with(context)
                .load(img_url)
                .bitmapTransform(new CircleTransformation(context))
                .error(getDefaultImage())
                .thumbnail(0.6f)
                .into(imgView);

    }

    public static void loadCircleView(Context context, String img_url, int default_img_res_id, ImageView imgView) {
        Glide.with(context)
                .load(img_url)
                .bitmapTransform(new CircleTransformation(context))
                .error(default_img_res_id)
                .thumbnail(0.6f)
                .into(imgView);

    }

    public static void loadFileView(Context context, String img_url, ImageView imgView) {
        Glide.with(context)
                .load(img_url)
                .dontAnimate()
//                .dontTransform()
                .placeholder(getDefaultImage())
                .error(getDefaultImage())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgView);
    }

    public static void loadFileView(Context context, String img_url, int default_img_res_id, ImageView imgView) {
        Glide.with(context)
                .load(img_url)
                .dontAnimate()
//                .dontTransform()
                .placeholder(getDefaultImage())
                .error(getDefaultImage())
                .thumbnail(0.5f)
                .error(default_img_res_id)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgView);
    }
}
