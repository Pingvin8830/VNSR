package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class NewStreetDialog extends DialogFragment implements View.OnClickListener {
    private TextView mTxtError;
    private Animation mAnimError;
    private EditText mEdtxtName;
    private Spinner mSpnType;
    private final StreetTypeDao mStreetTypeDao;

    public NewStreetDialog(StreetTypeDao dao) { mStreetTypeDao = dao; }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mAnimError = AnimationUtils.loadAnimation(getContext(),R.anim.error);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_new_street, null);
        mTxtError = dialogView.findViewById(R.id.txtError);
        mEdtxtName = dialogView.findViewById(R.id.edtxtName);
        mSpnType = dialogView.findViewById(R.id.spnType);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mStreetTypeDao.getAllNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnType.setAdapter(adapter);

        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);

        mTxtError.setVisibility(View.INVISIBLE);
        builder.setView(dialogView).setMessage("Add a new street");

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

        bttnCancel.setOnClickListener(this);
        bttnAdd.setOnClickListener(this);

        return builder.create();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.bttnCancel) { dismiss(); }
        else if (v.getId() == R.id.bttnAdd) {
            // Создаём новую улицу
            String name = mEdtxtName.getText().toString();
            StreetType streetType = mStreetTypeDao.find(mSpnType.getSelectedItem().toString());

            if (name.isEmpty()) {
                mTxtError.setText("Bad name");
                mTxtError.startAnimation(mAnimError);
            } else {
                Street newStreet = new Street(streetType.getId(), name);

                // Получаем ссылку на Fragment
                StreetsFragment callingFragment = (StreetsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("streets");

                // Передаём newStreet обратно в Fragment
                callingFragment.createNewStreet(newStreet);

                // Закрываем диалог
                dismiss();
            }
        }
    }
}