package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.StreetsAdapter;

public class StreetsFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private final StreetDao mDao;
    private final StreetTypeDao mStreetTypeDao;
    private final List<Street> mStreets;
    private TextView mTxtError;
    private StreetsAdapter mAdapter;

    public StreetsFragment (StreetDao dao, StreetTypeDao streetTypeDao) {
        super();
        mDao = dao;
        mStreetTypeDao = streetTypeDao;
        mStreets = mDao.getAll();
        for (Street street : mStreets) { street.setStreetType(mStreetTypeDao); }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        View v = inflater.inflate(R.layout.kladr_streets, container, false);
        mTxtError = v.findViewById(R.id.txtError);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        Button bttnAdd = v.findViewById(R.id.bttnAdd);

        mAdapter = new StreetsAdapter(this, mStreets);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (mStreets.isEmpty()) {
            mTxtError.setText("Streets not found");
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }
        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mStreetTypeDao.getCount() < 1) { Toast.makeText(getContext(), "Street types not found", Toast.LENGTH_SHORT).show(); }
                        else {
                            NewStreetDialog dialog = new NewStreetDialog(mStreetTypeDao);
                            dialog.show(mFragmentManager, "");
                        }
                    }
                }
        );

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewStreet(Street street) {
        mDao.create(street);
        street = mDao.find(street.getTypeId(), street.getName()); // Получаем новый корректный Id
        street.setStreetType(mStreetTypeDao); // Устанавливаем Foreign
        mStreets.add(street);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void showStreet(int position) {
        Street street = mStreets.get(position);
        ShowStreetDialog dialog = new ShowStreetDialog(street);
        dialog.show(mFragmentManager, "");
    }

    public void deleteStreet(Street street) {
        int position = mStreets.indexOf(street);
        mDao.delete(street);
        mStreets.remove(street);
        mAdapter.notifyItemRemoved(position);
        if (mStreets.isEmpty()) {
            mTxtError.setText("Streets not found");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
