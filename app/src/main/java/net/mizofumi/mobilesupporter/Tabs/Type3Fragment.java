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
public class Type3Fragment extends Fragment {


    public Type3Fragment() {
        // Required empty public constructor
    }

    public static Type3Fragment newInstance(){
        return new Type3Fragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabview, container, false);
    }

}
