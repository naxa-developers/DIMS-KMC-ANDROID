package np.com.naxa.iset.database.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.databaserepository.InventoryListDetailsRepository;
import np.com.naxa.iset.inventory.model.InventoryListDetails;


public class InventoryListDetailsViewModel extends AndroidViewModel {

    private InventoryListDetailsRepository mRepository;
    private Flowable<List<InventoryListDetails>> mAllInventoryListDetailsList;
    private Flowable<List<InventoryListDetails>> mCategoryWiseList;
    private Flowable<List<InventoryListDetails>> mSubCategoryWiseList;
    private Flowable<List<InventoryListDetails>> mCatSubCatWiseList;
    private Flowable<List<String>> mAllDistinctCatName;
    private Flowable<List<String>> mAllDistinctSubCatName;
    private Flowable<List<String>> mAllDistinctSubCatNameFromCategory;

    public InventoryListDetailsViewModel(@NonNull Application application) {
        super(application);

        mRepository = new InventoryListDetailsRepository(application);

        mAllInventoryListDetailsList = mRepository.getAllInventoryListDetailsList();
        mAllDistinctCatName = mRepository.getDistinctCategoryList();
        mAllDistinctSubCatName = mRepository.getDistinctSubCategoryist();

    }

    public Flowable<List<InventoryListDetails>> getAllInventoryListDetailsList() {
        return mAllInventoryListDetailsList;
    }

    public Flowable<List<String>> getDistinctCategoryList() {
        return mAllDistinctCatName;
    }

    public Flowable<List<String>> getDistinctSubCategoryist() {
        return mAllDistinctSubCatName;
    }

    public Flowable<List<String>> getDistinctSubCategoryistFromCategory(String category) {
        mAllDistinctSubCatNameFromCategory = mRepository.getDistinctSubCategoryistFromCategory(category);

        return mAllDistinctSubCatNameFromCategory;
    }

    public Flowable<List<InventoryListDetails>> getCategoryWiseList(String category) {
        mCategoryWiseList = mRepository.getCategoryWiseList(category);
        return mCategoryWiseList;
    }

    public Flowable<List<InventoryListDetails>> getSubCategoryWiseList(String subCategory) {
        mSubCategoryWiseList = mRepository.getSubCategoryWiseList(subCategory);
        return mSubCategoryWiseList;
    }

    public Flowable<List<InventoryListDetails>> getCatSubCatWiseList(String category, String subCategory) {
        if(category.equals("All") && subCategory.equals("All")){
            mCatSubCatWiseList = mRepository.getAllInventoryListDetailsList();
        }

        if(!category.equals("All") && subCategory.equals("All")){
            mCatSubCatWiseList = mRepository.getCategoryWiseList(category);
        }

        if(!category.equals("All") && !subCategory.equals("All")){
            mCatSubCatWiseList = mRepository.getCatSubCatWiseList(category, subCategory);
        }
        return mCatSubCatWiseList;
    }

    public void deleteSpecificRow(String unique_id) {
        mRepository.deleteSpecific(unique_id);
    }


    public long insert(InventoryListDetails InventoryListDetails) {
        Log.d("VIewholder", "insert: " + InventoryListDetails.getSubcatName());
        return mRepository.insert(InventoryListDetails);
    }

    public void insertAll(List<InventoryListDetails> InventoryListDetails) {
        Log.d("VIewholder", "insert: " + InventoryListDetails.get(0).getSubcatName());
         mRepository.insertAll(InventoryListDetails);
    }
}
