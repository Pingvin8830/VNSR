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
    private final List<Region> mRegions;
    private TextView mTxtError;
    private RegionsAdapter mAdapter;

    public RegionsFragment (RegionDao dao) {
        super();
        mDao = dao;
        mRegions = mDao.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        View v = inflater.inflate(R.layout.kladr_regions, container, false);
        mTxtError = v.findViewById(R.id.txtError);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        Button bttnAdd = v.findViewById(R.id.bttnAdd);

        mAdapter = new RegionsAdapter(this, mRegions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (mRegions.isEmpty()) {
            mTxtError.setText("Regoins not found");
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }
        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewRegionDialog dialog = new NewRegionDialog();
                        dialog.show(mFragmentManager, "");
                    }
                }
        );

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewRegion(Region region) {
        mDao.create(region);
        mRegions.add(region);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void showRegion(int position) {
        Region region = mRegions.get(position);
        ShowRegionDialog dialog = new ShowRegionDialog(region);
        dialog.show(mFragmentManager, "");
    }

    public void deleteRegion(Region region) {
        int position = mRegions.indexOf(region);
        mDao.delete(region);
        mRegions.remove(region);
        mAdapter.notifyItemRemoved(position);
        if (mRegions.isEmpty()) {
            mTxtError.setText("Regions not found");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
