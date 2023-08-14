<template>
  <div class="card-container">
    <div
      :key="landmark.landmarkId"
      class="landmark-card"
      :class="{read: showHide}"
      @click="toggleShowHide"
    >
      <img :src="landmark.landmarkPhoto" alt="photo of landmark" />

      <div id="landmark-info" :class="{ read: showHide }">
        <h1>{{ landmark.landmarkName }}</h1>
        <div class="likes">
          <button
            @click="
              addLikes(),
                (showHide = !showHide),
                (likedDisliked.reviewed = true),
                (likedDisliked.landmarkId=landmark.landmarkId)
            "
            :disabled="likedDisliked.reviewed"
          >
            <font-awesome-icon icon="fa-solid fa-thumbs-up" />
          </button>
          <p>{{ likes }}</p>
          <button
            @click="
              addDislikes(landmark.landmarkId),
                (showHide = !showHide),
                (likedDisliked.reviewed = true),
                (likedDisliked.landmarkId=landmark.landmarkId)
            "
            :disabled="likedDisliked.reviewed"
          >
            <font-awesome-icon icon="fa-solid fa-thumbs-down" />
          </button>
          <p>{{ dislikes }}</p>
        </div>

        <div v-show="showHide">
          <p>{{ landmark.landmarkDescription }}</p>
          <br />
          <p>{{ landmark.landmarkAddress }}</p>
          <br />
          <p>{{ landmark.landmarkVenueType }}</p>

          <p v-for="day in weekHours" :key="day">{{ day }}</p>
      
        <!-- <p class="hOfO">{{ landmark.landmarkHoursOfOperation.replaceAll(", "," | ") }}</p> -->
         </div>
      </div>
    </div>
  </div>
</template>

<script>
import ItineraryService from "../services/ItineraryService";
import DestinationService from "../services/DestinationService";
import LandmarkService from "../services/LandmarkService";
import axios from "axios";
export default {
  name: "landmark-card",
  props: ["landmark", "searchResult"],
  data() {
    return {
      //weekHours: [],
      pathId: this.$route.params.id,
      likes: this.landmark.landmarkLikeCount,
      dislikes: this.landmark.landmarkDislikeCount,
      showHide: false,
      toggleReview: false,
      likedDisliked: {
        userId: 0,
        landmarkId: this.landmark.landmarkId,
        reviewed: false,
      },
      review: {
        userId: 0,
        landmarkId: this.landmark.landmarkId,
      }
    };
  },
  computed: {
    weekHours() {
      let weekArr = this.landmark.landmarkHoursOfOperation.split(", ");
      return weekArr;
    } 
  },
  methods: {
    addLikes() {
      LandmarkService.addLikeToLandmark(this.landmark.landmarkId).then((r) => {
        if (r.status === 200) {
          console.log(r.status);
          this.likes++;
          this.addReview();
          // location.reload();
        }
      });
    },
    addDislikes(landmarkId) {
      LandmarkService.addDislikeToLandmark(landmarkId).then((r) => {
        if (r.status === 200) {
          console.log(r.status);
          this.dislikes++;
          this.addReview();
          // location.reload();
        }
      });
    },
    addReview(){
      LandmarkService.reviewLandmark(this.likedDisliked).then((r) => {
        if(r.status === 200) {
          console.log(r.status);
        }
      })
    },
    getReview(){
      LandmarkService.getLandmarkReview(this.likedDisliked).then((r) =>{
        if(this.likedDisliked.userId === r.data.userId && this.likedDisliked.landmarkId === r.data.landmarkId) {
          return r.data.reviewed;
        }
      })
    },
    toggleShowHide() {
      this.showHide = !this.showHide;
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
  created() {
    ItineraryService.getAllItineraries().then((response) => {
      this.itineraries = response.data;
    });
    axios
      .get("/api/current-user-id")
      .then((response) => {
        this.likedDisliked.userId = response.data.userId;
        this.review.userId = response.data.userId;
      })
      .catch((error) => {
        console.log(error);
      });
      LandmarkService.getLandmarkReview(this.landmark.landmarkId).then((r) =>{
        console.log(r.data.reviewed)
        if(this.likedDisliked.userId === r.data.userId && this.likedDisliked.landmarkId === r.data.landmarkId) {
          return this.likedDisliked.reviewed = r.data.reviewed;
        }
      })
  },

  mounted() {
    axios
      .get("/api/current-user-id")
      .then((response) => {
        this.likedDisliked.userId = response.data.userId;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style scoped>
/* CSS styling still a work in progress */
/* .landmark-container {
  display: flex;
  flex-wrap: wrap;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: "landmark-card landmark-card";
} */

.landmark-card {
  grid-area: landmark-card;
  border-radius: 10px;
  border: 2px solid;
  width: 500px;
  height: 400px;
  margin: 30px;
  position: relative;
}
img {
  border-radius: inherit;
  position: absolute;
  height: 100%;
  width: 100%;
  object-fit: cover;
}

#landmark-info {
  padding: 5px;
  position: inherit;
  color: whitesmoke;
  text-shadow: 2px 2px 2px black;
  transition: height 5s;
}
.read {
  height: auto;
  background: linear-gradient(
    rgba(0, 0, 0, 1),
    rgba(0, 0, 0, 0.75),
    rgba(0, 0, 0, 1)
  );
}

div.likes {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 5px;
}

div.likes button {
  border-radius: 10px;
  color: #004aad;
}



@media only screen and (max-width: 1200px) {
  .card-container {
    display: flex;
    justify-content: center;
  }

  .landmark-card {
    border-radius: 10px;
    border: 2px solid;
    height: auto;
    width: 85vw;
    margin: 5px;
    position: relative;
  }
  img {
    border-radius: inherit;
    position: absolute;
    height: 100%;
    width: 100%;
    object-fit: cover;
  }

  #landmark-info {
    padding: 5px;
    position: inherit;
    color: whitesmoke;
    text-shadow: 2px 2px 2px black;
    transition: height 5s;
  }
  .read {
    background: linear-gradient(
      rgba(0, 0, 0, 1),
      rgba(0, 0, 0, 0.75),
      rgba(0, 0, 0, 1)
    );
  }

  div.likes {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 5px;
  }

  div.likes button {
    border-radius: 10px;
    color: #004aad;
  }
}
</style>