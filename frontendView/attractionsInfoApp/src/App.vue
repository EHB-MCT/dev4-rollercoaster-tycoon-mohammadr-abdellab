<script setup>
import { ref, onMounted } from 'vue';
import { RouterLink, RouterView, useRoute } from 'vue-router';

let loggedInUser = ref(null);
useRoute();


onMounted(() => {
  loggedInUser.value = JSON.parse(localStorage.getItem('loggedInUser'));
});

const logout = () => {
  localStorage.removeItem('loggedInUser');
  loggedInUser.value = null;
  window.location.href = "/login";
};

const isAdmin = () => {
  return loggedInUser.value && loggedInUser.value.role === 'admin';
};
</script>

<template>
  <header>
    <div class="wrapper">
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
          <RouterLink class="navbar-brand" exact to="/">Attractions</RouterLink>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
            aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <RouterLink class="nav-link" exact to="/">Home</RouterLink>
              </li>
              <li class="nav-item">
                <RouterLink v-if="isAdmin()" class="nav-link" exact to="/Dashboard">Admin Dashboard</RouterLink>
              </li>
              <li class="nav-item">
                <RouterLink class="nav-link" exact to="/Favorites">Favorites</RouterLink>
              </li>
              <span style="color: white;" class="navbar-text ms-5" v-if="loggedInUser">Welcome, {{
                loggedInUser.username
              }} !</span>

            </ul>

            <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
              <li class="nav-item" v-if="loggedInUser">
                <button style="margin-left: 10px;" class="nav-link btn btn-primary" @click="logout">Logout</button>
              </li>
              <li class="nav-item" v-else>
                <button style="margin-left: 10px;" v-if="$route.name !== 'login'" class="nav-link btn btn-primary"
                  @click="goToLogin">Login</button>
                <button style="margin-left: 10px;" v-if="$route.name === 'login'" class="nav-link btn btn-primary"
                  @click="goToRegister">Register</button>
              </li>
            </ul>

          </div>
        </div>
      </nav>
    </div>
  </header>
  <RouterView />
</template>

<script>
export default {
  methods: {
    goToLogin() {
      this.$router.push({ name: 'login' })
    },
    goToRegister() {
      this.$router.push({ name: 'register' })
    }
  }
}
</script>
