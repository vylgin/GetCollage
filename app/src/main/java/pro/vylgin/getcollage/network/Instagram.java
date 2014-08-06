package pro.vylgin.getcollage.network;

import java.util.List;

import pro.vylgin.getcollage.model.User;
import pro.vylgin.getcollage.model.Users;
import pro.vylgin.getcollage.settings.Config;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface Instagram {
    @GET("/users/search?client_id=" + Config.INSTAGRAM_CLIENT_ID)
    Users findUser(@Query("q") String username);
}
