package np.com.naxa.iset.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import np.com.naxa.iset.R;
import np.com.naxa.iset.utils.recycleviewutils.LinearLayoutManagerWithSmoothScroller;
import np.com.naxa.iset.utils.recycleviewutils.RecyclerViewType;

public class QuizHomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.txtLBL)
    TextView txtLBL;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecyclerViewType recyclerViewType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);
        ButterKnife.bind(this);

        setupToolBar();
        setUpRecyclerView();
    }


    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DRR Quiz");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //setup recycler view
    private void setUpRecyclerView() {
        recyclerViewType = RecyclerViewType.GRID;

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        populateRecyclerView();
    }

    //populate recycler view
    private void populateRecyclerView() {
        ArrayList<SectionQuizModel> sectionModelArrayList = new ArrayList<SectionQuizModel>();

        sectionModelArrayList.addAll(SectionQuizModel.getQuizList());
        SectionRecyclerViewQuizAdapter adapter = new SectionRecyclerViewQuizAdapter(this, recyclerViewType, sectionModelArrayList);
        recyclerView.setAdapter(adapter);
    }
}
