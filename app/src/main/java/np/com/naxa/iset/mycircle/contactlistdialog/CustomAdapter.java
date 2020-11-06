package np.com.naxa.iset.mycircle.contactlistdialog;

import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import androidx.core.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter  extends FragmentPagerAdapter {
    List<Fragment> mFragmentCollection = new ArrayList<>();
    List<String> mTitleCollection = new ArrayList<>();

    public CustomAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(String title, Fragment fragment)
    {
        mTitleCollection.add(title);
        mFragmentCollection.add(fragment);
    }

    //Needed for
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleCollection.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentCollection.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentCollection.size();
    }
}