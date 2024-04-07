package ru.sknt.vlasovnetwork.vnsr;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.daos.car.RefuelDao;
import ru.sknt.vlasovnetwork.vnsr.daos.kladr.CityDao;
import ru.sknt.vlasovnetwork.vnsr.daos.kladr.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.daos.kladr.RegionDao;
import ru.sknt.vlasovnetwork.vnsr.daos.kladr.StreetDao;
import ru.sknt.vlasovnetwork.vnsr.daos.travels.ToolRoadDao;
import ru.sknt.vlasovnetwork.vnsr.daos.travels.TravelDao;
import ru.sknt.vlasovnetwork.vnsr.daos.travels.WayDao;
import ru.sknt.vlasovnetwork.vnsr.models.car.Refuel;
import ru.sknt.vlasovnetwork.vnsr.models.kladr.City;
import ru.sknt.vlasovnetwork.vnsr.models.kladr.CityType;
import ru.sknt.vlasovnetwork.vnsr.models.kladr.Region;
import ru.sknt.vlasovnetwork.vnsr.models.kladr.Street;
import ru.sknt.vlasovnetwork.vnsr.models.travels.ToolRoad;
import ru.sknt.vlasovnetwork.vnsr.models.travels.Travel;
import ru.sknt.vlasovnetwork.vnsr.models.travels.TravelState;
import ru.sknt.vlasovnetwork.vnsr.daos.travels.TravelStateDao;
import ru.sknt.vlasovnetwork.vnsr.models.travels.Way;

public class MainActivity extends AppCompatActivity {
    private VNSRDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getApplicationContext(), VNSRDatabase.class, "vnsr-db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        setContentView(R.layout.activity_main);

        TravelStateDao travelStateDao = db.travelStateDao();
        TravelDao travelDao = db.travelDao();
        ToolRoadDao toolRoadDao = db.toolRoadDao();
        WayDao wayDao = db.wayDao();
        RefuelDao refuelDao = db.refuelDao();
        RegionDao regionDao = db.regionDao();
        StreetDao streetDao = db.streetDao();
        CityTypeDao cityTypeDao = db.cityTypeDao();
        CityDao cityDao = db.cityDao();

        travelStateDao.truncate();
        travelDao.truncate();
        toolRoadDao.truncate();
        wayDao.truncate();
        refuelDao.truncate();
        regionDao.truncate();
        streetDao.truncate();
        cityTypeDao.truncate();
        cityDao.truncate();

        TextView txtTravelStates = findViewById(R.id.txtTravelStates);
        TextView txtTravels = findViewById(R.id.txtTravels);
        TextView txtToolRoads = findViewById(R.id.txtToolRoads);
        TextView txtWays = findViewById(R.id.txtWays);
        TextView txtRefuels = findViewById(R.id.txtRefuels);
        TextView txtRegions = findViewById(R.id.txtRegions);
        TextView txtStreets = findViewById(R.id.txtStreets);
        TextView txtCityTypes = findViewById(R.id.txtCityTypes);
        TextView txtCityes = findViewById(R.id.txtCityes);

        TravelState travelState = new TravelState("Doing");
        Travel travel = new Travel("Sea 2024", "Anton, Axenia, Maksim", travelState.getId(), 10f, 57.08f);
        ToolRoad toolRoad = new ToolRoad(travel.getId(), "M11", "SPb", "MSK", 2000f);
        Way way = new Way(travel.getId(), 1, 1, 100.5f);
        Refuel refuel = new Refuel(1, 1, 2, 1, 1, 35.2f, 57.07f, 1005.68f, 500, 10.0f, 10569, 300f, 11.5f, "15:24");
        Region region = new Region("78", "Saint-Petersburg");
        Street street = new Street(1, "Otech");
        CityType cityType = new CityType("City", "ct");
        City city = new City("SPb", cityType.getId());

        travelStateDao.create(travelState);
        travelStateDao.create(new TravelState("Plaining"));
        travelDao.create(travel);
        travelDao.create(new Travel("Test travel", "Anton", travelState.getId(), 10f, 57.08f));
        toolRoadDao.create(toolRoad);
        toolRoadDao.create(new ToolRoad(travel.getId(), "Test tool road", "Start", "End", 10.5f));
        wayDao.create(way);
        wayDao.create(new Way(travel.getId(), 1, 1, 100.5f));
        refuelDao.create(refuel);
        refuelDao.create(new Refuel(1, 1, 1, 1, 1, 1f, 1f, 1f, 1, 1f, 1, 1f, 1f, "1"));
        regionDao.create(region);
        regionDao.create(new Region("98", "Moscow"));
        streetDao.create(street);
        streetDao.create(new Street(2, "Test street"));
        cityTypeDao.create(cityType);
        cityTypeDao.create(new CityType("Test city type", "tc"));
        cityDao.create(city);
        cityDao.create(new City("Test city", 2));

