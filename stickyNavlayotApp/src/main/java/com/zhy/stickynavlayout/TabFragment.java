package com.zhy.stickynavlayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.stickynavlayout.view.SimpleViewPagerIndicator;
import com.zhy.stickynavlayout.view.StickyNavLayout;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static android.content.ContentValues.TAG;

/*
* https://www.cnblogs.com/qingblog/archive/2012/09/03/2668346.html
* */
public class TabFragment extends Fragment {
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private RecyclerView mRecyclerView;
    // private TextView mTextView;
    private List<String> mDatas = new ArrayList<String>();
    private View view;
    private PtrClassicFrameLayout ptrLayout;
    private StickyNavLayout stickyNavLayout;
    private int[] location;
    private SimpleViewPagerIndicator svpi;
    private View relaive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab, container, false);

        initView();
        return view;

    }

    private void initView() {
        relaive = getActivity().findViewById(R.id.id_stickynavlayout_topview);
        svpi = (SimpleViewPagerIndicator) getActivity().findViewById(R.id.id_stickynavlayout_indicator);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_stickynavlayout_innerscrollview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ptrLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_layout);
        stickyNavLayout = (StickyNavLayout) getActivity().findViewById(R.id.stickynavlayout);

        for (int i = 0; i < 50; i++) {
            mDatas.add(mTitle + " -> " + i);
        }

        mRecyclerView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item, mDatas) {
            @Override
            public void convert(ViewHolder holder, String o) {
                holder.setText(R.id.id_info, o);
            }
        });

        initEnvent();
    }

    private void initEnvent() {

        ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                boolean b =  PtrDefaultHandler2.checkContentCanBePulledDown(frame,content,header);
                //boolean b = PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, header);
                Log.i(TAG, "checkCanDoRefresh: ===="+(b&&checkRefreshOrLoad())+b+checkRefreshOrLoad());
                return (b&&checkRefreshOrLoad());
                //return super.checkCanDoRefresh(frame, content, header);
            }

        });
    }

    public static TabFragment newInstance(String title) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
    public boolean checkRefreshOrLoad(){
        location = new int[2];
        mRecyclerView.getLocationOnScreen(location);
        int height = svpi.getHeight();
        int hh = relaive.getHeight();
        height+=hh;
        Log.i(TAG, "initView: ="+location[1]+"  "+height);
        if (location[1]>=height){
            return true;
        }else{
            return false;
        }
    }

}
