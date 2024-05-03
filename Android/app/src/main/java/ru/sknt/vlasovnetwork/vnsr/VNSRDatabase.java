package ru.sknt.vlasovnetwork.vnsr;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ru.sknt.vlasovnetwork.vnsr.car.daos.FuelDao;
import ru.sknt.vlasovnetwork.vnsr.car.daos.FuelStationDao;
import ru.sknt.vlasovnetwork.vnsr.car.daos.RefuelDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.AddressDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.RegionDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.HotelDao;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.PointDao;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.TollRoadDao;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.TravelDao;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.WayDao;
import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;
import ru.sknt.vlasovnetwork.vnsr.car.models.Refuel;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Hotel;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TollRoad;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Way;
import ru.sknt.vlasovnetwork.vnsr.sync.daos.TaskDao;
import ru.sknt.vlasovnetwork.vnsr.sync.models.Task;

@Database(
        version = 7,
        entities = {
                Travel.class,
                TollRoad.class,
                Way.class,
                Hotel.class,
                Point.class,
                FuelStation.class,
                Fuel.class,
                Refuel.class,
                Region.class,
                CityType.class,
                City.class,
                StreetType.class,
                Street.class,
                Address.class,
                Task.class
        }//,
        //autoMigrations = {
        //        @AutoMigration(from=6, to=7)
        //}
)
@TypeConverters({Converters.class})
public abstract class VNSRDatabase extends RoomDatabase {
    public abstract TravelDao travelDao();
    public abstract TollRoadDao toolRoadDao();
    public abstract WayDao wayDao();
    public abstract HotelDao hotelDao();
    public abstract PointDao pointDao();
    public abstract FuelStationDao fuelStationDao();
    public abstract FuelDao fuelDao();
    public abstract RefuelDao refuelDao();
    public abstract RegionDao regionDao();
    public abstract CityTypeDao cityTypeDao();
    public abstract CityDao cityDao();
    public abstract StreetTypeDao streetTypeDao();
    public abstract StreetDao streetDao();
    public abstract AddressDao addressDao();
    public abstract TaskDao taskDao();
}
