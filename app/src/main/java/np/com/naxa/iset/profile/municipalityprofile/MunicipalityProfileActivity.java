package np.com.naxa.iset.profile.municipalityprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import np.com.naxa.iset.R;
import np.com.naxa.iset.profile.wardprofile.WardProfileActivity;
import np.com.naxa.iset.utils.sectionmultiitemUtils.DataServer;
import np.com.naxa.iset.utils.sectionmultiitemUtils.SectionMultipleItem;
import np.com.naxa.iset.utils.sectionmultiitemUtils.SectionMultipleItemAdapter;

public class MunicipalityProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.selectWardLBL)
    TextView selectWardLBL;
    @BindView(R.id.spn_ward_no)
    Spinner spnWardNo;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<SectionMultipleItem> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality_profile);
        ButterKnife.bind(this);

        setupToolBar();
        setUpSpinner();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 1. create entityList which item data extend SectionMultiEntity
        mData = DataServer.getSectionMultiData();

        setupRecyclerView();


    }


    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Municipality Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpSpinner() {
        ArrayAdapter<String> wardAdapter = new ArrayAdapter<String>(MunicipalityProfileActivity.this,
                R.layout.item_spinner_text_black, getResources().getStringArray(R.array.ward_no));
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWardNo.setAdapter(wardAdapter);


        spnWardNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    Intent intent = new Intent(MunicipalityProfileActivity.this, WardProfileActivity.class);
                    intent.putExtra("ward", spnWardNo.getSelectedItem().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setupRecyclerView(){
        // create adapter which extend BaseSectionMultiItemQuickAdapter provide your headerResId
        SectionMultipleItemAdapter sectionAdapter = new SectionMultipleItemAdapter(R.layout.def_section_head, mData);
        sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SectionMultipleItem item = (SectionMultipleItem) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.card_view:
                        if (item.getMultiItemSectionModel() != null) {
                            Toast.makeText(MunicipalityProfileActivity.this, item.getMultiItemSectionModel().getData_key(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        Toast.makeText(MunicipalityProfileActivity.this, "OnItemChildClickListener " + position, Toast.LENGTH_LONG).show();
                        break;

                }
            }
        });
        recyclerView.setAdapter(sectionAdapter);
    }


}
