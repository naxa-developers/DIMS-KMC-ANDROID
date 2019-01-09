package np.com.naxa.iset.quiz.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import np.com.naxa.iset.R;
import np.com.naxa.iset.quiz.QuizTestActivity;


/**
 * Created by nishon.tan on 4/21/2017.
 */

public class FormEndFragment extends Fragment implements QuizTestActivity.onFragmentVisibleListener {

    onFormFinishedListener listener;
    Button save, btnEvaluate;
    TextView tvDisplayResult;
    public static final String TAG = "FormEndFragment";
    private onEvaluationCompletionListner evaltionCompletionListener;

    public FormEndFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_form_end, container, false);

        btnEvaluate = (Button) rootview.findViewById(R.id.btn_evaluate);
        save = (Button) rootview.findViewById(R.id.save);
        tvDisplayResult = (TextView) rootview.findViewById(R.id.tv_display_result);

        btnEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyEvaluateForm();

            }
        });


        return rootview;

    }

    public void setOnEvaltionCompletionListener(onEvaluationCompletionListner  evaltionCompletionListener){
        this.evaltionCompletionListener = evaltionCompletionListener;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return;
        if (activity instanceof onFormFinishedListener) {
            listener = (onFormFinishedListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement onFormFinishedListener");
        }
    }

    @Override
    public void fragmentBecameVisible(int position) {
        notifyLastFragment();
    }

    private void notifyEvaluateForm() {

        try {
            listener.evaluateForm();
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }

        tvDisplayResult.setText (((QuizTestActivity)getActivity()).displayResult());
    }


    private void notifyLastFragment() {
        listener = (onFormFinishedListener) getActivity();
        try {
            listener.lastFragment();
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }
    }


//    @Override
//    public void displayResult(String result) {
//
//        tvDisplayResult.setText(result);
//
//    }
}
