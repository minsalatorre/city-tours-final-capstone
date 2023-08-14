<template>
<div class="register-container">
  <div class="form-group">
     <div id="register" class="text-center">
    <!-- <form class="register-form" @submit.prevent="register">
      <h1>Create your Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username"><font-awesome-icon icon="fa-solid fa-user" /></label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password"><font-awesome-icon icon="fa-solid fa-lock" /></label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword"><font-awesome-icon icon="fa-solid fa-check" /><font-awesome-icon icon="fa-solid fa-lock" /></label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit">Create Account</button>
      <p><router-link class="login-link" :to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form> -->
    <b-form-group @submit.prevent="register" class="form-input-group">
        <h2>Create your Account</h2>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <b-input-group class="input-group" >
        <b-form-input type="text" id="username" v-model="user.username" required autofocus placeholder="Create a username.."></b-form-input>
      </b-input-group>
      <b-input-group class="input-group" >
        <b-form-input type="password" id="password" v-model="user.password" required placeholder="Create a password.."></b-form-input>
      </b-input-group>
      <b-input-group class="input-group" >
        <b-form-input type="password" id="confirmPassword" v-model="user.confirmPassword" required placeholder="Re-enter password.."></b-form-input>
      </b-input-group>
      <b-input-group-btn>
        <b-btn @click="register" class="input-btn" variant="light" type="submit">Create Account</b-btn>
      </b-input-group-btn>
      <b-input-group >
      <b-link class="login-link" href="http://localhost:8080/login">Already have an account? Log in.</b-link>
      </b-input-group>
    </b-form-group>
  </div>
  </div>
</div>

</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
/* CSS styling still a work in progress */
/* div#register.text-center {
 align-content: center;
 width: 50%;
} */

h2{
   font-family:'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
   color: white;
 }

.register-container {
  /* background-image: linear-gradient(#8AAAE5, #004AAD ); */
  /* height: 70vh; */
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  margin-right: 25%;
  margin-left: 25%;
}

#register { 
  border: 2px solid black;
  /* background-color: rgba(255, 255, 255, 0.281); */
  background-image: linear-gradient(#8AAAE5, #004AAD );
  margin: 1em;
  border-radius: 10px;
  text-align: center;
  padding: 30px;
}

.form-input-group {
  margin-bottom: 1rem;
}
.input-group {
  margin-bottom: 5px;
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
  .register-container {
    /* height: 70vh; */
    display: flex;
    justify-content: center;
    align-items: center;
    /* background-image: linear-gradient(#8AAAE5, #004AAD ); */
    border-radius: 10px;
    margin: 3%
  }

  .register {
    background-image: linear-gradient(#8AAAE5, #004AAD );
  /* background-color: rgba(255, 255, 255, 0.281); */
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
