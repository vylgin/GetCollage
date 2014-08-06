package pro.vylgin.getcollage.ui.activity;

import android.app.Activity;

import com.octo.android.robospice.SpiceManager;
import pro.vylgin.getcollage.network.InstagramRetrofitSpiceService;

public class BaseActivity extends Activity {
    private SpiceManager spiceManager = new SpiceManager(InstagramRetrofitSpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    public SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
