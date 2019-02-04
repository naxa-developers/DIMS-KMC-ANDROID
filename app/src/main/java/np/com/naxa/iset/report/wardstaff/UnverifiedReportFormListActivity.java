package np.com.naxa.iset.report.wardstaff;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Network;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.iset.R;
import np.com.naxa.iset.database.entity.ReportDetailsEntity;
import np.com.naxa.iset.mycircle.registeruser.UserModel;
import np.com.naxa.iset.network.UrlClass;
import np.com.naxa.iset.network.retrofit.NetworkApiClient;
import np.com.naxa.iset.network.retrofit.NetworkApiInterface;
import np.com.naxa.iset.profile.wardprofile.fragments.ManagementBoardFragment;
import np.com.naxa.iset.profile.wardprofile.fragments.PeopleFragment;
import np.com.naxa.iset.profile.wardprofile.fragments.RiskHazardFragment;
import np.com.naxa.iset.profile.wardprofile.fragments.ViewPagerAdapter;
import np.com.naxa.iset.profile.wardprofile.fragments.WardProfileFragment;
import np.com.naxa.iset.report.wardstaff.model.UnverifiedFormListResponse;
import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.JsonGsonConverterUtils;
import np.com.naxa.iset.utils.SharedPreferenceUtils;
import np.com.naxa.iset.utils.ToastUtils;
import np.com.naxa.iset.viewmodel.ReportDetailsViewModel;

public class UnverifiedReportFormListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    NetworkApiInterface apiInterface;
    SharedPreferenceUtils sharedPreferenceUtils;
    ReportDetailsViewModel reportDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unverified_report_form_list);
        ButterKnife.bind(this);
        setupToolBar();

        sharedPreferenceUtils = new SharedPreferenceUtils(UnverifiedReportFormListActivity.this);
        reportDetailsViewModel = ViewModelProviders.of(this).get(ReportDetailsViewModel.class);
        apiInterface = NetworkApiClient.getAPIClient().create(NetworkApiInterface.class);
        setupViewPager(viewpager);

        getUnverifiedFormList();

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Unverified Forms" );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UnverifiedNormalFormListFragment(), "Normal");
        adapter.addFragment(new UnverifiedEditedFormListFragment(), "Edited");
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

    }

    private void getUnverifiedFormList(){
        UserModel userModel = JsonGsonConverterUtils.getUserFromJson(sharedPreferenceUtils.getStringValue(SharedPreferenceUtils.USER_DETAILS, null));

        if(userModel.getEmail() == null || userModel.getEmail().equals("")){
            ToastUtils.infoAlert(UnverifiedReportFormListActivity.this, "User Details not found");
            return;
        }
        apiInterface.getUnverifiedReportResponse(UrlClass.API_ACCESS_TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UnverifiedFormListResponse>() {
                    @Override
                    public void onNext(UnverifiedFormListResponse unverifiedFormListResponse) {
                        if (unverifiedFormListResponse.getError() ==0){
                            if(unverifiedFormListResponse.getData() == null){
                                DialogFactory.createCustomDialog(UnverifiedReportFormListActivity.this, "No new data found", new DialogFactory.CustomDialogListener() {
                                    @Override
                                    public void onClick() {

                                    }
                                }).show();
                                return;
                            }
                            saveUnverifiedDataToDatabase(unverifiedFormListResponse.getData());

                        }else {
                            DialogFactory.createCustomErrorDialog(UnverifiedReportFormListActivity.this, unverifiedFormListResponse.getMessage(), new DialogFactory.CustomDialogListener() {
                                @Override
                                public void onClick() {

                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createCustomErrorDialog(UnverifiedReportFormListActivity.this, e.getMessage(), new DialogFactory.CustomDialogListener() {
                            @Override
                            public void onClick() {

                            }
                        }).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveUnverifiedDataToDatabase(List<ReportDetailsEntity> reportDetailsEntities){

        Observable.just(reportDetailsEntities)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<List<ReportDetailsEntity>, Iterable<ReportDetailsEntity>>() {
                    @Override
                    public Iterable<ReportDetailsEntity> apply(List<ReportDetailsEntity> reportDetailsEntities) throws Exception {
                        return reportDetailsEntities;
                    }
                })
                .map(new Function<ReportDetailsEntity, ReportDetailsEntity>() {
                    @Override
                    public ReportDetailsEntity apply(ReportDetailsEntity reportDetailsEntity) throws Exception {
                        return reportDetailsEntity;
                    }
                })
                .subscribe(new DisposableObserver<ReportDetailsEntity>() {
                    @Override
                    public void onNext(ReportDetailsEntity reportDetailsEntity) {
                        reportDetailsViewModel.insert(reportDetailsEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        DialogFactory.createCustomErrorDialog(UnverifiedReportFormListActivity.this, e.getMessage(), new DialogFactory.CustomDialogListener() {
                            @Override
                            public void onClick() {

                            }
                        }).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
