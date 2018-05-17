package us.mifeng.stick_app.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import us.mifeng.stick_app.R;
import us.mifeng.stick_app.adapter.RecyAdapter;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends Fragment {

    private Handler handler = new Handler();
    private View view;
    private RecyclerView mRecyclerview;
    private ArrayList<String>mData = new ArrayList<>();
    private RecyAdapter adapter;
    private SwipeRefreshLayout mRefresh;
    private LinearLayoutManager linearManager;

    private LiveFragment(String title) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_live, container, false);
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
        mRecyclerview = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(linearManager);
        adapter = new RecyAdapter(mData,getActivity());
        mRecyclerview.setAdapter(adapter);

        //mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        //mRefresh.setProgressViewOffset(false,0, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
        //mRefresh();
        loadMore();

    }

    private void loadMore() {
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int firstVisibleItem;
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+1 == adapter.getItemCount()){
                    adapter.changeMoreStatus(1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String>ll = new ArrayList<String>();
                            ll.add("酷酷酷酷酷");
                            mData.addAll(ll);
                            adapter.notifyDataSetChanged();
                            adapter.changeMoreStatus(0);
                        }
                    },2500);
                }else if (newState == RecyclerView.SCROLL_STATE_IDLE && firstVisibleItem==1){
                    adapter.changeHeadStatus(1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String>ll = new ArrayList<String>();
                            ll.add("酷酷酷酷酷");
                            mData.addAll(ll);
                            adapter.notifyDataSetChanged();
                            adapter.changeHeadStatus(0);
                        }
                    },2500);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearManager.findLastVisibleItemPosition();

                firstVisibleItem = linearManager.findFirstVisibleItemPosition();
                Log.i(TAG, "onScrolled: ====="+firstVisibleItem);
            }
        });
    }

    private void mRefresh() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefresh.setRefreshing(false);
                    }
                },1600);
            }
        });
    }

    public static LiveFragment newInstance(String title){
        LiveFragment fragment = new LiveFragment(title);
        return fragment;
    }

}
