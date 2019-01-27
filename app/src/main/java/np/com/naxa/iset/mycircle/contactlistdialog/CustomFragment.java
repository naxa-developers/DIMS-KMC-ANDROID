package np.com.naxa.iset.mycircle.contactlistdialog;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.R;
import np.com.naxa.iset.mycircle.ContactModel;
import np.com.naxa.iset.mycircle.MyCircleContactAddDialogListAdapter;
import np.com.naxa.iset.mycircle.MyCircleContactListInviteAdapter;
import np.com.naxa.iset.viewmodel.MyCircleContactViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomFragment extends Fragment {
    private String mText = "";
    private List<ContactModel> contactModelList = new ArrayList<ContactModel>();

    private boolean registered = false ;



    public static CustomFragment createInstance(List<ContactModel> contactModels, boolean registered)
    {
        CustomFragment fragment = new CustomFragment();
//        fragment.mText = text;
        fragment.registered = registered;
        fragment.contactModelList = contactModels;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_list_tab_dialog_fragment_all_layout,container,false);

if(registered) {
    RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewContactsDialog);
    MyCircleContactAddDialogListAdapter myCircleContactAddDialogListAdapter = new MyCircleContactAddDialogListAdapter(R.layout.contact_dialog_row_item_layout, contactModelList);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(myCircleContactAddDialogListAdapter);

}else {
    RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewContactsDialog);
    MyCircleContactListInviteAdapter myCircleContactAddDialogListAdapter = new MyCircleContactListInviteAdapter(R.layout.contact_dialog_row_item_invite_layout, contactModelList);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(myCircleContactAddDialogListAdapter);
}

//        ((TextView) v.findViewById(R.id.textView)).setText(mText);
        return v;
    }
}
