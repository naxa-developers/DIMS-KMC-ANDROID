package np.com.naxa.iset.home;

import android.app.Application;
import android.app.usage.ExternalStorageStats;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;
import android.widget.Toast;

import com.franmontiel.localechanger.LocaleChanger;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class ISET extends Application {
    private static Context context;

    public static final List<Locale> SUPPORTED_LOCALES =
            Arrays.asList(
                    new Locale("en", "US"),
                    new Locale("ne", "NP")
            );

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Timber.plant(new Timber.DebugTree());

        LocaleChanger.initialize(getApplicationContext(), SUPPORTED_LOCALES);
    }

    public static Context getInstance() {
        return context;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleChanger.onConfigurationChanged();
    }


}
