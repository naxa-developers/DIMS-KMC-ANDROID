package np.com.naxa.iset.database.databaserepository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.ISETRoomDatabase;
import np.com.naxa.iset.database.dao.MessageHelperDao;
import np.com.naxa.iset.firebase.MessageHelper;

public class MessageHelperRepository {
    private MessageHelperDao mMessageHelperDao;
    private Flowable<List<MessageHelper>> mAllMessageList;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public MessageHelperRepository(Application application) {
        ISETRoomDatabase db = ISETRoomDatabase.getDatabase(application);
        mMessageHelperDao = db.messageHelperDao();
        mAllMessageList = mMessageHelperDao.getAllMessageList();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public Flowable<List<MessageHelper>> getAllMessageList() {
        return mAllMessageList;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (MessageHelper messageHelper) {
        Log.d("MessageHelperRepository", "insert: "+ messageHelper.getMessage());
        new insertAsyncTask(mMessageHelperDao).execute(messageHelper);
    }

    private static class insertAsyncTask extends AsyncTask<MessageHelper, Void, Void> {

        private MessageHelperDao mAsyncTaskDao;

        insertAsyncTask(MessageHelperDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MessageHelper... params) {
            Log.d("MessageHelperRepository", "doInBackground: "+ params[0].getMessage());
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
