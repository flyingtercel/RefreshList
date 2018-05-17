package us.mifeng.stick_app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import us.mifeng.stick_app.R;

/**
 * Created by 黑夜之火 on 2018/5/14.
 */

public class SuRecyAdapter extends RecyclerView.Adapter<SuRecyAdapter.PHolder>{
    ArrayList<String>mData;

    public SuRecyAdapter(ArrayList<String> mData) {
        this.mData = mData;
    }

    @Override
    public PHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.su_recycler_item,parent,false);
        PHolder holder = new PHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PHolder holder, int position) {
        holder.tvName.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class PHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public PHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.su_text);

        }
    }
}
