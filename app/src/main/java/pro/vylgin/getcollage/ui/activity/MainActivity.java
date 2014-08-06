package pro.vylgin.getcollage.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import pro.vylgin.getcollage.R;
import pro.vylgin.getcollage.ui.fragment.FirstFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.ui_container, FirstFragment.newInstance())
                .commit();
    }

}
