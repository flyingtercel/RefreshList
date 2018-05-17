package us.mifeng.stick_app.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import us.mifeng.stick_app.R;
import us.mifeng.stick_app.adapter.SuRecyAdapter;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PemFragment extends Fragment{
    private Handler handler = new Handler();
    private ArrayList<String>mData = new ArrayList<>();
    private View view;
    private SuRecyAdapter adapter;
    private XRecyclerView xRecyclerView;

    private PemFragment(String title) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pem, container, false);
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
                adapter.notifyDataSetChanged();
            }
        },2000);
    }

    private void findView() {
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.xRecyclerView);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter = new SuRecyAdapter(mData);
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh: ======");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xRecyclerView.refreshComplete();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                Log.i(TAG, "onLoadMore: =====");
                loadMore();

            }
        });
    }

    private void loadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                xRecyclerView.loadMoreComplete();
            }
        },2300);
    }

    public static PemFragment newInstance(String title){
        PemFragment fragment = new PemFragment(title);
        return fragment;
    }
}
