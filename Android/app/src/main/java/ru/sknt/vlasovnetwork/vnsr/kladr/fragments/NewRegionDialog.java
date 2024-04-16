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

public class NewRegionDialog extends DialogFragment implements View.OnClickListener {
    private Animation mAnimError;
    private TextView mTxtError;
    private EditText mEdtxtCode;
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
        View dialogView = inflater.inflate(R.layout.kladr_new_region, null);
        mTxtError = dialogView.findViewById(R.id.txtError);
        mEdtxtCode = dialogView.findViewById(R.id.edtxtCode);
        mEdtxtName = dialogView.findViewById(R.id.edtxtName);
        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);

        mTxtError.setVisibility(View.INVISIBLE);

        bttnCancel.setOnClickListener(this);
        bttnAdd.setOnClickListener(this);

        builder.setView(dialogView).setMessage("Add a new region");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bttnCancel) { dismiss(); }
        else if (v.getId() == R.id.bttnAdd) {
            // Создаём новый регион
            String code = mEdtxtCode.getText().toString();
            String name = mEdtxtName.getText().toString();

            if ((code.length() > 3) || (code.isEmpty())) {
                mTxtError.setText("Bad code");
                mTxtError.startAnimation(mAnimError);
            } else if (name.isEmpty()) {
                mTxtError.setText("Bad name");
                mTxtError.startAnimation(mAnimError);
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
}
