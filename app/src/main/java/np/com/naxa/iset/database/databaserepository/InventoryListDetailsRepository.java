package np.com.naxa.iset.database.databaserepository;

import android.app.Application;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.database.ISETRoomDatabase;
import np.com.naxa.iset.database.dao.InventoryListDetailsDao;
import np.com.naxa.iset.inventory.model.InventoryListDetails;

public class InventoryListDetailsRepository {
    private InventoryListDetailsDao mDao;
    Flowable<List<InventoryListDetails>> mAllPublicationslsList;
    Flowable<List<InventoryListDetails>> mAllCategoryWiseList;
    Flowable<List<InventoryListDetails>> mAllSubCategoryWiseList;
    Flowable<List<InventoryListDetails>> mAllCatSubCatWiseList;
    Flowable<List<String>> mAllDistinctCatName;
    Flowable<List<String>> mAllDistinctSubCatName;

    public InventoryListDetailsRepository(Application application) {
        ISETRoomDatabase db = ISETRoomDatabase.getDatabase(application);
        mDao = db.inventoryListDetailsDao();
        mAllPublicationslsList = mDao.getAllInventoryListDetailsList();
        mAllDistinctCatName = mDao.getDistinctCategoryList();
        mAllDistinctSubCatName = mDao.getDistinctSubCategoryist();

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public Flowable<List<InventoryListDetails>> getAllInventoryListDetailsList() {
        return mAllPublicationslsList;
    }

    public Flowable<List<String>> getDistinctCategoryList() {
        return mAllDistinctCatName;
    }

    public Flowable<List<String>> getDistinctSubCategoryist() {
        return mAllDistinctSubCatName;
    }

    public Flowable<List<InventoryListDetails>> getCategoryWiseList(String category) {
        mAllCategoryWiseList = mDao.getCategoryWiseInventoryList(category);

        return mAllCategoryWiseList;
    }

    public Flowable<List<InventoryListDetails>> getSubCategoryWiseList(String subCategory) {
        mAllSubCategoryWiseList = mDao.getSubCategoryWiseInventoryList(subCategory);

        return mAllSubCategoryWiseList;
    }

    public Flowable<List<InventoryListDetails>> getCatSubCatWiseList(String category, String subCategory) {
        mAllCatSubCatWiseList = mDao.getCatSubCatWiseInventoryList(category, subCategory);

        return mAllCatSubCatWiseList;
    }


    public void deleteSpecific(String unique_id){
        mDao.deleteSpecific(unique_id);
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public long insert (InventoryListDetails InventoryListDetails) {
        final long[] id = new long[1];
        Observable.just(InventoryListDetails)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableObserver<InventoryListDetails>() {
                    @Override
                    public void onNext(InventoryListDetails InventoryListDetails1) {
                        id[0] =   mDao.insert(InventoryListDetails1);
                        Log.d("MessageHelperRepository", "insert: "+ InventoryListDetails1.getCategory());
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

    public void insertAll(List<InventoryListDetails> inventoryListDetailsList){
//        List<Long> longList = null;

        Observable.just(inventoryListDetailsList)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMapIterable(new Function<List<InventoryListDetails>, Iterable<InventoryListDetails>>() {
                    @Override
                    public Iterable<InventoryListDetails> apply(List<InventoryListDetails> inventoryListDetails) throws Exception {
                        return inventoryListDetails;
                    }
                })
                .map(new Function<InventoryListDetails, InventoryListDetails>() {
                    @Override
                    public InventoryListDetails apply(InventoryListDetails inventoryListDetails) throws Exception {

                        return inventoryListDetails;
                    }
                })
                .subscribe(new DisposableObserver<InventoryListDetails>() {
                    @Override
                    public void onNext(InventoryListDetails inventoryListDetails) {
                       mDao.insert(inventoryListDetails);
                        Log.d("MessageHelperRepository", "insert: "+ inventoryListDetails.getCategorySlug());
                        Log.d("MessageHelperRepository", "insert: "+ inventoryListDetails.getSubCatSlug());

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
