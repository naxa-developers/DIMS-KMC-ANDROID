package np.com.naxa.iset.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import np.com.naxa.iset.R;
import np.com.naxa.iset.utils.SharedPreferenceUtils;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_general)
    Toolbar toolbar;
    @BindView(R.id.switchNotifyWhenSilent)
    Switch switchNotifyWhenSilent;
    @BindView(R.id.spinnerChooseLanguage)
    Spinner spinnerChooseLanguage;

    SharedPreferenceUtils sharedPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setupToolBar();

        sharedPreferenceUtils = new SharedPreferenceUtils(SettingsActivity.this);

        setUpSpinner();

        setSwitchButtonListner();

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    private void setUpSpinner() {
        ArrayAdapter<String> wardAdapter = new ArrayAdapter<String>(SettingsActivity.this,
                R.layout.item_spinner_text_black, getResources().getStringArray(R.array.choose_language));
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChooseLanguage.setAdapter(wardAdapter);


        spinnerChooseLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sharedPreferenceUtils.setValue(SharedPreferenceUtils.APP_LANGUAGE, wardAdapter.getItem(position));
                Toast.makeText(SettingsActivity.this, "Your default app language will be "+wardAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setSwitchButtonListner(){
        switchNotifyWhenSilent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                if(isChecked == true){
                    sharedPreferenceUtils.setValue(SharedPreferenceUtils.IS_NOTIFY_ME, true);
                    Toast.makeText(SettingsActivity.this, "We will notify you soon.", Toast.LENGTH_SHORT).show();
                }else {
                    sharedPreferenceUtils.setValue(SharedPreferenceUtils.IS_NOTIFY_ME, false);
                    Toast.makeText(SettingsActivity.this, "We won't notify you.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
