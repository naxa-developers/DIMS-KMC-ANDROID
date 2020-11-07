package np.com.naxa.iset.database.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.combinedentity.EducationAndCommon;
import np.com.naxa.iset.database.databaserepository.EducationalInstitutesRepository;
import np.com.naxa.iset.database.entity.EducationalInstitutes;

/**
 * Created by samir on 5/08/2018.
 */

public class EducationalInstitutesViewModel  extends AndroidViewModel {
    private EducationalInstitutesRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<EducationalInstitutes>> mAllEducationalInstitutes;

    public EducationalInstitutesViewModel(Application application) {
        super(application);
        mRepository = new EducationalInstitutesRepository(application);

        mAllEducationalInstitutes = mRepository.getAllEducationalInstitutes();
    }
    //    contact
    public LiveData<List<EducationalInstitutes>> getmAllCommonPlacesAttrb() { return mAllEducationalInstitutes; }

    public void insert(EducationalInstitutes educationalInstitutes) {
        Log.d("VIewholder", "insert: "+educationalInstitutes.getContact_no());
        mRepository.insert(educationalInstitutes); }

    public Flowable<List<EducationAndCommon>> getAllEducationDetailList() {
        return mRepository.getAll();
    }

}