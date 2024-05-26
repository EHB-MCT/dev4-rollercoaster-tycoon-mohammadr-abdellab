<template>
    <div class="container mt-5">
        <h1 class="mb-4">Admin Dashboard</h1>
        <div class="card mb-4">
            <div class="card-header">Attractions</div>
            <div class="card-body">
                <table class="table">
                    <thead>
                        <tr>
                            <th><strong>Name</strong></th>
                            <th><strong>Category</strong></th>
                            <th><strong>Capacity</strong></th>
                            <th><strong>Year Built</strong></th>
                            <th><strong>Image</strong></th>
                            <th><strong>Video</strong></th>
                            <th><strong>Actions</strong></th>
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
            <div class="card-header">Manage Breakdowns</div>
            <div class="card-body">
                <ul class="list-group">
                    <li class="list-group-item" v-for="breakdown in breakdowns" :key="breakdown.id">
                        <div>
                            <h3>{{ breakdown.attraction.name }}</h3>
                            <p><strong>Status:</strong> {{ breakdown.resolved ? 'Resolved' : 'Unresolved' }}</p>
                            <p><strong>Date Reported:</strong> {{ breakdown.dateReported }}</p>
                            <p v-if="breakdown.dateResolved"><strong>Date Resolved:</strong> {{ breakdown.dateResolved
                                }}</p>
                            <p><strong>Description:</strong> {{ breakdown.description }}</p>
                        </div>
                        <div class="mt-2">
                            <button class="btn btn-success btn-sm" @click="resolveBreakdown(breakdown.id)"
                                :disabled="breakdown.resolved">Resolve</button>
                            <button class="btn btn-danger btn-sm ms-2"
                                @click="deleteBreakdown(breakdown.id)">Delete</button>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
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
            <div class="card-header">Register Breakdown</div>
            <div class="card-body">
                <form @submit.prevent="addBreakdown">
                    <div class="mb-3">
                        <label for="attraction" class="form-label">Attraction</label>
                        <select class="form-select" id="attraction" v-model="newBreakdown.attractionId" required>
                            <option v-for="attraction in attractions" :key="attraction.id" :value="attraction.id">
                                {{ attraction.name }}</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="dateReported" class="form-label">Date Reported</label>
                        <input type="date" class="form-control" id="dateReported" v-model="newBreakdown.dateReported"
                            required>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" v-model="newBreakdown.description"
                            required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Register Breakdown</button>
                </form>
            </div>
        </div>
        <div class="card mb-4">
            <div class="card-header">Manage Maintenance</div>
            <div class="card-body">
                <form @submit.prevent="addMaintenance">
                    <div class="mb-3">
                        <label for="attraction" class="form-label">Attraction</label>
                        <select class="form-select" id="attraction" v-model="newMaintenance.attractionId" required>
                            <option v-for="attraction in attractions" :key="attraction.id" :value="attraction.id">
                                {{ attraction.name }}</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="maintenanceDate" class="form-label">Maintenance Date</label>
                        <input type="date" class="form-control" id="maintenanceDate" v-model="newMaintenance.date"
                            required>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Maintenance</button>
                </form>

                <div v-for="attraction in attractions" :key="attraction.id" class="mt-4">
                    <h5>{{ attraction.name }} - Maintenance Dates</h5>
                    <ul class="list-group">
                        <li v-for="maintenance in attraction.maintenanceDates" :key="maintenance.id"
                            class="list-group-item">
                            {{ maintenance.date }}
                            <button class="btn btn-danger btn-sm ms-2"
                                @click="deleteMaintenance(attraction.id, maintenance.id)">Delete</button>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div v-if="message" class="alert alert-info fixed-bottom mx-auto" style="width: 90%; max-width: 600px;"
            role="alert">
            {{ message }}
        </div>
        <div v-if="errorMessage" class="alert alert-danger fixed-bottom mx-auto" style="width: 90%; max-width: 600px;"
            role="alert">
            {{ errorMessage }}
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
            newBreakdown: {
                attractionId: '',
                dateReported: '',
                description: '',
                resolved: false,
                dateResolved: null
            },
            newMaintenance: {
                attractionId: '',
                date: ''
            },
            attractions: [],
            categories: [],
            breakdowns: [],
            message: "",
            errorMessage: ""

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
        async fetchBreakdowns() {
            try {
                const response = await axios.get('http://localhost:9000/breakdowns');
                this.breakdowns = response.data;
            } catch (error) {
                console.error('Error fetching breakdowns:', error);
            }
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
                this.errorMessage = "Category cannot be deleted because it has associated attractions !";
                setTimeout(() => {
                    this.errorMessage = "";
                }, 2000);
            }
        },
        async addBreakdown() {
            try {
                const { attractionId, dateReported, description } = this.newBreakdown;
                const breakdownData = {
                    attractionId,
                    dateReported,
                    description,
                    resolved: false,
                    dateResolved: null
                };
                const { data } = await axios.post('http://localhost:9000/breakdowns', breakdownData);
                this.breakdowns.push(data);
                this.message = "Breakdown registered successfully!";
                this.newBreakdown = {
                    attractionId: '',
                    dateReported: '',
                    description: '',
                    resolved: false,
                    dateResolved: null
                };
                setTimeout(() => {
                    this.message = "";
                }, 2000);
            } catch (error) {
                alert('Failed to register breakdown. Please try again later.');
            }
        },
        async resolveBreakdown(id) {
            try {
                await axios.put(`http://localhost:9000/breakdowns/resolve/${id}`);
                this.fetchBreakdowns();
                this.message = "Breakdown resolved successfully!";
                setTimeout(() => {
                    this.message = "";
                }, 2000);
            } catch (error) {
                alert('Failed to resolve breakdown. Please try again later.');
            }
        },
        async deleteBreakdown(id) {
            try {
                await axios.delete(`http://localhost:9000/breakdowns/${id}`);
                this.breakdowns = this.breakdowns.filter(breakdown => breakdown.id !== id);
                this.errorMessage = "Breakdown deleted successfully!";
                setTimeout(() => {
                    this.errorMessage = "";
                }, 2000);
            } catch (error) {
                alert('Failed to delete breakdown. Please try again later.');
            }
        },
        async addMaintenance() {
            try {
                const { attractionId, date } = this.newMaintenance;
                const maintenanceData = {
                    attractionId,
                    date
                };
                console.log(maintenanceData)
                const { data } = await axios.post('http://localhost:9000/maintenance/add', maintenanceData);
                const attraction = this.attractions.find(attraction => attraction.id === attractionId);
                if (attraction) {
                    attraction.maintenanceDates.push(data);
                }
                this.message = "Maintenance date added successfully!";
                this.newMaintenance = { attractionId: '', date: '' };
                setTimeout(() => {
                    this.message = "";
                }, 2000);
            } catch (error) {
                alert('Failed to add maintenance date. Please try again later.');
            }
        },
        async deleteMaintenance(attractionId, maintenanceId) {
            try {
                await axios.delete(`http://localhost:9000/maintenance/delete/${maintenanceId}`);
                const attraction = this.attractions.find(attraction => attraction.id === attractionId);
                if (attraction) {
                    attraction.maintenanceDates = attraction.maintenanceDates.filter(date => date.id !== maintenanceId);
                }
                this.message = "Maintenance date deleted successfully!";
                setTimeout(() => {
                    this.message = "";
                }, 2000);
            } catch (error) {
                alert('Failed to delete maintenance date. Please try again later.');
            }
        }
    },

    mounted() {
        this.fetchAttractions();
        this.fetchCategories();
        this.fetchBreakdowns();
    }
};
</script>
