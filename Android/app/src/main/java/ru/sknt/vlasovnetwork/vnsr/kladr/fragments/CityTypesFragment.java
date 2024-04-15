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
    private List<CityType> mCityTypes;
    private TextView mTxtError;
    private CityTypesAdapter mAdapter;

    public CityTypesFragment (CityTypeDao dao) {
        super();
        mDao = dao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        mCityTypes = mDao.getAll();
        View v = inflater.inflate(R.layout.kladr_city_types, container, false);
        mTxtError = v.findViewById(R.id.txtError);
        if (mCityTypes.isEmpty()) {
            mTxtError.setText("City types not found");
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }
        Button bttnAdd = v.findViewById(R.id.bttnAdd);
        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewCityTypeDialog dialog = new NewCityTypeDialog();
                        dialog.show(mFragmentManager, "");
                    }
                }
        );
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        mAdapter = new CityTypesAdapter(this, mCityTypes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Устанавливаем адаптер
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewCityType(CityType ct) {
        mDao.create(ct);
        ct = mDao.find(ct.getName());
        mCityTypes.add(ct);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void showCityType(int cityTypeToShow) {
        ShowCityTypeDialog dialog = new ShowCityTypeDialog();
        dialog.sendCityTypeSelected(mCityTypes.get(cityTypeToShow));
        dialog.show(mFragmentManager, "");
    }

    public void deleteCityType(CityType ct) {
        int pos = mCityTypes.indexOf(ct);
        mDao.delete(ct);
        mCityTypes.remove(ct);
        mAdapter.notifyItemRemoved(pos);
        if (mCityTypes.isEmpty()) {
            mTxtError.setText("City types not found");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
