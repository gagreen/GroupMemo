package com.hanyang.groupmemo.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hanyang.groupmemo.R;
import com.hanyang.groupmemo.ReadActivity;
import com.hanyang.groupmemo.databinding.ItemMemosBinding;
import com.hanyang.groupmemo.db.MemoItem;

import java.util.ArrayList;
import java.util.Calendar;

/** RecyclerView를 위한 Adapter **/
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    private ArrayList<MemoItem> mDataList;
    private Context ctx;

    public MemoAdapter(ArrayList<MemoItem> mDataList, Context ctx) {
        this.mDataList = mDataList;
        this.ctx = ctx;
    }
    /************* Adapter OverRiding ****************/
    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.ViewHolder holder, int position) {
        holder.bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return (mDataList != null ? mDataList.size() : 0);
    }

    /** ViewHolder 정의 **/
    class ViewHolder extends RecyclerView.ViewHolder {

        ItemMemosBinding ib;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ib = ItemMemosBinding.bind(itemView);
        }

        public void bind(final MemoItem item) {
            ib.tvTitle.setText(item.getTitle());
            ib.tvContext.setText(item.getContext());
            ib.tvTimestamp.setText(convertToString(item.getTimestamp()));
            ib.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ReadActivity.class);
                    Log.d("asdf", item.getMemoKey()+"0");
                    intent.putExtra("memoKey", item.getMemoKey());
                    ctx.startActivity(intent);
                }
            });
        }

        /* timestamp To String format Date */
        @SuppressLint("DefaultLocale")
        String convertToString(long millis) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(millis);

            return String.format("%02d:%02d:%02d",
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND)
            );
        }
    }

}

