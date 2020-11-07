package np.com.naxa.iset.mycircle.contactlistdialog;


import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.R;
import np.com.naxa.iset.mycircle.ContactModel;
import np.com.naxa.iset.mycircle.contactlistdialog.contactlistadapter.MyCircleContactAddDialogListAdapter;
import np.com.naxa.iset.database.viewmodel.MyCircleContactViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisteredContactListFragment extends Fragment {
    private String mText = "";

    private boolean registered = false ;



    MyCircleContactViewModel myCircleContactViewModel;


    public RegisteredContactListFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_list_tab_dialog_registered_fragment_layout,container,false);

        myCircleContactViewModel = ViewModelProviders.of(this).get(MyCircleContactViewModel.class);


        myCircleContactViewModel.getAllRegisteredContacts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<List<ContactModel>>() {
                    @Override
                    public void onNext(List<ContactModel> contactModels) {

                        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewContactsDialog);
                        MyCircleContactAddDialogListAdapter myCircleContactAddDialogListAdapter = new MyCircleContactAddDialogListAdapter(R.layout.contact_dialog_row_item_layout, contactModels);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(myCircleContactAddDialogListAdapter);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return v;
    }

}
