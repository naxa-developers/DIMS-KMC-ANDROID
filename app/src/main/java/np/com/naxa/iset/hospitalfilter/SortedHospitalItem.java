package np.com.naxa.iset.hospitalfilter;

import np.com.naxa.iset.database.combinedentity.HospitalAndCommon;


public class SortedHospitalItem {

    HospitalAndCommon hospitalAndCommon;
    String distance;

    public SortedHospitalItem(HospitalAndCommon hospitalAndCommon, String distance) {
        this.hospitalAndCommon = hospitalAndCommon;
        this.distance = distance;
    }

    public HospitalAndCommon getHospitalAndCommon() {
        return hospitalAndCommon;
    }

    public void setHospitalAndCommon(HospitalAndCommon hospitalAndCommon) {
        this.hospitalAndCommon = hospitalAndCommon;
    }

    public String getDistance() {
        String distanceInKmMeter;
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
