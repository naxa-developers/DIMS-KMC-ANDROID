package np.com.naxa.iset.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.andrognito.flashbar.Flashbar;

import np.com.naxa.iset.home.ISET;

import static android.widget.Toast.*;

public final class ToastUtils {

    public static Flashbar infoAlert(Activity context, String msg) {
        return new Flashbar.Builder(context)
                .gravity(Flashbar.Gravity.TOP)
                .message(
                        "Flashbar is shown at the top. You can also have more than one line in "
                                + "the flashbar. The bar will dynamically adjust its size.")
                .build();

    }

    public static void showToast(@NonNull String msg) {
        Toast.makeText(ISET.getInstance(), msg, LENGTH_SHORT).show();
    }
}
