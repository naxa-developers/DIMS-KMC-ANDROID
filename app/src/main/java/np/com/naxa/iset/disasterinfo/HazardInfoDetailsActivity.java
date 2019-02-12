package np.com.naxa.iset.disasterinfo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.R;
import np.com.naxa.iset.disasterinfo.model.DisasterInfoDetailsEntity;
import np.com.naxa.iset.quiz.QuizTestActivity;
import np.com.naxa.iset.utils.sectionmultiitemUtils.DataServer;
import np.com.naxa.iset.database.viewmodel.DisasterInfoDetailsViewModel;

import static android.text.Html.*;

public class HazardInfoDetailsActivity extends AppCompatActivity {

    private static final String TAG = "HazardInfoDetails";
    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tvBody)
    TextView tvBody;
    @BindView(R.id.btnPlayQuiz)
    Button btnPlayQuiz;
    @BindView(R.id.btnBeforeHappens)
    Button btnBeforeHappens;
    @BindView(R.id.btnWhenHappens)
    Button btnWhenHappens;
    @BindView(R.id.btnAfterHappens)
    Button btnAfterHappens;

    HazardListModel hazardListModel;
    String category = "";

    DisasterInfoDetailsViewModel disasterInfoDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazard_info_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        category = intent.getStringExtra("OBJ");
        disasterInfoDetailsViewModel = ViewModelProviders.of(this).get(DisasterInfoDetailsViewModel.class);

        setupToolBar();
        initUI(category);
    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        if(category == null){
        getSupportActionBar().setTitle("Hazard Details");
        }else {
            getSupportActionBar().setTitle(category);
            btnBeforeHappens.setText("Before "+category);

            tvTitle.setText(category);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    DataServer dataServer = new DataServer();
    HazardListModel hazardListModel1 = new HazardListModel();
    private void initUI(String category){

        disasterInfoDetailsViewModel.getSpecificDisasterInfo(category, "introduction")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<DisasterInfoDetailsEntity>() {
                    @Override
                    public void onNext(DisasterInfoDetailsEntity disasterInfoDetailsEntity) {

                        WindowManager mWinMgr = (WindowManager)HazardInfoDetailsActivity.this.getSystemService(Context.WINDOW_SERVICE);
                        int displayWidth = mWinMgr.getDefaultDisplay().getWidth();
                        Glide.with(HazardInfoDetailsActivity.this)
                                .load(disasterInfoDetailsEntity.getPhoto())
                                .override(displayWidth, 200)
                                .into(imageView);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tvBody.setText(fromHtml(disasterInfoDetailsEntity.getDesc(),0 ,new ImageGetter(), null));
                        } else {
                            tvBody.setText(fromHtml(disasterInfoDetailsEntity.getDesc()));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });



//        if(hazardListModel.getTitle().equals("Earthquake") || hazardListModel.getTitle().equals("Landslide")){
//            if(hazardListModel.getTitle().equals("Earthquake")) {
//                hazardListModel1 = dataServer.getEarthquakeDetails();
//            }else {
//                hazardListModel1 = dataServer.getLandslideDetails();
//
//            }

//            WindowManager mWinMgr = (WindowManager)HazardInfoDetailsActivity.this.getSystemService(Context.WINDOW_SERVICE);
//            int displayWidth = mWinMgr.getDefaultDisplay().getWidth();
//            Glide.with(HazardInfoDetailsActivity.this)
//                    .load(hazardListModel1.getImage())
//                    .override(displayWidth, 200)
//                    .into(imageView);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////                tvBody.setText(Html.fromHtml(hazardListModel1.getDesc(), Html.FROM_HTML_MODE_COMPACT));
//                tvBody.setText(fromHtml(getStringContstant(),0 ,new ImageGetter(), null));
//            } else {
////                tvBody.setText(Html.fromHtml(hazardListModel1.getDesc()));
//                tvBody.setText(fromHtml(getStringContstant()));
//            }
////        }

//        if(hazardListModel.getTitle().equals("Landslide")){
//            this.hazardListModel = dataServer.getEarthquakeDetails();
//        }

    }

    List<String> imageList = new ArrayList<String>();
    private class ImageGetter implements Html.ImageGetter {

        public Drawable getDrawable(String source) {
            int id;
            if (!source.equals("")) {
//                id = R.drawable.hughjackman;
                imageList.add(source);
                Log.d(TAG, "getDrawable: "+imageList.size());
            }
            else {
                return null;
            }

//            Drawable d = getResources().getDrawable(id);
//            d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
//            return d;
            return null;
        }
    };



    @OnClick({R.id.btnPlayQuiz, R.id.btnBeforeHappens, R.id.btnWhenHappens, R.id.btnAfterHappens})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPlayQuiz:
                startActivity(new Intent(HazardInfoDetailsActivity.this, QuizTestActivity.class));
                break;
            case R.id.btnBeforeHappens:
                startNewActivity("before");
                break;
            case R.id.btnWhenHappens:
                startNewActivity("during");
                break;
            case R.id.btnAfterHappens:
                startNewActivity("after");
                break;
        }
    }

    public void startNewActivity( String subcatname){
        Intent intent = new Intent(HazardInfoDetailsActivity.this, HazardThingsToDoActivity.class);
        intent.putExtra("OBJ", category);
        intent.putExtra("OBJ1", subcatname);
        startActivity(intent);
    }

    @Contract(pure = true)
    private String getStringContstant(){
//        String string = "<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:8pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">If you are inside a Building Drop, Cover and Hold</span></span></p>\\r\\n\\r\\n<ul style=\\\"margin-top:0pt;margin-bottom:0pt;\\\">\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Find a safe place and &ldquo;DROP&rdquo;</span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">&ldquo;COVER&rdquo; your head and your neck</span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">&ldquo;HOLD&rdquo; on to something stable</span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Take deep breaths and stay calm</span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Stay where you are until shaking stops</span></span></p>\\r\\n\\t</li>\\r\\n</ul>\\r\\n\\r\\n<p>&nbsp;</p>\\r\\n\\r\\n<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-indent: -36pt;text-align: justify;padding:0pt 0pt 0pt 36pt;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">If you are trapped inside</span></span></p>\\r\\n\\r\\n<ul style=\\\"margin-top:0pt;margin-bottom:0pt;\\\">\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Remain quiet, breathe slowly and believe in your survival</span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Panicking and shouting can exhaust you very quickly</span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">wait for signals &amp; respond with a whistle </span></span></p>\\r\\n\\t</li>\\r\\n</ul>\\r\\n\\r\\n<p>&nbsp;</p>\\r\\n\\r\\n<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-indent: -36pt;text-align: justify;padding:0pt 0pt 0pt 36pt;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; color: rgb(0, 0, 0); background-color: transparent; font-weight: 700; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Do not</span></span></p>\\r\\n\\r\\n<ul style=\\\"margin-top:0pt;margin-bottom:0pt;\\\">\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Do not Panic and run </span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Staircases are usually unsafe!</span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Do not jump out from windows, balconies! </span></span></p>\\r\\n\\t</li>\\r\\n</ul>\\r\\n\\r\\n<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-indent: -36pt;text-align: justify;padding:0pt 0pt 0pt 36pt;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">If your are outside</span></span></p>\\r\\n\\r\\n<ul style=\\\"margin-top:0pt;margin-bottom:0pt;\\\">\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Get into nearest open space</span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">If you are in a city, seek shelter under doorways</span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Do not try to walk through narrow streets</span></span></p>\\r\\n\\t</li>\\r\\n\\t<li dir=\\\"ltr\\\" style=\\\"list-style-type: disc; font-size: 12pt; font-family: &quot;Noto Sans Symbols&quot;; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre;\\\">\\r\\n\\t<p dir=\\\"ltr\\\" style=\\\"line-height:1.295;margin-top:0pt;margin-bottom:0pt;text-align: justify;\\\"><span id=\\\"docs-internal-guid-d4899caa-7fff-751c-2f17-4c5aebbef279\\\"><span style=\\\"font-size: 12pt; font-family: &quot;Times New Roman&quot;; background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\\\">Be Careful if you are driving (avoid Bridges, tall buildings, do not stop abruptly) </span></span></p>\\r\\n\\t</li>\\r\\n</ul>\\r\\n";
       String string = "<ol>\\r\\n\\t<li><span style=\\\"font-size:18px;\\\">this&nbsp;is bold text&nbsp;<strong>bold text&nbsp;</strong>and italic . <span style=\\\"background-color:Yellow;\\\">How about we do som colorful text</span></span></li>\\r\\n\\t<li><font face=\\\"monospace\\\">Mark&nbsp; yellow</font></li>\\r\\n</ol>\\r\\n\\r\\n<p>paragaraph changed here now add some image down&nbsp; &nbsp;&nbsp;</p>\\r\\n\\r\\n<p><img alt=\\\"\\\" src=\\\"http://kmc.naxa.com.np/uploads/images/editor/images/23_thumb.jpg\\\" style=\\\"width: 375px; height: 500px;\\\" /></p>\\r\\n\\r\\n<p>Now for second image down</p>\\r\\n\\r\\n<p><img alt=\\\"\\\" src=\\\"http://kmc.naxa.com.np/uploads/images/editor/images/24_thumb.jpg\\\" style=\\\"width: 500px; height: 375px;\\\" /></p>\\r\\n\\r\\n<p>That all to text,</p>\\r\\n";
        return string;
    }
}
