package np.com.naxa.iset.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import np.com.naxa.iset.mycircle.ContactModel;

@Dao
public interface MyCircleContactDao {
    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from ContactModel ORDER BY cid ASC")
    Flowable<List<ContactModel>> getAllContactList();

    @Query("SELECT * from ContactModel WHERE registered =1 AND add_to_circle =0")
    Flowable<List<ContactModel>> getRegisteredContactList();

    @Query("SELECT * from ContactModel WHERE registered =0")
    Flowable<List<ContactModel>> getUnRegisteredContactList();

    @Query("SELECT * from ContactModel WHERE registered =1 AND add_to_circle =1")
    Flowable<List<ContactModel>> getMyCircleContactList();

    // We do not need a conflict strategy, because the word is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContactModel contact);

    @Query("DELETE FROM ContactModel")
    void deleteAll();
}
