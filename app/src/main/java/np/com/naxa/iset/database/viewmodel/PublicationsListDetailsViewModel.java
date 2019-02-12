package np.com.naxa.iset.database.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.databaserepository.PublicationsListDetailsRepository;
import np.com.naxa.iset.publications.entity.PublicationsListDetails;


public class PublicationsListDetailsViewModel extends AndroidViewModel {

    private PublicationsListDetailsRepository mRepository;
    private Flowable<List<PublicationsListDetails>> mAllPublicationsListtDetailsList;
    private Flowable<List<PublicationsListDetails>> mCategoryWiseList;

    public PublicationsListDetailsViewModel(@NonNull Application application) {
        super(application);

        mRepository = new PublicationsListDetailsRepository(application);

        mAllPublicationsListtDetailsList = mRepository.getAllReportDetailsList();

    }

    public Flowable<List<PublicationsListDetails>> getAllReportDetailsList() {
        return mAllPublicationsListtDetailsList;
    }

    public Flowable<List<PublicationsListDetails>> getCategoryWiseList(String key) {
        mCategoryWiseList = mRepository.getCategoryWiseList(key);
        return mCategoryWiseList;
    }

    public void deleteSpecificRow(String unique_id) {
        mRepository.deleteSpecific(unique_id);
    }


    public long insert(PublicationsListDetails PublicationsListDetails) {
        Log.d("VIewholder", "insert: " + PublicationsListDetails.getTitle());
        return mRepository.insert(PublicationsListDetails);
    }
}
