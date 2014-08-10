package pro.vylgin.getcollage.network;

import pro.vylgin.getcollage.model.InstagramUsers;
import pro.vylgin.getcollage.settings.Config;
import retrofit.http.GET;
import retrofit.http.Query;

public interface Instagram {
    @GET("/users/search?client_id=" + Config.INSTAGRAM_CLIENT_ID)
    InstagramUsers findUser(@Query("q") String username);
}
