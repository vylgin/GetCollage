package pro.vylgin.getcollage.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pro.vylgin.getcollage.R;

public class InstagramPhotosFragment extends Fragment {
    private static final String ARG_ID_INSTAGRAM_USER = "id_instagram_user";

    private String mIdInstagramUser;

    public static InstagramPhotosFragment newInstance(String idInstagramUser) {
        InstagramPhotosFragment fragment = new InstagramPhotosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_INSTAGRAM_USER, idInstagramUser);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIdInstagramUser = getArguments().getString(ARG_ID_INSTAGRAM_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instagram_photos, container, false);

        TextView instagramLoginTextView = (TextView) view.findViewById(R.id.helloInstagramLoginTextView);
        instagramLoginTextView.setText(String.format("Photos for user_id: %s:", mIdInstagramUser));

        return view;
    }

}
