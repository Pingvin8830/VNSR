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
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class NewCityTypeDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Animation animError = AnimationUtils.loadAnimation(getContext(),R.anim.error);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_new_city_type, null);
        final TextView txtError = dialogView.findViewById(R.id.txtError);
        final EditText edtxtShort = dialogView.findViewById(R.id.edtxtShort);
        final EditText edtxtName = dialogView.findViewById(R.id.edtxtName);
        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);

        txtError.setVisibility(View.INVISIBLE);
        builder.setView(dialogView).setMessage("Add a new city type");

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
                        // Создаём новый тип города
                        String shortName = edtxtShort.getText().toString();
                        String name = edtxtName.getText().toString();

                        if ((shortName.length() > 3) || (shortName.isEmpty())) {
                            txtError.setText("Bad short name");
                            txtError.startAnimation(animError);
                        } else if (name.isEmpty()) {
                            txtError.setText("Bad name");
                            txtError.startAnimation(animError);
                        } else {
                            CityType newCityType = new CityType(name, shortName);

                            // Получаем ссылку на Fragment
                            CityTypesFragment callingFragment = (CityTypesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("city_types");

                            // Передаём newRegion обратно в Fragment
                            callingFragment.createNewCityType(newCityType);

                            // Закрываем диалог
                            dismiss();
                        }
                    }
                }
        );

        return builder.create();
    }
}
