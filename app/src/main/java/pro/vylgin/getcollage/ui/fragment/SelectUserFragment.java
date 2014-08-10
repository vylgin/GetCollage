package pro.vylgin.getcollage.ui.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.octo.android.robospice.persistence.exception.CacheLoadingException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import pro.vylgin.getcollage.R;
import pro.vylgin.getcollage.model.InstagramUsers;
import pro.vylgin.getcollage.settings.Config;
import pro.vylgin.getcollage.ui.activity.BaseActivity;
import pro.vylgin.getcollage.ui.activity.MainActivity;
import pro.vylgin.getcollage.ui.adapter.InstagramUsersArrayAdapter;

public class SelectUserFragment extends Fragment {
    private InstagramUsers mInstagramUsers;

    public static SelectUserFragment newInstance() {
        return new SelectUserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Future<InstagramUsers> task = ((MainActivity) getActivity())
                    .getSpiceManager()
                    .getDataFromCache(InstagramUsers.class, Config.INSTAGRAM_USERS);
            mInstagramUsers = task.get();
        } catch (CacheLoadingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_user, container, false);

        InstagramUsersArrayAdapter instagramUsersArrayAdapter = new InstagramUsersArrayAdapter(
                getActivity(),
                R.layout.instagram_users_list_item,
                mInstagramUsers.getData());

        ListView instagramUsersListView = (ListView) view.findViewById(R.id.instagramUserListView);
        instagramUsersListView.setAdapter(instagramUsersArrayAdapter);
        instagramUsersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ui_container, InstagramPhotosFragment.newInstance(mInstagramUsers.getData().get(position).getId()))
                        .commit();
            }
        });

        return view;
    }


}
