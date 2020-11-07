
package np.com.naxa.iset.publications.entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublicationsListResponse {

    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<PublicationsListDetails> data = null;

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

    public List<PublicationsListDetails> getData() {
        return data;
    }

    public void setData(List<PublicationsListDetails> data) {
        this.data = data;
    }

}
