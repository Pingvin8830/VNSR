package ru.sknt.vlasovnetwork.vnsr;

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

public abstract class ObjectsFragment extends Fragment implements View.OnClickListener {
    protected FragmentManager mFragmentManager;
    protected TextView mTxtError;
    protected RecyclerView mRecyclerView;

    public ObjectsFragment() {
        super();
        setObjectList();
    }

    protected abstract void setObjectList();
    protected abstract void setRecyclerViewAdapter();
    protected abstract boolean isObjectListEmpty();
    protected abstract String getErrorEmptyObjectsText();
    protected abstract String getBeforeError();
    public abstract void showObject(int position);
    protected abstract void showNewDialog();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentManager = getActivity().getSupportFragmentManager();
        View view = inflater.inflate(R.layout.list_layout, container, false);

        // Get views
        mTxtError = view.findViewById(R.id.txtError);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        Button bttnAdd = view.findViewById(R.id.bttnAdd);

        // Setting recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setRecyclerViewAdapter();

        // Set first error if list objects is empty
        if (isObjectListEmpty()) {
            mTxtError.setText(getErrorEmptyObjectsText());
            mTxtError.setVisibility(View.VISIBLE);
        } else {
            mTxtError.setVisibility(View.INVISIBLE);
        }

        // Set button listener
        bttnAdd.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        String error = getBeforeError();
        if (!error.isEmpty()) { Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show(); }
        else {
            showNewDialog();
        }
    }
}