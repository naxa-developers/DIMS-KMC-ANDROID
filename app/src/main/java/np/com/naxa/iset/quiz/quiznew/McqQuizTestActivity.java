package np.com.naxa.iset.quiz.quiznew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;
import np.com.naxa.iset.quiz.QuizConstants;
import np.com.naxa.iset.utils.DialogFactory;

public class McqQuizTestActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbarGeneral;
    @BindView(R.id.tv_question_lbl)
    TextView tvQuestionLbl;
    @BindView(R.id.radio_yes_quiz_test)
    RadioButton radioYesQuizTest;
    @BindView(R.id.radio_no_quiz_test)
    RadioButton radioNoQuizTest;
    @BindView(R.id.radio_group_quiz_test)
    RadioGroup radioGroupQuizTest;
    @BindView(R.id.answer_1)
    Button btnAnswer1;
    @BindView(R.id.answer_2)
    Button btnAnswer2;
    @BindView(R.id.answer_3)
    Button btnAnswer3;
    @BindView(R.id.answer_4)
    Button btnAnswer4;
    @BindView(R.id.mcq_group_quiz_test)
    LinearLayout mcqGroupQuizTestLayout;

    String toolbarTitle = "" ;
    int questionPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_quiz_test);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        toolbarTitle = intent.getStringExtra("OBJ");

        setupToolBar();
        tvQuestionLbl.setText(QuizConstants.LifeCoachingTestQuestion[questionPos]);

    }

    private void setupToolBar() {
        setSupportActionBar(toolbarGeneral);
        getSupportActionBar().setTitle(toolbarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    String questionElaboration = "A natural disaster is a major adverse event resulting from natural processes of the Earth; examples are floods, hurricanes, tornadoes, volcanic eruptions, earthquakes, tsunamis, and other geologic processes. A natural disaster can cause loss of life or damage property,[1] and typically leaves some economic damage in its wake, the severity of which depends on the affected population's resilience, or ability to recover and also on the infrastructure available";
    @OnClick({R.id.answer_1, R.id.answer_2, R.id.answer_3, R.id.answer_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.answer_1:
                showElaborationDialog(true);
                break;

            case R.id.answer_2:
                showElaborationDialog(false);
                break;

            case R.id.answer_3:
                showElaborationDialog(false);
                break;

            case R.id.answer_4:
                showElaborationDialog(false);
                break;
        }
    }


    private void showElaborationDialog(@NonNull Boolean isAnswer){
        String answerStatus = "";
        answerStatus = isAnswer?"Correct":"Incorrect";
        int questionNoProgress = questionPos+1;

        DialogFactory.createQuizAnsElaborationDialog(McqQuizTestActivity.this, "Question "+ questionNoProgress+ "/20",
                answerStatus, QuizConstants.LifeCoachingTestQuestion[questionPos], questionElaboration, new DialogFactory.CustomDialogListener() {
                    @Override
                    public void onClick() {
                        questionPos++;
                        int questionNo = questionPos+1;
                        tvQuestionLbl.setText(QuizConstants.LifeCoachingTestQuestion[questionPos]);

                        btnAnswer1.setText("Question no "+questionNo +" Option 1");
                        btnAnswer2.setText("Question no "+questionNo +" Option 2");
                        btnAnswer3.setText("Question no "+questionNo +" Option 3");
                        btnAnswer4.setText("Question no "+questionNo +" Option 4");
                    }
                }).show();


    }
}
