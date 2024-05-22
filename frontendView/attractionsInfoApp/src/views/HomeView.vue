<template>
  <div class="container">
    <h1 class="text-center my-4">Attractions</h1>
    <div class="row">
      <div class="col-md-1">
        <div class="list-group">
          <a href="#" class="list-group-item list-group-item-action" :class="{ active: selectedCategory === null }"
            @click="filterByCategory(null)">All</a>
          <a href="#" v-for="category in categories" :key="category.id" class="list-group-item list-group-item-action"
            :class="{ active: selectedCategory === category.id }" @click="filterByCategory(category.id)">
            {{ category.name }}
          </a>
        </div>
      </div>
      <div class="col-md-3" v-for="attraction in filteredAttractions" :key="attraction.id">
        <div class="card mb-3" style="width: 100%; height: 40pc;">
          <img class="card-img-top" style="height: 15pc; object-fit: cover;" :src="attraction.image"
            alt="Image of {{ attraction.name }}">
          <div class="card-body">
            <h5 class="card-title">{{ attraction.name }}</h5>
            <ul class="list-group">
              <li class="list-group-item disabled">Category: {{ attraction.category.name }}</li>
              <li class="list-group-item"> Year Built: {{ attraction.yearBuilt }}<br>
              </li>
              <li class="list-group-item"> Speed: {{ attraction.speed }} km/h<br>
              </li>
              <li class="list-group-item"> Height Requirement: {{ attraction.heightRequirement }}cm<br>
              </li>
              <li class="list-group-item"> Operational: {{ attraction.operational ? 'Yes' : 'No' }}<br>
              </li>
              <li class="list-group-item"> Maintenance Dates: {{ attraction.maintenanceDates.join(', ') }}
                <br>
              </li>
            </ul>
            <a :href="attraction.onRideVideo || '#'" class="btn btn-primary mt-2" target="_blank">Watch On-Ride
              Video</a>
            <br>
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart"
              viewBox="0 0 16 16">
              <path style="width: 1px;"
                d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143q.09.083.176.171a3 3 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15" />
            </svg><button class="btn btn-info mt-2" @click="addToFavorites(attraction)">Add to Favorites</button>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "axios";

export default {
  name: "AttractionsView",
  data() {
    return {
      attractions: [],
      categories: [],
      selectedCategory: null,
      favorites: []
    };
  },
  computed: {
    filteredAttractions() {
      if (this.selectedCategory === null) {
        return this.attractions;
      }
      return this.attractions.filter(attraction => attraction.category.id === this.selectedCategory);
    }
  },
  async created() {
    try {
      const response = await axios.get("http://localhost:9000/attractions/all");
      this.attractions = response.data;
      const categoriesResponse = await axios.get("http://localhost:9000/categories");
      this.categories = categoriesResponse.data;

    } catch (error) {
      console.error("Error fetching data:", error);
    }
  },
  methods: {
    filterByCategory(categoryId) {
      this.selectedCategory = categoryId;
    },
    addToFavorites(attraction) {
      if (!this.favorites.some(fav => fav.id === attraction.id)) {
        this.favorites.push(attraction);
        alert(`${attraction.name} added to favorites!`);
      } else {
        alert(`${attraction.name} is already in favorites!`);
      }
    }
  }
};
</script>
