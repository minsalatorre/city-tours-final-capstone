<!-- template for Vue Google Maps API intergration > https://github.com/achongsBiz/gmaps-marker-example/tree/master -->


<template>
  <div>  
    <div class="map-container">
      <div id="grid-container">

      <!--Google Maps will render map here-->
      <div id="map"></div>


      <div id="input-area">

          <!-- <p class="input-instructions">For best results the address should have this format: <em>1400 John F Kennedy Blvd, Philadelphia, PA 19107</em></p> -->

          <!-- can update this section to import locations later -->
          <!-- Location to Add: <input v-model="currentInput" type="input"/>
          <button v-on:click="addToList">Add to Route</button> -->
          
          <!-- Add Landmark Names here?-->
        <h3>Current Locations:</h3>
        <b-form id="currentLocations">   
        <b-input-group id="currentList" v-for="(location, index) of locations" v-bind:key="index">
              <b-form-input class="current-inputs" v-model="locations[index]"/> 
              <b-button v-on:click="removeFromList(index)"  variant="danger" id="remove">Remove</b-button>
        </b-input-group>
        </b-form>

        <b-input-group-btn>
          <br>
          <b-btn v-on:click="generateRoute" class="input-btn" variant="secondary">Generate Route</b-btn>
        </b-input-group-btn>

          <!-- <button v-on:click="generateRoute">Generate Route</button><br><br> -->
          <!-- <div id="currentList" v-for="(location, index) of locations" v-bind:key="index">
              <input class="current-inputs" v-model="locations[index]"/> 
              <button v-on:click="removeFromList(index)">Remove</button>
          </div> -->
      </div>
      <!--Google Maps will render directions here-->
      <div id="panel"></div>

    </div>
    </div>
  </div>
</template>

<script>
import ItineraryService from '../services/ItineraryService';
import LandmarkService from '../services/LandmarkService';
export default {
  name: "Map",
  data() {
    return {
      map: null,
      routeService : null,
      routeRendererService: null,
      currentInput : "",
      roundTrip : true,
      mapCenter: { lat: 39.9526, lng: -75.1652 },
      pathId: this.$route.params.id,
      //load locations from landmarks in itenerary DB
      //sample itenerary:
      //starting location, mark a comes from itinerary 
        //location b ..ect comes from landmarks through dest table
        //"526 Market St, Philadelphia, PA 19106",
      // "Arch St &, N 10th St, Philadelphia, PA 19107",
      // "2600 Benjamin Franklin Pkwy, Philadelphia, PA 19130",
      locations: [],
      landmarks: [],
      startingPoint: []

    };
  },
  created() {
    ItineraryService.getSpecificItinerary(this.pathId).then(
      (response) => {
        this.startingPoint = response.data;
      }
    );
    LandmarkService.getLandmarksOnItinerary(this.pathId).then((response) => {
      this.landmarks = response.data;
      this.locations = this.landmarks.map(landmark => landmark.landmarkAddress);
      this.locations.unshift(this.startingPoint.itineraryStartingPoint);
    });
  },

  methods: {
    // This function is called during load, but can also be called to reset the map
    initMap() {

      this.map = new window.google.maps.Map(document.getElementById("map"), {
        center: this.mapCenter,
        zoom: 12,
        maxZoom: 20,
        minZoom: 3,
        streetViewControl: true,
        mapTypeControl: true,
        fullscreenControl: true,
        zoomControl: true,
      });
      let noPOIStyle = [
        {
          featureType: "poi",
          elementType: "labels",
          stylers: [{ visibility: "off" }],
        },
      ];
      this.map.setOptions({ styles: noPOIStyle });
    },

    // This function is called to add a new location
    addToList() {

        if(this.currentInput.trim().length === 0) {
            window.alert("Location cannot be empty");
            return;
        }
        this.locations.push(this.currentInput);
        this.currentInput = "";
    },

    // This function is called to remove a location
    removeFromList(index) {

        if(this.locations.length == 2) {
          window.alert("A start and end location must be present");
          return;
        }

        this.locations.splice(index, 1);
    },

    // This function calls the Google Maps API, renders the route
    // and retrieves the directions
    generateRoute() {

      for(let i=0; i < this.locations.length; i++) {

        if (this.locations[i].trim().length === 0) {
          window.alert("Location cannot be empty");
          return;
        }
      }


      const panel = document.getElementById("panel");
      panel.innerHTML = '';
      this.initMap();

       this.routeService = new window.google.maps.DirectionsService();
       this.routeRendererService = new window.google.maps.DirectionsRenderer();
       
       this.routeRendererService.setMap(this.map);
       this.routeRendererService.setPanel(panel);

      let myWaypoints = [];
      
      /*
        The API expects a single waypoint to be an object like this:
        
        {
          location: "123 somewhere St...",
          stopover: true
        }
      */

      for(let i=1; i < this.locations.length -1; i++) {
        myWaypoints.push( 
          {
            location: this.locations[i],
            stopover: true
          }
        );
      }

       this.routeService.route(
        {
          origin: this.locations[0],
          destination: this.locations[this.locations.length-1],
          waypoints: myWaypoints,
          travelMode: window.google.maps.TravelMode.DRIVING,
          avoidTolls: true,
          optimizeWaypoints: true
        }
       ).then(
          (result) => {
            this.routeRendererService.setDirections(result);
          }
       ). catch(
          (error) => {
            console.log(error + "Could not generate route");
          }
       );

    }
  },

  mounted() {
    this.initMap();
  },
};
</script>

<style scoped>

#grid-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-areas: 
  "map inputs"
  "directions directions";
}

#map {
  grid-area: map;
  width: 650px;
  height: 400px;
  padding: 25px;
  margin: 25px;
}

#input-area {
  grid-area: inputs;
}

#panel {
  grid-area: directions;
}

.current-inputs {
  width: 350px;
}

h3{
  font-weight: bold;
}

@media only screen and (max-width: 800px) {
  .map-container {
    display: flex;
    justify-content: center;
    align-content: center;
    /* background-color: #8aaae55e; */
    border-radius: 15px;
  }

  #grid-container {
  /* display: grid;
  grid-template-columns: 1fr;
  grid-template-areas: 
  "map"
  "inputs"
  "directions"
  "directions"; */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

#map {
  grid-area: map;
  width: 80vw;
  padding: 25px;
  margin: 25px;
}

#input-area {
  grid-area: inputs;
}

#panel {
  width: 80vw;
  margin: 25px;
  /* grid-area: directions; */
}

.current-inputs {
  width: 60vw;
  margin: 5px;
}
.currentLocations{
  margin-top: 200px;
}

}
</style>