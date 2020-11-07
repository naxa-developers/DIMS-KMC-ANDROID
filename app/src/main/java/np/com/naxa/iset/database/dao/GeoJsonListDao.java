package np.com.naxa.iset.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
