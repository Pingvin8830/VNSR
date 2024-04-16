package ru.sknt.vlasovnetwork.vnsr.kladr.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.RegionDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;

@Entity(
        tableName = "kladr_address",
        indices = {
                @Index(
                        value = {"name"},
                        unique = true
                )
        }
)
public class Address {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "name")
    private final String mName;
    @ColumnInfo(name = "region_id")
    private int mRegionId;
    @Ignore
    private Region mRegion;
    @ColumnInfo(name = "city_id")
    private int mCityId;
    @Ignore
    private City mCity;
    @ColumnInfo(name = "street_id")
    private int mStreetId;
    @Ignore
    private Street mStreet;
    @ColumnInfo(name = "house")
    private final String mHouse;
    @ColumnInfo(name = "building")
    private final String mBuilding;
    @ColumnInfo(name = "flat")
    private int mFlat;

    public int getId() { return this.mId; }
    public String getName() { return this.mName; }
    public int getRegionId() { return this.mRegionId; }
    public Region getRegion() { return this.mRegion; }
    public int getCityId() { return this.mCityId; }
    public City getCity() { return mCity; }
    public int getStreetId() { return this.mStreetId; }
    public Street getStreet() { return this.mStreet; }
    public String getHouse() { return this.mHouse; }
    public String getBuilding() { return this.mBuilding; }
    public int getFlat() { return this.mFlat; }

    public void setId(int id) { this.mId = id; }
    public void setRegion(RegionDao dao) { this.mRegion = dao.find(mRegionId); }
    public void setCity(CityDao cityDao, CityTypeDao cityTypeDao) {
        this.mCity = cityDao.find(this.mCityId);
        this.mCity.setCityType(cityTypeDao);
    }
    public void setStreet(StreetDao streetDao, StreetTypeDao streetTypeDao) {
        this.mStreet = streetDao.find(this.mStreetId);
        this.mStreet.setStreetType(streetTypeDao);
    }

    public Address (String name, int regionId, int cityId, int streetId, String house, String building, int flat) {
        this.mName = name;
        this.mRegionId = regionId;
        this.mCityId = cityId;
        this.mStreetId = streetId;
        this.mHouse = house;
        this.mBuilding = building;
        this.mFlat = flat;
    }
    public Address (String name, Region region, City city, Street street, String house, String building, int flat) {
        this.mName = name;
        this.mRegion = region;
        this.mCity = city;
        this.mStreet = street;
        this.mHouse = house;
        this.mBuilding = building;
        this.mFlat = flat;
        this.mRegionId = region.getId();
        this.mCityId = city.getId();
        this.mStreetId = street.getId();
    }

}
