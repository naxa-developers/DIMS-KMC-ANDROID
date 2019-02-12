package np.com.naxa.iset.database.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import np.com.naxa.iset.database.databaserepository.GeoJsonListEntityRepository;
import np.com.naxa.iset.database.entity.GeoJsonListEntity;

public class GeoJsonListViewModel extends AndroidViewModel {
    private GeoJsonListEntityRepository mRepository;

    private LiveData<List<GeoJsonListEntity>> mAllGeoJsonListEntity;
    private LiveData<GeoJsonListEntity> mSpecifiedGeoJsonEntity;

    public GeoJsonListViewModel(Application application) {
        super(application);
        mRepository = new GeoJsonListEntityRepository(application);

        mAllGeoJsonListEntity = mRepository.getAllGeoJsonListEntity();
    }

    public LiveData<List<GeoJsonListEntity>> getAllGeoJsonCategoryEntity() {
        return mAllGeoJsonListEntity;
    }

    public LiveData<GeoJsonListEntity> getmSpecificGeoJsonEntity(String category_table) {
        mSpecifiedGeoJsonEntity = mRepository.getSpecificGeoJsonEntity(category_table);
        return mSpecifiedGeoJsonEntity;
    }

    public void insert(GeoJsonListEntity geoJsonListEntity) {
        mRepository.insert(geoJsonListEntity);

    }
}
