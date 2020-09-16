import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import store from '../store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/opportunities',
    name : 'opportunities',
    component: () => import(/* webpackChunkName: "about" */ '../views/Opportunities.vue')
  },

  {
    path: '/signup',
    name : 'signup',
    component: () => import(/* webpackChunkName: "about" */ '../views/DoctorSignUpForm.vue')
  },
  {
    path: '/login',
    name : 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/sessionDetails/:id',
    name : 'sessionDetails',
    component: () => import(/* webpackChunkName: "about" */ '../views/SessionDetails.vue')
  },
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path == '/login') {
    next();
  }
  else if(!store.getters['session/authStatus']){
    next('/login');
  }
  else {
    next();
  }
})

export default router
