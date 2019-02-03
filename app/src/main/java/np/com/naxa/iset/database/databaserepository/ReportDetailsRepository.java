package np.com.naxa.iset.database.databaserepository;

import android.app.Application;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.database.ISETRoomDatabase;
import np.com.naxa.iset.database.dao.ReportDetailsDao;
import np.com.naxa.iset.database.entity.ReportDetailsEntity;

public class ReportDetailsRepository {
     private ReportDetailsDao mReportDetailsDao;
     Flowable<List<ReportDetailsEntity>> mAllReportDetailsList;
     Flowable<List<ReportDetailsEntity>> mAllUnverifiedReportDetailsList;

    public ReportDetailsRepository(Application application) {
        ISETRoomDatabase db = ISETRoomDatabase.getDatabase(application);
        mReportDetailsDao = db.reportDetailsDao();
        mAllReportDetailsList = mReportDetailsDao.getAllReportDetailsList();
        mAllUnverifiedReportDetailsList = mReportDetailsDao.getUnVerifiedReportDetailsList();

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public Flowable<List<ReportDetailsEntity>> getAllReportDetailsList() {
        return mAllReportDetailsList;
    }

    public Flowable<List<ReportDetailsEntity>> getAllUnVerifiedReportDetailsList() {
        return mAllUnverifiedReportDetailsList;
    }

    public void deleteSpecific(String unique_id){
        mReportDetailsDao.deleteSpecific(unique_id);
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public long insert (ReportDetailsEntity reportDetailsEntity) {
        final long[] id = new long[1];
        Observable.just(reportDetailsEntity)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<ReportDetailsEntity>() {
                    @Override
                    public void onNext(ReportDetailsEntity reportDetailsEntity1) {
                      id[0] =   mReportDetailsDao.insert(reportDetailsEntity1);
                        Log.d("MessageHelperRepository", "insert: "+ reportDetailsEntity1.getName_reporter());
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
