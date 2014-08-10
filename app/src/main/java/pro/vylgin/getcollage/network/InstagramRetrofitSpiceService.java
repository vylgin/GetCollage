package pro.vylgin.getcollage.network;

import android.app.Application;

import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.Persister;
import com.octo.android.robospice.persistence.binary.InFileBitmapObjectPersister;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.memory.LruCacheBitmapObjectPersister;
import com.octo.android.robospice.persistence.retrofit.RetrofitObjectPersister;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import pro.vylgin.getcollage.model.InstagramUsers;
import pro.vylgin.getcollage.settings.Config;

public class InstagramRetrofitSpiceService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(Instagram.class);
    }

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager manager = new CacheManager();

        InFileBitmapObjectPersister filePersister = new InFileBitmapObjectPersister(getApplication());
        LruCacheBitmapObjectPersister memoryPersister = new LruCacheBitmapObjectPersister(filePersister, 1024 * 1024);
        Persister persister = new RetrofitObjectPersister<InstagramUsers>(application, getConverter(), InstagramUsers.class);

        manager.addPersister(memoryPersister);
        manager.addPersister(persister);

        return manager;
    }

    @Override
    protected String getServerUrl() {
        return Config.BASE_URL;
    }

}
