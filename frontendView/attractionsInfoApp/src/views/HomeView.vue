<template>
  <div class="container-fluid">
    <h1 class="text-center my-4">Attractions</h1>
    <div class="row">
      <div class="col-md-2">
        <div class="list-group">
          <a href="#" class="list-group-item list-group-item-action" :class="{ active: selectedCategory === null }"
            @click="filterByCategory(null)">All</a>
          <a href="#" v-for="category in categories" :key="category.id" class="list-group-item list-group-item-action"
            :class="{ active: selectedCategory === category.id }" @click="filterByCategory(category.id)">
            {{ category.name }}
          </a>
        </div>
      </div>
      <div class="col-md-9">
        <div class="row">
          <div class="col-lg-3 col-md-6 mb-4" v-for="attraction in filteredAttractions" :key="attraction.id">
            <div class="card h-100" :class="{ 'border-danger': unresolvedBreakdownsByAttractionId[attraction.id] }">
              <img class="card-img-top" style="height: 15pc; object-fit: cover;" :src="attraction.image"
                alt="Image of {{ attraction.name }}">
              <div v-if="unresolvedBreakdownsByAttractionId[attraction.id]" class="badge bg-danger position-absolute"
                style="top: 0; left: 0; width: 100%; font-size: medium;">Unresolved
                Breakdown(s)</div>

              <div class="card-body">
                <h5 class="card-title">{{ attraction.name }}</h5>
                <ul class="list-group list-group-flush">
                  <li class="list-group-item disabled"><strong>Category:</strong> {{ attraction.category.name }}</li>
                  <li class="list-group-item"><strong>Year Built</strong>: {{ attraction.yearBuilt }}</li>
                  <li class="list-group-item"><strong>Speed:</strong> {{ attraction.speed }} km/h</li>
                  <li class="list-group-item"><strong>Capacity:</strong> {{ attraction.capacity }}</li>
                  <li class="list-group-item"><strong>Height Requirement:</strong> {{ attraction.heightRequirement }} cm
                  </li>
                  <li class="list-group-item"><strong>Operational:</strong> {{ attraction.operational ? 'Yes' : 'No' }}
                  </li>
                  <li class="list-group-item"><strong>Maintenance Dates:</strong> {{
                    attraction.maintenanceDates.join(' , ') }}</li>
                  <li class="list-group-item"><strong>number of breakdowns
                      :</strong> {{ attraction.breakdownCount }}
                  </li>
                </ul>
                <a :href="attraction.onRideVideo" class="btn btn-primary" target="_blank"
                  v-if="attraction.onRideVideo">Watch
                  Video</a>
                <span v-else>No Video Available</span>
                <button v-if="!favorites.some(fav => fav.id === attraction.id)" class="btn mt-2"
                  @click="addToFavorites(attraction)" style="border: none;">
                  <svg xmlns="http://www.w3.org/2000/svg" cursor="pointer" width="30" height="30" fill="red"
                    class="bi bi-heart" viewBox="0 0 16 16">
                    <path
                      d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143q.09.083.176.171a3 3 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15" />
                  </svg>
                </button>
                <button v-else class="btn mt-2" @click="addToFavorites(attraction)" style="border: none;">
                  <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="red" class="bi bi-heart-fill"
                    viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                      d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314" />
                  </svg>
                </button>
                <br>
                <button v-if="isAdmin" @click="editAttraction(attraction)" class="btn btn-warning mt-2">
                  <i class="bi bi-pencil-square"> Edit</i>
                </button>
                <br>
                <button @click="planMaintenance(attraction)" class="btn btn-warning mt-2">
                  Plan Maintenance
                </button>
                <br>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="message" class="alert alert-info fixed-bottom mx-auto" style="width: 90%; max-width: 600px;"
      role="alert">
      {{ message }}
    </div>
  </div>
</template>



<script>
import axios from "axios";
import { ref } from 'vue';

let loggedInUser = ref(null);


export default {
  name: "AttractionsView",
  data() {
    return {
      attractions: [],
      categories: [],
      selectedCategory: null,
      favorites: [],
      message: "",
      breakdowns: []

    };
  },
  computed: {
    filteredAttractions() {
      if (this.selectedCategory === null) {
        return this.attractions;
      }
      return this.attractions.filter(attraction => attraction.category.id === this.selectedCategory);
    },
    unresolvedBreakdownsByAttractionId() {
      const unresolvedBreakdowns = this.breakdowns.filter(breakdown => !breakdown.resolved);
      const unresolvedBreakdownsByAttractionId = {};
      unresolvedBreakdowns.forEach(breakdown => {
        if (!unresolvedBreakdownsByAttractionId[breakdown.attraction.id]) {
          unresolvedBreakdownsByAttractionId[breakdown.attraction.id] = true;

        }
      });
      this.attractions.forEach(attraction => {
        if (unresolvedBreakdownsByAttractionId[attraction.id]) {
          attraction.operational = false;
        } else {
          attraction.operational = true;
        }
      });

      return unresolvedBreakdownsByAttractionId;
    },
    isAdmin() {
      return loggedInUser.value && loggedInUser.value.role === 'admin';
    },

  },
  async created() {
    try {
      const response = await axios.get("http://localhost:9000/attractions/all");
      this.attractions = response.data;
      const categoriesResponse = await axios.get("http://localhost:9000/categories");
      this.categories = categoriesResponse.data;

      await this.fetchBreakdowns();


    } catch (error) {
      console.error("Error fetching data:", error);
    }
  },
  methods: {
    filterByCategory(categoryId) {
      this.selectedCategory = categoryId;
    },
    async getFavorites() {
      try {
        const user = JSON.parse(localStorage.getItem('loggedInUser'));
        const userId = user.id;
        const response = await axios.get(`http://localhost:9000/users/${userId}/favorites`);
        this.favorites = response.data;
      } catch (error) {
        console.error("Error fetching favorites:", error);
      }
    },
    async addToFavorites(attraction) {
      const user = JSON.parse(localStorage.getItem('loggedInUser'));
      const userId = user.id;
      const attractionId = attraction.id;
      try {
        if (!this.favorites.some(fav => fav.id === attraction.id)) {
          // Ajouter l'attraction aux favoris
          await axios.post(`http://localhost:9000/users/${userId}/favorites/${attractionId}`);
          this.favorites.push(attraction);
          this.message = `${attraction.name} added to favorites!`;

        } else {

          const existingFavoriteIndex = this.favorites.findIndex(fav => fav.id === attraction.id);
          await axios.delete(`http://localhost:9000/users/${userId}/favorites/${attractionId}`);
          this.favorites.splice(existingFavoriteIndex, 1);
          this.message = "Removed from favorite list!";
        }
        setTimeout(() => {
          this.message = "";
        }, 2000);
      } catch (error) {
        console.error("Error toggling favorites:", error);
      }
    },
    async fetchBreakdowns() {
      try {
        const response = await axios.get("http://localhost:9000/breakdowns");
        this.breakdowns = response.data;
      } catch (error) {
        console.error("Error fetching breakdowns:", error);
      }
    },
    editAttraction(attraction) {
      this.$router.push({ name: 'EditAttraction', params: { id: attraction.id } });
    },
  },
  mounted() {
    if (this.attractions.length > 0) {
      this.fetchMaintenanceDate(this.attractions[0].id);
    }

    this.getFavorites();
    loggedInUser.value = JSON.parse(localStorage.getItem('loggedInUser'));

  }
};
</script>
