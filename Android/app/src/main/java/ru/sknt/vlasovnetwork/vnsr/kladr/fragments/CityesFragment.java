package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.CityesAdapter;

public class CityesFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private final CityDao mDao;
    private final CityTypeDao mCityTypeDao;
    private final List<City> mCityes;
    private TextView mTxtError;
    private CityesAdapter mAdapter;

    public CityesFragment (CityDao dao, CityTypeDao cityTypeDao) {
        super();
        mDao = dao;
        mCityTypeDao = cityTypeDao;
        mCityes = mDao.getAll();
        for (City city : mCityes) { city.setCityType(mCityTypeDao); }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        View v = inflater.inflate(R.layout.kladr_cityes, container, false);
        mTxtError = v.findViewById(R.id.txtError);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        Button bttnAdd = v.findViewById(R.id.bttnAdd);

        mAdapter = new CityesAdapter(this, mCityes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (mCityes.isEmpty()) {
            mTxtError.setText("Cityes not found");
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }

        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewCityDialog dialog = new NewCityDialog(mCityTypeDao);
                        dialog.show(mFragmentManager, "");
                    }
                }
        );

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewCity(City city) {
        mDao.create(city);
        mCityes.add(city);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void showCity(int position) {
        City city = mCityes.get(position);
        ShowCityDialog dialog = new ShowCityDialog(city);
        dialog.show(mFragmentManager, "");
    }

    public void deleteCity(City city) {
        int position = mCityes.indexOf(city);
        mDao.delete(city);
        mCityes.remove(city);
        mAdapter.notifyItemRemoved(position);
        if (mCityes.isEmpty()) {
            mTxtError.setText("Cityes not found");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
