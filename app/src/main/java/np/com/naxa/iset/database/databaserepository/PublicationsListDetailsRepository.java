package np.com.naxa.iset.database.databaserepository;

import android.app.Application;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.database.ISETRoomDatabase;
import np.com.naxa.iset.database.dao.PublicationsListDao;
import np.com.naxa.iset.publications.entity.PublicationsListDetails;


public class PublicationsListDetailsRepository {
    private PublicationsListDao mDao;
        Flowable<List<PublicationsListDetails>> mAllPublicationslsList;
        Flowable<List<PublicationsListDetails>> mAllCategoryWiseList;

        public PublicationsListDetailsRepository(Application application) {
            ISETRoomDatabase db = ISETRoomDatabase.getDatabase(application);
            mDao = db.publicationsListDao();
            mAllPublicationslsList = mDao.getAllPublicationslsList();

        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        public Flowable<List<PublicationsListDetails>> getAllReportDetailsList() {
            return mAllPublicationslsList;
        }

        public Flowable<List<PublicationsListDetails>> getCategoryWiseList(String type) {
            mAllCategoryWiseList = mDao.getCategoryWiseList(type);

            return mAllCategoryWiseList;
        }


        public void deleteSpecific(String unique_id){
            mDao.deleteSpecific(unique_id);
        }

        // You must call this on a non-UI thread or your app will crash.
        // Like this, Room ensures that you're not doing any long running operations on the main
        // thread, blocking the UI.
        public long insert (PublicationsListDetails PublicationsListDetails) {
            final long[] id = new long[1];
            Observable.just(PublicationsListDetails)
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new DisposableObserver<PublicationsListDetails>() {
                        @Override
                        public void onNext(PublicationsListDetails PublicationsListDetails1) {
                            id[0] =   mDao.insert(PublicationsListDetails1);
                            Log.d("MessageHelperRepository", "insert: "+ PublicationsListDetails1.getName());
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
