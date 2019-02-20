package np.com.naxa.iset.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.publications.entity.PublicationsListDetails;

@Dao
public interface PublicationsListDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from PublicationsListDetails ORDER BY pid ASC")
    Flowable<List<PublicationsListDetails>> getAllPublicationslsList();


    @Query("SELECT * from PublicationsListDetails WHERE type LIKE :type")
    Flowable<List<PublicationsListDetails>> getTypeWiseList(String type);

    @Query("SELECT * from PublicationsListDetails WHERE name LIKE :name")
    Flowable<List<PublicationsListDetails>> getNameWiseList(String name);

    @Query("SELECT * from PublicationsListDetails WHERE name LIKE :name AND type LIKE :type")
    Flowable<List<PublicationsListDetails>> getNameTypeWiseList(String name, String type);

    @Query("SELECT DISTINCT name from PublicationsListDetails")
    Flowable<List<String>> getDistinctNameList();

    @Query("SELECT DISTINCT type from PublicationsListDetails")
    Flowable<List<String>> getDistinctTypeist();

    @Query("SELECT DISTINCT type from PublicationsListDetails WHERE name LIKE :name")
    Flowable<List<String>> getDistinctTypeLIstFromName(String name);


    // We do not need a conflict strategy, because the word is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PublicationsListDetails PublicationsListDetails);


    @Query("DELETE FROM PublicationsListDetails")
    void deleteAll();

    @Query("DELETE FROM PublicationsListDetails WHERE id LIKE :unique_id")
    void deleteSpecific(String unique_id);
}
