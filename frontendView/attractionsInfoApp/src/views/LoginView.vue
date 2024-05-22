<template>
  <div class="container d-flex justify-content-center mt-5" style="height: 100vh;">
    <div style="width: 500px;">
      <h1 class="text-center mb-4">Attractions App</h1>
      <h2 class="text-center mb-4">Login Page</h2>

      <form @submit.prevent="submitForm">
        <div class="mb-3">
          <label for="inputEmail" class="form-label">Email address</label>
          <input type="email" class="form-control" id="inputEmail" v-model="email" />
        </div>
        <div class="mb-3">
          <label for="inputPassword" class="form-label">Password</label>
          <input type="password" class="form-control" id="inputPassword" v-model="password" />
        </div>
        <button type="submit" class="btn btn-primary w-100">Login</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginView",
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    async submitForm() {
      const loginData = {
        email: this.email,
        password: this.password,
      };

      try {
        const response = await axios.post(
          "http://localhost:9000/users/login",
          loginData
        );
        console.log("User status:", response.data);

        if (response.status === 200) {
          localStorage.setItem('loggedInUser', JSON.stringify(response.data));
          window.location.href = "/";
        }
      } catch (error) {
        console.error("Error logging in:", error.response.data);
        alert(error.response.data.message)
      }
    },
  },
};
</script>