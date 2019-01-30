package np.com.naxa.iset.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ReportDetailsEntity")
public class ReportDetailsEntity implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("incident_type")
    @Expose
    @ColumnInfo(name = "incident_type")
    private String incident_type;

    @SerializedName("date")
    @Expose
    @ColumnInfo(name = "date")
    private String date;

    @SerializedName("time")
    @Expose
    @ColumnInfo(name = "time")
    private String time;

    @SerializedName("vdc_mun")
    @Expose
    @ColumnInfo(name = "vdc_mun")
    private String vdc_mun ;

    @SerializedName("place_name")
    @Expose
    @ColumnInfo(name = "place_name")
    private String place_name;

    @SerializedName("ward")
    @Expose
    @ColumnInfo(name = "ward")
    private String ward  ;

    @SerializedName("risk_level")
    @Expose
    @ColumnInfo(name = "risk_level")
    private String risk_level  ;

    @SerializedName("photo")
    @Expose
    @ColumnInfo(name = "photo")
    private String photo  ;

    @SerializedName("status")
    @Expose
    @ColumnInfo(name = "status")
    private String status   ;

    @SerializedName("name_reporter")
    @Expose
    @ColumnInfo(name = "name_reporter")
    private String name_reporter   ;

    @SerializedName("address")
    @Expose
    @ColumnInfo(name = "address")
    private String address   ;

    @SerializedName("contact_reporter")
    @Expose
    @ColumnInfo(name = "contact_reporter")
    private String contact_reporter ;

    @SerializedName("remarks")
    @Expose
    @ColumnInfo(name = "remarks")
    private String remarks;

    @SerializedName("verify")
    @Expose
    @ColumnInfo(name = "verify")
    private String verify;

    @SerializedName("latitude")
    @Expose
    @ColumnInfo(name = "latitude")
    private String latitude ;

    @SerializedName("longitude")
    @Expose
    @ColumnInfo(name = "longitude")
    private String longitude ;

    public ReportDetailsEntity(String incident_type, String date, String time, String vdc_mun, String place_name, String ward, String risk_level, String photo, String status, String name_reporter, String address, String contact_reporter, String remarks, String verify, String latitude, String longitude) {
        this.incident_type = incident_type;
        this.date = date;
        this.time = time;
        this.vdc_mun = vdc_mun;
        this.place_name = place_name;
        this.ward = ward;
        this.risk_level = risk_level;
        this.photo = photo;
        this.status = status;
        this.name_reporter = name_reporter;
        this.address = address;
        this.contact_reporter = contact_reporter;
        this.remarks = remarks;
        this.verify = verify;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIncident_type() {
        return incident_type;
    }

    public void setIncident_type(String incident_type) {
        this.incident_type = incident_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVdc_mun() {
        return vdc_mun;
    }

    public void setVdc_mun(String vdc_mun) {
        this.vdc_mun = vdc_mun;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName_reporter() {
        return name_reporter;
    }

    public void setName_reporter(String name_reporter) {
        this.name_reporter = name_reporter;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_reporter() {
        return contact_reporter;
    }

    public void setContact_reporter(String contact_reporter) {
        this.contact_reporter = contact_reporter;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.incident_type);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeString(this.vdc_mun);
        dest.writeString(this.place_name);
        dest.writeString(this.ward);
        dest.writeString(this.risk_level);
        dest.writeString(this.photo);
        dest.writeString(this.status);
        dest.writeString(this.name_reporter);
        dest.writeString(this.address);
        dest.writeString(this.contact_reporter);
        dest.writeString(this.remarks);
        dest.writeString(this.verify);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    protected ReportDetailsEntity(Parcel in) {
        this.id = in.readInt();
        this.incident_type = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.vdc_mun = in.readString();
        this.place_name = in.readString();
        this.ward = in.readString();
        this.risk_level = in.readString();
        this.photo = in.readString();
        this.status = in.readString();
        this.name_reporter = in.readString();
        this.address = in.readString();
        this.contact_reporter = in.readString();
        this.remarks = in.readString();
        this.verify = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public static final Parcelable.Creator<ReportDetailsEntity> CREATOR = new Parcelable.Creator<ReportDetailsEntity>() {
        @Override
        public ReportDetailsEntity createFromParcel(Parcel source) {
            return new ReportDetailsEntity(source);
        }

        @Override
        public ReportDetailsEntity[] newArray(int size) {
            return new ReportDetailsEntity[size];
        }
    };
}
