package np.com.naxa.iset.utils;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
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
    private ToastUtils() {

    }

    public static void showShortToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(int messageResource) {
        showToast(messageResource, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    public static void showLongToast(int messageResource) {
        showToast(messageResource, Toast.LENGTH_LONG);
    }

    private static void showToast(String message, int duration) {
        Toast.makeText(ISET.getInstance(), message, duration).show();
    }

    private static void showToast(int messageResource, int duration) {
        Toast.makeText(ISET.getInstance(), ISET.getInstance().getString(messageResource), duration).show();
    }

    public static void showShortToastInMiddle(int messageResource) {
        showToastInMiddle(ISET.getInstance().getString(messageResource), Toast.LENGTH_SHORT);
    }

    public static void showShortToastInMiddle(String message) {
        showToastInMiddle(message, Toast.LENGTH_SHORT);
    }

    public static void showLongToastInMiddle(int messageResource) {
        showToastInMiddle(ISET.getInstance().getString(messageResource), Toast.LENGTH_LONG);
    }

    private static void showToastInMiddle(String message, int duration) {
        Toast toast = Toast.makeText(ISET.getInstance(), message, duration);
        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(21);
        messageTextView.setGravity(Gravity.CENTER);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}

