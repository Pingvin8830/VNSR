package ru.sknt.vlasovnetwork.vnsr.kladr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.StreetsFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;

public class StreetsAdapter extends RecyclerView.Adapter<StreetsAdapter.ListItemHolder> {
    private final List<Street> mStreets;
    private final StreetsFragment mFragment;

    public StreetsAdapter(StreetsFragment fragment, List<Street> streets) {
        mFragment = fragment;
        mStreets = streets;
    }

    @NonNull
    @Override
    public StreetsAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kladr_street_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StreetsAdapter.ListItemHolder holder, int position) {
        Street street = mStreets.get(position);
        holder.mTxtType.setText(street.getStreetType().getShort());
        holder.mTxtName.setText(street.getName());
    }

    @Override
    public int getItemCount() {
        return mStreets.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtType;
        public TextView mTxtName;

        public ListItemHolder(View view) {
            super(view);
            mTxtType = view.findViewById(R.id.txtType);
            mTxtName = view.findViewById(R.id.txtName);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showStreet(getAdapterPosition());
        }
    }
}
