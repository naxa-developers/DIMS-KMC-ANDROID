package np.com.naxa.iset.quiz.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.naxa.iset.R;
import np.com.naxa.iset.quiz.QuizTestActivity;

/**
 * Created by nishon.tan on 4/21/2017.
 */

public class FormStartFragment  extends Fragment implements  QuizTestActivity.onFragmentVisibleListener {

    onFormFinishedListener listener;

    public FormStartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_start, container, false);
        return rootview;

    }

    @Override
    public void fragmentBecameVisible(int position) {
    notifyFirstFragment();
    }

    private void notifyFirstFragment(){
        listener = (onFormFinishedListener) getActivity();
        try {
            listener.firstFragment();
        } catch (ClassCastException cce){

        }
    }
}
