<template>
  <div>
    <div class="itinerary-form-container">
      <div class="add-itinerary">
        <h2>Add Itinerary Details</h2>
        <b-form-group class="form-input-group">
        <!-- Input Name -->
        <b-input-group>
           <b-form-input class="input-group"
          type="text"
          placeholder="Name"
          v-model="itinerary.itineraryName">
        </b-form-input>
        </b-input-group>
        <!-- Input Date -->
        <b-input-group>
           <b-form-input class="input-group"
          type="date"
          v-model="itinerary.itineraryDate">
        </b-form-input>
        </b-input-group>
        <!-- Input Address -->
        <b-input-group>
           <b-form-input id="autocomplete"
          ref="startingPoint"
          class="input-group"
          type="text"
          placeholder="Starting Address"
          v-model="itinerary.itineraryStartingPoint">
        </b-form-input>
        </b-input-group>
        <!-- Input Btn -->
        <b-input-group-btn>
          <b-btn @click="addItinerary" class="input-btn" variant="light" type="submit">Submit</b-btn>
        </b-input-group-btn>
        <!-- Link return home -->
      </b-form-group>
      <b-link class="cancel-link" href="http://localhost:8080/">Cancel</b-link>

      </div>
      <!-- <form class="new-itinerary" v-on:submit.prevent="addItinerary">
        <label for="name">Name for the Itinerary: </label>
        <input
          class="name-input"
          type="text"
          placeholder="Name"
          v-model="itinerary.itineraryName"
        />
        <br />
        <label for="date">Date: </label>
        <input
          class="date-input"
          type="date"
          v-model="itinerary.itineraryDate"
        />
        <br />
        <label for="starting-point">Starting Point: </label>
        <input
          id="autocomplete"
          ref="startingPoint"
          class="starting-point-input"
          type="text"
          placeholder="Starting Address"
          v-model="itinerary.itineraryStartingPoint"
        />
        <br />
        <button class="add-btn">Add Itinerary</button>
        <p><router-link class="cancel-link" :to="{ name: 'home' }">Cancel</router-link></p>
      </form> -->
      <!-- Link return home -->
      
    </div>
  </div>
</template>

<script>
import itineraryService from "../services/ItineraryService.js";
import moment from "moment";

export default {
  data() {
    return {
      itinerary: {
        itineraryName: "",
        itineraryDate: "",
        itineraryStartingPoint: "",
      },
    };
  },
  mounted() {
    const autocomplete = new window.google.maps.places.Autocomplete(
      // this.$refs["startingPoint"]
      document.getElementById("autocomplete")
    );
    autocomplete.setComponentRestrictions({
      contry: ["us"],
    });
    autocomplete.addListener("place_changed", () => {
      const place = autocomplete.getPlace();
      this.itinerary.itineraryStartingPoint = place.formatted_address;
    });
  },
  created() {},
  methods: {
    addItinerary() {
      if (this.itinerary.itineraryName === "") {
        window.alert("Please fill in the name for your new itinerary");
      } else if (
        this.itinerary.itineraryDate === "" ||
        moment(this.itinerary.itineraryDate).isBefore(moment())
      ) {
        window.alert("Please fill in a correct date for your new itinerary");
      } else if (this.itinerary.itineraryStartingPoint === "") {
        window.alert(
          "Please fill in an address as starting point for your new itinerary"
        );
      } else {
        window.alert("Successfully saved it!");
        itineraryService
          .createNewItinerary(this.itinerary)
          .then((response) => {
            console.log("response", response);
            if (response.status === 200) {
              this.itinerary.itineraryName = " ";
              this.itinerary.itineraryDate = "";
              this.itinerary.itineraryStartingPoint = "";
              this.$router.back();
            }
          })
          .catch((error) => {
            this.handleErrorResponse(error, "adding");
          });
      }
    },
  },
};
</script>

<style scoped>
h2{
   font-family:'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
   color: white;
 }

input,
button {
  margin: 10px 0;
}

.itinerary-form-container{
display: flex;
justify-content: center;
align-items: center;
/* background-image: linear-gradient(#8AAAE5, #004AAD); */
border-radius: 10px;
margin-right: 15%;
  margin-left: 15%;
}

.new-itinerary {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  /* background-color: rgba(255, 255, 255, 0.281); */
  /* background-image: linear-gradient(#8AAAE5, #004AAD ); */
  margin: 5em;
  border-radius: 10%;
  padding: 10px;
  width: 40vw;
}

.add-itinerary {
  background-image: linear-gradient(#8AAAE5, #004AAD );
  margin: 1em;
  border-radius: 10%;
  text-align: center;
  padding: 30px;
}

.form-input-group {
  margin-bottom: 1rem;
}

.input-group {
  margin-bottom: 5px;
}

.input-btn {
  margin-top: 10px;
}

.add-btn {
  border-radius: 5px;
}

.cancel-link {
  color: white;
}
</style>