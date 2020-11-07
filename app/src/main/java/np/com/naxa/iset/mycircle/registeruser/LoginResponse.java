package np.com.naxa.iset.mycircle.registeruser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("error")
    @Expose
    Integer error;

    @SerializedName("data")
    @Expose
    UserModel data;

    @SerializedName("message")
    @Expose
    String message ;

    public LoginResponse(Integer error, UserModel data, String message) {
        this.error = error;
        this.data = data;
        this.message = message;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
