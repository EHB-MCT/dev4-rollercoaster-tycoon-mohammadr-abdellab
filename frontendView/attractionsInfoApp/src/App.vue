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
                <RouterLink class="nav-link" exact to="/items">Admin Dashboard</RouterLink>
              </li>
              <li class="nav-item">
                <RouterLink class="nav-link" exact to="/loans">Favorites</RouterLink>
              </li>
            </ul>

            <span style="color: white;" class="navbar-text ms-auto" v-if="loggedInUser">Welcome, {{ loggedInUser.name
              }}</span>

            <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <button style="margin-left: 10px;" class="nav-link btn btn-primary" @click="logout">Login</button>
              </li>
            </ul>

          </div>
        </div>
      </nav>
    </div>
  </header>

  <RouterView />

</template>
