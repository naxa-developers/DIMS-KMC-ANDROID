package np.com.naxa.iset.database.databaserepository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import np.com.naxa.iset.database.ISETRoomDatabase;
import np.com.naxa.iset.database.dao.ContactDao;
import np.com.naxa.iset.database.entity.Contact;


/**
 * Created by samir on 4/22/2018.
 */

public class ContactRepository {
    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ContactRepository(Application application) {
        ISETRoomDatabase db = ISETRoomDatabase.getDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Contact contact) {
        Log.d("MessageHelperRepository", "insert: "+ contact.getLastName());
        new insertAsyncTask(mContactDao).execute(contact);
    }

    private static class insertAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        insertAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            Log.d("MessageHelperRepository", "doInBackground: "+ params[0].getLastName());
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
