package np.com.naxa.iset.database.databaserepository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.ISETRoomDatabase;
import np.com.naxa.iset.database.combinedentity.OpenAndCommon;
import np.com.naxa.iset.database.dao.OpenSpaceDao;
import np.com.naxa.iset.database.entity.OpenSpace;


/**
 * Created by samir on 4/23/2018.
 */

public class OpenSpaceRepository {
    private OpenSpaceDao mOpenSpaceDao;
    private LiveData<List<OpenSpace>> mAllOpenSpaces;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public OpenSpaceRepository(Application application) {
        ISETRoomDatabase db = ISETRoomDatabase.getDatabase(application);
        mOpenSpaceDao = db.openSpaceDao();
        mAllOpenSpaces = mOpenSpaceDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<OpenSpace>> getAllOpenSpaces() {
        return mAllOpenSpaces;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (OpenSpace openSpace) {
        Log.d("MessageHelperRepository", "insert: "+ openSpace.getAccess_roa());
        mOpenSpaceDao.insert(openSpace);
//        new OpenSpaceRepository.insertAsyncTask(mOpenSpaceDao).execute(openSpace);
    }

    public Flowable<List<OpenAndCommon>> getAll(){
        return mOpenSpaceDao.getAllOpenSpacelist();
    }

    private static class insertAsyncTask extends AsyncTask<OpenSpace, Void, Void> {

        private OpenSpaceDao mAsyncTaskDao;

        insertAsyncTask(OpenSpaceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final OpenSpace... params) {
            Log.d("MessageHelperRepository", "doInBackground: "+ params[0].getAccommodat());
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
