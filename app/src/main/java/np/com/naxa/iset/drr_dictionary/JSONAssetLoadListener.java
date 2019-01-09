package np.com.naxa.iset.drr_dictionary;

/**
 * Created on 11/8/17
 * by nishon.tan@gmail.com
 */


public interface JSONAssetLoadListener {

    void onFileLoadComplete(String s);

    void onFileLoadError(String errorMsg);

}

