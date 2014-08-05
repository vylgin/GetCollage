package pro.vylgin.getcollage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class FirstFragment extends Fragment {

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
                String instagramLogin = ((EditText) getActivity().findViewById(R.id.instagramUserEditText)).getText().toString();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ui_container, InstagramPhotosFragment.newInstance(instagramLogin))
                        .commit();
            }
        });

        return view;
    }

}
