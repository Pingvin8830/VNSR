package ru.sknt.vlasovnetwork.vnsr.kladr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.AddressesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ListItemHolder> {
    private final List<Address> mAddresses;
    private final AddressesFragment mFragment;

    public AddressesAdapter(AddressesFragment fragment, List<Address> addresses) {
        mFragment = fragment;
        mAddresses = addresses;
    }

    @NonNull
    @Override
    public AddressesAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kladr_address_item, parent, false);
        return new ListItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.ListItemHolder holder, int position) {
        Address address = mAddresses.get(position);
        holder.mTxtName.setText(address.getName());
        holder.mTxtCity.setText(address.getCity().toString());
        holder.mTxtStreet.setText(address.getStreet().toString());
        holder.mTxtHome.setText(", д. " + address.getHouse());
        if (!address.getBuilding().isEmpty()) { holder.mTxtBuilding.setText(", копр. " + address.getBuilding()); }
        else { holder.mTxtBuilding.setText(""); }
        if (address.getFlat() != 0) { holder.mTxtFlat.setText(", кв. " + address.getFlat()); }
        else { holder.mTxtFlat.setText(""); }
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    public class ListItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtName;
        public TextView mTxtCity;
        public TextView mTxtStreet;
        public TextView mTxtHome;
        public TextView mTxtBuilding;
        public TextView mTxtFlat;

        public ListItemHolder(View view) {
            super(view);
            mTxtName = view.findViewById(R.id.txtName);
            mTxtCity = view.findViewById(R.id.txtCity);
            mTxtStreet = view.findViewById(R.id.txtStreet);
            mTxtHome = view.findViewById(R.id.txtHome);
            mTxtBuilding = view.findViewById(R.id.txtBuilding);
            mTxtFlat = view.findViewById(R.id.txtFlat);
            view.setClickable(true);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mFragment.showAddress(getAdapterPosition());
        }
    }
}
