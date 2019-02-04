package np.com.naxa.iset.report.wardstaff;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import np.com.naxa.iset.R;
import np.com.naxa.iset.database.entity.ReportDetailsEntity;
import np.com.naxa.iset.report.ReportSavedFormListAdapter;
import np.com.naxa.iset.viewmodel.ReportDetailsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnverifiedEditedFormListFragment extends Fragment {

    private static final String TAG = "UnverifiedEditedForm";

//    @BindView(R.id.recyclerViewUnverifiedEditedFormList)
    RecyclerView recyclerViewUnverifiedEditedFormList;
//    Unbinder unbinder;

    ReportDetailsViewModel reportDetailsViewModel;


    public UnverifiedEditedFormListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_unverified_edited_form_list, container, false);

        reportDetailsViewModel = ViewModelProviders.of(this).get(ReportDetailsViewModel.class);
        setupRecycleView(view);

//        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void setupRecycleView(View view) {
        recyclerViewUnverifiedEditedFormList = (RecyclerView) view.findViewById(R.id.recyclerViewUnverifiedEditedFormList);

        UnverifiedEditedListAdapter unverifiedEditedListAdapter = new UnverifiedEditedListAdapter(R.layout.saved_forms_list_row_item_layout, null);
        recyclerViewUnverifiedEditedFormList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewUnverifiedEditedFormList.setAdapter(unverifiedEditedListAdapter);

        getUnverifiedEditedFormList();
    }

    private void getUnverifiedEditedFormList() {

        reportDetailsViewModel.getAllUnVerifiedReportDetailsEditedList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<List<ReportDetailsEntity>>() {
                    @Override
                    public void onNext(List<ReportDetailsEntity> reportDetailsEntities) {
//                        Gson gson = new Gson();
//                        String json = gson.toJson(reportDetailsEntities.get(0));
//                        Log.d(TAG, "onNext: insert" + json);

                        ((UnverifiedEditedListAdapter) recyclerViewUnverifiedEditedFormList.getAdapter()).replaceData(reportDetailsEntities);

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
}
