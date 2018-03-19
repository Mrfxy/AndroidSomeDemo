package com.jtv.videodemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jtv.videodemo.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> list;
    private List<Class> activityList;

    public MenuAdapter(Context context, List<String> list, List<Class> activityList) {
        this.context = context;
        this.list = list;
        this.activityList = activityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        ViewHolder holder = (ViewHolder) holder1;
        holder.tv_menu_item.setText(list.get(position));

        holder.tv_menu_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < activityList.size()){
                    Intent intent = new Intent(context,activityList.get(position));
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "角标越界了啊", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_menu_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_menu_item = itemView.findViewById(R.id.tv_menu_item);
        }
    }

}
