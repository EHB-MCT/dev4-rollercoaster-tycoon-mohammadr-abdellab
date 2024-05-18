<template>
  <div class="container">
    <h1 class="text-center my-4">Attractions</h1>
    <div class="row">
      <div class="col-md-3" v-for="attraction in attractions" :key="attraction.id">
        <div class="card mb-3" style="width: 100%; height: 40pc;">
          <img class="card-img-top" style="height: 15pc; object-fit: cover;" :src="attraction.image"
            alt="Image of {{ attraction.name }}">
          <div class="card-body">
            <h5 class="card-title">{{ attraction.name }}</h5>
            <ul class="list-group">
              <li class="list-group-item disabled"> Category: {{ attraction.category }}<br>
              </li>
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
      attractions: []
    };
  },
  async created() {
    try {
      const response = await axios.get("http://localhost:9000/attractions/all");
      this.attractions = response.data;
    } catch (error) {
      console.error("Error fetching attractions:", error);
    }
  }
};
</script>
