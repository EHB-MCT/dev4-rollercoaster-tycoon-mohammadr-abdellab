<template>
  <div class="container-fluid">
    <h1 class="text-center my-4">Your Favorite Attractions</h1>
    <div class="row">
      <div v-if="favorites.length === 0" class="col-12">
        <p class="text-center">You have no favorite attractions yet.</p>
      </div>
      <div class="col-lg-3 col-md-6 mb-4" v-for="attraction in favorites" :key="attraction.id">
        <div class="card h-100">
          <img class="card-img-top" style="height: 15pc; object-fit: cover;" :src="attraction.image" alt="Image of {{ attraction.name }}">
          <div class="card-body">
            <h5 class="card-title">{{ attraction.name }}</h5>
            <ul class="list-group list-group-flush">
              <li class="list-group-item disabled">Category: {{ attraction.category.name }}</li>
              <li class="list-group-item">Year Built: {{ attraction.yearBuilt }}</li>
              <li class="list-group-item">Speed: {{ attraction.speed }} km/h</li>
              <li class="list-group-item">Height Requirement: {{ attraction.heightRequirement }} cm</li>
              <li class="list-group-item">Operational: {{ attraction.operational ? 'Yes' : 'No' }}</li>
              <li class="list-group-item">Maintenance Dates: {{ attraction.maintenanceDates.join(', ') }}</li>
            </ul>
            <a :href="attraction.onRideVideo || '#'" class="btn btn-primary mt-2" target="_blank">Watch On-Ride Video</a>
            <button class="btn mt-2" @click="toggleFavorite(attraction)" style="border: none;">
              <svg v-if="isFavorite(attraction)" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="red" class="bi bi-heart-fill" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314" />
              </svg>
              <svg v-else xmlns="http://www.w3.org/2000/svg" cursor="pointer" width="30" height="30" fill="red" class="bi bi-heart" viewBox="0 0 16 16">
                <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143q.09.083.176.171a3 3 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15" />
              </svg>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "FavoritesView",
  data() {
    return {
      favorites: []
    };
  },
  async created() {
    await this.loadFavorites();
  },
  methods: {
    async loadFavorites() {
      try {
        const user = JSON.parse(localStorage.getItem('loggedInUser'));
        const userId = user.id;
        const response = await axios.get(`http://localhost:9000/users/${userId}/favorites`);
        this.favorites = response.data;
      } catch (error) {
        console.error("Error fetching favorites:", error);
      }
    },
    async toggleFavorite(attraction) {
      const user = JSON.parse(localStorage.getItem('loggedInUser'));
      const userId = user.id;
      const attractionId = attraction.id;
      try {
        if (this.isFavorite(attraction)) {
          // Remove attraction from favorites
          await axios.delete(`http://localhost:9000/users/${userId}/favorites/${attractionId}`);
          this.favorites = this.favorites.filter(fav => fav.id !== attractionId);
        } else {
          // Add attraction to favorites
          await axios.post(`http://localhost:9000/users/${userId}/favorites/${attractionId}`);
          this.favorites.push(attraction);
        }
      } catch (error) {
        console.error("Error toggling favorites:", error);
      }
    },
    isFavorite(attraction) {
      return this.favorites.some(fav => fav.id === attraction.id);
    }
  }
};
</script>
