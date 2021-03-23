package com.jousen.plugin.jdialog.adapter;

import android.content.res.ColorStateList;
import android.text.TextPaint;
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

public class JDialogGridAdapter extends RecyclerView.Adapter<JDialogGridAdapter.VH> {
    private final List<JDialogItem> items;
    private final boolean hideIcon;
    private final boolean boldText;
    private OnItemClickListener onItemClickListener;

    public JDialogGridAdapter(List<JDialogItem> items, boolean hideIcon, boolean boldText) {
        this.items = items;
        this.hideIcon = hideIcon;
        this.boldText = boldText;
    }

    @Override
    public void onBindViewHolder(@NonNull JDialogGridAdapter.VH holder, int position) {
        JDialogItem item = items.get(position);
        holder.text.setText(item.text);
        holder.text.setOnClickListener(v -> onItemClickListener.itemClick(position));

        if (!hideIcon) {
            holder.icon.setImageResource(item.icon > 0 ? item.icon : R.drawable.jdialog_list);
            if (item.iconTint != 0) {
                holder.icon.setImageTintList(ColorStateList.valueOf(item.iconTint));
            }
            holder.icon.setOnClickListener(v -> onItemClickListener.itemClick(position));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public JDialogGridAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jdialog_grid_items, parent, false);
        return new VH(v, hideIcon, boldText);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        public final ImageView icon;
        public final TextView text;

        public VH(View v, boolean hideIcon, boolean boldText) {
            super(v);
            icon = v.findViewById(R.id.j_dialog_list_icon);
            text = v.findViewById(R.id.j_dialog_list_text);
            if (boldText) {
                TextPaint paint = text.getPaint();
                if (paint != null) {
                    paint.setFakeBoldText(true);
                }
            }
            if (hideIcon) {
                icon.setVisibility(View.GONE);
            }
        }
    }
}
