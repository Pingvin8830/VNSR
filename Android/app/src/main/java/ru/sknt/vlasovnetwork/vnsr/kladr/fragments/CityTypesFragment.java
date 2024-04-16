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
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.CityTypesAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class CityTypesFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private final CityTypeDao mDao;
    private final List<CityType> mCityTypes;
    private TextView mTxtError;
    private CityTypesAdapter mAdapter;

    public CityTypesFragment (CityTypeDao dao) {
        super();
        mDao = dao;
        mCityTypes = mDao.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        View v = inflater.inflate(R.layout.kladr_city_types, container, false);
        mTxtError = v.findViewById(R.id.txtError);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        Button bttnAdd = v.findViewById(R.id.bttnAdd);

        mAdapter = new CityTypesAdapter(this, mCityTypes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (mCityTypes.isEmpty()) {
            mTxtError.setText("City types not found");
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }
        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewCityTypeDialog dialog = new NewCityTypeDialog();
                        dialog.show(mFragmentManager, "");
                    }
                }
        );

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewCityType(CityType cityType) {
        mDao.create(cityType);
        cityType = mDao.find(cityType.getName()); // Получаем новый корректный Id
        mCityTypes.add(cityType);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void showCityType(int position) {
        CityType cityType = mCityTypes.get(position);
        ShowCityTypeDialog dialog = new ShowCityTypeDialog(cityType);
        dialog.show(mFragmentManager, "");
    }

    public void deleteCityType(CityType cityType) {
        int position = mCityTypes.indexOf(cityType);
        mDao.delete(cityType);
        mCityTypes.remove(cityType);
        mAdapter.notifyItemRemoved(position);
        if (mCityTypes.isEmpty()) {
            mTxtError.setText("City types not found");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
