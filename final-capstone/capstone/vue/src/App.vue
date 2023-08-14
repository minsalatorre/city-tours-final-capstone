<template class= "main">
<div class="app" id="app">
     
    <div id="nav"> 
      <img id="logo" src="./assets/logo.png" alt="city tours logo">
      <div>
        <Menu :closeOnNavigation="true" :no-overlay="true" :width="200" :right="true" ref="menu">
          <div class="menu-user-info" v-for="user in filteredUsersList" :key="user.id">
        <!-- insert avatar image here -->
        <h5 >Hello, {{user.username}} <avatar :fullname="user.username" :size="32" /></h5>
          </div>
           <ul>
          <li>
          <router-link class="nav-link" v-bind:to="{ name: 'home' }">Home</router-link>
          </li>
          <li>
            <router-link class="nav-link" v-bind:to="{ name: 'trip'}" > Trips </router-link>
          </li>
          <li>
          <router-link class="nav-link"
          v-bind:to="{ name: 'logout' }"
          v-if="$store.state.token != ''"
          >Logout</router-link
        >
        <router-link class="nav-link" v-bind:to="{ name: 'login' }" v-else>Login</router-link>
          </li>
        </ul>
          </Menu>
      </div>
      <!-- <div class="burger-menu">

        <ul>
          <li>
          <router-link class="nav-link" v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
          </li>
          <li>
          <router-link class="nav-link" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
          </li>
        </ul>
      </div> -->
    </div>
    <!-- <img class="bg-img" src="./assets/philadelphia-skyline.jpg" alt="Philly skyline"> -->
    <div class="main">
      <div>
</div>
   
    <router-view class="router-view" />
    </div>
   <div id="Footer">
     <app-footer  year='2023' />
   </div>
  </div>
</template>

<script>
import axios from 'axios'
import ItineraryService from './services/ItineraryService'
import Avatar from 'vue-avatar-component'
import { Slide } from 'vue-burger-menu'
import AppFooter from './components/AppFooter.vue'

export default {
  components: {
    Menu: Slide,
    AppFooter,
    Avatar
    
  },
  data() {
    return {
      users: [],
      userId: -1
    }
  },

  mounted() {
    axios.get("/api/current-user-id").then(
      (response) => {
        this.userId = response.data.userId;
      }).catch(
        (error) => {
        console.log(error);
      }),

    this.$refs.menu.$el.style.backgroundColor = '#fff'
     this.$refs.menu.$el.style.color = '#000'

  },
created (){
  ItineraryService.getUsers().then((response) => {
      this.users = response.data;
    });
},
computed: {
  filteredUsersList() {
      return this.users.filter((user) => user.id == this.userId);
    },
}
}
</script>


<style scoped>

.app {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

#app {
  font-family: Arial, Helvetica, sans-serif;
  display: flex;
  flex-direction: column;
}


  .router-view {
    flex-grow: 2;
  }

.bg-img{
  position: absolute;
  opacity: 50%;
}

.main {
  position: relative;
}

#logo {
  border-radius: 50%;
  height: 150px;
  border: 2px solid;
  margin: 1rem 0 0 3rem;

}
#nav {
  display: flex;
  justify-content: center;
}

.nav-link {
  border: 1px solid;
  
}

.burger-menu {
  background-color: aqua;
  color: black;
}

div.bm-menu {
  background-color: #8AAAE5;
}

nav.bm-item-list ul {
  list-style: none;
  display: flex;
  flex-direction: column;
}

.nav-link {
  border: none;
  text-decoration: none;
  color: white;
}

#footer{
  
  margin-top: auto;
}

@media only screen and (max-width: 800px) { 
  .app {
    display: flex;
    flex-direction: column;
  }

 #app {
  font-family: Arial, Helvetica, sans-serif;
  display: flex;
}

.router-view {
    flex-grow: 2;
  }

#footer{
  margin-top:99vh;
}
.bg-img{
  position: absolute;
  opacity: 50%;
}

.main {
  position: relative;
  height: -webkit-fill-available;
}

#logo {
  border-radius: 50%;
  height: 150px;
  border: 2px solid;
  margin: 10px 0 0 0;
}
#nav {
  display: flex;
  justify-content: center;
}

.nav-link {
  border: 1px solid;
  color: white;
}

li {
  color: white;
}

.menu-user-info {
  color: white;
}

.burger-menu {
  background-color: aqua;
  color: black;
}

div.bm-menu {
  background-color: #8AAAE5;
}

nav.bm-item-list ul {
  list-style: none;
  display: flex;
  flex-direction: column;
}

.nav-link {
  border: none;
  text-decoration: none;
  color: white;
}
#footer{
  display: flex;
  justify-content: flex-end;
}
}


/* .burger-menu {
    color: var(--primary-color);
    font-size: 2rem;
    border: 0;
    background-color: transparent;
    cursor: pointer;
    display: none;
}
.burger-menu {
          display: block;
      }

nav ul.show {
          transform: translateX(0);
      }
nav ul {
          position: fixed;
          background-color: var(--bg-color);
          flex-direction: column;
          top: 86px;
          left: 10%;
          width: 80%;
          text-align: center;
          transform: translateX(120%);
          transition: transform 0.5s ease-in;
      } */
</style>