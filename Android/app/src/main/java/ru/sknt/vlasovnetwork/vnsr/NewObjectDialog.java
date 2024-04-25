package ru.sknt.vlasovnetwork.vnsr;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

public abstract class NewObjectDialog extends DialogFragment implements View.OnClickListener {
    private TextView mTxtError;
    private  Animation mAnimError;
    protected View mDialogView;

    protected abstract void setObjectsLists();
    protected abstract int getLayoutCode();
    protected abstract void getDataViews();
    protected abstract void setAdapters();
    protected abstract String getDialogMessageText();
    protected abstract void setData();
    protected abstract String getErrorText();
    protected abstract void createObject();

    public NewObjectDialog() {
        setObjectsLists();
    }

    @SuppressLint("InflateParams")
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
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        mDialogView = inflater.inflate(R.layout.create_layout, null);
        ConstraintLayout containerContent = mDialogView.findViewById(R.id.containerContent);
        inflater.inflate(getLayoutCode(), containerContent, true);
        mTxtError = mDialogView.findViewById(R.id.txtError);
        Button bttnAdd = mDialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = mDialogView.findViewById(R.id.bttnCancel);
        getDataViews();

        setAdapters();

        mTxtError.setVisibility(View.INVISIBLE);
        builder.setView(mDialogView).setMessage(getDialogMessageText());

        bttnCancel.setOnClickListener(this);
        bttnAdd.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bttnCancel) { dismiss(); }
        else if (view.getId() == R.id.bttnAdd) {
            setData();
            String error = getErrorText();
            if (!error.isEmpty()) {
                mTxtError.setText(error);
                mTxtError.startAnimation(mAnimError);
            } else {
                createObject();
                dismiss();
            }
        }
    }
}
