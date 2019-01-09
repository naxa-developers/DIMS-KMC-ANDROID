package np.com.naxa.iset;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import np.com.naxa.iset.home.ISET;

public class DatabaseDataSPClass {
    private static SharedPreferences sharedPreferences;
    private static Context context;

    public DatabaseDataSPClass(Context context) {
        this.context = context;
    }

    public static boolean checkIfDataPresent(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("database",false);
    }

    public static void saveDataPresent(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ISET.getInstance());
        sharedPreferences.edit().putBoolean("database", true).commit();
    }
}
