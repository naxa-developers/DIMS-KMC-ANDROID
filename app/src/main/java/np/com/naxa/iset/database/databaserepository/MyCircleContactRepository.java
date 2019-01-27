package np.com.naxa.iset.database.databaserepository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.database.ISETRoomDatabase;
import np.com.naxa.iset.database.dao.MyCircleContactDao;
import np.com.naxa.iset.mycircle.ContactModel;

public class MyCircleContactRepository {
        private MyCircleContactDao mContactDao;
        private Flowable<List<ContactModel>> mAllContacts;
        private Flowable<List<ContactModel>> mAllRegisteredContacts;
        private Flowable<List<ContactModel>> mAllUnRegisteredContacts;
        private Flowable<List<ContactModel>> mAllMyCircleContacts;

        // Note that in order to unit test the WordRepository, you have to remove the Application
        // dependency. This adds complexity and much more code, and this sample is not about testing.
        // See the BasicSample in the android-architecture-components repository at
        // https://github.com/googlesamples
        public MyCircleContactRepository(Application application) {
            ISETRoomDatabase db = ISETRoomDatabase.getDatabase(application);
            mContactDao = db.myCircleContactDao();
            mAllContacts = mContactDao.getAllContactList();
            mAllRegisteredContacts = mContactDao.getRegisteredContactList();
            mAllUnRegisteredContacts = mContactDao.getUnRegisteredContactList();
            mAllMyCircleContacts = mContactDao.getMyCircleContactList();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        public Flowable<List<ContactModel>> getAllContacts() {
            return mAllContacts;
        }

    public Flowable<List<ContactModel>> getAllRegisteredContacts() {
        return mAllRegisteredContacts;
    }

    public Flowable<List<ContactModel>> getAllUnRegisteredContacts() {
        return mAllUnRegisteredContacts;
    }

    public Flowable<List<ContactModel>> getAllMyCircleContacts() {
        return mAllMyCircleContacts;
    }

        // You must call this on a non-UI thread or your app will crash.
        // Like this, Room ensures that you're not doing any long running operations on the main
        // thread, blocking the UI.
        public void insert (ContactModel contact) {
            Observable.just(contact)
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new DisposableObserver<ContactModel>() {
                        @Override
                        public void onNext(ContactModel contactModel) {
                            mContactDao.insert(contactModel);
                            Log.d("MessageHelperRepository", "insert: "+ contact.getName());
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }


}
