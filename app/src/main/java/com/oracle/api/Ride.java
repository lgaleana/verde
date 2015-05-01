package com.oracle.api;

public class Ride
{
    String m_rideID;
    String m_userID;
    String m_address;
    double m_gallonsSaved;
    String m_driverID;
    int m_pickupOrder;

    long m_pickup_time;
    long m_end_time;

    public Ride() {

    }

    public Ride(String rideID,
                String userID,
                String address,
                String driverID,
                int pickupOrder,
                long pickupTime,
                long endTime,
                double gallonsSaved)
    {
        this.m_rideID = rideID;
        this.m_userID = userID;
        this.m_address = address;
        this.m_driverID = driverID;
        this.m_pickupOrder = pickupOrder;
        this.m_pickup_time = pickupTime;
        this.m_end_time = endTime;
        this.m_gallonsSaved = gallonsSaved;
    }
    public double getGallons()
    {
        return this.m_gallonsSaved;
    }
    public String getRideID()
    {
        return this.m_rideID;
    }
    public String getUserID()
    {
        return this.m_userID;
    }
    public String getAddress()
    {
        return this.m_address;
    }
    public String getDriverID()
    {
        return this.m_driverID;
    }
    public int getPickupOrder()
    {
        return this.m_pickupOrder;
    }
    public long getPickupTime()
    {
        return this.m_pickup_time;
    }
    public long getEndTime()
    {
        return this.m_end_time;
    }
	/*public int getDriverRating()
	{
		return this.m_driverRating;
	}
	public int getGallonsSaved()
	{
		return this.m_gallons;
	}*/
}
