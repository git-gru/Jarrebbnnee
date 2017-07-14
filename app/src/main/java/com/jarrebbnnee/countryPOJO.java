package com.jarrebbnnee;

/**
 * Created by vi6 on 17-Mar-17.
 */

public class countryPOJO {

    String id, iso, iso3, fips, country, continent, currency_code, currency_name, phone_prefix, postal_code, languages, geonameid, is_display;


    public countryPOJO(String id, String iso, String iso3, String fips, String country, String continent, String currency_code, String currency_name, String phone_prefix, String postal_code, String languages, String geonameid, String is_display) {
        this.id = id;
        this.iso = iso;
        this.iso3 = iso3;
        this.fips = fips;
        this.country = country;
        this.continent = continent;
        this.currency_code = currency_code;
        this.currency_name = currency_name;
        this.phone_prefix = phone_prefix;
        this.postal_code = postal_code;
        this.languages = languages;
        this.geonameid = geonameid;
        this.is_display = is_display;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getFips() {
        return fips;
    }

    public void setFips(String fips) {
        this.fips = fips;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getPhone_prefix() {
        return phone_prefix;
    }

    public void setPhone_prefix(String phone_prefix) {
        this.phone_prefix = phone_prefix;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getGeonameid() {
        return geonameid;
    }

    public void setGeonameid(String geonameid) {
        this.geonameid = geonameid;
    }

    public String getIs_display() {
        return is_display;
    }

    public void setIs_display(String is_display) {
        this.is_display = is_display;
    }
}
