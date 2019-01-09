package np.com.naxa.iset.home;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.ArrayList;
import java.util.List;

import np.com.naxa.iset.R;
import np.com.naxa.iset.database.entity.GeoJsonCategoryEntity;
import np.com.naxa.iset.home.model.MapDataCategory;

public class MySection extends SectionEntity<MapDataCategory> {
    private boolean isMore;


    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MySection(MapDataCategory t) {
        super(t);
    }


    public static List<MySection> getResourcesCatergorySections(List<GeoJsonCategoryEntity> geoJsonCategoryEntitiesList) {

        List<MySection> list = new ArrayList<>();

        if (geoJsonCategoryEntitiesList.size() > 0) {
            for (GeoJsonCategoryEntity geoJsonCategoryEntity : geoJsonCategoryEntitiesList
                    ) {
                list.add(new MySection(new MapDataCategory(geoJsonCategoryEntity.getCategoryPhoto(), geoJsonCategoryEntity.getCategoryName(), geoJsonCategoryEntity.getCategoryTable(), MapDataCategory.POINT, R.drawable.marker_default, geoJsonCategoryEntity.getSummaryName())));
            }
        }
        return list;
    }

    public static List<MySection> getHazardCatergorySections(List<GeoJsonCategoryEntity> geoJsonCategoryEntitiesList) {
        List<MySection> list = new ArrayList<>();

        if (geoJsonCategoryEntitiesList.size() > 0) {
            for (GeoJsonCategoryEntity geoJsonCategoryEntity : geoJsonCategoryEntitiesList
                    ) {
                list.add(new MySection(new MapDataCategory(geoJsonCategoryEntity.getCategoryPhoto(), geoJsonCategoryEntity.getCategoryName(), geoJsonCategoryEntity.getCategoryTable(), MapDataCategory.POINT, R.drawable.marker_default, geoJsonCategoryEntity.getSummaryName())));
            }
        }

        return list;
    }

    public static List<MySection> getBaseDataCatergorySections(List<GeoJsonCategoryEntity> geoJsonCategoryEntitiesList) {
        List<MySection> list = new ArrayList<>();

        if (geoJsonCategoryEntitiesList.size() > 0) {
            for (GeoJsonCategoryEntity geoJsonCategoryEntity : geoJsonCategoryEntitiesList
                    ) {
                list.add(new MySection(new MapDataCategory(geoJsonCategoryEntity.getCategoryPhoto(), geoJsonCategoryEntity.getCategoryName(), geoJsonCategoryEntity.getCategoryTable(), MapDataCategory.POINT, R.drawable.marker_default, geoJsonCategoryEntity.getSummaryName())));
            }
        }

        return list;
    }
}
