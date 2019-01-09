package np.com.naxa.iset.emergencynumbers;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import np.com.naxa.iset.R;
import np.com.naxa.iset.event.EmergenctContactCallEvent;
import np.com.naxa.iset.newhomepage.SectionModel;
import np.com.naxa.iset.newhomepage.SectionRecyclerViewAdapter;
import np.com.naxa.iset.utils.recycleviewutils.LinearLayoutManagerWithSmoothScroller;
import np.com.naxa.iset.utils.recycleviewutils.RecyclerViewType;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class EmergencyNumbersActivity extends AppCompatActivity {

    @BindView(R.id.txtTollfreeLBL)
    TextView txtTollfreeLBL;
    @BindView(R.id.txtFeelfreeToCallLBL)
    TextView txtFeelfreeToCallLBL;
    @BindView(R.id.btnTollFreeNo1)
    Button btnTollFreeNo1;
    @BindView(R.id.btnTollFreeNo2)
    Button btnTollFreeNo2;
    @BindView(R.id.recycler_view_emergency_no)
    RecyclerView recyclerViewEmergencyNo;
    @BindView(R.id.toolbar_general)
    Toolbar toolbar;

    private RecyclerViewType recyclerViewType;
    private final int RESULT_CALL = 150;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_numbers);
        ButterKnife.bind(this);

        setupToolBar();
        setUpRecyclerView();
    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Emergency Numbers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //setup recycler view
    private void setUpRecyclerView() {
        recyclerViewType = RecyclerViewType.LINEAR_VERTICAL;

        recyclerViewEmergencyNo.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManagerWithSmoothScroller(this);
        recyclerViewEmergencyNo.setLayoutManager(linearLayoutManager);

        populateRecyclerView();
    }

    //populate recycler view
    private void populateRecyclerView() {
        ArrayList<SectionEmergencyNoModel> sectionModelArrayList = new ArrayList<SectionEmergencyNoModel>();

        sectionModelArrayList.addAll(SectionEmergencyNoModel.getEmergencyNoList());
        SectionRecyclerViewEmergencyNoAdapter adapter = new SectionRecyclerViewEmergencyNoAdapter(this, recyclerViewType, sectionModelArrayList);
        recyclerViewEmergencyNo.setAdapter(adapter);
    }


    @OnClick({R.id.btnTollFreeNo1, R.id.btnTollFreeNo2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnTollFreeNo1:
                requestPermissionForPhone("9898999986");
                break;

            case R.id.btnTollFreeNo2:
                requestPermissionForPhone("9898999999");
                break;
        }
    }


    @AfterPermissionGranted(RESULT_CALL)
    private void requestPermissionForPhone(String number) {
        String call = Manifest.permission.CALL_PHONE;
        if (EasyPermissions.hasPermissions(this, call)) {
            startPhoneCall(number);

        } else {
            EasyPermissions.requestPermissions(this, "Provide Phone Call Permission",
                    RESULT_CALL, call);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    public void startPhoneCall(String number){
        long contact_no = Long.parseLong(number);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+contact_no));
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRVItemClick(EmergenctContactCallEvent.ContactItemClick itemClick) {
        String contact_no = itemClick.getContactNo();
        requestPermissionForPhone(contact_no);
    }

}
