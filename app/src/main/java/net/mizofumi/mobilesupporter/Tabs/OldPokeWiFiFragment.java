package net.mizofumi.mobilesupporter.Tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mizofumi.mobilesupporter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OldPokeWiFiFragment extends Fragment {


    public OldPokeWiFiFragment() {
        // Required empty public constructor
    }

    public static OldPokeWiFiFragment newInsance(){
        return new OldPokeWiFiFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabview, container, false);
    }

}
