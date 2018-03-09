package cn.foxconn.matthew.mobilesafe.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import cn.foxconn.matthew.mobilesafe.R;


/**
 * autour: lqm
 * desc: 系统自带的分享
 */
//TODO 如何调用系统自带的分享功能
public class SharesUtils {

    public static void share(Context context, int stringRes) {
        share(context, context.getString(stringRes));
    }


    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }

    /**
     * 分享功能
     * @param context
     * @param extraText
     */
    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.action_share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(
                Intent.createChooser(intent, context.getString(R.string.action_share)));
    }
}
