
package np.com.naxa.iset.report.wardstaff.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import np.com.naxa.iset.database.entity.ReportDetailsEntity;

public class UnverifiedFormListResponse {

    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<ReportDetailsEntity> data = null;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ReportDetailsEntity> getData() {
        return data;
    }

    public void setData(List<ReportDetailsEntity> data) {
        this.data = data;
    }

}
