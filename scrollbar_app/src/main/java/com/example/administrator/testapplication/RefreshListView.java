package com.example.administrator.testapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RefreshListView extends ListView implements OnScrollListener {
    //头部视图的高度
    private View headerView;
    //底部视图的高度
    private View footerView;
    private TextView mTv_Top;
    private ImageView mImg_Top;
    private ProgressBar mPgb_Top;
    //第一条可见item的position
    private int firstVisibleItem;
    //头部视图的高度
    private int headerHeight;
    //底部视图的高度
    private int footerHeight;
    //起始Y的坐标
    private int startY;
    //当前的状态
    private static final int PULL_DOWN = 1;// 下拉刷新
    private static final int RELEASE = 2;// 放开刷新
    private static final int REFRESHING = 3;// 正在刷新
    private static int currentstate = PULL_DOWN;
    private OnRefreshListener listener;
    //属性动画
    private Animation upAnimation;
    private Animation downAnimation;
    private boolean isBottom = false;
    private boolean isLoading = false;

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
        initHeader(context);
        initFooter(context);
        initAnimation();
    }

    private void initFooter(Context context) {
        footerView = LayoutInflater.from(context).inflate(R.layout.footer, null);
        footerView.measure(0, 0);
        footerHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerHeight, 0, 0);
        addFooterView(footerView);
    }

    private void initAnimation() {
        upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(3000);
        upAnimation.setFillAfter(true);
    }

    private void initHeader(Context context) {
        headerView = LayoutInflater.from(context).inflate(R.layout.header, null);
        mTv_Top = (TextView) headerView.findViewById(R.id.mTv_Top);
        mImg_Top = (ImageView) headerView.findViewById(R.id.mImg_Top);
        mPgb_Top = (ProgressBar) headerView.findViewById(R.id.mPgb_Top);
        headerView.measure(0, 0);
        headerHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerHeight, 0, 0);
        addHeaderView(headerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//开始触摸屏幕时
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//触摸移动时
                int moveY = (int) ev.getY();
                int dis = moveY - startY;
                int topPadding = -headerHeight + dis;
                if (firstVisibleItem == 0 && topPadding > -headerHeight) {
                    if (topPadding > 20 && currentstate == PULL_DOWN) {
                        currentstate = RELEASE;
                        refreshHeader();
                    } else if (topPadding < 20 && currentstate == RELEASE) {
                        currentstate = PULL_DOWN;
                        refreshHeader();
                    }
                    headerView.setPadding(0, topPadding, 0, 0);
                    return true;
                }
                break;
//触摸停止时
            case MotionEvent.ACTION_UP:
                if (currentstate == RELEASE) {
                    currentstate = REFRESHING;
                    refreshHeader();
                    if (listener != null) {
                        listener.refresh();
                    }
                } else if (currentstate == PULL_DOWN) {
                    headerView.setPadding(0, -headerHeight, 0, 0);
                }
                break;
        }


        return super.onTouchEvent(ev);
    }

    private void refreshHeader() {
        switch (currentstate) {
            case PULL_DOWN:
                mTv_Top.setText("下拉刷新");
                mImg_Top.setAnimation(downAnimation);
                break;
            case RELEASE:
                mTv_Top.setText("放开刷新");
                mImg_Top.setAnimation(upAnimation);
                break;
            case REFRESHING:
                headerView.setPadding(0, 0, 0, 0);
                mTv_Top.setText("正在刷新");
                mImg_Top.clearAnimation();
                mImg_Top.setVisibility(View.GONE);
                mPgb_Top.setVisibility(View.VISIBLE);
                break;
        }
    }

    //头部加载完隐藏
    public void refreshComplete() {
        headerView.setPadding(0, -headerHeight, 0, 0);
        currentstate = PULL_DOWN;
        mTv_Top.setText("正在刷新");
        mImg_Top.setVisibility(View.VISIBLE);
        mPgb_Top.setVisibility(View.GONE);
    }

    //下部加载完隐藏
    public void loadComplete() {
        footerView.setPadding(0, -footerHeight, 0, 0);
        isLoading = false;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    public interface OnRefreshListener {
        void loadMore();

        void refresh();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        if (getLastVisiblePosition() == totalItemCount - 1) {
            isBottom = true;
        } else {
            isBottom = false;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE && isBottom && !isLoading) {
            isLoading = true;
            footerView.setPadding(0, 0, 0, 0);
            if (listener != null) {
                listener.loadMore();
            }
        }
    }
}
