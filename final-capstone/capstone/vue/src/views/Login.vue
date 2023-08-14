<template>
  <div class="login-container">
    <div id="login">
    <!-- <form @submit.prevent="login">
      <h1 >Log in to start your journey!</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username"><font-awesome-icon icon="fa-solid fa-user" /></label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">
          <font-awesome-icon icon="fa-solid fa-lock" />
        </label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Sign in</button>
      <p>
      <router-link class="register" :to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
    </form> -->
    <b-form-group class="form-input-group">
      <h2>Log in to start your journey!</h2>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <!-- <font-awesome-icon icon="fa-solid fa-user" /> -->
      <b-input-group class="input-group">
        <b-form-input type="text" id="username" v-model="user.username" placeholder="username" required autofocus ></b-form-input>
      </b-input-group>
      <b-input-group class="input-group">
        <b-form-input type="password" id="password" v-model="user.password" required placeholder="password" ></b-form-input>
      </b-input-group>
      <b-input-group-btn>
        <b-btn @click="login" class="input-btn" variant="light" type="submit">Log In</b-btn>
      </b-input-group-btn>
      <b-input-group>
        <b-link class="login-link" href="http://localhost:8080/register">Need an account? Sign up.</b-link>
      </b-input-group>
    </b-form-group>
  </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>
 h2{
   font-family:'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
   color: white;
 }
.login-container {
  /* background-image: linear-gradient(#8AAAE5, #004AAD ); */
  /* height: 70vh; */
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  margin-right: 25%;
  margin-left: 25%;
  
}
#login {
  border: 2px solid black;
  /* background-color: rgba(255, 255, 255, 0.281); */
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
  margin-bottom: 10px;
}

label {
  margin-right: 0.5rem;
}

.input-btn {
  margin-top: 10px;
}

.login-link {
  margin-top: 10px;
  text-decoration: underline;
  color: white;
}

@media only screen and (max-width: 800px) {
  .login-container {
  /* background-color: linear-gradient(#8AAAE5, #004AAD ); */
  /* height: 70vh; */
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  margin: 3%
}
  #login {
  /* background-color: rgba(255, 255, 255, 0.281); */
   background-color: linear-gradient(#8AAAE5, #004AAD );
  margin: 5em;
  border-radius: 10%;
  text-align: center;
  padding: 10px;
}
.form-input-group {
  margin-bottom: .5rem;
}
label {
  margin-right: 0.5rem;
}

.login-link {
  margin-top: 10px;
  text-decoration: underline;
  color: white;
}

}
</style>