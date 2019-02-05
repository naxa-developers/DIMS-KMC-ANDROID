
package np.com.naxa.iset.drr_dictionary.data_glossary;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TerminologiesListResponse {

    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<WordsWithDetailsModel> data = null;

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

    public List<WordsWithDetailsModel> getData() {
        return data;
    }

    public void setData(List<WordsWithDetailsModel> data) {
        this.data = data;
    }

}
