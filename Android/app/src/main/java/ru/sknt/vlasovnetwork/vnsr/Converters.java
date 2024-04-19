package ru.sknt.vlasovnetwork.vnsr;

import androidx.room.TypeConverter;

import java.util.Date;

import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class Converters {
    @TypeConverter
    public static FormatedDate timestampToDate(Long value) { return value == null ? null : new FormatedDate(value); }
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static int addressToInt(Address address) { return address == null ? null : address.getId(); }
    @TypeConverter
    public static Address intToAddress(int id) { return id == 0 ? null : MainActivity.AddressDao.find(id); }

    @TypeConverter
    public static int regionToInt(Region region) { return region == null ? null : region.getId(); }
    @TypeConverter
    public static Region intToRegion(int id) { return id == 0 ? null : MainActivity.RegionDao.find(id); }

    @TypeConverter
    public static int cityToInt(City city) { return city == null ? null : city.getId(); }
    @TypeConverter
    public static City intToCity(int id) { return id == 0 ? null : MainActivity.CityDao.find(id); }

    @TypeConverter
    public static int streetToInt(Street street) { return street == null ? null : street.getId(); }
    @TypeConverter
    public static Street intToStreet(int id) { return id == 0 ? null : MainActivity.StreetDao.find(id); }

    @TypeConverter
    public static int cityTypeToInt(CityType cityType) { return cityType == null ? null : cityType.getId(); }
    @TypeConverter
    public static CityType intToCityType(int id) { return id == 0 ? null : MainActivity.CityTypeDao.find(id); }

    @TypeConverter
    public static int streetTypeToInt(StreetType streetType) { return  streetType == null ? null : streetType.getId(); }
    @TypeConverter
    public static StreetType intToStreetType(int id) { return id == 0 ? null : MainActivity.StreetTypeDao.find(id); }

    @TypeConverter
    public static int fuelStationToInt(FuelStation fuelStation) { return fuelStation == null ? null : fuelStation.getId(); }
    @TypeConverter
    public static FuelStation intToFuelStation(int id) { return id == 0 ? null : MainActivity.FuelStationDao.find(id); }

    @TypeConverter
    public static int fuelToInt(Fuel fuel) { return fuel == null ? null : fuel.getId(); }
    @TypeConverter
    public static Fuel intToFuel(int id) { return id == 0 ? null : MainActivity.FuelDao.find(id); }
}
