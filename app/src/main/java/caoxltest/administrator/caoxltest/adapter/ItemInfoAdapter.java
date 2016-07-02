package caoxltest.administrator.caoxltest.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import caoxltest.administrator.caoxltest.R;
import caoxltest.administrator.caoxltest.bean.DetailBean;

/**
 * Created by Administrator on 2016/6/25.
 */
public class ItemInfoAdapter extends BaseAdapter {

    private List<DetailBean.ElementsBean> list;
    private Context mContext;

    public ItemInfoAdapter(Context context, List<DetailBean.ElementsBean> details) {
        mContext = context;
        list = details;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailBean.ElementsBean bean = list.get(position);
        ViewHold viewHold = null;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_layout,null);
            viewHold = new ViewHold();
            viewHold.tvDh = (TextView) convertView.findViewById(R.id.tv_dh);
            viewHold.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHold);
        }
        viewHold = (ViewHold) convertView.getTag();
        viewHold.tvDate.setText(bean.getComeOutTime());
        viewHold.tvDh.setText(bean.getLayoutID());
        return convertView;
    }

    class ViewHold{
        TextView tvDate;
        TextView tvDh;
    }
}
