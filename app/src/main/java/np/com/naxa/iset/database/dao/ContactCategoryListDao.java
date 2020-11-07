package np.com.naxa.iset.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.emergencyContacts.model.ContactCategoryListDetails;

@Dao
public interface ContactCategoryListDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from ContactCategoryListDetails ORDER BY cid ASC")
    Flowable<List<ContactCategoryListDetails>> getAllContactCategoryList();


    @Query("SELECT * from ContactCategoryListDetails WHERE category LIKE :category")
    Flowable<List<ContactCategoryListDetails>> getCategoryWiseList(String category);


    // We do not need a conflict strategy, because the word is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ContactCategoryListDetails contactCategoryListDetails);


    @Query("DELETE FROM ContactCategoryListDetails")
    void deleteAll();

    @Query("DELETE FROM ContactCategoryListDetails WHERE id LIKE :unique_id")
    void deleteSpecific(String unique_id);
}
