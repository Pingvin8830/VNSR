package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class NewStreetDialog extends NewObjectDialog {
    private List<StreetType> mStreetTypes;
    private EditText mEdtxtName;
    private Spinner mSpnStreetType;
    private String mName;
    private StreetType mStreetType;

    @Override
    protected void setObjectsLists() { mStreetTypes = MainActivity.StreetTypeDao.getAll(); }

    @Override
    protected int getLayoutCode() {
        return R.layout.kladr_new_street;
    }

    @Override
    protected void getDataViews() {
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
        mSpnStreetType = mDialogView.findViewById(R.id.spnType);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<StreetType> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mStreetTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnStreetType.setAdapter(adapter);
    }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_new_street);
    }

    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mStreetType = mStreetTypes.get(mSpnStreetType.getSelectedItemPosition());
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mName.isEmpty()) { error = getResources().getString(R.string.err_bad_name); }
        return error;
    }

    @Override
    protected void createObject() {
        Street street = new Street(mName, mStreetType);
        StreetsFragment callingFragment = (StreetsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("streets");
        assert callingFragment != null;
        callingFragment.createNewStreet(street);
    }
}