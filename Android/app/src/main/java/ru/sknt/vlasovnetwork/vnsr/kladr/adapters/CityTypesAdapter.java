package ru.sknt.vlasovnetwork.vnsr.kladr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.CityTypesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class CityTypesAdapter extends RecyclerView.Adapter<CityTypesAdapter.ListItemHolder> {
    private final List<CityType> mCityTypes;
    private final CityTypesFragment mFragment;

    public CityTypesAdapter(CityTypesFragment fragment, List<CityType> cityTypes) {
        mFragment = fragment;
        mCityTypes = cityTypes;
    }

    @NonNull
    @Override
    public CityTypesAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kladr_city_type_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityTypesAdapter.ListItemHolder holder, int position) {
        CityType cityType = mCityTypes.get(position);
        holder.mTxtShort.setText(cityType.getShort());
        holder.mTxtName.setText(cityType.getName());
    }

    @Override
    public int getItemCount() {
        return mCityTypes.size();
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
            mFragment.showCityType(getAdapterPosition());
        }
    }
}
