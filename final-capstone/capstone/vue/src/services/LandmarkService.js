import axios from "axios";

export default {
    getAllLandmarks() {
        return axios.get('/landmarks');
    },
    getLandmarksOnItinerary(itineraryId) {
        return axios.get(`/itinerary/${itineraryId}/landmarks`)
    },
    addLikeToLandmark(landmarkId) {
        return axios.put(`landmarks/${landmarkId}/like`)
    },
    addDislikeToLandmark(landmarkId) {
        return axios.put(`/landmarks/${landmarkId}/dislike`)
    },
    searchLandmark(searchInput) {
        return axios.get(`searchLandmark/${searchInput}`)
    },

    catalogLandmark(landmark){
        return axios.post('/addLandmark', landmark)
    },
    reviewLandmark(review) {
        return axios.post('/review', review)
    },
    getLandmarkReview(landmarkId) {
        return axios.get(`/review/${landmarkId}`)
    }
}