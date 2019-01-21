package np.com.naxa.iset.mycircle.contactlistdialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import np.com.naxa.iset.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabbedDialog  extends DialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    Button btnClose;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.contact_list_tab_dialog_layout,container,false);

        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager);
        btnClose = (Button) rootview.findViewById(R.id.btnClose);

        CustomAdapter adapter = new CustomAdapter(getChildFragmentManager());
        adapter.addFragment("All",CustomFragment.createInstance("All"));
        adapter.addFragment("Registered",CustomFragment.createInstance("Registered"));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show the dialog.
            }
        });

//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(rootview.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        return rootview;
    }
}
