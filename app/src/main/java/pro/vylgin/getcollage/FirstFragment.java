package pro.vylgin.getcollage;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        ((Button) view.findViewById(R.id.GetCollageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instagramUser = ((EditText) getActivity().findViewById(R.id.instagramUserEditText)).getText().toString();
                Toast.makeText(getActivity(), String.format("Get Collage, %s!", instagramUser), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
