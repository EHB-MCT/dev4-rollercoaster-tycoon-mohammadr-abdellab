<template>
    <div class="container mt-5">
        <h1 class="mb-4">Admin Dashboard</h1>

        <div class="card mb-4">
            <div class="card-header">Add New Attraction</div>
            <div class="card-body">
                <form @submit.prevent="addAttraction">
                    <div class="mb-3">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" v-model="newAttraction.name" required>
                    </div>
                    <div class="mb-3">
                        <label for="category" class="form-label">Category</label>
                        <select class="form-select" id="category" v-model="newAttraction.category" required>
                            <option v-for="category in categories" :key="category.id" :value="category.name">{{
                                category.name }}</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="capacity" class="form-label">Capacity</label>
                        <input type="number" class="form-control" id="capacity" v-model="newAttraction.capacity"
                            required>
                    </div>
                    <div class="mb-3">
                        <label for="capacity" class="form-label">Speed (km/h)</label>
                        <input type="number" class="form-control" id="speed" v-model="newAttraction.speed" required>
                    </div>
                    <div class="mb-3">
                        <label for="yearBuilt" class="form-label">Year Built</label>
                        <input type="number" class="form-control" id="yearBuilt" v-model="newAttraction.yearBuilt"
                            required>
                    </div>
                    <div class="mb-3">
                        <label for="video" class="form-label">Video (optional)</label>
                        <input type="url" class="form-control" id="video" v-model="newAttraction.onRideVideo">
                    </div>
                    <div class="mb-3">
                        <label for="image" class="form-label">Image URL</label>
                        <input type="url" class="form-control" id="image" v-model="newAttraction.image" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Attraction</button>
                </form>
            </div>
        </div>

        <div class="card mb-4">
            <div class="card-header">Attractions</div>
            <div class="card-body">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Capacity</th>
                            <th>Year Built</th>
                            <th>Image</th>
                            <th>Video</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="attraction in attractions" :key="attraction.id">
                            <td>{{ attraction.name }}</td>
                            <td>{{ attraction.category.name }}</td>
                            <td>{{ attraction.capacity }}</td>
                            <td>{{ attraction.yearBuilt }}</td>
                            <td><img :src="attraction.image" alt="attraction image" class="img-thumbnail" width="100">
                            </td>
                            <td>
                                <a :href="attraction.onRideVideo" class="btn btn-primary" target="_blank"
                                    v-if="attraction.onRideVideo">Watch
                                    Video</a>
                                <span v-else>No Video Available</span>
                            </td>
                            <td>
                                <button class="btn btn-danger" @click="deleteAttraction(attraction.id)">Delete</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="card mb-4">
            <div class="card-header">Manage Categories</div>
            <div class="card-body">
                <form @submit.prevent="addCategory">
                    <div class="mb-3">
                        <label for="categoryName" class="form-label">Category Name</label>
                        <input type="text" class="form-control" id="categoryName" v-model="newCategory" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Category</button>
                </form>
                <ul class="list-group mt-3">
                    <li class="list-group-item d-flex justify-content-between align-items-center"
                        v-for="category in categories" :key="category.id">
                        {{ category.name }}
                        <button class="btn btn-danger btn-sm" @click="deleteCategory(category.id)">Delete</button>
                    </li>
                </ul>
            </div>
        </div>
        <h1 class="mb-4">Breakdown Registration</h1>

        <div class="card mb-4">
            <div class="card-header">Report a Breakdown</div>
            <div class="card-body">
                <form @submit.prevent="reportBreakdown">
                    <div class="mb-3">
                        <label for="attraction" class="form-label">Attraction</label>
                        <select class="form-select" v-model="selectedAttraction" required>
                            <option v-for="attraction in attractions" :key="attraction.id" :value="attraction.id">
                                {{ attraction.name }}
                            </option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" v-model="description" rows="3"
                            required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Report Breakdown</button>
                </form>
            </div>
        </div>
        <div class="card">
            <div class="card-header">Manage Breakdowns</div>
            <div class="card-body">
                <ul class="list-group">
                    <li class="list-group-item d-flex justify-content-between align-items-center"
                        v-for="breakdown in breakdowns" :key="breakdown.id">
                        {{ breakdown.attraction.name }} - {{ breakdown.status }}
                        <button class="btn btn-success btn-sm" @click="resolveBreakdown(breakdown.id)">Resolve</button>
                    </li>
                </ul>
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

export default {
    data() {
        return {
            newAttraction: {
                name: '',
                category: '',
                capacity: 0,
                yearBuilt: 0,
                image: '',
                onRideVideo: '',
                operational: true,
                maintenanceDates: [],
                speed: '',
                heightRequirement: ''
            },
            newCategory: '',
            attractions: [],
            categories: [],
            breakdowns: [],
            message: ""
        };
    },
    methods: {
        async fetchAttractions() {
            try {
                const response = await axios.get('http://localhost:9000/attractions/all');
                this.attractions = response.data;
            } catch (error) {
                console.error('Error fetching attractions:', error);
            }
        },
        async fetchCategories() {
            const categoriesResponse = await axios.get("http://localhost:9000/categories");
            this.categories = categoriesResponse.data;

        },
        fetchBreakdowns() {
            // Fetch the list of breakdowns from the backend
        },
        async addAttraction() {
            try {
                const { name, category, capacity, yearBuilt, image, onRideVideo, operational, maintenanceDates, speed, heightRequirement } = this.newAttraction;
                const attractionData = {
                    name,
                    category,
                    capacity,
                    yearBuilt,
                    image,
                    onRideVideo,
                    operational,
                    maintenanceDates,
                    speed,
                    heightRequirement
                };
                const { data } = await axios.post('http://localhost:9000/attractions/add', attractionData);
                console.log(data)
                this.attractions.push(data);

                this.message = "Attraction added successfully !";
                setTimeout(() => {
                    this.message = "";
                }, 2000);

            } catch (error) {
                alert('Failed to add attraction. Please try again later.');
            }
        },

        async deleteAttraction(attractionId) {
            await axios.delete(`http://localhost:9000/attractions/${attractionId}`);

            this.attractions = this.attractions.filter(attraction => attraction.id !== attractionId);

            this.message = "Attraction deleted successfully !";
            setTimeout(() => {
                this.message = "";
            }, 2000);

        },
        async addCategory() {
            try {
                const categoryData = {
                    name: this.newCategory
                };

                await axios.post('http://localhost:9000/categories', categoryData);

                this.newCategory = '';

                this.fetchCategories();

                this.message = "Category added successfully !";
                setTimeout(() => {
                    this.message = "";
                }, 2000);
            } catch (error) {
                alert('Failed to add category. Please try again later.');
            }
        },
        async deleteCategory(id) {
            try {
                await axios.delete(`http://localhost:9000/categories/${id}`);

                this.fetchCategories();

                this.message = "Category deleted  successfully !";
                setTimeout(() => {
                    this.message = "";
                }, 2000);
            } catch (error) {
                alert('Failed to delete category. Please try again later.');
            }
        },
        async reportBreakdown() {
            try {
                const breakdownData = {
                    attractionId: this.selectedAttraction,
                    description: this.description
                };
                await axios.post("http://localhost:9000/breakdowns", breakdownData);
                alert("Breakdown reported successfully!");


            } catch (error) {
                alert("Failed to report breakdown. Please try again later.");
            }
        },
        resolveBreakdown(id) {
            // Resolve a breakdown by sending a PUT request to the backend
        }
    },

    mounted() {
        this.fetchAttractions();
        this.fetchCategories();
        this.fetchBreakdowns();
    }
};
</script>
