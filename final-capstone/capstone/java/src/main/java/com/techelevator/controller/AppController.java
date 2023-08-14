package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.model.*;
import com.techelevator.services.GooglePlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class AppController {


    @Autowired
    private LandmarkDao landmarkDao;

    @Autowired
    private ItineraryDao itineraryDao;

    @Autowired
    private JdbcItineraryDao jdbcItineraryDao;

    @Autowired
    private TripDao tripDao;

    @Autowired
    private DestinationDao destinationDao;

    @Autowired
    private GooglePlacesService googlePlacesService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SharedTripDao sharedTripDao;

    @Autowired
    private ReviewDao reviewDao;

    //Get Requests Landmarks | Itineraries
    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/landmarks", method = RequestMethod.GET)
    public List<Landmark> getAllLandmarks() {
        List<Landmark> allLandmarksList = landmarkDao.getAllLandmarks();
        return allLandmarksList;
    }

    @RequestMapping(path = "/itinerary/{itineraryId}/landmarks", method = RequestMethod.GET)
    public List<Landmark> getLandmarksByItineraryId(@PathVariable int itineraryId) {
        return landmarkDao.getLandmarksOnItinerary(itineraryId);
    }
    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/landmarks/{landmarkId}", method = RequestMethod.GET)
    public Landmark getLandmarkById(@PathVariable int landmarkId) {
        return landmarkDao.getSpecificLandmarkById(landmarkId);
    }


    @RequestMapping(path = "/itineraries", method = RequestMethod.GET)
    public List<Itinerary> getItinerariesByUser(Principal principal) {
        List<Itinerary> itinerariesByUser = itineraryDao.getAllItineraries(jdbcItineraryDao.getUserIdByUsername(principal.getName()));
        return itinerariesByUser;
    }

    @RequestMapping(path = "/trips/{userId}/shared/itineraries", method = RequestMethod.GET)
    public List<Itinerary> getSharedItineraries(Principal user) {
        List<Itinerary> sharedItineraries = itineraryDao.getSharedItineraries(jdbcItineraryDao.getUserIdByUsername(user.getName()));
        return sharedItineraries;
    }

    @RequestMapping(path = "trips/{userId}/combined", method = RequestMethod.GET)
    public List<Itinerary> getCombinedItineraries(Principal user){
        List<Itinerary> comboList = itineraryDao.getCombinedItineraries(jdbcItineraryDao.getUserIdByUsername(user.getName()));
        return comboList;
    }


    @RequestMapping(path = "/itinerary/{itineraryId}", method = RequestMethod.GET)
    public Itinerary getItineraryById(@PathVariable int itineraryId, Principal user) {
        //Itinerary empty = null;
        Itinerary itineraryById = itineraryDao.getSpecificItinerary(itineraryId);
        //We add this to try to display only the itineraries of the principal
//        List<Itinerary> itinerariesByUser = itineraryDao.getAllItinerariesByUserName(user.getName());
//        if(itinerariesByUser.contains(itineraryById)) {
//            return itineraryById;
//        }
        // for(Itinerary itinerary: itinerariesByUser){
        //     if(!itinerary.equals(itineraryById)){
        //         return empty;
        //     }
        return itineraryById;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable int userId){
        return userDao.getUserById(userId);
    }

//    @RequestMapping(path = "/itineraries", method = RequestMethod.GET)
//    public List<Itinerary> getAllItineraries(int userId){
//        return itineraryDao.getAllItineraries(userId);
//    }

    // CRUD Operations here

    //CREATE

    @RequestMapping(path = "/addItinerary", method = RequestMethod.POST)
    public void createNewItinerary(@Valid @RequestBody Itinerary itinerary, Principal user) {
        itineraryDao.createNewItinerary(itinerary, user.getName());
    }

    @RequestMapping(path = "/trips", method = RequestMethod.POST)
    public void addTrip(int userId, int itineraryId) {
        tripDao.addTrip(userId, itineraryId);
    }


    @RequestMapping(path = "/destinations", method = RequestMethod.POST)
    public void addDestination(@Valid @RequestBody Destination destination) {
        destinationDao.addDestination(destination.getItineraryId(), destination.getLandmarkId());
    }

    @RequestMapping(path = "/trips/shared/itineraries/add", method = RequestMethod.POST)
    public void addSharedItinerary(@Valid @RequestBody SharedTrip sharedTrip){
        sharedTripDao.addSharedTrip(sharedTrip);
    }

    //UPDATE
    @RequestMapping(path = "/itinerary/{itineraryId}/edit", method = RequestMethod.PUT)
    public void updatedItinerary(@Valid @RequestBody Itinerary itinerary, @PathVariable int itineraryId) {
        itineraryDao.updateItinerary(itinerary);
    }

    //DELETE
    @RequestMapping(path = "/itinerary/{itineraryId}", method = RequestMethod.DELETE)
    public void deleteItinerary(@PathVariable int itineraryId) {
        itineraryDao.deleteItinerary(itineraryId);
    }


    @RequestMapping(path = "/itinerary/{itineraryId}/landmarks/{landmarkId}", method = RequestMethod.DELETE)
    public void deleteDestination(@PathVariable int itineraryId, @PathVariable int landmarkId) {
        destinationDao.deleteDestination(itineraryId, landmarkId);

    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "landmarks/{landmarkId}/likes", method = RequestMethod.GET)
    @ResponseBody
    public int getLikesFromLandmark(@PathVariable int landmarkId) {
       return landmarkDao.getLikesFromLandmark(landmarkId);
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "landmarks/{landmarkId}/dislikes", method = RequestMethod.GET)
    public int getDislikesFromLandmark(@PathVariable int landmarkId) {
        return landmarkDao.getDislikesFromLandmark(landmarkId);
    }

    @PreAuthorize("permitAll()")
//    @RequestMapping(path = "landmarks/{landmarkId}/likes", method = RequestMethod.PUT)
    @PutMapping("landmarks/{landmarkId}/like")
    public void addLikeToLandmark(@PathVariable int landmarkId){
        landmarkDao.addLikeToLandmark(landmarkId);
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "landmarks/{landmarkId}/dislike", method = RequestMethod.PUT)
    public void addDislikeToLandmark(@PathVariable int landmarkId){
        landmarkDao.addDislikesToLandmark(landmarkId);
    }

    //TEST API FOR LANDMARKS
    @PreAuthorize("permitAll()")
    @GetMapping(path = "/searchLandmark/{searchQuery}")
    public Landmark test(@PathVariable String searchQuery) {
        return googlePlacesService.getApiResults(searchQuery);
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "/addLandmark", method = RequestMethod.POST)
    public Landmark addLandmarkFromAPI(@RequestBody Landmark landmark) {
        int newLandmarkId = landmarkDao.addNewLandmark(landmark);
        return landmarkDao.getSpecificLandmarkById(newLandmarkId);
    }

    @GetMapping("/api/current-user-id")
    public Map<String, Integer> getCurrentUserIdJson(Principal user){
        Map<String, Integer> result = new HashMap<>();
        int curUserId = userDao.findIdByUsername(user.getName());
        result.put("userId", curUserId);
        return result;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/itineraryOwner/{tripId}")
    public String getOwnerUserName(@PathVariable int tripId){
        int currentUserId = sharedTripDao.getShareTrip(tripId).getUser1Id();
        User owner = userDao.getUserById(currentUserId);
        String username = owner.getUsername();
        return username;
    }

    @PostMapping("/review")
    public void reviewLandmark(@RequestBody Review review) {
        reviewDao.addReview(review);
    }

    @GetMapping("/review/{landmarkId}")
    public Review getReview(@PathVariable int landmarkId, Principal principal){
        return reviewDao.getReview(userDao.findIdByUsername(principal.getName()), landmarkId);
    }


}

