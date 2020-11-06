package np.com.naxa.iset.quiz.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import androidx.core.app.Fragment;
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
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.ToastUtils;


public class LifeCoachingTestFormFragment extends Fragment implements QuizTestActivity.onFragmentVisibleListener {
    @BindView(R.id.spinner_fragment_question_lbl)
    TextView tvQuestionLbl;
    @BindView(R.id.radio_yes_life_coaching_test)
    RadioButton radioYesLifeCoachingTest;
    @BindView(R.id.radio_no_life_coaching_test)
    RadioButton radioNoLifeCoachingTest;
    Unbinder unbinder;
    @BindView(R.id.answer_1)
    TextView tvAnswer1;
    @BindView(R.id.answer_2)
    TextView tvAnswer2;
    @BindView(R.id.answer_3)
    TextView tvAnswer3;
    @BindView(R.id.answer_4)
    TextView tvAnswer4;
    private Spinner answersSpinner;
    private TextView tvQuestionLBL, tvAnswer1LBL, tvAnswer2LBL, tvAnswer3LBL, tvAnswer4LBL;
    private final String TAG = "LifeCoachingQnFragment";
    private String testQuestion;
    private String[] answers;
    private String userSelectedAnswer = "yes";
    private onAnswerSelectedListener listener;
    private onFormFinishedListener form_listner;

    private int fragmentPos = 0;


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
//        tvAnswer1LBL = (TextView) rootview.findViewById(R.id.answer_1);
//        tvAnswer2LBL = (TextView) rootview.findViewById(R.id.answer_2);
//        tvAnswer3LBL = (TextView) rootview.findViewById(R.id.answer_3);
//        tvAnswer4LBL = (TextView) rootview.findViewById(R.id.answer_4);
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

        fragmentPos = pos;


    }


    private void sendAnswerToActvitiy(int pos) {

        String questionName = "q" + pos;

        try {
            listener.onAnswerSelected(questionName, userSelectedAnswer);
            Log.e(TAG, "sendAnswerToActvitiySAMIR: " + questionName + " , " + userSelectedAnswer);
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


    String questionElaboration = "A natural disaster is a major adverse event resulting from natural processes of the Earth; examples are floods, hurricanes, tornadoes, volcanic eruptions, earthquakes, tsunamis, and other geologic processes. A natural disaster can cause loss of life or damage property,[1] and typically leaves some economic damage in its wake, the severity of which depends on the affected population's resilience, or ability to recover and also on the infrastructure available";
    @OnClick({R.id.radio_yes_life_coaching_test, R.id.radio_no_life_coaching_test, R.id.answer_1, R.id.answer_2, R.id.answer_3, R.id.answer_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.radio_yes_life_coaching_test:
                userSelectedAnswer = "yes";

                Log.e(TAG, "onRADIOViewClickedSAMIR: " + userSelectedAnswer);
                sendAnswerToActvitiy(fragmentPos);
                break;
            case R.id.radio_no_life_coaching_test:
                userSelectedAnswer = "no";
                Log.e(TAG, "onRADIOViewClickedSAMIR: " + userSelectedAnswer);

                sendAnswerToActvitiy(fragmentPos);
                break;

            case R.id.answer_1:
                ToastUtils.showShortToast("answer 1");
              showElaborationDialog(true);
                break;

            case R.id.answer_2:
                ToastUtils.showShortToast("answer 2");
                showElaborationDialog(false);
                break;

            case R.id.answer_3:
                ToastUtils.showShortToast("answer 3");
                showElaborationDialog(false);
                break;

            case R.id.answer_4:
                ToastUtils.showShortToast("answer 4");
                showElaborationDialog(false);
                break;
        }
    }


    private void showElaborationDialog(@NonNull Boolean isAnswer){
        String answerStatus = "";
        answerStatus = isAnswer?"Correct":"Incorrect";

        DialogFactory.createQuizAnsElaborationDialog(getActivity(), "Question "+ fragmentPos+ "/20",
                answerStatus, testQuestion, questionElaboration, new DialogFactory.CustomDialogListener() {
                    @Override
                    public void onClick() {

                    }
                }).show();
    }

}

