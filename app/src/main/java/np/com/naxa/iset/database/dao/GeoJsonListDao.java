package np.com.naxa.iset.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import np.com.naxa.iset.database.entity.GeoJsonListEntity;

@Dao
public interface GeoJsonListDao {

    @Query("SELECT * from GeoJsonListEntity ORDER BY id ASC")
    LiveData<List<GeoJsonListEntity>> getGeoJsonListEntity();

    @Query("SELECT * from GeoJsonListEntity WHERE category_table =:category_table")
    LiveData<GeoJsonListEntity> getGeoJsonListByTableName(String category_table);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GeoJsonListEntity geoJsonListEntity);

    @Query("DELETE FROM GeoJsonListEntity")
    void deleteAll();
}
