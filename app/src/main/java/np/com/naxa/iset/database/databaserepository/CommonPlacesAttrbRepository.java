package np.com.naxa.iset.database.databaserepository;


import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.ISETRoomDatabase;
import np.com.naxa.iset.database.dao.CommonPlacesAttrbDao;
import np.com.naxa.iset.database.entity.CommonPlacesAttrb;

/**
 * Created by samir on 5/08/2018.
 */

public class CommonPlacesAttrbRepository {

    private static final String TAG = "CommonPlacesAttrb";

    public static long rowID;

    public static ArrayList<Long> pID = new ArrayList<>();

    private CommonPlacesAttrbDao mCommonPlacesAttrbDao;
    private Flowable<List<CommonPlacesAttrb>> mAllCommonPlacesAttrb;
    private List<CommonPlacesAttrb> mCommonPlacesList;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public CommonPlacesAttrbRepository(Application application) {
        ISETRoomDatabase db = ISETRoomDatabase.getDatabase(application);
        mCommonPlacesAttrbDao = db.commonPlacesAttrbDao();
        mAllCommonPlacesAttrb = mCommonPlacesAttrbDao.getFirstInsertedCommonPlaces();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public Flowable<List<CommonPlacesAttrb>> getAllCommonPlacesAttrb() {
        return mAllCommonPlacesAttrb;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public long insert(CommonPlacesAttrb commonPlacesAttrb) {
        Log.d("CommonPlacesRepository", "insert: " + commonPlacesAttrb.getName());

        rowID = mCommonPlacesAttrbDao.insert(commonPlacesAttrb);
//        new CommonPlacesAttrbRepository.insertAsyncTask(mCommonPlacesAttrbDao).execute(commonPlacesAttrb);
        return rowID;
    }

    public Flowable<List<CommonPlacesAttrb>> getPlacesContaining(String value) {
        return mCommonPlacesAttrbDao.getPlacesContaining(value);
    }

    public Flowable<List<CommonPlacesAttrb>> getPlaceByType(String type){
        return mCommonPlacesAttrbDao.getPlaceByType(type);
    }



//    private static class insertAsyncTask extends AsyncTask<CommonPlacesAttrb, Void, Void> {
//
//        private CommonPlacesAttrbDao mAsyncTaskDao;
//
//        insertAsyncTask(CommonPlacesAttrbDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final CommonPlacesAttrb... params) {
//            Log.d("MessageHelperRepository", "doInBackground: "+ params[0].getName());
//            rowID = mAsyncTaskDao.insert(params[0]);
//            pID.add(rowID);
//
//       return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//    }
}
