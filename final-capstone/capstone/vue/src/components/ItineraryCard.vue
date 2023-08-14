<template>
  <div class="itinerary-container">
    <div class="itinerary-card">
      <div class="itinerary-details">
        <h1>
          {{ itinerary.itineraryName }}
        </h1>
        <div v-for="user in getUsername" :key="user.id">
          <!-- insert avatar image here || user 1 name -->
          <h5>
            By {{ ItineraryOwner.length > 0 ? ItineraryOwner : user.username }}
            <avatar
              :fullname="
                ItineraryOwner.length > 0 ? ItineraryOwner : user.username
              "
              :size="32"
            />
          </h5>
        </div>
        <h3>Plan for:</h3>
        <h5>{{ getFormatedDate(itinerary.itineraryDate) }}</h5>
        <h3>Starting Point:</h3>
        <h5>{{ itinerary.itineraryStartingPoint }}</h5>

        <router-link :to="{ name: 'edit-itinerary', params: { id: pathId } }">
          <b-input-group-btn>
            <b-btn>Edit this itinerary</b-btn>
          </b-input-group-btn>
        </router-link>

        <b-input-group-btn class="share-button">
          <b-btn @click="toggleShowUsers" variant="primary"> Share </b-btn>
          <div v-for="user in filteredUsersList" :key="user.id">
            <div v-show="showUsers">
                  <h3 class="users">
                    {{ user.username }}
                  </h3>
                  <b-btn
                    id="share-user"
                    @click="
                      addUser2Id(user.id);
                      addSharedTripToUser();
                      toggleShowUsers();
                    "
                    variant="success"
                  >
                    Add User to Trip</b-btn
                  >
            </div>
          </div>
        </b-input-group-btn>

        <!-- <div class="share-button">
        <button @click="toggleShowUsers">Share</button>
        <div v-for="user in filteredUsersList" :key="user.id">
          <div v-show="showUsers">
            <ul>
              <li>
                <h2>
                  {{ user.username }}
                </h2>
                <button
                  @click="
                    addUser2Id(user.id);
                    addSharedTripToUser();
                  "
                >
                  Add User to Trip
                </button>
              </li>
            </ul>
          </div>
        </div>
      </div> -->
      </div>
      <div>
        <route-details />
        <div
          class="landmark-details"
          v-for="landmark in landmarks"
          :key="landmark.landmark_id"
        >
          <landmark-card :landmark="landmark" />
          <div class="button-container">
            <b-btn
              size="sm"
              id="remove-button"
              variant="danger"
              v-on:click="removeDestination(pathId, landmark.landmarkId)"
            >
              <b-icon icon="x"></b-icon>
            </b-btn>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Avatar from "vue-avatar-component";
import DestinationService from "../services/DestinationService";
import LandmarkCard from "../components/LandmarkCard.vue";
import ItineraryService from "../services/ItineraryService.js";
import LandmarkService from "../services/LandmarkService.js";
import RouteDetails from "../components/RouteDetails.vue";
import axios from "axios";
import moment from 'moment';
export default {
  components: {
    RouteDetails,
    LandmarkCard,
    Avatar,
  },
  data() {
    return {
      itinerary: {},
      landmarks: [],
      users: [],
      showUsers: false,
      pathId: this.$route.params.id,
      ItineraryOwner: "",
      sharedTrip: {
        tripId: parseInt(this.$route.params.id),
        user1Id: 0,
        user2Id: 0,
      },
    };
  },
  computed: {
    filteredUsersList() {
      return this.users.filter((user) => user.id != this.sharedTrip.user1Id && user.username != this.ItineraryOwner);
    },
    getUsername() {
      return this.users.filter((user) => user.id === this.sharedTrip.user1Id);
    },

    // filteredItinerary() {
    //   return this.itinerary.filter(
    //     (itinerary) => itinerary.itineraryId === this.pathId
    //   );
    // },
  },
  created() {
    ItineraryService.getSpecificItinerary(this.sharedTrip.tripId).then(
      (response) => {
        this.itinerary = response.data;
      }
    );

    LandmarkService.getLandmarksOnItinerary(this.pathId).then((response) => {
      this.landmarks = response.data;
    });
    ItineraryService.getUsers().then((response) => {
      this.users = response.data;
    });
    ItineraryService.getOwnerName(this.pathId).then((response) => {
      this.ItineraryOwner = response.data;
    });
  },
  methods: {
    getFormatedDate(date) {
      return moment(date).format("dddd, MMM Do YYYY");
    },
    getOwnerName() {
      ItineraryService.getOwnerName(this.pathId).then((response) => {
        this.ItineraryOwner = response.data;
      });
    },
    toggleShowUsers() {
      this.showUsers = !this.showUsers;
    },
    addUser2Id(id) {
      this.sharedTrip.user2Id = id;
      console.log(id);
      console.log(this.sharedTrip.user2Id);
    },
    addSharedTripToUser() {
      console.log(this.sharedTrip);
      ItineraryService.addSharedTrip(this.sharedTrip)
        .then((r) => {
          console.log(r.status);
          alert("User was added to Trip!");
        })
        .catch((error) => {
          console.log(error.status);
        });
    },
    removeDestination(itineraryId, landmarkId) {
      let choice = confirm("Delete Destination?");
      if (choice) {
        DestinationService.removeDestination(itineraryId, landmarkId).then(
          (response) => {
            console.log(response);
            this.$router.go(`/itinerary/${itineraryId}`);
          }
        );
      }
    },
  },
  mounted() {
    axios
      .get("/api/current-user-id")
      .then((response) => {
        this.sharedTrip.user1Id = response.data.userId;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style scoped>
h2 {
  color: black;
}
#share-user {
  height: 2.2rem;
  font-size: 90%;
}
h1 {
  color: black;
  font-weight: 1000;
}
h3 {
  font-weight: bold;
}

h3.users {
  font-weight: 10;
  color: black;
  margin-top: 0.5rem;
  margin-bottom: 0.05rem;
}

.landmark-details {
  display: inline-block;
  justify-content: center;
}
.button-container {
  display: flex;
  justify-content: center;
}
#remove-button {
  margin: .3rem 2rem 0 0;
}

.itinerary-details {
  align-content: center;
  margin: 2.5rem;
  
}
.itinerary-container {
  font-family: Arial, Helvetica, sans-serif;
}

.share-button {
  margin: 0.5rem;
}

@media only screen and (max-width: 800px) {
  .itinerary-details {
    margin: 2.5rem;
    margin-bottom: 0.1rem;
  }
.landmark-details {
  width: auto;
  margin: 0 5vw 0 5vw;
  display: flex;
  justify-content: center;
}

 .button-container {
 
 
  display: flex;
  align-self: flex-start;
  
}
#remove-button {
  margin: 0;
}
}
</style>