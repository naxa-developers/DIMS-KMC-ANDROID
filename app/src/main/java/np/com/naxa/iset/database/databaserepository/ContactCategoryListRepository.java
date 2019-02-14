package np.com.naxa.iset.database.databaserepository;

import android.app.Application;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.database.ISETRoomDatabase;
import np.com.naxa.iset.database.dao.ContactCategoryListDao;
import np.com.naxa.iset.emergencyContacts.model.ContactCategoryListDetails;

public class ContactCategoryListRepository {
    private ContactCategoryListDao mDao;
    Flowable<List<ContactCategoryListDetails>> mAllContactCategoryList;
    Flowable<List<ContactCategoryListDetails>> mAllCategoryWiseList;

    public ContactCategoryListRepository(Application application) {
        ISETRoomDatabase db = ISETRoomDatabase.getDatabase(application);
        mDao = db.contactCategoryListDao();
        mAllContactCategoryList = mDao.getAllContactCategoryList();

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public Flowable<List<ContactCategoryListDetails>> getAllContactCategoryList() {
        return mAllContactCategoryList;
    }

    public Flowable<List<ContactCategoryListDetails>> getCategoryWiseList(String type) {
        mAllCategoryWiseList = mDao.getCategoryWiseList(type);

        return mAllCategoryWiseList;
    }


    public void deleteSpecific(String unique_id){
        mDao.deleteSpecific(unique_id);
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public long insert (ContactCategoryListDetails ContactCategoryListDetails) {
        final long[] id = new long[1];
        Observable.just(ContactCategoryListDetails)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<ContactCategoryListDetails>() {
                    @Override
                    public void onNext(ContactCategoryListDetails ContactCategoryListDetails1) {
                        id[0] =   mDao.insert(ContactCategoryListDetails1);
                        Log.d("MessageHelperRepository", "insert: "+ ContactCategoryListDetails1.getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return id[0];
    }

}
