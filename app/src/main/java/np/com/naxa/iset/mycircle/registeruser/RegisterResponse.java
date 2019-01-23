package np.com.naxa.iset.mycircle.registeruser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @SerializedName("data")
    @Expose
     Integer error;

    @SerializedName("message")
    @Expose
    String message ;

    public RegisterResponse(Integer error, String message) {
        this.error = error;
        this.message = message;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer data) {
        this.error = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
