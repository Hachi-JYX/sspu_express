package caoxltest.administrator.caoxltest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import caoxltest.administrator.caoxltest.R;
import caoxltest.administrator.caoxltest.bean.DetailBean;

/**
 * Created by Administrator on 2016/7/2.
 */
public class SearchInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DetailBean.ElementsBean> list;
    private Context mContext;

    public SearchInfoAdapter(List<DetailBean.ElementsBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_layout,null);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DetailBean.ElementsBean bean = list.get(position);
        ItemViewHolder holde = (ItemViewHolder) holder;
        holde.tvDate.setText(bean.getComeOutTime());
        holde.tvDh.setText(bean.getLayoutID());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        TextView tvDh;
        public ItemViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvDh = (TextView) itemView.findViewById(R.id.tv_dh);
        }
    }
}
