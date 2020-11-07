package np.com.naxa.iset.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.disasterinfo.model.DisasterInfoDetailsEntity;

@Dao
public interface DisasterInfoDetailsDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from DisasterInfoDetailsEntity ORDER BY did ASC")
    Flowable<List<DisasterInfoDetailsEntity>> getAllDisasterInfoDetailsEntityList();

    @Query("SELECT DISTINCT categoryname from DisasterInfoDetailsEntity ORDER BY categoryname")
    Flowable<List<String>> getAllDistinctCategories();

    @Query("SELECT * from DisasterInfoDetailsEntity WHERE categoryname LIKE :categoryname AND subcatname LIKE :subcatname")
    Flowable<DisasterInfoDetailsEntity> getSpecificDisasterInfoDetailsEntity(String categoryname, String subcatname);




    // We do not need a conflict strategy, because the word is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(DisasterInfoDetailsEntity disasterInfoDetailsEntity);

    @Query("DELETE FROM DisasterInfoDetailsEntity")
    void deleteAll();

    @Query("DELETE FROM DisasterInfoDetailsEntity WHERE id_from_server LIKE :id_from_server")
    void deleteSpecific(String id_from_server);

}
