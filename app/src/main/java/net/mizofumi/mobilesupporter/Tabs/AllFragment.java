package net.mizofumi.mobilesupporter.Tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import net.mizofumi.mobilesupporter.Adapter.MobileAdapter;
import net.mizofumi.mobilesupporter.Mobile;
import net.mizofumi.mobilesupporter.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

    int columnCount = 2;
    RecyclerView recyclerView;
    MobileAdapter adapter;

    public AllFragment() {
        // Required empty public constructor
    }

    public static AllFragment newInstance(){
        return new AllFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabview, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        adapter = new MobileAdapter(getContext());
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(columnCount,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);
        getData();

        return view;
    }

    public void getData(){

        NCMBQuery<NCMBObject> query = new NCMBQuery<>("Mobile");
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> list, NCMBException e) {
                if (e == null){
                    for (NCMBObject object : list) {
                        Mobile mobile = new Mobile();
                        mobile.setObjectId(object.getObjectId());
                        mobile.setCreateDate(object.getCreateDate());
                        mobile.setUpdateDate(object.getUpdateDate());
                        mobile.setName(object.getString("name"));
                        mobile.setModel(object.getString("model"));
                        mobile.setType(object.getString("type"));
                        mobile.setSim(object.getString("sim"));
                        mobile.setReleaseDate(object.getDate("releaseDate"));
                        adapter.addMobile(mobile);
                    }
                }else {
                    Toast.makeText(getContext(),"データの取得に失敗しました。",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
