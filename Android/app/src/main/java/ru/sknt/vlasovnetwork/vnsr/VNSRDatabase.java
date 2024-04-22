package ru.sknt.vlasovnetwork.vnsr;

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
import ru.sknt.vlasovnetwork.vnsr.daos.travels.HotelDao;
import ru.sknt.vlasovnetwork.vnsr.daos.travels.PointDao;
import ru.sknt.vlasovnetwork.vnsr.daos.travels.ToolRoadDao;
import ru.sknt.vlasovnetwork.vnsr.daos.travels.TravelDao;
import ru.sknt.vlasovnetwork.vnsr.daos.travels.WayDao;
import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;
import ru.sknt.vlasovnetwork.vnsr.car.models.Refuel;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;
import ru.sknt.vlasovnetwork.vnsr.models.travels.Hotel;
import ru.sknt.vlasovnetwork.vnsr.models.travels.Point;
import ru.sknt.vlasovnetwork.vnsr.models.travels.ToolRoad;
import ru.sknt.vlasovnetwork.vnsr.models.travels.Travel;
import ru.sknt.vlasovnetwork.vnsr.models.travels.TravelState;
import ru.sknt.vlasovnetwork.vnsr.daos.travels.TravelStateDao;
import ru.sknt.vlasovnetwork.vnsr.models.travels.Way;

@Database(
        version = 1,
        entities = {
                TravelState.class,
                Travel.class,
                ToolRoad.class,
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
                Address.class
        }
//        autoMigrations = {
//                @AutoMigration(from = 1, to = 2),
//                @AutoMigration(from = 2, to = 3),
//                @AutoMigration(from = 3, to = 4, spec = VNSRDatabase.Migration3to4.class),
//                @AutoMigration(from = 4, to = 5),
//                @AutoMigration(from = 5, to = 6)
//        }
)
@TypeConverters({Converters.class})
public abstract class VNSRDatabase extends RoomDatabase {
    //@RenameTable(fromTableName = "TravelState", toTableName = "travels_travelstate")
    //static class Migration3to4 implements AutoMigrationSpec { }

    public abstract TravelStateDao travelStateDao();
    public abstract TravelDao travelDao();
    public abstract ToolRoadDao toolRoadDao();
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
}
