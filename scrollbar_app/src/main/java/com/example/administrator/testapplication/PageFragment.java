package com.example.administrator.testapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 作者：Administrator on 2016/5/10 15:27
 * 邮箱：906514731@qq.com
 * 这个是显示详情界面你的fragment必须要实现这个接口
 */
public class PageFragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RefreshListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        listView = (RefreshListView) view.findViewById(R.id.refreshListView);
        List<String> strlist = new ArrayList<String>();
        for (int i = 0; i < new Random().nextInt(100) + 31; i++) {
            strlist.add(String.valueOf(i));
        }
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1,strlist
        );
        listView.setAdapter(adapter);

        initRefresh();
        return view;
    }

    private void initRefresh() {

        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void loadMore() {

            }

            @Override
            public void refresh() {

            }
        });
    }


    //必须要返回当前滑动的view
    @Override
    public View getScrollableView() {

        return listView;
    }
}