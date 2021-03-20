package com.jousen.plugin.jdialog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jousen.plugin.jdialog.JDialogItem;
import com.jousen.plugin.jdialog.R;
import com.jousen.plugin.jdialog.listener.OnItemClickListener;

import java.util.List;

public class JDialogListAdapter extends RecyclerView.Adapter<JDialogListAdapter.VH> {
    private final List<JDialogItem> items;
    private final boolean hideIcon;
    private OnItemClickListener onItemClickListener;

    public JDialogListAdapter(List<JDialogItem> items, boolean hideIcon) {
        this.items = items;
        this.hideIcon = hideIcon;
    }

    @Override
    public void onBindViewHolder(@NonNull JDialogListAdapter.VH holder, int position) {
        JDialogItem item = items.get(position);
        holder.text.setText(item.text);
        holder.text.setOnClickListener(v -> onItemClickListener.itemClick(position));

        if (!hideIcon) {
            holder.icon.setBackgroundResource(item.icon > 0 ? item.icon : R.drawable.jdialog_list);
            holder.icon.setOnClickListener(v -> onItemClickListener.itemClick(position));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public JDialogListAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jdialog_list_items, parent, false);
        return new VH(v, hideIcon);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        public final ImageView icon;
        public final TextView text;

        public VH(View v, boolean hideIcon) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.j_dialog_list_icon);
            text = (TextView) v.findViewById(R.id.j_dialog_list_text);
            if (hideIcon) {
                icon.setVisibility(View.GONE);
            }
        }
    }
}
