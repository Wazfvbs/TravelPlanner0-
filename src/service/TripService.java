package service;

import dao.TripDAO;
import model.Trip;

import java.util.List;

public class TripService {
    private TripDAO tripDAO = new TripDAO();

    public boolean addTrip(Trip trip) {
        return tripDAO.addTrip(trip);
    }

    public List<Trip> getUserTrips(int userId) {
        return tripDAO.getTripsByUserId(userId);
    }

    public String recommendTrip(String preferences) {
        return tripDAO.recommendTrip(preferences);
    }
}
