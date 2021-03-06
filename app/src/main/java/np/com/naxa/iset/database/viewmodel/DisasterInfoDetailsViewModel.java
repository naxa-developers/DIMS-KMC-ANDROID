package np.com.naxa.iset.database.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.databaserepository.DisasterInfoDetailsRepository;
import np.com.naxa.iset.disasterinfo.model.DisasterInfoDetailsEntity;

public class DisasterInfoDetailsViewModel extends AndroidViewModel {

    private DisasterInfoDetailsRepository mRepository;
    private Flowable<List<DisasterInfoDetailsEntity>> mAllDisasterInfoDetailsList;
    private Flowable<List<String>> mAllDistinctCategories;
    private Flowable<DisasterInfoDetailsEntity> mSpecificDisasterInfo;

    public DisasterInfoDetailsViewModel(@NonNull Application application) {
        super(application);

        mRepository = new DisasterInfoDetailsRepository(application);

        mAllDisasterInfoDetailsList = mRepository.getAllReportDetailsList();
        mAllDistinctCategories = mRepository.getAllDistinctCategories();
    }

    public Flowable<List<DisasterInfoDetailsEntity>> getAllReportDetailsList() { return mAllDisasterInfoDetailsList; }
    public Flowable<List<String>> getAllDistinctCategories() { return mAllDistinctCategories; }
    public Flowable<DisasterInfoDetailsEntity> getSpecificDisasterInfo(String categoryname, String subcatname) {
        mSpecificDisasterInfo = mRepository.getSpecificDisasterInfo(categoryname, subcatname);
        return mSpecificDisasterInfo; }

    public void deleteSpecificRow(String unique_id){
        mRepository.deleteSpecific(unique_id);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public long insert(DisasterInfoDetailsEntity reportDetailsEntity) {
        Log.d("VIewholder", "insert: "+reportDetailsEntity.getCategoryname());
        return mRepository.insert(reportDetailsEntity); }
}
