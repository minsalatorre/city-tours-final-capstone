<template>
<div>
  <div class="edit-itinerary-form-container">
    <div class="edit-itinerary">
      <h2>Edit Itinerary Details</h2>
      <b-form-group class="form-input-group">
        <!-- Edit Name -->
        <b-input-group>
          <b-form-input class="input-group"
          type="text"
        v-model="itinerary.itineraryName">
          </b-form-input>
        </b-input-group>
        <!-- Edit Date -->
        <b-input-group>
          <b-form-input class="input-group"
          type="date"
        v-model="itinerary.itineraryDate">
          </b-form-input>
        </b-input-group>
        <!-- Edit Address -->
        <b-input-group>
          <b-form-input class="input-group"
          id="autocomplete"
        ref="startingPoint"
        type="text"
        v-model="itinerary.itineraryStartingPoint">
          </b-form-input>
        </b-input-group>
        <!-- Btn -->
        <b-btn-group>
          <b-btn @click="submitForm" class="input-btn"
          variant="light" type="submit">
          Submit
          </b-btn>
        </b-btn-group>
      </b-form-group>
      <b-link class="cancel-link" href="http://localhost:8080/">Cancel</b-link>
    </div>
  </div>
</div>

  <!-- <form v-on:submit.prevent="submitForm" class="edit-form">
    <div>
      <label for="itinerary-name">Name: </label>
      <input
        class="name-input"
        type="text"
        v-model="itinerary.itineraryName"
      />
    </div>
    <div>
     <label for="date">Date: </label>
      <input
        class="date-input"
        type="date"
        v-model="itinerary.itineraryDate"
      />
    </div>
    <div>
      <label for="starting-point">Starting Point: </label>
      <input
        id="autocomplete"
        ref="startingPoint"
        class="starting-point-input"
        type="text"
        v-model="itinerary.itineraryStartingPoint"
      />
    </div>
    <button>Submit</button>
  </form> -->
</template>

<script>
import ItineraryService from '../services/ItineraryService'
export default {
    data() {
        return {
            itinerary: {},
            pathId: this.$route.params.id,
        }
    },

  mounted() {
    const autocomplete = new window.google.maps.places.Autocomplete(
      document.getElementById("autocomplete"));
      autocomplete.setComponentRestrictions({
        contry: ["us"]
      })
    autocomplete.addListener("place_changed", () => {
      const place = autocomplete.getPlace();
      this.itinerary.itineraryStartingPoint = place.formatted_address;
    })
  },

    created() {
        ItineraryService.getSpecificItinerary(this.pathId)
        .then((response) => {
            this.itinerary = response.data;
        })
    },

    methods: {
        submitForm() {
            ItineraryService.updateItinerary(this.itinerary).then((response) => {
                if(response.status == 200){
                   this.$router.push(`/itinerary/${this.pathId}`);
                }
            }
            ) 
            .catch((error) => {
          this.handleErrorResponse(error, "submiting");
        });
        }
    }
};

</script>

<style>
div h2 {
   font-family:'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
   color: white;
 }

.edit-itinerary-form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  margin-left: 15%;
  margin-right: 15%;
}
.edit-itinerary {
  border: 2px solid black;
  display: flex;
  flex-direction: column;
  align-items: center;
  /* background-color: rgba(255, 255, 255, 0.281); */
  background-image: linear-gradient(#8AAAE5, #004AAD );
  text-align: center;
  margin: 5em;
  border-radius: 10%;
  padding: 30px;
  /* width: 40vw; */
}

.form-input-group {
  width: 300px;
  margin-bottom: 1rem;
}

.input-group {
  margin-bottom: 5px;
}

.cancel-link {
  color: white;
}

.input-btn {
  margin-top: 10px;
}
</style>