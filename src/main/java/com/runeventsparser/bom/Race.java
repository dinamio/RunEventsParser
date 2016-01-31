package com.runeventsparser.bom;

import java.util.Date;

/**
 * Created by Николай on 31.01.2016.
 */
public class Race extends EntityWithName {


    private Date raceDate;


    private String city;

    public Date getRaceDate() {
        return raceDate;
    }


    public void setRaceDate(Date raceDate) {
        this.raceDate = raceDate;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }
}
