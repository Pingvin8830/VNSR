package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;

public class NewRegionDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_new_region, null);
        final EditText edtxtCode = dialogView.findViewById(R.id.edtxtCode);
        final EditText edtxtName = dialogView.findViewById(R.id.edtxtName);
        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);
        builder.setView(dialogView).setMessage("Add a new region");

        // Обрабатываем кнопку Cancel
        bttnCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { dismiss(); }
                }
        );

        bttnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Создаём новый регион
                        String code = edtxtCode.getText().toString();
                        String name = edtxtName.getText().toString();
                        Region newRegion = new Region(code, name);

                        // Получаем ссылку на Fragment
                        RegionsFragment callingFragment = (RegionsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("regions");

                        // Передаём newRegion обратно в Fragment
                        callingFragment.createNewRegion(newRegion);

                        // Закрываем диалог
                        dismiss();
                    }
                }
        );

        return builder.create();
    }
}
