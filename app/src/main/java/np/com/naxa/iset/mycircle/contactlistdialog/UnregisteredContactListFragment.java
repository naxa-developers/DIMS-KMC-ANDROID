package np.com.naxa.iset.mycircle.contactlistdialog;


import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
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
import np.com.naxa.iset.mycircle.contactlistdialog.contactlistadapter.MyCircleContactListInviteAdapter;
import np.com.naxa.iset.database.viewmodel.MyCircleContactViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnregisteredContactListFragment extends Fragment {

    MyCircleContactViewModel myCircleContactViewModel;


    public UnregisteredContactListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_unregistered_contact_list, container, false);

        myCircleContactViewModel = ViewModelProviders.of(this).get(MyCircleContactViewModel.class);


        myCircleContactViewModel.getAllUnRegisteredContacts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<List<ContactModel>>() {
                    @Override
                    public void onNext(List<ContactModel> contactModels) {

                        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewUnregisteredContactsDialog);
                        MyCircleContactListInviteAdapter myCircleContactAddDialogListAdapter = new MyCircleContactListInviteAdapter(R.layout.contact_dialog_row_item_invite_layout, contactModels);
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
