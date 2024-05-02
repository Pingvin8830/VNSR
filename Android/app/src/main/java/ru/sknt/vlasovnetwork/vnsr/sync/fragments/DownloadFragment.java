package ru.sknt.vlasovnetwork.vnsr.sync.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.sknt.vlasovnetwork.vnsr.FormatedDate;
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
import ru.sknt.vlasovnetwork.vnsr.sync.models.Task;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Way;

public class DownloadFragment extends Fragment implements View.OnClickListener, Response.ErrorListener {
    private RequestQueue mVolleyQueue;
    private TextView mTxtError;
    protected Map<String, Map<String, TextView>> mViews;
    protected Map<String, Map<String, Integer>> mValues;
    private final String[] mSyncObjects = {"StreetType", "CityType", "Street", "City", "Region", "Address", "Fuel", "FuelStation", "Refuel", "Travel", "Point"};
    private final String[] mSyncValues = {"Count", "Success", "Fail"};
    public int requestsCount = 0;
    protected int mSyncCount = -1;
    protected int mGetCount = 0;
    private SyncActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVolleyQueue = Volley.newRequestQueue(requireContext());
        mActivity = (SyncActivity) getActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.sync_progress, container, false);
        mTxtError = mainView.findViewById(R.id.txtError);
        mTxtError.setText("");

        TextView txtTitle = mainView.findViewById(R.id.txtTitle);
        txtTitle.setText(R.string.lbl_download);

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
                case "Travel":
                    tmpViews = createMapViews(mainView, R.id.txtTravelsCount, R.id.txtTravelsSuccess, R.id.txtTravelsFail);
                    break;
                case "Point":
                    tmpViews = createMapViews(mainView, R.id.txtPointsCount, R.id.txtPointsSuccess, R.id.txtPointsFail);
                    break;
            }
            for (String syncValue : mSyncValues) {
                tmpValues.put(syncValue, 0);
                Objects.requireNonNull(tmpViews.get(syncValue)).setText("0");
            }
            mViews.put(syncObject, tmpViews);
            mValues.put(syncObject, tmpValues);
            try {
                download(syncObject, "Count");
            } catch (JSONException e) {
                mTxtError.setText("Error get objects count for object " + syncObject);
            }
        }
        try {
            download("all", "Count");
        } catch (JSONException e) {
            mTxtError.setText("Error get all objects count");
        }
        return mainView;
    }
    @Override
    public void onClick(View view) {
        MainActivity.TaskDao.truncate();
        mGetCount = 0;
        MainActivity.TaskDao.truncate();
        for (String syncObject : mSyncObjects) {
            for (String syncValue : mSyncValues) {
                if (syncValue.equals("Count")) { continue; }
                Objects.requireNonNull(mValues.get(syncObject)).put(syncValue, 0);
                Objects.requireNonNull(Objects.requireNonNull(mViews.get(syncObject)).get(syncValue)).setText("0");
            }
        }
        for (String syncObject : mSyncObjects) {
            try {
                download(syncObject, "all");
            } catch (JSONException e) {
                this.mTxtError.setText("Error get objects " + syncObject);
            }
        }
    }
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        closeRequest();
        mTxtError.setText(volleyError.toString());
    }

    private Map<String, TextView> createMapViews(View view, int countId, int successId, int failId) {
        Map<String, TextView> res = new HashMap<>();
        res.put("Count", view.findViewById(countId));
        res.put("Success", view.findViewById(successId));
        res.put("Fail", view.findViewById(failId));
        return res;
    }
    private void download(String syncObject, String data) throws JSONException {
        JSONObject requestData = new JSONObject();
        requestData.put("object_name", syncObject);
        requestData.put("data", data);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                mActivity.getServerUrl() + "/sync/get",
                requestData,
                response -> {
                    closeRequest();
                    DownloadFragment fragment = (DownloadFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("download");
                    String syncObject1 = "";
                    String responseData = "";
                    try {
                        syncObject1 = response.getString("object_name");
                        responseData = response.getString("data");
                    } catch (JSONException e) {
                        assert fragment != null;
                        fragment.mTxtError.setText("Error parse JSON response");
                    }

                    switch (responseData) {
                        case "Count":
                            int valueInt = -1;
                            try {
                                valueInt = response.getInt("value");
                                syncObject1 = response.getString("object_name");
                            } catch (JSONException e) {
                                assert fragment != null;
                                fragment.mTxtError.setText("Error parse response json");
                            }
                            assert fragment != null;
                            if (syncObject1.equals("all")) {
                                fragment.mSyncCount = valueInt; }
                            else {
                                Objects.requireNonNull(fragment.mValues.get(syncObject1)).put("Count", valueInt);
                                Objects.requireNonNull(Objects.requireNonNull(fragment.mViews.get(syncObject1)).get("Count")).setText(String.valueOf(valueInt));
                            }
                            break;
                        case "all":
                            JSONArray values = new JSONArray();
                            try {
                                values = response.getJSONArray("value");
                            } catch (JSONException e) {
                                assert fragment != null;
                                fragment.mTxtError.setText("Error find values");
                            }
                            for (int i=0; i< values.length(); i++) {
                                JSONObject valueJson = new JSONObject();
                                try {
                                    valueJson = values.getJSONObject(i);
                                } catch (JSONException e) {
                                    assert fragment != null;
                                    fragment.mTxtError.setText("Error parse ids from json");
                                }
                                int objectId = -1;
                                try {
                                    objectId = valueJson.getInt("id");
                                } catch (JSONException e) {
                                    assert fragment != null;
                                    fragment.mTxtError.setText("Error parse id from json");
                                }
                                for (Iterator<String> it = valueJson.keys(); it.hasNext(); ) {
                                    String field = it.next();
                                    String objectValue = "";
                                    try {
                                        objectValue = valueJson.getString(field);
                                    } catch (JSONException e) {
                                        assert fragment != null;
                                        fragment.mTxtError.setText("Error parse field from json");
                                    }
                                    Task task = new Task(syncObject1, objectId, field, objectValue);
                                    MainActivity.TaskDao.create(task);
                                }
                                assert fragment != null;
                                fragment.mGetCount++;
                                int tmp = Objects.requireNonNull(fragment.mValues.get(syncObject1)).get("Success");
                                tmp++;
                                Objects.requireNonNull(fragment.mValues.get(syncObject1)).put("Success", tmp);
                                Objects.requireNonNull(Objects.requireNonNull(fragment.mViews.get(syncObject1)).get("Success")).setText(String.valueOf(tmp));
                            }
                    }
                    assert fragment != null;
                    if ((fragment.mGetCount == fragment.mSyncCount) && fragment.mGetCount > 0) {
                        try {
                            startSync();
                            fragment.mTxtError.setText("Sync successed");
                        } catch (JSONException e) {
                            fragment.mTxtError.setText("Sync failed");
                        }
                    }
                },
                this
        );
        addRequest(request);
    }
    private void closeRequest() {
        this.requestsCount--;
        mTxtError.setText("Active requests: " + this.requestsCount);
    }
    private void addRequest(JsonObjectRequest request) {
        this.requestsCount++;
        mTxtError.setText("Active requests: " + this.requestsCount);
        mVolleyQueue.add(request);
    }
    private void startSync() throws JSONException {
        Map<String, List<Integer>> distinctObjects = new HashMap<>();
        for (Task task : MainActivity.TaskDao.getAll()) {
            String syncObject = task.getObjectName();
            int objectId = task.getObjectId();

            List<Integer> ids = distinctObjects.get(task.getObjectName());
            try {
                if (!ids.contains(objectId)) { ids.add(objectId); } }
            catch (NullPointerException e) {
                ids = new ArrayList<>();
                ids.add(objectId);
            }

            distinctObjects.put(syncObject, ids);
        }
        for (String syncObject : mSyncObjects) {
            List<Integer> ids = distinctObjects.get(syncObject);
            assert ids != null;
            for (int id : ids) {
                JSONObject target = new JSONObject();
                target.put("object_name", syncObject).put("object_id", id);
                List<Task> tasks = MainActivity.TaskDao.filter(syncObject, id);
                for (Task task : tasks) { target.put(task.getField(), task.getValue()); }
                JSONObject findDateTimeJson;
                FormatedDate findDateTime;
                switch (syncObject) {
                    case "StreetType":
                        StreetType streetType = new StreetType(target);
                        StreetType findStreetType = MainActivity.StreetTypeDao.find(target.getString("name"));
                        if (findStreetType == null) { MainActivity.StreetTypeDao.create(streetType); }
                        break;
                    case "CityType":
                        CityType cityType = new CityType(target);
                        CityType findCityType = MainActivity.CityTypeDao.find(target.getString("name"));
                        if (findCityType == null) { MainActivity.CityTypeDao.create(cityType); }
                        break;
                    case "Region":
                        Region region = new Region(target);
                        Region findRegion = MainActivity.RegionDao.find(target.getString("code"));
                        if (findRegion == null) { MainActivity.RegionDao.create(region); }
                        break;
                    case "Street":
                        Street street = new Street(target);
                        JSONObject findStreetTypeJson = new JSONObject(target.getString("street_type"));
                        Street findStreet = MainActivity.StreetDao.find(target.getString("name"), findStreetTypeJson.getString("name"));
                        if (findStreet == null) { MainActivity.StreetDao.create(street); }
                        break;
                    case "City":
                        City city = new City(target);
                        City findCity = MainActivity.CityDao.find(target.getString("name"));
                        if (findCity == null) { MainActivity.CityDao.create(city); }
                        break;
                    case "Address":
                        Address address = new Address(target);
                        Address findAddress = MainActivity.AddressDao.find(target.getString("name"));
                        if (findAddress == null) { MainActivity.AddressDao.create(address); }
                        break;
                    case "Fuel":
                        Fuel fuel = new Fuel(target);
                        Fuel findFuel = MainActivity.FuelDao.find(target.getString("name"));
                        if (findFuel == null) { MainActivity.FuelDao.create(fuel); }
                        break;
                    case "FuelStation":
                        FuelStation fuelStation = new FuelStation(target);
                        FuelStation findFuelStation = MainActivity.FuelStationDao.find(target.getString("company"), target.getString("number"));
                        if (findFuelStation == null) { MainActivity.FuelStationDao.create(fuelStation); }
                        break;
                    case "Refuel":
                        Refuel refuel = new Refuel(target);
                        findDateTimeJson = new JSONObject(target.getString("datetime"));
                        findDateTime = new FormatedDate(findDateTimeJson);
                        Refuel findRefuel = MainActivity.RefuelDao.find(findDateTime);
                        if (findRefuel == null) { MainActivity.RefuelDao.create(refuel); }
                        break;
                    case "Travel":
                        Travel travel = new Travel(target);
                        Travel findTravel = MainActivity.TravelDao.find(target.getString("name"));
                        if (findTravel == null) { MainActivity.TravelDao.create(travel); }
                        break;
                    case "Point":
                        Point point = new Point(target);

                        JSONObject findArrivalDateTimeJson;
                        JSONObject findDepartureDateTimeJson;
                        FormatedDate findArrivalDateTime;
                        FormatedDate findDepartureDateTime;

                        try { findArrivalDateTimeJson = new JSONObject(target.getString("arrival_datetime")); }
                        catch (JSONException e) { findArrivalDateTimeJson = null; }
                        try { findDepartureDateTimeJson = new JSONObject(target.getString("departure_datetime")); }
                        catch (JSONException e) { findDepartureDateTimeJson = null; }

                        if (findArrivalDateTimeJson == null) { findArrivalDateTime = new FormatedDate(0L); }
                        else { findArrivalDateTime = new FormatedDate(findArrivalDateTimeJson); }
                        if (findDepartureDateTimeJson == null) { findDepartureDateTime = new FormatedDate(0L); }
                        else { findDepartureDateTime = new FormatedDate(findDepartureDateTimeJson); }

                        Point findPoint = MainActivity.PointDao.find(findArrivalDateTime.getTime(), findDepartureDateTime.getTime());
                        if (findPoint == null) { MainActivity.PointDao.create(point); }
                        break;
                }
            }
        }
    }
}
