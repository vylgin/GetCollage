package pro.vylgin.getcollage.network;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.octo.android.robospice.retrofit.RetrofitJackson2SpiceService;

import pro.vylgin.getcollage.settings.Config;

public class InstagramRetrofitSpiceService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(Instagram.class);
    }

    @Override
    protected String getServerUrl() {
        return Config.BASE_URL;
    }

}
