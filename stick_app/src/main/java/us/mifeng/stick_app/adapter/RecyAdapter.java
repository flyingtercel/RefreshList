package us.mifeng.stick_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import us.mifeng.stick_app.R;

/**
 * Created by 黑夜之火 on 2018/5/12.
 */

public class RecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String>mData;
    private Context context;
    private static int TYPE_ITEM = 0;
    private static int TYPE_FOOT = 1;
    private static int TYPE_HEAD = 2;
    private int loadMoreStatus;
    private int headStatus;

    public RecyAdapter(ArrayList<String> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM){
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
            ReHolder holder = new ReHolder(view);
            return holder;
        }else if (viewType == TYPE_FOOT){
            View view = LayoutInflater.from(context).inflate(R.layout.recycle_foot,parent,false);
            FootHolder holder = new FootHolder(view);
            return holder;
        }else if (viewType == TYPE_HEAD){
            View view = LayoutInflater.from(context).inflate(R.layout.recycle_head,parent,false);
            HeadHolder holder = new HeadHolder(view);
            return holder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadHolder){
            HeadHolder headHolder = (HeadHolder) holder;
            if (headStatus == 0){
                headHolder.headText.setVisibility(View.GONE);
                headHolder.headText.setText("正在刷新");
            }else{
                headHolder.headText.setVisibility(View.VISIBLE);
            }
            //((HeadHolder) holder).headText.setText("我是头部布局");
        }else if (holder instanceof ReHolder){
            ReHolder reHolder = (ReHolder) holder;
            reHolder.tView.setText(mData.get(position-1));
        }else if (holder instanceof FootHolder){
            FootHolder footHolder = (FootHolder) holder;
            switch (loadMoreStatus){
                case 0:
                    footHolder.footText.setText("上啦加载更多");
                    break;
                case 1:
                    footHolder.footText.setText("正在加载更多数据");
                    break;
                case 2:
                    footHolder.footText.setText("已经没有更多数据了");
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mData.size()>0){
            return mData.size()+2;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }else if (position+1==getItemCount()){
            return TYPE_FOOT;
        }else{
            return TYPE_ITEM;
        }
        //return super.getItemViewType(position);
    }
    /*loadMoreStatus   0:上啦加载更多
    *                  1：正在加载中
    *                  2：加载完成，已经没有更多数据了
    * */
    public void changeMoreStatus(int status){
        loadMoreStatus = status;
        notifyDataSetChanged();
    }
    public void changeHeadStatus(int headStatus){
        this.headStatus = headStatus;
        notifyDataSetChanged();
    }

    class ReHolder extends RecyclerView.ViewHolder{
        TextView tView ;
        public ReHolder(View itemView) {
            super(itemView);
            tView = (TextView) itemView.findViewById(R.id.tView);
        }
    }
    class FootHolder extends RecyclerView.ViewHolder{
        TextView footText ;
        public FootHolder(View itemView) {
            super(itemView);
            footText = (TextView) itemView.findViewById(R.id.footView);
        }
    }
    class HeadHolder extends RecyclerView.ViewHolder{
        TextView headText ;
        public HeadHolder(View itemView) {
            super(itemView);
            headText = (TextView) itemView.findViewById(R.id.headView);
        }
    }

}
