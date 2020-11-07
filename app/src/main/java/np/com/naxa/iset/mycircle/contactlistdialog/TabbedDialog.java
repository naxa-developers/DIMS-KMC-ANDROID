package np.com.naxa.iset.mycircle.contactlistdialog;


import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import np.com.naxa.iset.R;
import np.com.naxa.iset.event.MyCircleContactEvent;
import np.com.naxa.iset.database.viewmodel.MyCircleContactViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabbedDialog extends DialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    Button btnClose;
    MyCircleContactViewModel myCircleContactViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.contact_list_tab_dialog_layout, container, false);


        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager);
        btnClose = (Button) rootview.findViewById(R.id.btnClose);

        CustomAdapter adapter = new CustomAdapter(getChildFragmentManager());

        myCircleContactViewModel = ViewModelProviders.of(this).get(MyCircleContactViewModel.class);
        adapter.addFragment("Registered", new  RegisteredContactListFragment());
        adapter.addFragment("UnRegistered", new UnregisteredContactListFragment());
//        adapter.notifyDataSetChanged();

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show the dialog.
                EventBus.getDefault().post(new MyCircleContactEvent.MyCircleContactDialogCloseClick());

            }
        });

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(rootview.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        return rootview;
    }


}
