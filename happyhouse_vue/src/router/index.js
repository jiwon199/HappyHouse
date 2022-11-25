import Vue from 'vue'
import VueRouter from 'vue-router'
import MainView from '../views/MainView.vue'
import HouseView from '../views/HouseView.vue'
import ChartView from '../views/ChartView'
import axios from 'axios'

Vue.use(VueRouter)

const onlyAuthUser = async (to, from, next) => {
    if (sessionStorage.getItem("token") == null) {
        alert("로그인 사용자만")
        return; //토큰있는지 확인하고
    }
    axios.get("http://localhost:9999/rest/user/getTokenInfo", { //있다면 토큰이 유효한지 확인함
        headers: {
            "Authentication": sessionStorage.getItem("token"),
        }
    })
        .then(() => {
            next();
        }) //유효하다면 vuex에 토큰 정보입력 메서드실행해서 state의 로그인 정보 입력해줌
        .catch(() => {
            alert("토큰이 만료됨"); //유효하지 않다면 경고창
            sessionStorage.removeItem("token");
            this.clearLoginInfo();
        })
};

const routes = [
    {
        path: '/',
        name: 'main',
        component: MainView
    },
    {
        path: '/house',
        name: 'house',
        component: HouseView
    },
    {
        path: '/chart',
        name: 'chart',
        component: ChartView
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/LoginView.vue')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/RegisterView.vue')
    },
    {
        path: "/interestarea",
        name: "interestarea",
        component: () => import("@/components/user/InterestArea")
    },
    {
        path: "/edituser",
        name: "edituser",
        component: () => import("@/components/user/TheEditUser")
    },
    {
        path: "/adminConsole",
        name: "adminConsole",
        component: () => import("@/components/user/AdminConsole")
    },
	{
        path: "/adminConsole/userList",
        name: "userList",
        component: () => import("@/components/user/UserList")
    },
	{
		path: "/deleteuser",
		name: "deleteuser",
		component: () => import("@/components/user/DeleteUser")
	},
    {
        path: '/community',
        name: 'community',
        component: () => import('@/views/CommunityView'),
        redirect: "/community/list",
        children: [
            {
                path: "list",
                name: "communityList",
                component: () => import("@/components/community/board/CommunityList.vue")
            },
            {
                path: "write",
                name: "communityWrite",
                beforeEnter: onlyAuthUser,
                component: () => import("@/components/community/board/CommunityWrite.vue")
            },
            {
                path: "/detail/",
                name: "communityDetail",
                component: () => import("@/components/community/board/CommunityDetail.vue")
            },
			{
                path: "edit",
                name: "communityEdit",
                component: () => import("@/components/community/board/CommunityEdit.vue")
            },
        ]
    },
    {
        path: '/notice',
        name: 'notice',
        component: () => import('@/views/NoticeView')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
