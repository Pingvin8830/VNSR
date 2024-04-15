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
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.StreetsAdapter;

public class StreetsFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private final StreetDao mDao;
    private final StreetTypeDao mStreetTypeDao;
    private List<Street> mStreets;
    private TextView mTxtError;
    private StreetsAdapter mAdapter;

    public StreetsFragment (StreetDao dao, StreetTypeDao streetTypeDao) {
        super();
        mDao = dao;
        mStreetTypeDao = streetTypeDao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        mStreets = mDao.getAll();
        View v = inflater.inflate(R.layout.kladr_streets, container, false);
        mTxtError = v.findViewById(R.id.txtError);
        if (mStreets.isEmpty()) {
            mTxtError.setText("Streets not found");
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }
        Button bttnAdd = v.findViewById(R.id.bttnAdd);
        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewStreetDialog dialog = new NewStreetDialog(mStreetTypeDao);
                        dialog.show(mFragmentManager, "");
                    }
                }
        );
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        mAdapter = new StreetsAdapter(this, mStreets, mStreetTypeDao);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Устанавливаем адаптер
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewStreet(Street s) {
        mDao.create(s);
        s = mDao.find(s.getTypeId(), s.getName());
        mStreets.add(s);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void showStreet(int streetToShow) {
        ShowStreetDialog dialog = new ShowStreetDialog(mStreetTypeDao);
        dialog.sendStreetSelected(mStreets.get(streetToShow));
        dialog.show(mFragmentManager, "");
    }

    public void deleteStreet(Street s) {
        int pos = mStreets.indexOf(s);
        mDao.delete(s);
        mStreets.remove(s);
        mAdapter.notifyItemRemoved(pos);
        if (mStreets.isEmpty()) {
            mTxtError.setText("Streets not found");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
