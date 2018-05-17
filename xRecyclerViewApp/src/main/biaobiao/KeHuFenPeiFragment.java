package com.ydz.yundzapp.customer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydz.yundzapp.R;
import com.ydz.yundzapp.api.APIHelper;
import com.ydz.yundzapp.baseapp.BaseListViewPullFragment;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yao on 2018/2/28.
 */

@SuppressLint("ValidFragment")
public class KeHuFenPeiFragment extends BaseListViewPullFragment {

    private static int REFRESH_REQUEST = 0x1;
    private static int LOADMORE_REQUEST = 0x2;
    private static int DEFAULT_REQUEST = 0x3;
    private int mType;//1 潜在  2签约
    private String cname = "";
    private String cuerid = "";
    private static CheckBox cb_checkbox;
    private static ArrayList<ViewHolder> list;
    private static int u;
    private ViewHolder vh;
    private int tag;
    public boolean flage = false;

    public KeHuFenPeiFragment() {
    }

    public void setdata(String cname, String cuerid) {
        this.cname = cname;
        this.cuerid = cuerid;
        APIHelper.getInstance().putAPI(new KeHuFenPeiAPI(this, "getpotentiallist", cname, cuerid, "20", "1", DEFAULT_REQUEST));
    }

    @Override
    protected AbsListItem getListItem(int type) {
        return new SignItem(this);
    }

    @Override
    protected View getList(View contentView) {
        return contentView;
    }

    @Override
    protected int getMode() {
        return MODE_BOTH;
    }

    @Override
    protected int getHeaderLayoutID() {
        return R.layout.kehu_fenpei_header;
    }

    @Override
    protected void initHeader(View header) {
        super.initHeader(header);
        LinearLayout linearLayout = header.findViewById(R.id.ll_ll);
        final CheckBox cb_checkboxs = header.findViewById(R.id.cb_checkbox);
        cb_checkboxs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < getCount(); i++) {
                    list.get(i).getCb_checkbox().setChecked(cb_checkboxs.isChecked());
               }

                Log.e("我是长度", "啊啊" + list.size());
                for (int i = 0; i < getCount(); i++) {
                    if (flage) {
                        list.get(i).getCb_checkbox().setChecked(true);
                        flage = true;
                    } else {
                        list.get(i).getCb_checkbox().setChecked(true);
                        flage = false;
                    }
                }
            }
        });
    }

    @Override
    protected void onRefresh() {
        APIHelper.getInstance().putAPI(new KeHuFenPeiAPI(this, "getpotentiallist", cname, cuerid, "20", "1", REFRESH_REQUEST));
    }

    @Override
    protected void onLoadMore() {
        APIHelper.getInstance().putAPI(new KeHuFenPeiAPI(this, "getpotentiallist", cname, cuerid, "20", (getPageCount() + 1) + "", LOADMORE_REQUEST));

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        APIHelper.getInstance().putAPI(new KeHuFenPeiAPI(this, "getpotentiallist", "", "", "20", "1", DEFAULT_REQUEST));
    }

    @Override
    protected int getContentLayoutID() {
        return R.layout.base_fragment_def_list;
    }

    public void setContent(KeHuFenPeiBean signResponseData, int mRequestType) {
        setCurrentViewStatus(FRAGMENT_STATUS_CONTENT);
        if (mRequestType == REFRESH_REQUEST) {
            setData(signResponseData.getData());
        } else if (mRequestType == LOADMORE_REQUEST) {
            addData(signResponseData.getData());
        } else {
            setData(signResponseData.getData());
        }
        stopPull();

    }

    public class SignItem extends AbsListItem<KeHuFenPeiBean.DataBean> {

        TextView gongsimingchengTV;
        TextView yewuleixingTV;
        TextView yewuTV;
        LinearLayout ll_item;
        KeHuFenPeiFragment absBaseActivity;

        public SignItem(KeHuFenPeiFragment baseActivity) {
            absBaseActivity = baseActivity;
        }

        @Override
        public int getItemLayout() {
            return R.layout.kehu_fenpei;
        }

        @Override
        public void init(View contentView) {
            list = new ArrayList<>();
            vh = new ViewHolder();
            for (int i = 0; i < getCount(); i++) {
                gongsimingchengTV = contentView.findViewById(R.id.name);
                yewuleixingTV = contentView.findViewById(R.id.riqi);
                yewuTV = contentView.findViewById(R.id.renyuan);
                cb_checkbox = contentView.findViewById(R.id.cb_checkbox);
                ll_item = contentView.findViewById(R.id.ll_item);
                vh.setGongsimingchengTV(gongsimingchengTV);
                vh.setYewuleixingTV(yewuleixingTV);
                vh.setYewuTV(yewuTV);
                vh.setCb_checkbox(cb_checkbox);
                vh.getCb_checkbox().setTag(u);
                u++;
                list.add(vh);
            }
        }

        @Override
        public void bindData(final KeHuFenPeiBean.DataBean t) {
            vh.getGongsimingchengTV().setText(t.getCName());
            vh.getYewuleixingTV().setText(t.getCCreateTime());
            vh.getYewuTV().setText(t.getCCreateName());
            vh.getCb_checkbox().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (vh.getCb_checkbox().isChecked() == true) {
//                        if (t.isCheck()) {
//                            t.setCheck(false);
//                        } else {
//                            t.setCheck(true);
//                        }
//                        Log.e("ssssssss", t.getCID());
//                        v.setTag(u);
//                        absBaseActivity.showToast("llala");
                    }
                }
            });
        }
    }

    private class ViewHolder implements Serializable {
        private static final long serialVersionUID = 1L;
        TextView gongsimingchengTV;
        TextView yewuleixingTV;
        TextView yewuTV;
        CheckBox cb_checkbox;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }


        public TextView getGongsimingchengTV() {
            return gongsimingchengTV;
        }

        public void setGongsimingchengTV(TextView gongsimingchengTV) {
            this.gongsimingchengTV = gongsimingchengTV;
        }

        public TextView getYewuleixingTV() {
            return yewuleixingTV;
        }

        public void setYewuleixingTV(TextView yewuleixingTV) {
            this.yewuleixingTV = yewuleixingTV;
        }

        public TextView getYewuTV() {
            return yewuTV;
        }

        public void setYewuTV(TextView yewuTV) {
            this.yewuTV = yewuTV;
        }

        public CheckBox getCb_checkbox() {
            return cb_checkbox;
        }

        public void setCb_checkbox(CheckBox cb_checkbox) {
            this.cb_checkbox = cb_checkbox;
        }


    }
}
