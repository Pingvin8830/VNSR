package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.RegionsAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;

public class RegionsFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private final List<Region> mRegions = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        View v = inflater.inflate(R.layout.kladr_regions, container, false);
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
        RegionsAdapter adapter = new RegionsAdapter(this, mRegions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Добавляем аккуратную разделительную линию между элементами в списке
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        // Устанавливаем адаптер
        recyclerView.setAdapter(adapter);

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewRegion(Region r) {
        mRegions.add(r);
    }

    public void showRegion(int regionToShow) {
        ShowRegionDialog dialog = new ShowRegionDialog();
        dialog.sendRegionSelected(mRegions.get(regionToShow));
        dialog.show(mFragmentManager, "");
    }
}
