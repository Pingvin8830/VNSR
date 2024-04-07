package ru.sknt.vlasovnetwork.vnsr;

import androidx.room.Database;
import androidx.room.RoomDatabase;

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

@Database(
        version = 1,
        entities = {
                TravelState.class,
                Travel.class,
                ToolRoad.class,
                Way.class,
                Refuel.class,
                Region.class,
                Street.class,
                CityType.class,
                City.class
        }
//        autoMigrations = {
//                @AutoMigration(from = 1, to = 2),
//                @AutoMigration(from = 2, to = 3),
//                @AutoMigration(from = 3, to = 4, spec = VNSRDatabase.Migration3to4.class),
//                @AutoMigration(from = 4, to = 5),
//                @AutoMigration(from = 5, to = 6)
//        }
)
public abstract class VNSRDatabase extends RoomDatabase {
    //@RenameTable(fromTableName = "TravelState", toTableName = "travels_travelstate")
    //static class Migration3to4 implements AutoMigrationSpec { }

    public abstract TravelStateDao travelStateDao();
    public abstract TravelDao travelDao();
    public abstract ToolRoadDao toolRoadDao();
    public abstract WayDao wayDao();
    public abstract RefuelDao refuelDao();
    public abstract RegionDao regionDao();
    public abstract StreetDao streetDao();
    public abstract CityTypeDao cityTypeDao();
    public abstract CityDao cityDao();
}
