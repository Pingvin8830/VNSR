package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;

public class NewRegionDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Animation animError = AnimationUtils.loadAnimation(getContext(),R.anim.error);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_new_region, null);
        final TextView txtError = dialogView.findViewById(R.id.txtError);
        final EditText edtxtCode = dialogView.findViewById(R.id.edtxtCode);
        final EditText edtxtName = dialogView.findViewById(R.id.edtxtName);
        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);

        txtError.setVisibility(View.INVISIBLE);
        builder.setView(dialogView).setMessage("Add a new region");

        animError.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { txtError.setVisibility(View.VISIBLE); }
                    @Override
                    public void onAnimationEnd(Animation animation) { txtError.setVisibility(View.INVISIBLE); }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                }
        );

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

                        if ((code.length() > 3) || (code.isEmpty())) {
                            txtError.setText("Bad code");
                            txtError.startAnimation(animError);
                        } else if (name.isEmpty()) {
                            txtError.setText("Bad name");
                            txtError.startAnimation(animError);
                        } else {
                            Region newRegion = new Region(code, name);

                            // Получаем ссылку на Fragment
                            RegionsFragment callingFragment = (RegionsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("regions");

                            // Передаём newRegion обратно в Fragment
                            callingFragment.createNewRegion(newRegion);

                            // Закрываем диалог
                            dismiss();
                        }
                    }
                }
        );

        return builder.create();
    }
}
