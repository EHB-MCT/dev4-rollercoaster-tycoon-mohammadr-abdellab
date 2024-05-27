<template>
    <div class="container d-flex justify-content-center mt-5" style="height: 100vh;">
        <div style="width: 500px;">
            <h1 class="text-center mb-4">Register</h1>
            <form @submit.prevent="submitForm">
                <div class="mb-3">
                    <label for="inputName" class="form-label">Name</label>
                    <input type="text" class="form-control" id="inputName" v-model="name" />
                </div>
                <div class="mb-3">
                    <label for="inputEmail" class="form-label">Email address</label>
                    <input type="email" class="form-control" id="inputEmail" v-model="email" />
                </div>
                <div class="mb-3">
                    <label for="inputPassword" class="form-label">Password</label>
                    <input type="password" class="form-control" id="inputPassword" v-model="password" />
                </div>
                <div v-if="errorMessage" class="alert alert-danger" role="alert">
                    {{ errorMessage }}
                </div>
                <button type="submit" class="btn btn-primary">Register</button>
            </form>
            <p>Already have an account? <router-link to="/login">Log in</router-link></p>
        </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "RegisterView",
    data() {
        return {
            name: "",
            email: "",
            password: "",
            errorMessage: ""
        };
    },
    methods: {
        async submitForm() {
            const userData = {
                username: this.name,
                email: this.email,
                password: this.password,
                role: "user"
            };

            try {
                const response = await axios.post(
                    "http://localhost:9000/users/register",
                    userData
                );

                if (response.status === 200) {
                    localStorage.setItem('loggedInUser', JSON.stringify(response.data));
                    window.location.href = "/";
                }
            } catch (error) {
                this.errorMessage = "Email is already in use";
                console.error("Email is already in use");
            }
        },
    },
};
</script>
