package com.ihsan.brighterbrain.commons;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ihsan.brighterbrain.R;

/**
 * Created by ihsan on 1/9/2016.
 */
public class TransitionManager {

    public static void transitFrom(Activity activity, Class toActivity, Bundle bundle)
    {
        startActivity(activity, toActivity, bundle);
        activity.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    public static void slideUp(Activity activity, Class toActivity)
    {
        startActivity(activity, toActivity, null);
        activity.overridePendingTransition(R.anim.slide_up_activity, R.anim.slide_no_anim);

    }

    private static void startActivity(Activity activity, Class toActivity, Bundle bundle)
    {
        Intent i = new Intent(activity, toActivity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (bundle != null)
        {
            i.putExtras(bundle);
        }
        activity.startActivity(i);
    }

}
