import { createRouter, createWebHistory } from 'vue-router';
import home from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import DashboardView from '../views/DashboardView.vue'
import EditAttraction from '../views/EditAttractionView.vue'



const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: home
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: DashboardView,
      meta: { requiresAdmin: true }
    },
    {
      path: '/edit-attraction/:id',
      name: 'EditAttraction',
      component: EditAttraction,
      props: true
    }
  ]
})

router.beforeEach((to, from, next) => {
  const isAdmin = checkAdminStatus();
  if (to.matched.some(record => record.meta.requiresAdmin) && !isAdmin) {
    next('/');
  } else {
    next();
  }
});

function checkAdminStatus() {
  const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
  return loggedInUser && loggedInUser.role === 'admin';

}

export default router
