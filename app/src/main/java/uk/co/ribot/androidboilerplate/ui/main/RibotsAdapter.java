package uk.co.ribot.androidboilerplate.ui.main;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Ribot;

public class RibotsAdapter extends RecyclerView.Adapter<RibotsAdapter.RibotViewHolder> {

    private List<Ribot> mRibots;
    private View.OnClickListener mItemOnClickListener;

    @Inject
    public RibotsAdapter() {
        mRibots = new ArrayList<>();
    }

    public RibotsAdapter(RecyclerView.OnClickListener l) {
        mRibots = new ArrayList<>();
        mItemOnClickListener = l;
    }

    public void setRibots(List<Ribot> ribots) {
        mRibots = ribots;
    }

    @Override
    public RibotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ribot, parent, false);
        return new RibotViewHolder(itemView, mItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(final RibotViewHolder holder, int position) {
        Ribot ribot = mRibots.get(position);
        holder.hexColorView.setBackgroundColor(Color.parseColor(ribot.profile().hexColor()));
        holder.nameTextView.setText(String.format("%s %s",
                ribot.profile().name().first(), ribot.profile().name().last()));
        holder.emailTextView.setText(ribot.profile().email());
        holder.detailTextView.setText(ribot.profile().bio());
        holder.deleteBtn.setTag(ribot.profile());
        holder.itemView.setTag(ribot);
    }

    @Override
    public int getItemCount() {
        return mRibots.size();
    }

    class RibotViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        @BindView(R.id.view_hex_color) View hexColorView;
        @BindView(R.id.text_name) TextView nameTextView;
        @BindView(R.id.text_email) TextView emailTextView;
        @BindView(R.id.btn_delete) ImageButton deleteBtn;
        @BindView(R.id.text_detail) TextView detailTextView;
        private boolean mDetail = false;

        public RibotViewHolder(View itemView, View.OnClickListener l) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            deleteBtn.setOnClickListener(l);
            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "ItemClick " + mRibots.get(getAdapterPosition()).toString() + ":" + getAdapterPosition(),
                    Toast.LENGTH_SHORT).show();
            if (mDetail) {
                detailTextView.setVisibility(View.GONE);
            } else {
                detailTextView.setVisibility(View.VISIBLE);
            }
            mDetail = !mDetail;
        }
    }
}
