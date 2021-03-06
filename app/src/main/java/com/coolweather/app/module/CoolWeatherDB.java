package com.coolweather.app.module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.db.CoolWeatherOpenHelper;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lbf on 2016/3/2.
 */
public class CoolWeatherDB {
    /**
     * Database name
     */
    public static final String DB_NAME = "cool_weather";
    /**
     * Database version
     */
    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase database;

    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper
                (context,DB_NAME,null,VERSION);
        database = dbHelper.getWritableDatabase();
    }
    public synchronized static CoolWeatherDB getInstance(Context context){
        if(coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            database.insert("Province",null,values);
        }
    }
    public List<Province> loadProvince(){
        List<Province> provinces = new ArrayList<>();
        Cursor cursor = database.query("Province",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            Province province = new Province();
            province.setId(cursor.getInt(cursor.getColumnIndex("id")));
            province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
            province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
            provinces.add(province);
        }
        return provinces;
    }
    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id",city.getProvinceId());
            database.insert("City",null,values);
        }
    }
    public List<City> loadCity(int provinceId){
        List<City> cities = new ArrayList<>();
        Cursor cursor = database.query("City",null,"province_id = ?",
                new String[]{String.valueOf(provinceId)},null,null,null);
        while(cursor.moveToNext()){
            City city = new City();
            city.setId(cursor.getInt(cursor.getColumnIndex("id")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
            city.setProvinceId(provinceId);
            cities.add(city);
        }
        return cities;
    }
    public void saveCounty(County county){
        if(county != null){
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("county_code", county.getCountyCode());
            values.put("city_id",county.getCityId());
            database.insert("County",null,values);
        }
    }
    public List<County> loadCounty(int cityId){
        List<County> counties = new ArrayList<>();
        Cursor cursor = database.query("County",null,"city_id = ?",
                new String[]{String.valueOf(cityId)},null,null,null);
        while(cursor.moveToNext()){
            County county = new County();
            county.setId(cursor.getInt(cursor.getColumnIndex("id")));
            county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
            county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
            county.setCityId(cityId);
            counties.add(county);
        }
        return counties;
    }
}
