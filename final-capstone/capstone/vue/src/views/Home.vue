<template>
  <div class="home">
    <div class="home-header">
      <h1>Welcome to Philadelphia</h1>
      <p>Select a Landmark to visit</p>
    </div>
    <search-landmark class="search-bar" />
    <div class="carousel-container">
      <b-carousel
        id="carousel-fade"
        style="text-shadow: 0px 0px 2px #000"
        no-hover-pause
        no-animation
        :interval="6000"
        img-width="100%"
        img-height="100%"
      >
        <div class="c-photos">
          <b-carousel-slide
            img-src="https://upload.wikimedia.org/wikipedia/commons/6/63/PhiladelphiaMuseumOfArt2017.jpg"
          ></b-carousel-slide>
          <b-carousel-slide
            img-src="https://upload.wikimedia.org/wikipedia/commons/4/47/CITY_HALL_PHILADELPHIA.jpg"
          ></b-carousel-slide>
          <b-carousel-slide
            img-src="https://upload.wikimedia.org/wikipedia/commons/9/9b/View_of_Philadelphia_Skyline_from_University_of_Pennsylvania_Downtown_Campus_-_Philadelphia_-_Pennsylvania.jpg"
          ></b-carousel-slide>
          <b-carousel-slide
            img-src="https://digital.ihg.com/is/image/ihg/Philadelphia_header"
          ></b-carousel-slide>
           <b-carousel-slide
            img-src="https://live.staticflickr.com/4099/4926623160_1f7b087813_b.jpg"
          ></b-carousel-slide>
          <b-carousel-slide
            img-src="https://live.staticflickr.com/809/39264082740_66375d06bb_b.jpg"
          ></b-carousel-slide>
          <b-carousel-slide
            img-src="https://cdn.pixabay.com/photo/2012/12/19/04/08/philadelphia-70850_1280.jpg"
          ></b-carousel-slide>
          <b-carousel-slide
            img-src="https://cdn.pixabay.com/photo/2018/04/04/21/23/street-3291171_1280.jpg"
          ></b-carousel-slide>
        </div>
      </b-carousel>
    </div>
    <router-link class="create-link" :to="{ name: 'add-itinerary' }">
      <b-button id="link-button" block variant="primary">
        Create your Itinerary!
      </b-button>
    </router-link>
    <landmark-list class="tiles" />
  </div>
</template>

<script>
import LandmarkList from "../components/LandmarkList.vue";
import SearchLandmark from "../components/SearchLandmark.vue";

export default {
  name: "home",
  components: {
    LandmarkList,
    SearchLandmark,
  },
};
</script>

<style scoped>
.home {
  font-family: Arial, Helvetica, sans-serif;
}
.home-header {
  margin-top: 2rem;
  text-align: center;
}

.create-link {
  margin-left: 8vw;
  margin-top: 5rem;
}

.search-bar {
  margin-bottom: 2rem;
}

.tiles {
  justify-content: center;
}

.carousel-container {
  height: 400px;
  margin: 0 3vw 2vw 3vw;
  position: relative;
}

#carousel-fade {
  border: 2px solid;
  border-radius: 10px;
  height: inherit;
  overflow: hidden;
  position: relative;
  display: flex;
  padding-top: 5rem;
  align-items: center;
}

@media only screen and (max-width: 800px) {
.carousel-container {
  height: 150px;
  margin-bottom: 20px;
  position: relative;
}
#carousel-fade {
  padding-top: 0;
  align-items: center;
}

  .create-link {
    justify-content: center;
    display: flex;
    align-items: center;
    margin-top: 1rem;
    margin-bottom: 2rem;
  }

  #link-button {
    margin-bottom: 1rem;
  }
}
</style>