package pro.vylgin.getcollage.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pro.vylgin.getcollage.model.InstagramUsers;

public class UserRetrofitSpiceRequest extends RetrofitSpiceRequest<InstagramUsers, Instagram> {

    private String username;

    public UserRetrofitSpiceRequest(String username) {
        super(InstagramUsers.class, Instagram.class);
        this.username = username;
    }

    @Override
    public InstagramUsers loadDataFromNetwork() {
        return getService().findUser(username);
    }
}
