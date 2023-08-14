<template>
  <div class="searcher">
    <!-- <form @submit.prevent="searchResults(searchTerm), showResult=true">
     <label for="search-landmark"></label>
      <input class="searcher" type="text" v-model="searchTerm" placeholder="Search a place" >
      <input type="submit" value="Search">
     </form> -->

    <b-form
      id="search-container"
      @submit.prevent="searchResults(searchTerm), (showResult = true)"
    >
      <b-input-group id="search-bar">
        <b-form-input
          id="searcher"
          type="text"
          v-model="searchTerm"
          placeholder="Search a place"
        ></b-form-input>
        <b-button type="submit" variant="dark">Search</b-button>
      </b-input-group>
    </b-form>
    <div class="result" v-show="showResult">
      <landmark-card class="landmark-details" :landmark="newLandmark" />
      <b-button
        id="catalog"
        variant="warning"
        @click="catalogResult(searchTerm)"
        >Catalog</b-button
      >
    </div>
  </div>
</template>

<script>
import LandmarkService from "../services/LandmarkService";
import LandmarkCard from "../components/LandmarkCard.vue";
export default {
  components: { LandmarkCard },
  data() {
    return {
      showResult: false,
      searchTerm: "",
      newLandmark: {
        landmarkAddress: "",
        landmarkDescription: "",
        landmarkDislikeCount: 0,
        landmarkHoursOfOperation: "",
        landmarkId: "",
        landmarkLikeCount: 0,
        landmarkName: "",
        landmarkPhoto: "",
        landmarkVenueType: "",
      },
    };
  },
  methods: {
    searchResults(query) {
      query = this.searchTerm;
      console.log(query);
      LandmarkService.searchLandmark(query).then((r) => {
        this.newLandmark = r.data;
        console.log(r.data);
      });
    },
    resetSearch() {
      this.newLandmark = {};
    },
    catalogResult() {
      LandmarkService.catalogLandmark(this.newLandmark).then((r) => {
        console.log(r.data);
      });
      this.resetSearch();
      this.showResult = false;
      location.reload();
    },
  },
};
</script>

<style scoped>
#search-container {
  display: flex;
  justify-content: center;
  margin-bottom: 5px;
}

#catalog {
  margin-left: 6.5vw;
}

#search-bar {
  width: 20rem;
}
</style>