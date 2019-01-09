package np.com.naxa.iset.database.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.database.combinedentity.HospitalAndCommon;
import np.com.naxa.iset.database.entity.HospitalFacilities;


/**
 * Created by samir on 5/08/2018.
 */

@Dao
public interface HospitalFacilitiesDao  {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from hospital_facilities ORDER BY hid ASC")
    LiveData<List<HospitalFacilities>> getFirstInsertedHospital();



    @Query("SELECT DISTINCT :columnName FROM hospital_facilities")
    List<String> getDistinctColumnValues(String columnName);


    @Query("SELECT DISTINCT :columnName FROM hospital_facilities")
    LiveData<List<String>> getDistinctValuesFromColumn(String columnName);

    @Query("SELECT DISTINCT type FROM hospital_facilities ")
    LiveData<List<String>> getDistinctTypeList();

    @Query("SELECT DISTINCT number_of_beds FROM hospital_facilities ")
    LiveData<List<String>> getDistinctBedCapacityList();

    @Query("SELECT DISTINCT structure_type FROM hospital_facilities ")
    LiveData<List<String>> getDistinctStructureTypeList();

    @Query("SELECT DISTINCT evacuation_plan FROM hospital_facilities ")
    LiveData<List<String>> getDistinctEvacuationPlanList();


    @Query("SELECT * FROM hospital_facilities "
            + "INNER JOIN commonplacesattrb ON commonplacesattrb.uid = hospital_facilities.fk_common_places "
            + "WHERE  ward LIKE :ward AND type LIKE :hospital_type OR number_of_beds LIKE :bedCapacity" +
            " AND structure_type LIKE :building_structure AND emergency_service LIKE :available_facilities AND evacuation_plan LIKE :excavation_plans" +
            " AND ( medicine_in_stock LIKE :medicine_stock OR blood_in_stock LIKE :blood_stock OR disaster_preparedness_response_plan LIKE :disaster_plan" +
            " OR first_aid_and_emergency_rescue LIKE :first_aid OR earthquake_damage LIKE :earthquake_resilient)")
    LiveData<List<HospitalAndCommon>> getAllFilteredList(String ward, String hospital_type, String bedCapacity, String building_structure,
                                                         String available_facilities, String excavation_plans, String medicine_stock,
                                                         String blood_stock, String disaster_plan, String first_aid, String earthquake_resilient);

    @Query("SELECT * FROM hospital_facilities "
            + "INNER JOIN commonplacesattrb ON commonplacesattrb.uid = hospital_facilities.fk_common_places")
    Flowable<List<HospitalAndCommon>> getAllHospitalDetailList();



    // We do not need a conflict strategy, because the word is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HospitalFacilities hospitalFacilities);

    @Query("DELETE FROM hospital_facilities")
    void deleteAll();


}