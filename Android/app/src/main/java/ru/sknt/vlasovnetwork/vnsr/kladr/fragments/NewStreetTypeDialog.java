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
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class NewStreetTypeDialog extends DialogFragment implements View.OnClickListener {
    private  Animation mAnimError;
    private TextView mTxtError;
    private EditText mEdtxtShort;
    private EditText mEdtxtName;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mAnimError = AnimationUtils.loadAnimation(getContext(),R.anim.error);
        mAnimError.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { mTxtError.setVisibility(View.VISIBLE); }
                    @Override
                    public void onAnimationEnd(Animation animation) { mTxtError.setVisibility(View.INVISIBLE); }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                }
        );
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_new_street_type, null);
        mTxtError = dialogView.findViewById(R.id.txtError);
        mEdtxtShort = dialogView.findViewById(R.id.edtxtShort);
        mEdtxtName = dialogView.findViewById(R.id.edtxtName);
        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);

        mTxtError.setVisibility(View.INVISIBLE);

        bttnCancel.setOnClickListener(this);
        bttnAdd.setOnClickListener(this);

        builder.setView(dialogView).setMessage("Add a new street type");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bttnCancel) { dismiss(); }
        else if (v.getId() == R.id.bttnAdd) {
            // Создаём новый тип улицы
            String shortName = mEdtxtShort.getText().toString();
            String name = mEdtxtName.getText().toString();

            if ((shortName.length() > 3) || (shortName.isEmpty())) {
                mTxtError.setText("Bad short name");
                mTxtError.startAnimation(mAnimError);
            } else if (name.isEmpty()) {
                mTxtError.setText("Bad name");
                mTxtError.startAnimation(mAnimError);
            } else {
                StreetType newStreetType = new StreetType(name, shortName);

                // Получаем ссылку на Fragment
                StreetTypesFragment callingFragment = (StreetTypesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("street_types");

                // Передаём newRegion обратно в Fragment
                callingFragment.createNewStreetType(newStreetType);

                // Закрываем диалог
                dismiss();
            }
        }
    }
}
