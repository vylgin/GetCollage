package pro.vylgin.getcollage.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pro.vylgin.getcollage.model.User;
import pro.vylgin.getcollage.model.Users;

public class UserRetrofitSpiceRequest extends RetrofitSpiceRequest<Users, Instagram> {

    private String username;

    public UserRetrofitSpiceRequest(String username) {
        super(Users.class, Instagram.class);
        this.username = username;
    }

    @Override
    public Users loadDataFromNetwork() {
        return getService().findUser(username);
    }
}
