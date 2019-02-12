package np.com.naxa.iset.database.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.databaserepository.MyCircleContactRepository;
import np.com.naxa.iset.mycircle.ContactModel;


public class MyCircleContactViewModel extends AndroidViewModel {
    private MyCircleContactRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private Flowable<List<ContactModel>> mAllContacts;
    private Flowable<List<ContactModel>> mAllRegisteredContacts;
    private Flowable<List<ContactModel>> mAllUnRegisteredContacts;
    private Flowable<List<ContactModel>> mAllMyCircleContacts;

    public MyCircleContactViewModel(Application application) {
        super(application);
        mRepository = new MyCircleContactRepository(application);

        mAllContacts = mRepository.getAllContacts();
        mAllRegisteredContacts = mRepository.getAllRegisteredContacts();
        mAllUnRegisteredContacts = mRepository.getAllUnRegisteredContacts();
        mAllMyCircleContacts = mRepository.getAllMyCircleContacts();
    }
    //    contact
    public Flowable<List<ContactModel>> getAllContacts() { return mAllContacts; }
    public Flowable<List<ContactModel>> getAllRegisteredContacts() { return mAllRegisteredContacts; }
    public Flowable<List<ContactModel>> getAllUnRegisteredContacts() { return mAllUnRegisteredContacts; }
    public Flowable<List<ContactModel>> getAllMyCircleContacts() { return mAllMyCircleContacts; }

    public void insert(ContactModel contact) {
        Log.d("VIewholder", "insert: "+contact.getRegistered());
        mRepository.insert(contact); }
}