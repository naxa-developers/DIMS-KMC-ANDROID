package np.com.naxa.iset.quiz.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import np.com.naxa.iset.R;
import np.com.naxa.iset.quiz.QuizTestActivity;


public class LifeCoachingTestFormFragment extends Fragment implements QuizTestActivity.onFragmentVisibleListener {
    @BindView(R.id.spinner_fragment_question_lbl)
    TextView tvQuestionLbl;
    @BindView(R.id.radio_yes_life_coaching_test)
    RadioButton radioYesLifeCoachingTest;
    @BindView(R.id.radio_no_life_coaching_test)
    RadioButton radioNoLifeCoachingTest;
    Unbinder unbinder;
    private Spinner answersSpinner;
    private TextView tvQuestionLBL;
    private final String TAG = "LifeCoachingQnFragment";
    private String testQuestion;
    private String[] answers;
    private String userSelectedAnswer = "yes";
    private onAnswerSelectedListener listener;
    private onFormFinishedListener form_listner;

    private int fragmentPos = 0 ;


    public LifeCoachingTestFormFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_life_coaching_test_qn, container, false);
        initUI(rootview);
        unbinder = ButterKnife.bind(this, rootview);
        return rootview;

    }

    private void initUI(View rootview) {
//        answersSpinner = (Spinner) rootview.findViewById(R.id.spinner_fragment_spinner);
        tvQuestionLBL = (TextView) rootview.findViewById(R.id.spinner_fragment_question_lbl);
        setQuestionAndAnswers(rootview);

    }


    private void setQuestionAndAnswers(View rootview) {
        tvQuestionLBL.setText(testQuestion);
    }


    @Override
    public void fragmentBecameVisible(int fragmentPostionInViewPager) {
        getUserAnswer(fragmentPostionInViewPager);
        notifyFormFragment();

    }

    private void getUserAnswer(final int pos) {

        fragmentPos = pos ;


    }


    private void sendAnswerToActvitiy(int pos) {

        String questionName = "q" + pos;

        try {
            listener.onAnswerSelected(questionName, userSelectedAnswer);
            Log.e(TAG, "sendAnswerToActvitiySAMIR: "+questionName +" , "+ userSelectedAnswer);
        } catch (ClassCastException cce) {
            Log.e(TAG, cce.toString());
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onAnswerSelectedListener) {
            listener = (onAnswerSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return;
        if (activity instanceof onAnswerSelectedListener) {
            listener = (onAnswerSelectedListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public void prepareQuestionsAndAnswers(String spinnerQuestion, int fragmentId) {
        this.testQuestion = spinnerQuestion;


    }

    private void notifyFormFragment() {
        try {
            listener.formFragment();
        } catch (ClassCastException cce) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.radio_yes_life_coaching_test, R.id.radio_no_life_coaching_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_yes_life_coaching_test:
                userSelectedAnswer = "yes";

                Log.e(TAG, "onRADIOViewClickedSAMIR: "+userSelectedAnswer );
                sendAnswerToActvitiy(fragmentPos);
                break;
            case R.id.radio_no_life_coaching_test:
                userSelectedAnswer = "no";
                Log.e(TAG, "onRADIOViewClickedSAMIR: "+userSelectedAnswer );

                sendAnswerToActvitiy(fragmentPos);
                break;
        }
    }
}

