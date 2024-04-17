package ru.sknt.vlasovnetwork.vnsr;

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
/*public class NewAddressDialog extends NewObjectDialog {
    private EditText mEdtxtName;
    private Spinner mSpnRegion;
    private Spinner mSpnCity;
    private Spinner mSpnStreet;
    private EditText mEdtxtHome;
    private EditText mEdtxtBuilding;
    private EditText mEdtxtFlat;
    private final StreetDao mStreetDao;
    private final List<Region> mRegions;
    private final List<City> mCityes;
    private final List<Street> mStreets;

    @Override
    protected void setObjectsLists() {
        mRegions = MainActivity.RegionDao.getAll();
        mCityes = MainActivity.CityDao.getAll();
        mStreets = MainActivity.StreetDao.getAll();
        for (City city : mCityes) { city.setCityType(); }
        for (Street street : mStreets) { street.setStreetType(); }
    }

    @Override
    protected int getLayoutCode() { return R.layout.kladr_new_address; }

    @Override
    protected void getDataViews() {
        mEdtxtName = dialogView.findViewById(R.id.edtxtName);
        mSpnRegion = dialogView.findViewById(R.id.spnRegion);
        mSpnCity = dialogView.findViewById(R.id.spnCity);
        mSpnStreet = dialogView.findViewById(R.id.spnStreet);
        mEdtxtHome = dialogView.findViewById(R.id.edtxtHome);
        mEdtxtBuilding = dialogView.findViewById(R.id.edtxtBuilding);
        mEdtxtFlat = dialogView.findViewById(R.id.edtxtFlat);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<Region> regionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mRegions);
        ArrayAdapter<City> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mCityes);
        ArrayAdapter<Street> streetAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mStreets);

        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        streetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpnRegion.setAdapter(regionAdapter);
        mSpnCity.setAdapter(cityAdapter);
        mSpnStreet.setAdapter(streetAdapter);
    }

    @Override
    protected String getDialogMessageText() { return "Add a new address"; }


    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mRegion = mRegions.get(mSpnRegion.getSelectedItemPosition());
        mCity = mCityes.get(mSpnCity.getSelectedItemPosition());
        mStreet = mStreets.get(mSpnStreet.getSelectedItemPosition());
        mHome = mEdtxtHome.getText().toString();
        mBuilding = mEdtxtBuilding.getText().toString();
        mFlatString = mEdtxtFlat.getText().toString();
        if (mFlatString.isEmpty()) { mFlat = 0; }
        else { mFlat = Integer.parseInt(mFlatString); }
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mName.isEmpty()) { error = "Bad name"; }
        else if (home.isEmpty()) { error = "Bad home"; }
        return error;
    }

    @Override
    protected void createObject() {
        Address newAddress = new Address(mName, mRegion, mCity, mStreet, mHome, mBuilding, mFlat);
        AddressesFragment callingFragment = (AddressesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("addresses");
        callingFragment.createNewAddress(newAddress);
    }
}
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

        import java.util.List;

        import ru.sknt.vlasovnetwork.vnsr.R;
        import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
        import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
        import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class NewCityDialog extends DialogFragment implements View.OnClickListener {
    private TextView mTxtError;
    private Animation mAnimError;
    private EditText mEdtxtName;
    private Spinner mSpnType;
    private final List<CityType> mCityTypes;

    public NewCityDialog(CityTypeDao dao) {
        mCityTypes = dao.getAll();
    }

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
        View dialogView = inflater.inflate(R.layout.kladr_new_city, null);
        mTxtError = dialogView.findViewById(R.id.txtError);
        mEdtxtName = dialogView.findViewById(R.id.edtxtName);
        mSpnType = dialogView.findViewById(R.id.spnType);
        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);

        ArrayAdapter<CityType> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mCityTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnType.setAdapter(adapter);

        mTxtError.setVisibility(View.INVISIBLE);
        bttnCancel.setOnClickListener(this);
        bttnAdd.setOnClickListener(this);

        builder.setView(dialogView).setMessage("Add a new city");
        return builder.create();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.bttnCancel) { dismiss(); }
        else if (v.getId() == R.id.bttnAdd) {
            // Создаём новый город
            String name = mEdtxtName.getText().toString();
            CityType cityType = mCityTypes.get(mSpnType.getSelectedItemPosition());

            if (name.isEmpty()) {
                mTxtError.setText("Bad name");
                mTxtError.startAnimation(mAnimError);
            } else {
                City newCity = new City(name, cityType);

                // Получаем ссылку на Fragment
                CityesFragment callingFragment = (CityesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("cityes");

                // Передаём newCity обратно в Fragment
                callingFragment.createNewCity(newCity);

                // Закрываем диалог
                dismiss();
            }
        }
    }
}
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

public class NewCityTypeDialog extends DialogFragment implements View.OnClickListener {
    private Animation mAnimError;
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
        View dialogView = inflater.inflate(R.layout.kladr_new_city_type, null);
        mTxtError = dialogView.findViewById(R.id.txtError);
        mEdtxtShort = dialogView.findViewById(R.id.edtxtShort);
        mEdtxtName = dialogView.findViewById(R.id.edtxtName);
        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);

        mTxtError.setVisibility(View.INVISIBLE);

        bttnCancel.setOnClickListener(this);
        bttnAdd.setOnClickListener(this);

        builder.setView(dialogView).setMessage("Add a new city type");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bttnCancel) { dismiss(); }
        else if (v.getId() == R.id.bttnAdd) {
            // Создаём новый тип города
            String shortName = mEdtxtShort.getText().toString();
            String name = mEdtxtName.getText().toString();

            if ((shortName.length() > 3) || (shortName.isEmpty())) {
                mTxtError.setText("Bad short name");
                mTxtError.startAnimation(mAnimError);
            } else if (name.isEmpty()) {
                mTxtError.setText("Bad name");
                mTxtError.startAnimation(mAnimError);
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
}
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

        import java.util.List;

        import ru.sknt.vlasovnetwork.vnsr.R;
        import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
        import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;
        import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class NewStreetDialog extends DialogFragment implements View.OnClickListener {
    private TextView mTxtError;
    private Animation mAnimError;
    private EditText mEdtxtName;
    private Spinner mSpnType;
    private final List<StreetType> mStreetTypes;

    public NewStreetDialog(StreetTypeDao dao) {
        mStreetTypes = dao.getAll();
    }

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
        View dialogView = inflater.inflate(R.layout.kladr_new_street, null);
        mTxtError = dialogView.findViewById(R.id.txtError);
        mEdtxtName = dialogView.findViewById(R.id.edtxtName);
        mSpnType = dialogView.findViewById(R.id.spnType);
        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);

        ArrayAdapter<StreetType> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mStreetTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnType.setAdapter(adapter);

        mTxtError.setVisibility(View.INVISIBLE);

        bttnCancel.setOnClickListener(this);
        bttnAdd.setOnClickListener(this);

        builder.setView(dialogView).setMessage("Add a new street");
        return builder.create();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.bttnCancel) { dismiss(); }
        else if (v.getId() == R.id.bttnAdd) {
            // Создаём новую улицу
            String name = mEdtxtName.getText().toString();
            StreetType streetType = mStreetTypes.get(mSpnType.getSelectedItemPosition());

            if (name.isEmpty()) {
                mTxtError.setText("Bad name");
                mTxtError.startAnimation(mAnimError);
            } else {
                Street newStreet = new Street(streetType, name);

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
*/