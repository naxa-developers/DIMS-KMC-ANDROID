package np.com.naxa.iset.database.databaserepository;

import android.app.Application;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.database.ISETRoomDatabase;
import np.com.naxa.iset.database.dao.DisasterInfoDetailsDao;
import np.com.naxa.iset.disasterinfo.model.DisasterInfoDetailsEntity;

public class DisasterInfoDetailsRepository {private DisasterInfoDetailsDao mDao;
    Flowable<List<DisasterInfoDetailsEntity>> mAllDisasterInfoDetailsList;
    Flowable<List<String>> mAllDistinctDisasterList;
    Flowable<DisasterInfoDetailsEntity> mSpecificDisasterInfoDetails;

    public DisasterInfoDetailsRepository(Application application) {
        ISETRoomDatabase db = ISETRoomDatabase.getDatabase(application);
        mDao = db.disasterInfoDetailsDao();
        mAllDisasterInfoDetailsList = mDao.getAllDisasterInfoDetailsEntityList();
        mAllDistinctDisasterList = mDao.getAllDistinctCategories();

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public Flowable<List<DisasterInfoDetailsEntity>> getAllReportDetailsList() {
        return mAllDisasterInfoDetailsList;
    }
    public Flowable<List<String>> getAllDistinctCategories() {
        return mAllDistinctDisasterList;
    }

    public Flowable<DisasterInfoDetailsEntity> getSpecificDisasterInfo(String categoryname, String subcatname) {
        mSpecificDisasterInfoDetails = mDao.getSpecificDisasterInfoDetailsEntity(categoryname, subcatname);
        return mSpecificDisasterInfoDetails;
    }

    public void deleteSpecific(String id_from_server){
        mDao.deleteSpecific(id_from_server);
    }

    public void deleteAll(){
        mDao.deleteAll();
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public long insert (DisasterInfoDetailsEntity disasterInfoDetailsEntity) {
        final long[] id = new long[1];
        Observable.just(disasterInfoDetailsEntity)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<DisasterInfoDetailsEntity>() {
                    @Override
                    public void onNext(DisasterInfoDetailsEntity disasterInfoDetailsEntity1) {
                        Log.d("MessageHelperRepository", "insert: "+ disasterInfoDetailsEntity1.getSubcatname());
                        id[0] =   mDao.insert(disasterInfoDetailsEntity1);
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
