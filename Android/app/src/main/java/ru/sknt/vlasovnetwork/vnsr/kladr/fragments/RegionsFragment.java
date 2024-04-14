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
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.RegionsAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.RegionDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;

public class RegionsFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private final RegionDao mDao;
    private List<Region> mRegions;
    private TextView mTxtError;
    private RegionsAdapter mAdapter;

    public RegionsFragment (RegionDao dao) {
        super();
        mDao = dao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        mRegions = mDao.getAll();
        View v = inflater.inflate(R.layout.kladr_regions, container, false);
        mTxtError = v.findViewById(R.id.txtError);
        if (mRegions.isEmpty()) {
            mTxtError.setText("Regoins not found");
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }
        Button bttnAdd = v.findViewById(R.id.bttnAdd);
        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewRegionDialog dialog = new NewRegionDialog();
                        dialog.show(mFragmentManager, "");
                    }
                }
        );
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        mAdapter = new RegionsAdapter(this, mRegions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Добавляем аккуратную разделительную линию между элементами в списке
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        // Устанавливаем адаптер
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewRegion(Region r) {
        mDao.create(r);
        r = mDao.find(r.getCode());
        mRegions.add(r);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void showRegion(int regionToShow) {
        ShowRegionDialog dialog = new ShowRegionDialog();
        dialog.sendRegionSelected(mRegions.get(regionToShow));
        dialog.show(mFragmentManager, "");
    }

    public void deleteRegion(Region r) {
        int pos = mRegions.indexOf(r);
        mDao.delete(r);
        mRegions.remove(r);
        mAdapter.notifyItemRemoved(pos);
        if (mRegions.isEmpty()) {
            mTxtError.setText("Regions not found");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
