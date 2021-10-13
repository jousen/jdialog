package com.jousen.plugin.jdialog.adapter;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jousen.plugin.jdialog.JDialogItem;
import com.jousen.plugin.jdialog.R;
import com.jousen.plugin.jdialog.listener.OnItemClickListener;

import java.util.List;

public class JDialogSingleListAdapter extends RecyclerView.Adapter<JDialogSingleListAdapter.VH> {
    private final List<JDialogItem> items;
    private final boolean boldText;
    private OnItemClickListener onItemClickListener;

    public JDialogSingleListAdapter(List<JDialogItem> items, boolean boldText) {
        this.items = items;
        this.boldText = boldText;
    }

    @Override
    public void onBindViewHolder(@NonNull JDialogSingleListAdapter.VH holder, int position) {
        JDialogItem item = items.get(position);
        holder.text.setText(item.text);
        holder.text.setOnClickListener(v -> onItemClickListener.itemClick(position));
        holder.icon.setImageResource(item.icon > 0 ? item.icon : R.drawable.jdialog_list);
        //点击
        holder.layout.setOnClickListener(v -> onItemClickListener.itemClick(position));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public JDialogSingleListAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jdialog_list_items, parent, false);
        return new VH(v, boldText);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        public final LinearLayout layout;
        public final ImageView icon;
        public final TextView text;

        public VH(View v, boolean boldText) {
            super(v);
            layout = v.findViewById(R.id.jdialog_list_view);
            icon = v.findViewById(R.id.jdialog_list_icon);
            text = v.findViewById(R.id.jdialog_list_text);
            if (boldText) {
                TextPaint paint = text.getPaint();
                if (paint != null) {
                    paint.setFakeBoldText(true);
                }
            }
        }
    }
}
