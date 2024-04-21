package ru.sknt.vlasovnetwork.vnsr.sync.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;
import ru.sknt.vlasovnetwork.vnsr.car.models.Refuel;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;
import ru.sknt.vlasovnetwork.vnsr.sync.SyncActivity;

public class UploadFragment extends Fragment implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private RequestQueue mVolleyQueue;
    private TextView mTxtError;
    protected Map<String, Map<String, TextView>> mViews;
    protected Map<String, Map<String, Integer>> mValues;
    private String[] mSyncObjects = {"StreetType", "CityType", "Street", "City", "Region", "Address", "Fuel", "FuelStation", "Refuel"};
    private String[] mSyncValues = {"Count", "Success", "Fail"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVolleyQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.upload, container, false);
        mTxtError = mainView.findViewById(R.id.txtError);
        Button bttnStart = mainView.findViewById(R.id.bttnStart);
        bttnStart.setOnClickListener(this);

        Map<String, TextView> tmpViews;
        Map<String, Integer> tmpValues;
        mViews = new HashMap<>();
        mValues = new HashMap<>();

        for (String syncObject : mSyncObjects) {
            tmpViews = new HashMap<>();
            tmpValues = new HashMap<>();
            switch (syncObject) {
                case "StreetType":
                    tmpViews = createMapViews(mainView, R.id.txtStreetTypesCount, R.id.txtStreetTypesSuccess, R.id.txtStreetTypesFail);
                    break;
                case "CityType":
                    tmpViews = createMapViews(mainView, R.id.txtCityTypesCount, R.id.txtCityTypesSuccess, R.id.txtCityTypesFail);
                    break;
                case "Street":
                    tmpViews = createMapViews(mainView, R.id.txtStreetsCount, R.id.txtStreetsSuccess, R.id.txtStreetsFail);
                    break;
                case "City":
                    tmpViews = createMapViews(mainView, R.id.txtCityesCount, R.id.txtCityesSuccess, R.id.txtCityesFail);
                    break;
                case "Region":
                    tmpViews = createMapViews(mainView, R.id.txtRegionsCount, R.id.txtRegionsSuccess, R.id.txtRegionsFail);
                    break;
                case "Address":
                    tmpViews = createMapViews(mainView, R.id.txtAddressesCount, R.id.txtAddressesSuccess, R.id.txtAddressesFail);
                    break;
                case "Fuel":
                    tmpViews = createMapViews(mainView, R.id.txtFuelsCount, R.id.txtFuelsSuccess, R.id.txtFuelsFail);
                    break;
                case "FuelStation":
                    tmpViews = createMapViews(mainView, R.id.txtFuelStationsCount, R.id.txtFuelStationsSuccess, R.id.txtFuelStationsFail);
                    break;
                case "Refuel":
                    tmpViews = createMapViews(mainView, R.id.txtRefuelsCount, R.id.txtRefuelsSuccess, R.id.txtRefuelsFail);
                    break;
            }
            for (String syncValue : mSyncValues) {
                int value = 0;
                if (syncValue == "Count") {
                    switch (syncObject) {
                        case "StreetType":
                            value = MainActivity.StreetTypeDao.getCount();
                            break;
                        case "CityType":
                            value = MainActivity.CityTypeDao.getCount();
                            break;
                        case "Region":
                            value = MainActivity.RegionDao.getCount();
                            break;
                        case "Street":
                            value = MainActivity.StreetDao.getCount();
                            break;
                        case "City":
                            value = MainActivity.CityDao.getCount();
                            break;
                        case "Address":
                            value = MainActivity.AddressDao.getCount();
                            break;
                        case "Fuel":
                            value = MainActivity.FuelDao.getCount();
                            break;
                        case "FuelStation":
                            value = MainActivity.FuelStationDao.getCount();
                            break;
                        case "Refuel":
                            value = MainActivity.RefuelDao.getCount();
                            break;
                    }
                }
                tmpValues.put(syncValue, value);
                tmpViews.get(syncValue).setText(String.valueOf(value));
            }
            mViews.put(syncObject, tmpViews);
            mValues.put(syncObject, tmpValues);
        }
        return mainView;
    }
    @Override
    public void onClick(View view) {
        for (String syncObject : mSyncObjects) {
            for (String syncValue : mSyncValues) {
                if (syncValue.equals("Count")) { continue; }
                mValues.get(syncObject).put(syncValue, 0);
                mViews.get(syncObject).get(syncValue).setText("0");
            }
        }
        try {
            uploadStreetTypes();
            uploadCityTypes();
            uploadRegions();
            uploadStreets();
            uploadCityes();
            uploadAddresses();
            uploadFuels();
            uploadFuelStations();
            uploadRefuels();
        } catch (JSONException e) {
            mTxtError.setText("Error create json data");
        }
    }
    @Override
    public void onResponse(JSONObject response) {
        try {
            String syncObject = response.getString("object");
            TextView successedView = mViews.get(syncObject).get("Success");
            int successedValue = mValues.get(syncObject).get("Success");
            Log.i("VNSR DEBUG", "successedValue: " + successedValue);
            successedValue++;
            mValues.get(syncObject).put("Success", successedValue);
            successedView.setText(String.valueOf(successedValue));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        mTxtError.setText("" + response);
    }
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Toast.makeText(getContext(), "error2: " + volleyError, Toast.LENGTH_SHORT).show();
        Log.i("TESTS DEBUG", "error: " + volleyError);
    }

    private Map<String, TextView> createMapViews(View view, int countId, int successId, int failId) {
        Map<String, TextView> res = new HashMap<>();
        res.put("Count", view.findViewById(countId));
        res.put("Success", view.findViewById(successId));
        res.put("Fail", view.findViewById(failId));
        return res;
    }

    private void send(JSONObject data) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                SyncActivity.getUrl(),
                data,
                this,
                this
        );
        mVolleyQueue.add(request);

    }
    private void uploadStreetTypes() throws JSONException {
        for (StreetType streetType : MainActivity.StreetTypeDao.getAll()) { send(streetType.toJson()); }
    }
    private void uploadCityTypes() throws JSONException {
        for (CityType cityType : MainActivity.CityTypeDao.getAll()) { send(cityType.toJson()); }
    }
    private void uploadRegions() throws JSONException {
        for (Region region : MainActivity.RegionDao.getAll()) { send(region.toJson()); }
    }
    private void uploadStreets() throws JSONException {
        for (Street street : MainActivity.StreetDao.getAll()) { send(street.toJson()); }
    }
    private void uploadCityes() throws JSONException {
        for (City city : MainActivity.CityDao.getAll()) { send(city.toJson()); }
    }
    private void uploadAddresses() throws JSONException {
        for (Address address : MainActivity.AddressDao.getAll()) { send(address.toJson()); }
    }
    private void uploadFuels() throws JSONException {
        for (Fuel fuel : MainActivity.FuelDao.getAll()) { send(fuel.toJson()); }
    }
    private void uploadFuelStations() throws JSONException {
        for (FuelStation fuelStation : MainActivity.FuelStationDao.getAll()) { send(fuelStation.toJson()); }
    }
    private void uploadRefuels() throws JSONException {
        for (Refuel refuel : MainActivity.RefuelDao.getAll()) { send(refuel.toJson()); }
    }
}
