package np.com.naxa.iset.database.viewmodel;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.combinedentity.HospitalAndCommon;
import np.com.naxa.iset.database.databaserepository.HospitalFacilitiesRepository;
import np.com.naxa.iset.database.entity.HospitalFacilities;


/**
 * Created by samir on 5/08/2018.
 */

public class HospitalFacilitiesVewModel extends AndroidViewModel {
    private HospitalFacilitiesRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<HospitalFacilities>> mAllHospitalFacilities;
    private LiveData<List<HospitalAndCommon>> mAllFilteredHospitalFacilities;
    private LiveData<List<String>> mAllDistinctValuesList;

    private LiveData<List<String>> mAllDistinctTypeList;
    private LiveData<List<String>> mAllDistinctStructureTypeList;
    private LiveData<List<String>> mAllDistinctBedCapacityList;
    private LiveData<List<String>> mAllDistinctEvacuationPlanList;

    public HospitalFacilitiesVewModel(Application application) {
        super(application);
        mRepository = new HospitalFacilitiesRepository(application);

        mAllHospitalFacilities = mRepository.getAllHospitalFacilities();

    }
    public LiveData<List<HospitalFacilities>> getmAllHospitalFacilities() {
        return mAllHospitalFacilities;
    }

        public LiveData<List<String>> getAllDistinctValuesListFromColumn(String columnName) {
        Log.d("viewmodel", "getDistinctValuesFromColumn: "+columnName);
        mAllDistinctValuesList = mRepository.getDistinctValuesFromColumn(columnName);
        return mAllDistinctValuesList;
    }


    public LiveData<List<String>> getAllBedCapacityList() {
        mAllDistinctBedCapacityList = mRepository.getmAllBedCapacityList();

        return mAllDistinctBedCapacityList;
    }

    public LiveData<List<String>> getAllTypeList() {
        mAllDistinctTypeList = mRepository.getmAllTypeList();
        return mAllDistinctTypeList;
    }

    public LiveData<List<String>> getAllStructureTypeList() {
        mAllDistinctStructureTypeList = mRepository.getmAllStructureTypeList();

        return mAllDistinctStructureTypeList;
    }

    public LiveData<List<String>> getAllEvacuationPlanList() {
        mAllDistinctEvacuationPlanList = mRepository.getmAllEvacuationPlanList();

        return mAllDistinctEvacuationPlanList;
    }


    public void insert(HospitalFacilities hospitalFacilities) {
        Log.d("VIewholder", "insert: " + hospitalFacilities.getAmbulance_Service());
        mRepository.insert(hospitalFacilities);
    }



    public LiveData<List<HospitalAndCommon>> getFilteredList(String ward, String hospital_type, String bedCapacity, String building_structure, String available_facilities,
                                                              String excavation_plans, String medicine_stock, String blood_stock, String disaster_plan, String first_aid, String earthquake_resilient) {
        mAllFilteredHospitalFacilities = mRepository.getAllFilteredHospitalFacilities(ward, hospital_type, bedCapacity, building_structure, available_facilities, excavation_plans,
                medicine_stock,blood_stock,disaster_plan,first_aid,earthquake_resilient);


        return mAllFilteredHospitalFacilities;
    }

    public Flowable<List<HospitalAndCommon>> getAllHospitalDetailList(){
        return mRepository.getAllDetail();
    }


}