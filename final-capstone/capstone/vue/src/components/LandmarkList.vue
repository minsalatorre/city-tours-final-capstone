<template>
  <div class="landmark-container">
    <div
      class="landmark-card"
      v-for="landmark in landmarks"
      :key="landmark.landmarkId"
      @click="destination.landmarkId = landmark.landmarkId"
    >
      <landmark-card class="landmark-details" :landmark="landmark" />
      <div class="button-container">
        <b-dropdown 
        id="addToTrip" 
        text="Add To Trip" 
        offset="25" 
        class="m-md-2"
        variant="success">
          <b-dropdown-item
            v-for="itinerary in uniqueItins"
            :key="itinerary.itineraryId"
            @click.prevent="addItineraryId(itinerary.itineraryId)"
          >
            {{ itinerary.itineraryName }}
          </b-dropdown-item>
        </b-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import landmarkService from "../services/LandmarkService.js";
import LandmarkCard from "../components/LandmarkCard.vue";
import ItineraryService from "../services/ItineraryService";
import DestinationService from "../services/DestinationService";
export default {
  name: "landmark-list",
  components: {
    LandmarkCard,
  },
  data() {
    return {
      landmarks: [],
      itineraries: [],
      destination: {
        landmarkId: 0,
        itineraryId: 0,
      },
    };
  },
  computed: {
    uniqueItins() {
      const key = "itineraryId";
      const arrayUniqueByKey = [...new Map(this.itineraries.map(item =>
  [item[key], item])).values()];
    return arrayUniqueByKey;
    }
  },
  created() {
    landmarkService.getAllLandmarks().then((r) => {
      this.landmarks = r.data;
    });
    // ItineraryService.getAllItineraries().then((response) => {
    //   this.itineraries = response.data;
    // });
    ItineraryService.getComboItineraries().then((response)=>{
      this.itineraries = response.data
    })
  },
  methods: {
    addLandmarkId(id) {
      this.destination.landmarkId = id;
      console.log(id);
    },
    addItineraryId(id) {
      
      this.destination.itineraryId = id;
      this.dropDownActive = false;
      setTimeout(() => {
      DestinationService.addDestination(this.destination).then((r) => {
        console.log(r.status), 500})
      });
    },
  },
};
</script>

<style scoped>

.button-container {
  display: flex;
  justify-content: center;
}

  .landmark-card {
    margin-bottom: 1rem;
  }



@media only screen and (min-width: 800px) {
  .landmark-container {
    display: flex;
    flex-wrap: wrap;
    /* grid-template-columns: 1fr 1fr;
  grid-template-areas: "landmark-card landmark-card"; */
  }

}

/* 
div.landmark-card.landmark-details {
    grid-area: landmark-card;
    border-radius: 10px;
    border: 2px solid;
    width: 500px;
    height: 500px;
    margin: 100px;
    position: relative;
  } */
</style>