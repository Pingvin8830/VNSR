package ru.sknt.vlasovnetwork.vnsr.kladr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.CityesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;

public class CityesAdapter extends RecyclerView.Adapter<CityesAdapter.ListItemHolder> {
    private final List<City> mCityes;
    private final CityesFragment mFragment;

    public CityesAdapter(CityesFragment fragment, List<City> cityes) {
        mFragment = fragment;
        mCityes = cityes;
    }

    @NonNull
    @Override
    public CityesAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kladr_city_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityesAdapter.ListItemHolder holder, int position) {
        City city = mCityes.get(position);
        holder.mTxtType.setText(city.getCityType().getShort());
        holder.mTxtName.setText(city.getName());
    }

    @Override
    public int getItemCount() {
        return mCityes.size();
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
            mFragment.showObject(getAdapterPosition());
        }
    }
}
