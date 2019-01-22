package np.com.naxa.iset.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.databaserepository.ContactRepository;
import np.com.naxa.iset.database.databaserepository.MyCircleContactRepository;
import np.com.naxa.iset.mycircle.ContactModel;


public class MyCircleContactViewModel extends AndroidViewModel {
    private MyCircleContactRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private Flowable<List<ContactModel>> mAllContacts;

    public MyCircleContactViewModel(Application application) {
        super(application);
        mRepository = new MyCircleContactRepository(application);

        mAllContacts = mRepository.getAllContacts();
    }
    //    contact
    public Flowable<List<ContactModel>> getAllContacts() { return mAllContacts; }

    public void insert(ContactModel contact) {
        Log.d("VIewholder", "insert: "+contact.getId());
        mRepository.insert(contact); }
}