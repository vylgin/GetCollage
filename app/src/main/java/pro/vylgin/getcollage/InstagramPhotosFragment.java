package pro.vylgin.getcollage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InstagramPhotosFragment extends Fragment {
    private static final String INSTAGRAM_LOGIN = "instagram_login";

    private String mInstagramLogin;

    public static InstagramPhotosFragment newInstance(String instagramLogin) {
        InstagramPhotosFragment fragment = new InstagramPhotosFragment();
        Bundle args = new Bundle();
        args.putString(INSTAGRAM_LOGIN, instagramLogin);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInstagramLogin = getArguments().getString(INSTAGRAM_LOGIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instagram_photos, container, false);

        TextView instagramLoginTextView = (TextView) view.findViewById(R.id.helloInstagramLoginTextView);
        instagramLoginTextView.setText(String.format("Get Collage, %s!", mInstagramLogin));

        return view;
    }

}
