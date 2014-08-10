package pro.vylgin.getcollage.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import pro.vylgin.getcollage.R;
import pro.vylgin.getcollage.model.InstagramUsers;
import pro.vylgin.getcollage.network.UserRetrofitSpiceRequest;
import pro.vylgin.getcollage.settings.Config;
import pro.vylgin.getcollage.ui.activity.BaseActivity;

public class FirstFragment extends Fragment {

    private String instagramLogin;
    private ProgressBar progressBar;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        view.findViewById(R.id.GetCollageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText instagramLoginTextView = ((EditText) getActivity().findViewById(R.id.instagramUserEditText));
                instagramLogin = instagramLoginTextView.getText().toString();
                UserRetrofitSpiceRequest userRequest = new UserRetrofitSpiceRequest(instagramLogin);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(instagramLoginTextView.getWindowToken(), 0);

                ((BaseActivity) getActivity()).getSpiceManager().execute(userRequest, Config.INSTAGRAM_USERS, DurationInMillis.ONE_MINUTE, new ListUserRequestListener());
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    private class ListUserRequestListener implements RequestListener<InstagramUsers> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d("Error", spiceException.toString());
        }

        @Override
        public void onRequestSuccess(InstagramUsers users) {
            if (users.getData().size() == 1) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ui_container, InstagramPhotosFragment.newInstance(users.getData().get(0).getId()))
                        .commit();
            } else if (users.getData().size() > 1) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ui_container, SelectUserFragment.newInstance())
                        .commit();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
}
