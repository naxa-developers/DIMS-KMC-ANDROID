package np.com.naxa.iset.database.combinedentity;

import android.arch.persistence.room.Embedded;

import np.com.naxa.iset.database.entity.CommonPlacesAttrb;
import np.com.naxa.iset.database.entity.EducationalInstitutes;

public class EducationAndCommon {

    @Embedded
    EducationalInstitutes educationalInstitutes;

    @Embedded
    CommonPlacesAttrb commonPlacesAttrb;

    public EducationalInstitutes getEducationalInstitutes() {
        return educationalInstitutes;
    }

    public void setEducationalInstitutes(EducationalInstitutes educationalInstitutes) {
        this.educationalInstitutes = educationalInstitutes;
    }

    public CommonPlacesAttrb getCommonPlacesAttrb() {
        return commonPlacesAttrb;
    }

    public void setCommonPlacesAttrb(CommonPlacesAttrb commonPlacesAttrb) {
        this.commonPlacesAttrb = commonPlacesAttrb;
    }
}
