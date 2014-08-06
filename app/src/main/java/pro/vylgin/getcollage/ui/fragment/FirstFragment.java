package pro.vylgin.getcollage.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import pro.vylgin.getcollage.R;
import pro.vylgin.getcollage.model.User;
import pro.vylgin.getcollage.model.Users;
import pro.vylgin.getcollage.network.UserRetrofitSpiceRequest;
import pro.vylgin.getcollage.ui.activity.BaseActivity;

public class FirstFragment extends Fragment {

    private String instagramLogin;

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        view.findViewById(R.id.GetCollageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instagramLogin = ((EditText) getActivity().findViewById(R.id.instagramUserEditText)).getText().toString();
                UserRetrofitSpiceRequest userRequest = new UserRetrofitSpiceRequest(instagramLogin);

                ((BaseActivity) getActivity()).getSpiceManager().execute(userRequest, "instagram", DurationInMillis.ONE_MINUTE, new ListUserRequestListener());


            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    private class ListUserRequestListener implements RequestListener<Users> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d("Error", spiceException.toString());
        }

        @Override
        public void onRequestSuccess(Users users) {
            if (users.getData().size() == 1) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ui_container, InstagramPhotosFragment.newInstance(instagramLogin))
                        .commit();
            } else if (users.getData().size() > 1) {
                StringBuilder instagramLogins = new StringBuilder();
                for (User user : users.getData()) {
                    instagramLogins.append(user.getUsername()).append(" ");
                }

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ui_container, SelectUserFragment.newInstance(instagramLogins.toString()))
                        .commit();
            }
        }
    }
}
