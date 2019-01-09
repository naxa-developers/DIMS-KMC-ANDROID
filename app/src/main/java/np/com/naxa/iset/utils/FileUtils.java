package np.com.naxa.iset.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import np.com.naxa.iset.home.ISET;

/**
 * Created by nishon on 3/2/18.
 */

public class FileUtils {

    public static String readFromFile(int resourceId) {


        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;


        try {
            fIn = ISET.getInstance().getResources().openRawResource(resourceId);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {

            e.printStackTrace();
            e.getMessage();

        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {

                e2.printStackTrace();
                e2.getMessage();

            }
        }

        return returnString.toString();
    }
}