        List<TravelState> travelStates = travelStateDao.getAll();
        List<Travel> travels = travelDao.getAll();
        List<ToolRoad> toolRoads = toolRoadDao.getAll();
        List<Way> ways = wayDao.getAll();
        List<Refuel> refuels = refuelDao.getAll();
        List<Region> regions = regionDao.getAll();
        List<Street> streets = streetDao.getAll();
        List<CityType> cityTypes = cityTypeDao.getAll();
        List<City> cityes = cityDao.getAll();

        txtTravelStates.setText("TravelStates:\n");
        if (travelStates.size() > 0) {
            for (TravelState ts : travelStates) {
                txtTravelStates.setText(txtTravelStates.getText().toString() + ts.getId() + " - " + ts.getName() + "\n");
            }
        } else {
            txtTravelStates.setText("Have not travel states");
        }
        txtTravels.setText("Travels:\n");
        if (travels.size() > 0) {
            for (Travel t : travels) {
                txtTravels.setText(txtTravels.getText().toString() + t.getId() + " - " + t.getName() + "\n");
            }
        } else {
            txtTravels.setText("Have not travels");
        }
        txtToolRoads.setText("Tool roads:\n");
        if (toolRoads.size() > 0) {
            for (ToolRoad tr : toolRoads) {
                txtToolRoads.setText(txtToolRoads.getText().toString() + tr.getId() + " - " + tr.getName() + "\n");
            }
        } else {
            txtToolRoads.setText("Have not tool roads");
        }
        txtWays.setText("Ways:\n");
        if (ways.size() > 0) {
            for (Way w : ways) {
                txtWays.setText(txtWays.getText().toString() + w.getId() + " - " + w.getStartPointId() + "\n");
            }
        } else {
            txtWays.setText("Have not ways");
        }
        txtRefuels.setText("Refuels:\n");
        if (refuels.size() > 0) {
            for (Refuel r : refuels) {
                txtRefuels.setText(txtRefuels.getText().toString() + r.getId() + " - " + r.getDateTime() + "\n");
            }
        } else {
            txtRefuels.setText("Have not refuels");
        }
        txtRegions.setText("Regions:\n");
        if (regions.size() > 0) {
            for (Region r : regions) {
                txtRegions.setText(txtRegions.getText().toString() + r.getId() + " - " + r.getName() + "\n");
            }
        } else {
            txtRegions.setText("Have not regions");
        }
        txtStreets.setText("Streets:\n");
        if (streets.size() > 0) {
            for (Street s : streets) {
                txtStreets.setText(txtStreets.getText().toString() + s.getId() + " - " + s.getName() + "\n");
            }
        } else {
            txtStreets.setText("Have not streets");
        }
        txtCityTypes.setText("CityTypes:\n");
        if (cityTypes.size() > 0) {
            for (CityType ct : cityTypes) {
                txtCityTypes.setText(txtCityTypes.getText().toString() + ct.getId() + " - " + ct.getName() + "\n");
            }
        } else {
            txtCityTypes.setText("Have not city types");
        }
        txtCityes.setText("Cityes:\n");
        if (cityes.size() > 0) {
            for (City c : cityes) {
                txtCityes.setText(txtCityes.getText().toString() + c.getId() + " - " + c.getName() + "\n");
            }
        } else {
            txtCityes.setText("Have not cityes");
        }

        travelState = travelStateDao.find("Plaining");
        travel = travelDao.find("Test travel");
        toolRoad = toolRoadDao.find(1);
        way = wayDao.find(1);
        refuel = refuelDao.find(2l);
        region = regionDao.find("78");
        street = streetDao.find(1);
        cityType = cityTypeDao.find("City");
        city = cityDao.find("SPb");

        Toast.makeText(this, travelState.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, travel.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf(toolRoad), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf(way), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf(refuel.getDateTime()), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, region.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, street.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, cityType.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, city.getName(), Toast.LENGTH_SHORT).show();
    }
}