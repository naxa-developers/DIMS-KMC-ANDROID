package np.com.naxa.iset.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.File;

import np.com.naxa.iset.database.dao.CommonPlacesAttrbDao;
import np.com.naxa.iset.database.dao.ContactDao;
import np.com.naxa.iset.database.dao.DisasterInfoDetailsDao;
import np.com.naxa.iset.database.dao.EducationalInstitutesDao;
import np.com.naxa.iset.database.dao.GeoJsonCategoryDao;
import np.com.naxa.iset.database.dao.GeoJsonListDao;
import np.com.naxa.iset.database.dao.HospitalFacilitiesDao;
import np.com.naxa.iset.database.dao.MessageHelperDao;
import np.com.naxa.iset.database.dao.MyCircleContactDao;
import np.com.naxa.iset.database.dao.OpenSpaceDao;
import np.com.naxa.iset.database.dao.ReportDetailsDao;
import np.com.naxa.iset.database.entity.CommonPlacesAttrb;
import np.com.naxa.iset.database.entity.Contact;
import np.com.naxa.iset.database.entity.EducationalInstitutes;
import np.com.naxa.iset.database.entity.GeoJsonCategoryEntity;
import np.com.naxa.iset.database.entity.GeoJsonListEntity;
import np.com.naxa.iset.database.entity.HospitalFacilities;
import np.com.naxa.iset.database.entity.OpenSpace;
import np.com.naxa.iset.database.entity.ReportDetailsEntity;
import np.com.naxa.iset.disasterinfo.model.DisasterInfoDetailsEntity;
import np.com.naxa.iset.firebase.MessageHelper;
import np.com.naxa.iset.mycircle.ContactModel;
import np.com.naxa.iset.utils.CreateAppMainFolderUtils;

/**
 * Created by samir on 4/22/2018.
 */

@Database(entities = {Contact.class, OpenSpace.class, CommonPlacesAttrb.class, HospitalFacilities.class, EducationalInstitutes.class,
        GeoJsonCategoryEntity.class, GeoJsonListEntity.class, MessageHelper.class, ContactModel.class, ReportDetailsEntity.class,
        DisasterInfoDetailsEntity.class
}, version = 21, exportSchema = false)

public abstract class ISETRoomDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();
    public abstract MyCircleContactDao myCircleContactDao();
    public abstract MessageHelperDao messageHelperDao();
    public abstract OpenSpaceDao openSpaceDao();
    public abstract CommonPlacesAttrbDao commonPlacesAttrbDao();
    public abstract HospitalFacilitiesDao hospitalFacilitiesDao();
    public abstract EducationalInstitutesDao educationalInstitutesDao();
    public abstract GeoJsonCategoryDao geoJsonCategoryDao();
    public abstract GeoJsonListDao geoJsonListDao();
    public abstract ReportDetailsDao reportDetailsDao();
    public abstract DisasterInfoDetailsDao disasterInfoDetailsDao();

    private static ISETRoomDatabase INSTANCE;

    public static ISETRoomDatabase getDatabase(final Context context) {
        CreateAppMainFolderUtils createAppMainFolderUtils = new CreateAppMainFolderUtils(context, CreateAppMainFolderUtils.appmainFolderName);
        if (INSTANCE == null) {
            synchronized (ISETRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ISETRoomDatabase.class, createAppMainFolderUtils.getAppDataFolderName()+File.separator+"iset_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ContactDao mContactDao;
        private final MyCircleContactDao mMyCircleContactDao;
        private final MessageHelperDao mMessageHelperDao;
        private final OpenSpaceDao mOpenSpaceDao;
        private final CommonPlacesAttrbDao mCommonPlacesAttrbDao;
        private final HospitalFacilitiesDao mHospitalFacilitiesDao;
        private final EducationalInstitutesDao mEducationalInstitutesDao;
        private final GeoJsonCategoryDao mGeoJsonCategoryDao;
        private final GeoJsonListDao mGeoJsonListDao;
        private final ReportDetailsDao mReportDetailsDao;
        private final DisasterInfoDetailsDao mDisasterInfoDetailsDao;

        PopulateDbAsync(ISETRoomDatabase db) {
            mContactDao = db.contactDao();
            mMyCircleContactDao = db.myCircleContactDao();
            mMessageHelperDao = db.messageHelperDao();
            mOpenSpaceDao = db.openSpaceDao();
            mCommonPlacesAttrbDao = db.commonPlacesAttrbDao();
            mHospitalFacilitiesDao = db.hospitalFacilitiesDao();
            mEducationalInstitutesDao = db.educationalInstitutesDao();
            mGeoJsonCategoryDao = db.geoJsonCategoryDao();
            mGeoJsonListDao = db.geoJsonListDao();
            mReportDetailsDao = db.reportDetailsDao();
            mDisasterInfoDetailsDao = db.disasterInfoDetailsDao();

        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mContactDao.deleteAll();
//            mCommonPlacesAttrbDao.deleteAll();
//            mHospitalFacilitiesDao.deleteAll();
            insertContact();
            return null;
        }

        private void insertContact() {
            Contact contact = new Contact("samir", "dangal", 40);
            mContactDao.insert(contact);
            contact = new Contact("nishon", "tandukar", 40);
            mContactDao.insert(contact);
        }
    }

}
