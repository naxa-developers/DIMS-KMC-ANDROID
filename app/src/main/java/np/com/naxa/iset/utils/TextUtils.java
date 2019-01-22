package np.com.naxa.iset.utils;

import android.support.annotation.NonNull;
import android.util.Log;

public class TextUtils {
    private static final String TAG = "TextUtils";

    public static String validatePhoneNumber (@NonNull String rawPhoneNumber){
        String new_phoneNo = "";
        
        //            trunk '-' from contact number
        String trunk = rawPhoneNumber;
        String temp = trunk.replaceAll("-", "");
        if(rawPhoneNumber.length() > 10){

            int startIndex = 0;
            int endIndex = temp.length() - 10;
            Log.d(TAG, "validatePhoneNumber: "+ startIndex + " - "+endIndex);

            StringBuilder sb = new StringBuilder(temp);

            sb.delete(startIndex, endIndex);
            for (int i = endIndex; i < startIndex ; i-- ){
                sb.deleteCharAt(i);
            }
            new_phoneNo = sb.toString();

        }else {
            new_phoneNo = temp ;
        }

        Log.d(TAG, "validatePhoneNumber: "+ rawPhoneNumber);
        Log.d(TAG, "validatePhoneNumber: "+ new_phoneNo);

        return new_phoneNo;
    }
}
