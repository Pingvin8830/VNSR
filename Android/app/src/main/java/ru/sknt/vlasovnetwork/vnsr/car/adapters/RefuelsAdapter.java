package ru.sknt.vlasovnetwork.vnsr.car.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.fragments.RefuelsFragment;
import ru.sknt.vlasovnetwork.vnsr.car.models.Refuel;

public class RefuelsAdapter extends RecyclerView.Adapter<RefuelsAdapter.ListItemHolder> {
    private final List<Refuel> mRefuels;
    private final RefuelsFragment mFragment;

    public RefuelsAdapter(RefuelsFragment fragment, List<Refuel> refuels) {
        mFragment = fragment;
        mRefuels = refuels;
    }

    @NonNull
    @Override
    public RefuelsAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_refuel_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RefuelsAdapter.ListItemHolder holder, int position) {
        Refuel refuel = mRefuels.get(position);
        holder.mTxtDate.setText(refuel.getDateTime().toString());
    }

    @Override
    public int getItemCount() {
        return mRefuels.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtDate;

        public ListItemHolder(View view) {
            super(view);
            mTxtDate = view.findViewById(R.id.txtDate);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showObject(getAdapterPosition());
        }
    }
}
