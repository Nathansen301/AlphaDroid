package us.genetzky.alphaappdev.alphadroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Nathansen on 5/17/2015.
 */
public class DataAdapter extends BaseAdapter{
    int color1;
    Context context;
    List<DataItem> list;
    public DataAdapter(Context context,List<DataItem> list) {
        this.context = context;
        this.list = list;
        color1 = Color.parseColor("#ffcc00");
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        OpenTaskHolder mHolder;
        LayoutInflater inflater;
        DataItem openDataItem=list.get(position);
        if (convertView == null)
        {
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dataitem_4strings, null);
            mHolder = new OpenTaskHolder();
            mHolder.titleLayout = (RelativeLayout) convertView.findViewById(R.id.dataitem_4string_titlelayout);
            mHolder.data1 = (TextView)convertView.findViewById(R.id.dataitem_4string1);
            mHolder.data2 = (TextView)convertView.findViewById(R.id.dataitem_4string2);
            mHolder.data3 = (TextView)convertView.findViewById(R.id.dataitem_4string3);
            mHolder.data4 = (TextView)convertView.findViewById(R.id.dataitem_4string4);

            convertView.setTag(mHolder);
        } else {
            mHolder = (OpenTaskHolder)convertView.getTag();
        }
        mHolder.titleLayout.setBackgroundColor(color1);
        mHolder.data1.setText(openDataItem.getString(1));
        mHolder.data2.setText(openDataItem.getString(2));
        mHolder.data3.setText(openDataItem.getString(3));
        mHolder.data4.setText(openDataItem.getString(4));

        return convertView;
    }

    class OpenTaskHolder {
        RelativeLayout titleLayout;
        TextView data1,data2,data3,data4;
    }
}