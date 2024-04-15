package ru.sknt.vlasovnetwork.vnsr.kladr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.StreetTypesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class StreetTypesAdapter extends RecyclerView.Adapter<StreetTypesAdapter.ListItemHolder> {
    private final List<StreetType> mStreetTypes;
    private final StreetTypesFragment mFragment;

    public StreetTypesAdapter(StreetTypesFragment fragment, List<StreetType> streetTypes) {
        mFragment = fragment;
        mStreetTypes = streetTypes;
    }

    @NonNull
    @Override
    public StreetTypesAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kladr_street_type_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StreetTypesAdapter.ListItemHolder holder, int position) {
        StreetType streetType = mStreetTypes.get(position);
        holder.mTxtShort.setText(streetType.getShort());
        holder.mTxtName.setText(streetType.getName());
    }

    @Override
    public int getItemCount() {
        return mStreetTypes.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtShort;
        public TextView mTxtName;

        public ListItemHolder(View view) {
            super(view);
            mTxtShort = view.findViewById(R.id.txtShort);
            mTxtName = view.findViewById(R.id.txtName);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showStreetType(getAdapterPosition());
        }
    }
}
