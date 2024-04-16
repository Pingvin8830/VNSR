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
import ru.sknt.vlasovnetwork.vnsr.kladr.adapters.StreetTypesAdapter;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class StreetTypesFragment extends Fragment {
    private FragmentManager mFragmentManager;
    private final StreetTypeDao mDao;
    private final List<StreetType> mStreetTypes;
    private TextView mTxtError;
    private StreetTypesAdapter mAdapter;

    public StreetTypesFragment (StreetTypeDao dao) {
        super();
        mDao = dao;
        mStreetTypes = mDao.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        View v = inflater.inflate(R.layout.kladr_street_types, container, false);
        mTxtError = v.findViewById(R.id.txtError);
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        Button bttnAdd = v.findViewById(R.id.bttnAdd);

        mAdapter = new StreetTypesAdapter(this, mStreetTypes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        if (mStreetTypes.isEmpty()) {
            mTxtError.setText("Street types not found");
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }
        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewStreetTypeDialog dialog = new NewStreetTypeDialog();
                        dialog.show(mFragmentManager, "");
                    }
                }
        );

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewStreetType(StreetType streetType) {
        mDao.create(streetType);
        streetType = mDao.find(streetType.getName()); // Получаем новый корректный Id
        mStreetTypes.add(streetType);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void showStreetType(int position) {
        StreetType streetType = mStreetTypes.get(position);
        ShowStreetTypeDialog dialog = new ShowStreetTypeDialog(streetType);
        dialog.show(mFragmentManager, "");
    }

    public void deleteStreetType(StreetType streetType) {
        int position = mStreetTypes.indexOf(streetType);
        mDao.delete(streetType);
        mStreetTypes.remove(streetType);
        mAdapter.notifyItemRemoved(position);
        if (mStreetTypes.isEmpty()) {
            mTxtError.setText("Street types not found");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
