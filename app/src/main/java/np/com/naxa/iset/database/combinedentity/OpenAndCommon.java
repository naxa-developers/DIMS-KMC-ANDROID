package np.com.naxa.iset.database.combinedentity;

import androidx.room.Embedded;

import np.com.naxa.iset.database.entity.CommonPlacesAttrb;
import np.com.naxa.iset.database.entity.OpenSpace;

public class OpenAndCommon {
    @Embedded
    OpenSpace openSpace;

    @Embedded
    CommonPlacesAttrb commonPlacesAttrb;

    public OpenSpace getOpenSpace() {
        return openSpace;
    }

    public void setOpenSpace(OpenSpace openSpace) {
        this.openSpace = openSpace;
    }

    public CommonPlacesAttrb getCommonPlacesAttrb() {
        return commonPlacesAttrb;
    }

    public void setCommonPlacesAttrb(CommonPlacesAttrb commonPlacesAttrb) {
        this.commonPlacesAttrb = commonPlacesAttrb;
    }
}
