import axios from "axios";

export default {
    addDestination(destination) {
        return axios.post('/destinations', destination)
    },
    removeDestination(itineraryId, landmarkId) {
        return axios.delete(`/itinerary/${itineraryId}/landmarks/${landmarkId}`)
    }
}