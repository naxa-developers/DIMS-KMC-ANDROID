package np.com.naxa.iset.drr_dictionary.data_glossary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import np.com.naxa.iset.R;
import np.com.naxa.iset.drr_dictionary.JSONLoadImpl;
import np.com.naxa.iset.utils.TextViewUtils;

public class DataGlossaryWordDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tv_word_title)
    TextView tvWordTitle;
    @BindView(R.id.tv_word_desc)
    TextView tvWordDesc;
    @BindView(R.id.btn_watch_video)
    RelativeLayout btnWatchVideo;
    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.imageViewGlossaryDetails)
    ImageView imageViewGlossaryDetails;
    private WordsWithDetailsModel wordsWithDetailsModel;


    String videoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_glossary_word_details);
        ButterKnife.bind(this);

        Bundle data = getIntent().getExtras();
        wordsWithDetailsModel = (WordsWithDetailsModel) data.getParcelable("wordsWithDetails");

        initToolbar();

        tvWordTitle.setText(wordsWithDetailsModel.getTitle().trim());
        tvWordDesc.setText(wordsWithDetailsModel.getDesc());


        JSONLoadImpl
                .cacheGlossaryObj()
                .flatMapIterable(new Function<List<WordsWithDetailsModel>, Iterable<WordsWithDetailsModel>>() {
                    @Override
                    public Iterable<WordsWithDetailsModel> apply(List<WordsWithDetailsModel> wordsWithDetailsModels) throws Exception {
                        return wordsWithDetailsModels;
                    }
                })
                .flatMap(new Function<WordsWithDetailsModel, Observable<String>>() {
                    @Override
                    public Observable<String> apply(WordsWithDetailsModel wordsWithDetailsModel) throws Exception {
                        return Observable.just(wordsWithDetailsModel.getTitle().trim());
                    }
                })
                .toList()
                .subscribe(new DisposableSingleObserver<List<String>>() {
                    @Override
                    public void onSuccess(List<String> titleList) {
                        //TextViewUtils.highlightWordToBlue(strings, tvWordDesc);

                        String[] stringArray = titleList.toArray(new String[0]);
                        Arrays.sort(stringArray, Collections.<String>reverseOrder());
                        List<String> sortedTitleList = Arrays.asList(stringArray);

                        TextViewUtils.linkWordsToGlossary(sortedTitleList, tvWordDesc);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


        if (wordsWithDetailsModel.getTitle().trim().equalsIgnoreCase("Consent")) {

            videoURL = wordsWithDetailsModel.getVideo_URL();
            btnWatchVideo.setVisibility(View.VISIBLE);
        }
        if (wordsWithDetailsModel.getTitle().trim().equalsIgnoreCase("No Consent")) {
            videoURL = wordsWithDetailsModel.getVideo_URL();
            btnWatchVideo.setVisibility(View.VISIBLE);
        }
    }


    private void initToolbar() {
//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(wordsWithDetailsModel != null ? wordsWithDetailsModel.getTitle() : "Failed to load");
        toolbar.setTitle("DRR Dictionary");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                onBackPressed();
                return true;
            }
        });
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

    @Override
    public void onBackPressed() {
        // close view on back button pressed
        super.onBackPressed();
    }

    @OnClick(R.id.btn_goto_glossary)
    public void onGotoGlossaryClick() {
        Intent intent = new Intent(this, GlossaryListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_watch_video)
    public void onWatchVideoClick() {
//        Intent intent = new Intent(DataGlossaryWordDetailsActivity.this, YoutubeWebViewActivity.class);
//        intent.putExtra("url", videoURL);
//        startActivity(intent);
    }

}
