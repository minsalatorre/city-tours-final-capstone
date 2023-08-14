<template>
  <div class="trips-container">
    <!-- <div class="itinerary-container">
      <h1>Here are your Itineraries!</h1>
      <div
        class="itinerary-list"
        v-for="itinerary in itineraries"
        :key="itinerary.itineraryId"
      > -->
    <!-- List of Itineraries - Click on itinerary Name and route to the itinerary card -->
    <!-- <ul>
          <li>
            <p>
              <router-link
                class="itinerary-link"
                v-bind:to="{
                  name: 'itinerary',
                  params: { id: itinerary.itineraryId },
                }"
              >
                {{ itinerary.itineraryName }}
              </router-link>
              &nbsp;&nbsp;&nbsp;
              

              <font-awesome-icon
                v-model="itinerary.itineraryId"
                v-on:click="deleteItinerary(itinerary.itineraryId)"
                icon="fa-solid fa-trash"
              />
            </p>
            
          </li>
        </ul>
      </div>
    </div> -->
    <h1>Your Itineraries</h1>
    <div class="itinerary-cards-container">
      <b-card
        class="itinerary-list"
        v-for="itinerary in itineraries"
        :key="itinerary.itineraryId"
        style="max-width: 20rem"
        :title="itinerary.itineraryName"
        img-src="https://cdn.pixabay.com/photo/2019/11/21/20/34/philadelphia-4643451_640.jpg"
      > 
      <b-card-body>
       <router-link
              class="itinerary-link"
              v-bind:to="{
                name: 'itinerary',
                params: { id: itinerary.itineraryId },
              }"
            >
       
          <p>
             Planned For: {{ getFormatedDate(itinerary.itineraryDate) }}
          </p>
          </router-link>
          <b-button
              class="dlt-btn"
              v-model="itinerary.itineraryId"
              v-on:click="deleteItinerary(itinerary.itineraryId)"
              variant="danger"
            ><b-icon icon='trash'/></b-button>
        </b-card-body>
        
      </b-card>
    </div>

    <h1>Shared with You</h1>
    <div v-show="filteredList.length > 0" class="itinerary-cards-container">
      <b-card
        class="itinerary-list"
        v-for="sharedItinerary in filteredList"
        :key="sharedItinerary.itineraryId"
        style="max-width: 20rem"
        :title="sharedItinerary.itineraryName"
        img-src="https://cdn.pixabay.com/photo/2019/11/21/20/34/philadelphia-4643451_640.jpg"
      >
      <b-card-body>
      <router-link
              class="itinerary-link"
              v-bind:to="{
                name: 'itinerary',
                params: { id: sharedItinerary.itineraryId },
              }"
            >
        
          <p>
             Planned For: {{ getFormatedDate(sharedItinerary.itineraryDate) }}
          </p>
          </router-link>
          <b-button
              class="dlt-btn"
              v-model="sharedItinerary.itineraryId"
              v-on:click="deleteItinerary(itinerary.itineraryId)"
              variant="danger"
            ><b-icon icon='trash'/></b-button>
        </b-card-body>
        
      </b-card>
    </div>

    <!-- <div v-show="filteredList.length > 0" class="shared-itinerary-container">
      <h1>Itineraries Shared with You!</h1>
      <div
        class="shared-itinerary-list"
        v-for="sharedItinerary in filteredList"
        :key="sharedItinerary.itineraryId"
      > -->
    <!-- List of Shared Itineraries - Click on itinerary Name and route to the itinerary card -->
    <!-- <ul>
          <li>
            <p>
              <router-link
                class="itinerary-link"
                v-bind:to="{
                  name: 'itinerary',
                  params: { id: sharedItinerary.itineraryId },
                }"
              >
                {{ sharedItinerary.itineraryName }}
              </router-link>
              &nbsp;&nbsp;&nbsp;<font-awesome-icon
                v-model="sharedItinerary.itineraryId"
                v-on:click="deleteItinerary(sharedItinerary.itineraryId)"
                icon="fa-solid fa-trash"
              />
            </p>
          </li>
        </ul>
      </div>
    </div> -->
  </div>
</template>

<script>
import moment from "moment";
import ItineraryService from "../services/ItineraryService";
import LandmarkService from '../services/LandmarkService';
export default {
  data() {
    return {
      itineraries: [],
      sharedItineraries: [],
      landmarks: [],
      imageURL: "https://picsum.photos/200/300",
    };
  },
  created() {
    ItineraryService.getAllItineraries().then((response) => {
      this.itineraries = response.data;
    }),
      ItineraryService.getSharedItineraries().then((response) => {
        this.sharedItineraries = response.data;
        // this.sharedItineraries = this.sharedItineraries.filter(item => !this.itineraries.some(item2 => item.itineraryId === item2.itineraryId))
      });
      LandmarkService.getAllLandmarks().then((r)=>{
        this.landmarks = r.data
      })
  },
  computed: {
    filteredList() {
      return this.sharedItineraries.filter(
        (item) =>
          !this.itineraries.some(
            (item2) => item.itineraryId === item2.itineraryId
          )
      );
    },
  },
  methods: {
    getFormatedDate(date) {
      return moment(date).format("dddd, MMM Do YYYY");
    },
    deleteItinerary(itineraryId) {
      let choice = confirm("Do you want to delete this itinerary?");
      if (choice) {
        ItineraryService.deleteItinerary(itineraryId).then((response) => {
          console.log(response);
          this.$router.go(`/trip`);
        });
      }
    },
  },
};
</script>

<style>
.dlt-btn{
margin-left: 65%;
}

div.card-body {
  width: 350px
}
.trips-container {
  margin-top: 25px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
/* 
li {
  list-style: none;
} */

.itinerary-cards-container {
  display: flex;
  flex-wrap: wrap;
  /* background-image: linear-gradient(#8AAAE5, #004AAD ); */
  border-radius: 10px;
  justify-content: center;
  align-items: center;
  margin: 10px;
}

.itinerary-list {
  border: 1px solid black;
  box-shadow: 5px 10px 5px rgba(0, 0, 0, 0.185);
  margin: 10px;
}

.itinerary-link {
  color: black;
  text-decoration: none;
}
</style>