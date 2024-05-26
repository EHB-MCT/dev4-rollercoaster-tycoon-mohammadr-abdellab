<template>
    <div class="container">
        <h1 class="text-center my-4">Edit Attraction</h1>
        <form @submit.prevent="updateAttraction">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" v-model="attraction.name" required>
            </div>
            <div class="form-group">
                <label for="category">Category</label>
                <select class="form-control" id="category" v-model="selectedCategory" required>
                    <option v-for="category in categories" :key="category.id" :value="category.name">{{ category.name }}
                    </option>
                </select>
            </div>
            <div class="form-group">
                <label for="capacity">Capacity</label>
                <input type="number" class="form-control" id="capacity" v-model="attraction.capacity" required>
            </div>
            <div class="form-group">
                <label for="yearBuilt">Year Built</label>
                <input type="number" class="form-control" id="yearBuilt" v-model="attraction.yearBuilt" required>
            </div>
            <div class="form-group">
                <label for="image">Image URL</label>
                <input type="url" class="form-control" id="image" v-model="attraction.image" required>
            </div>
            <div class="form-group">
                <label for="onRideVideo">Video URL</label>
                <input type="url" class="form-control" id="onRideVideo" v-model="attraction.onRideVideo">
            </div>
            <div class="form-group">
                <label for="operational">Operational</label>
                <select class="form-control" id="operational" v-model="attraction.operational" required>
                    <option :value="true">Yes</option>
                    <option :value="false">No</option>
                </select>
            </div>
            <div class="form-group">
                <label for="speed">Speed (km/h)</label>
                <input type="number" class="form-control" id="speed" v-model="attraction.speed" required>
            </div>
            <div class="form-group">
                <label for="heightRequirement">Height Requirement (cm)</label>
                <input type="number" class="form-control" id="heightRequirement" v-model="attraction.heightRequirement"
                    required>
            </div>
            <button type="submit" class="btn btn-primary mt-2">Update Attraction</button>
        </form>
        <div v-if="message" class="alert alert-info fixed-bottom mx-auto" style="width: 90%; max-width: 600px;"
            role="alert">
            {{ message }}
        </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "EditAttractionView",
    data() {
        return {
            attraction: {
                name: "",
                category: { name: "" },
                capacity: 0,
                yearBuilt: 0,
                image: "",
                onRideVideo: "",
                operational: true,
                speed: 0,
                heightRequirement: 0,
                breakdownCount: null,
            },
            selectedCategory: null,
            categories: [],
            message: "",
            maintenanceDatesString: ""
        };
    },
    async created() {
        const attractionId = this.$route.params.id;
        try {
            const response = await axios.get(`http://localhost:9000/attractions/${attractionId}`);
            this.attraction = response.data;
            this.selectedCategory = this.attraction.category.name;

            const categoriesResponse = await axios.get("http://localhost:9000/categories");
            this.categories = categoriesResponse.data;

        } catch (error) {
            console.error("Error fetching attraction data:", error);
        }
    },
    methods: {
        async updateAttraction() {
            this.attraction.category = this.selectedCategory;

            const updatedAttraction = {
                ...this.attraction,
                maintenanceDates: []
            };
            try {
                await axios.put(`http://localhost:9000/attractions/${this.attraction.id}`, updatedAttraction);
                this.message = "Attraction updated successfully!";
                setTimeout(() => {
                    this.message = "";
                    this.$router.push({ name: 'home' });
                }, 2000);
            } catch (error) {
                console.error("Error updating attraction:", error);
                this.message = "Failed to update attraction.";
            }
        }
    }
};
</script>