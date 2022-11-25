<template>
  <div id="app">
    <div id="backImage"></div>
    <img src="@/assets/whitewall.png" class="whiteSpace" :class="{whiteDown:!this.$store.state.whiteSpace}"/>
    <new-nav-bar></new-nav-bar>
    <router-view  class="NanumSquareRound"/>	
  </div>
  
</template>
<script>
import NewNavBar from "@/components/NewNavBar.vue";
import ChannelService from '@/components/user/channelTalk.js'

export default {
  name: "App",
  components: {
	NewNavBar
  },
  created() {
	this.initChannalTalk();
  },
  methods: {
	initChannalTalk() {
		// Boot Channel as an anonymous user
		ChannelService.boot({
		"pluginKey": "22348274-16e6-4e8d-a029-41858a969858" //please fill with your plugin key
		});

		// Boot Channel as a registered user
		ChannelService.boot({
		"pluginKey": "22348274-16e6-4e8d-a029-41858a969858", //please fill with your plugin key
		"memberId": "해피하우스", //fill with user id
		"profile": {
			"name": "YOUR_USER_NAME", //fill with user name
			"mobileNumber": "YOUR_USER_MOBILE_NUMBER", //fill with user phone number
			"CUSTOM_VALUE_1": "VALUE_1", //any other custom meta data
			"CUSTOM_VALUE_2": "VALUE_2"
		}
		});

		// Shutdown Channel
	},
  }
};
</script>

<style scoped>
    .whiteSpace {
        z-index: -998;
        width: 100%;
        position: fixed;
        bottom: 0;
        transition: height 1s;
        height: 400px;
    }
    .whiteDown {
        height: 0px;
    }
    #backImage {
        z-index: -999;
        position: absolute;
        width: 100%;
        height: 100vh;
        background-image: url("@/assets/mainDoor.jpg");
        background-size: cover;
    }
</style>
<style>
@import url('https://fonts.googleapis.com/css2?family=Righteous&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Archivo+Black&display=swap');
.BMJUA {
	font-family:BMJUA;
}
.NanumSquareRound {
	font-family: 'NanumSquareRound';
}
.hoverPointer:hover {
	cursor: pointer;
}

button {
    outline: none;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
@font-face {
  font-family: "NanumSquareRound";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff") format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
    font-family: 'BMJUA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMJUA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>
