package us.mifeng.stick_app.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import us.mifeng.stick_app.R;
import us.mifeng.stick_app.adapter.RecyAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    private Handler handler = new Handler();
    private View view;
    private ArrayList<String>mData = new ArrayList<>();
    private RecyAdapter adapter;

    private MineFragment(String title) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        findView();
        requestNetData();

        return view;
    }

    private void requestNetData() {
        final ArrayList<String>list = new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add("我是用来测试数据"+i);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mData.addAll(list);
                //adapter.notifyDataSetChanged();
            }
        },2000);
    }

    private void findView() {


    }

    private void loadMore() {

    }

    private void mRefresh() {

    }

    public static MineFragment newInstance(String title){
        MineFragment fragment = new MineFragment(title);
        return fragment;
    }

}
