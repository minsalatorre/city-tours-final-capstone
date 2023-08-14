import axios from "axios";

export default {
    getSpecificItinerary(itineraryId) {
        return axios.get(`/itinerary/${itineraryId}`)
    },
    getAllItineraries(){
        return axios.get("/itineraries");
    },
    getSharedItineraries(userId){
        return axios.get(`/trips/${userId}/shared/itineraries`)
    },
    getComboItineraries(userId){
        return axios.get(`/trips/${userId}/combined`)
    },
    getUsers(){
        return axios.get('/users')
    },
    createNewItinerary(itinerary){
        return axios.post('/addItinerary', itinerary);
    },
    addSharedTrip(sharedTrip){
        return axios.post('/trips/shared/itineraries/add', sharedTrip)
    },
    updateItinerary(itinerary){
        return axios.put(`/itinerary/${itinerary.itineraryId}/edit`, itinerary)
    },
    deleteItinerary(itineraryId){
        return axios.delete(`/itinerary/${itineraryId}`)
    },
    getUser(userId){
        return axios.get(`/user/${userId}`, userId)
    },
    getOwnerName(tripId) {
        return axios.get(`/itineraryOwner/${tripId}`)
    }
}