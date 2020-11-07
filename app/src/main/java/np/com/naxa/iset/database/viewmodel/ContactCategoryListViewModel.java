package np.com.naxa.iset.database.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.databaserepository.ContactCategoryListRepository;
import np.com.naxa.iset.emergencyContacts.model.ContactCategoryListDetails;


public class ContactCategoryListViewModel extends AndroidViewModel {

    private ContactCategoryListRepository mRepository;
    private Flowable<List<ContactCategoryListDetails>> mAllContactCategoryList;
    private Flowable<List<ContactCategoryListDetails>> mCategoryWiseList;

    public ContactCategoryListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new ContactCategoryListRepository(application);

        mAllContactCategoryList = mRepository.getAllContactCategoryList();

    }

    public Flowable<List<ContactCategoryListDetails>> getAllContactCategoryList() {
        return mAllContactCategoryList;
    }

    public Flowable<List<ContactCategoryListDetails>> getCategoryWiseList(String key) {
        mCategoryWiseList = mRepository.getCategoryWiseList(key);
        return mCategoryWiseList;
    }

    public void deleteSpecificRow(String unique_id) {
        mRepository.deleteSpecific(unique_id);
    }


    public long insert(ContactCategoryListDetails ContactCategoryListDetails) {
        Log.d("VIewholder", "insert: " + ContactCategoryListDetails.getNameId());
        return mRepository.insert(ContactCategoryListDetails);
    }
}
