package ru.sknt.vlasovnetwork.vnsr;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public abstract class ShowObjectDialog extends DialogFragment implements View.OnClickListener {
    protected View mDialogView;

    protected abstract int getLayoutCode();
    protected abstract void getDataViews();
    protected abstract void setData();
    protected abstract String getDialogMessageText();
    protected abstract void deleteObject();

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        mDialogView = inflater.inflate(R.layout.detail_layout, null);
        ConstraintLayout containerView = mDialogView.findViewById(R.id.container);
        inflater.inflate(getLayoutCode(), containerView, true);
        Button bttnCancel = mDialogView.findViewById(R.id.bttnCancel);
        Button bttnDelete = mDialogView.findViewById(R.id.bttnDelete);
        getDataViews();
        setData();
        bttnCancel.setOnClickListener(this);
        bttnDelete.setOnClickListener(this);
        builder.setView(mDialogView).setMessage(getDialogMessageText());
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bttnCancel) { dismiss(); }
        else if (view.getId() == R.id.bttnDelete) {
            deleteObject();
            dismiss();
        }
    }
}
