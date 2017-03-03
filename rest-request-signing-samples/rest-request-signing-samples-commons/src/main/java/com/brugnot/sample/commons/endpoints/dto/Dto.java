package com.brugnot.sample.commons.endpoints.dto;

import java.util.Date;

/**
 * Sample Dto
 * Created by Antonin on 03/03/2017.
 */
public class Dto {

    /**
     * String
     */
    private String stringValue;

    /**
     * int
     */
    private int intValue;

    /**
     * date
     */
    private Date date;

    /**
     * Empty Constructor for Serialization/Deserialization
     */
    public Dto() {
    }

    /**
     * Constructor with all properties
     * @param stringValue
     * @param intValue
     * @param date
     */
    public Dto(String stringValue, int intValue, Date date) {
        this.stringValue = stringValue;
        this.intValue = intValue;
        this.date = date;
    }

    /**
     *
     * @return
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     *
     * @param stringValue
     */
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     *
     * @return
     */
    public int getIntValue() {
        return intValue;
    }

    /**
     *
     * @param intValue
     */
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
