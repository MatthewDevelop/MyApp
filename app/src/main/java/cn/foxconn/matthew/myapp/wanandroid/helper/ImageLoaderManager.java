package cn.foxconn.matthew.myapp.wanandroid.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import cn.foxconn.matthew.myapp.R;

/**
 * @author:Matthew
 * @date:2018/3/3
 * @email:guocheng0816@163.com
 */

public class ImageLoaderManager {

    /**
     * 加载图片
     * @param context
     * @param imgUrl
     * @param imageView
     */
    public static void loadImage(Context context, String imgUrl, ImageView imageView) {

        Glide.with(context)
                .load(imgUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.default_img)
                        .dontAnimate()
                        .error(R.drawable.default_img)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);
    }


    /**
     * 缓存图片到本地
     */
    public static File cacheFile(Context context, String imgUrl){
        File cacheFile = null;
        FutureTarget<File> future = Glide.with(context)
                .load(imgUrl)
                .downloadOnly(500, 500);
        try {
            cacheFile = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheFile;
    }
}
