package np.com.naxa.iset.drr_dictionary;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.RawRes;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import np.com.naxa.iset.home.ISET;

/**
 * Created on 11/8/17
 * by nishon.tan@gmail.com
 */

public class JSONAssetLoadTask extends AsyncTask<Void, Void, String> {
    private JSONAssetLoadListener listener;
    private BufferedReader reader = null;
    private int resourceId;
    private Context context;

    public JSONAssetLoadTask(@RawRes int resourceId, JSONAssetLoadListener listener) {
        this.listener = listener;
        this.resourceId = resourceId;
    }

    public JSONAssetLoadTask(@RawRes int resourceId, JSONAssetLoadListener listener, Context context) {
        this.listener = listener;
        this.resourceId = resourceId;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return readFromFile(resourceId);
    }

    private String readFromFile(int resourceId) {


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
            listener.onFileLoadError(e.getMessage());
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
                listener.onFileLoadError(e2.getMessage());
            }
        }

        return returnString.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onFileLoadComplete(s);
        super.onPostExecute(s);
    }
}
