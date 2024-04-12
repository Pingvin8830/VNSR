package ru.sknt.vlasovnetwork.vnsr.kladr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.RegionsFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;

public class RegionsAdapter extends RecyclerView.Adapter<RegionsAdapter.ListItemHolder> {
    private final List<Region> mRegions;
    private final RegionsFragment mFragment;

    public RegionsAdapter(RegionsFragment fragment, List<Region> regions) {
        mFragment = fragment;
        mRegions = regions;
    }

    @NonNull
    @Override
    public RegionsAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kladr_region_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RegionsAdapter.ListItemHolder holder, int position) {
        Region region = mRegions.get(position);
        holder.mTxtCode.setText(region.getCode());
        holder.mTxtName.setText(region.getName());
    }

    @Override
    public int getItemCount() {
        return mRegions.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtCode;
        public TextView mTxtName;

        public ListItemHolder(View view) {
            super(view);
            mTxtCode = view.findViewById(R.id.txtCode);
            mTxtName = view.findViewById(R.id.txtName);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showRegion(getAdapterPosition());
        }
    }
}
