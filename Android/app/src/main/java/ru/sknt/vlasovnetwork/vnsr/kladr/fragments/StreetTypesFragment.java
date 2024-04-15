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
    private List<StreetType> mStreetTypes;
    private TextView mTxtError;
    private StreetTypesAdapter mAdapter;

    public StreetTypesFragment (StreetTypeDao dao) {
        super();
        mDao = dao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        mStreetTypes = mDao.getAll();
        View v = inflater.inflate(R.layout.kladr_street_types, container, false);
        mTxtError = v.findViewById(R.id.txtError);
        if (mStreetTypes.isEmpty()) {
            mTxtError.setText("Street types not found");
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }
        Button bttnAdd = v.findViewById(R.id.bttnAdd);
        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NewStreetTypeDialog dialog = new NewStreetTypeDialog();
                        dialog.show(mFragmentManager, "");
                    }
                }
        );
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
        mAdapter = new StreetTypesAdapter(this, mStreetTypes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Устанавливаем адаптер
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void createNewStreetType(StreetType st) {
        mDao.create(st);
        st = mDao.find(st.getName());
        mStreetTypes.add(st);
        mTxtError.setVisibility(View.INVISIBLE);
    }

    public void showStreetType(int streetTypeToShow) {
        ShowStreetTypeDialog dialog = new ShowStreetTypeDialog();
        dialog.sendStreetTypeSelected(mStreetTypes.get(streetTypeToShow));
        dialog.show(mFragmentManager, "");
    }

    public void deleteStreetType(StreetType st) {
        int pos = mStreetTypes.indexOf(st);
        mDao.delete(st);
        mStreetTypes.remove(st);
        mAdapter.notifyItemRemoved(pos);
        if (mStreetTypes.isEmpty()) {
            mTxtError.setText("Street types not found");
            mTxtError.setVisibility(View.VISIBLE);
        }
    }
}
