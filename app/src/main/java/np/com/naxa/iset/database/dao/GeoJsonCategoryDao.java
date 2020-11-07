package np.com.naxa.iset.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import np.com.naxa.iset.database.entity.GeoJsonCategoryEntity;

@Dao
public interface GeoJsonCategoryDao {

    @Query("SELECT * from GeoJsonCategoryEntity ORDER BY id ASC")
    LiveData<List<GeoJsonCategoryEntity>> getGeoJsonCategoryList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GeoJsonCategoryEntity geoJsonCategoryEntity);

    @Query("SELECT * from GeoJsonCategoryEntity WHERE category_type =:category_type")
    Maybe<List<GeoJsonCategoryEntity>> getGeoJsonListByCategoryType(String category_type);

    @Query("DELETE FROM GeoJsonCategoryEntity")
    void deleteAll();
}
