package pro.vylgin.getcollage.ui.fragment;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pro.vylgin.getcollage.R;

public class SelectUserFragment extends Fragment {
    private static final String ARG_USERS = "users";

    private String mInstagramLogins;

    public static SelectUserFragment newInstance(String instagramUsers) {
        SelectUserFragment fragment = new SelectUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERS, instagramUsers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInstagramLogins = getArguments().getString(ARG_USERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_user, container, false);

        TextView instagramLoginTextView = (TextView) view.findViewById(R.id.selectUserTextView);
        instagramLoginTextView.setText(String.format("Select user: %s!", mInstagramLogins));

        return view;
    }


}
