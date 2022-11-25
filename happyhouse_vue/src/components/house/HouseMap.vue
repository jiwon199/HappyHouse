<template>
  <div class="mapArea" id="mapArea">
    <ul id="category">
      <li
        v-for="(item, index) in category"
        :key="index"
        :item="item"
        :id="item.id"
        :data-order="item.data_order"
        :class="item.class"
        @click="onClickCategory(item.id)"
      >
        <span :class="item.span_class"></span>{{ item.text }}
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex";

const houseStore = "houseStore";

export default {
  name: "HouseMap",
  data() {
    return {
      map: null,
      main_markers: [], //aptMarker
      markers: [], //카테고리 markers
      placeOverlay: null,
      contentNode: null,
      ps: null,
      infoWindow:null,
      currCategory: "",
      category: [
        { id: "BK9", data_order: "0", class: "", text: "은행", span_class: "category_bg bank" },
        { id: "MT1", data_order: "1", class: "", text: "마트", span_class: "category_bg mart" },
        { id: "PM9", data_order: "2", class: "", text: "약국", span_class: "category_bg pharmacy" },
        { id: "OL7", data_order: "3", class: "", text: "주유소", span_class: "category_bg oil" },
        { id: "CE7", data_order: "4", class: "", text: "카페", span_class: "category_bg cafe" },
        { id: "CS2", data_order: "5", class: "", text: "편의점", span_class: "category_bg store" },
      ],
    };
  },

  //지도 초기화 하기
  mounted() {
    const script = document.createElement("script");
    /* global kakao */
    script.onload = () => kakao.maps.load(this.initMap);
    script.src =
      "//dapi.kakao.com/v2/maps/sdk.js?appkey=7421f111a6cf185511db9211279df5aa&autoload=false&libraries=services";
    document.head.appendChild(script);
  },
  computed: {
    ...mapState(houseStore, ["houses"]),
  },

  watch: {
    //집을 조회할때마다 호출됨
    houses() {
      //좌표들을 모두 지도에 찍는다.
      this.addAllMarker();
    },
  },

  methods: {
    //지도를 초기화하는 함수
    initMap() {
      const container = document.getElementById("mapArea");
      const option = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3, // 지도의 확대 레벨
      };
      this.map = new kakao.maps.Map(container, option);
      //주변 편의시설 보기 위한 카테고리 추가
      this.initCategory();
    },

    //지도에 마커 하나를 더하는 함수
    addAptMarker(position, apartMentName) {
      var marker = new kakao.maps.Marker({
        position: position,
      });
      if (apartMentName != null) {
        marker.setTitle(apartMentName);
      }
      marker.setMap(this.map);

      this.main_markers.push(marker);
    },

    //클릭한 위치로 지도 중심 옮기는 함수
    moveCenter(lat, lng, apartMentName) {
      this.addAptMarker(new kakao.maps.LatLng(lat, lng), apartMentName);
      var moveLatLon = new kakao.maps.LatLng(lat, lng);

      var iwContent = '<div style="padding:5px; font-family: "NanumSquareRound"; ">'+apartMentName +'</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(lat, lng); //인포윈도우 표시 위치입니다

      var marker = this.findMarker(apartMentName);
       
      if(this.infoWindow!=null)this.infoWindow.close();
      // 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({
        position: iwPosition,
        content: iwContent,
        removable: true,
      });

      // 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
      infowindow.open(this.map, marker);
      this.infoWindow=infowindow;
      this.map.panTo(moveLatLon);
    },

    //아파트명이 주어지면 마커리스트에서 해당 마커를 반환
    findMarker(apartMentName) {
      var marker;
      for (var i = 0; i < this.main_markers.length; i++) {
        var title = this.main_markers[i].getTitle();
        if (title == apartMentName) marker = this.main_markers[i];
      }
      return marker;
    },
     
    //리스트에 있는 모든 아파트의 마커 찍는 함수
    addAllMarker() {
      for (var i = 0; i < this.houses.length; i++) {
        this.addAptMarker(new kakao.maps.LatLng(this.houses[i].lat, this.houses[i].lng), this.houses[i].apartmentName);
      }
      if (this.houses.length >= 1) this.moveCenter(this.houses[0].lat, this.houses[0].lng,this.houses[0].apartmentName);

      //if (this.$store.state.logined_id) this.$refs.selectInfo.setInfo();
    },
    //주변 편의시설 카테고리 만드는 함수
    initCategory() {
      // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
      (this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 })),
        (this.contentNode = document.createElement("div")), // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
        (this.markers = []), // 마커를 담을 배열입니다
        (this.currCategory = ""); // 현재 선택된 카테고리를 가지고 있을 변수입니다

      // 장소 검색 객체를 생성합니다
      this.ps = new kakao.maps.services.Places(this.map);

      // 지도에 idle 이벤트를 등록합니다
      kakao.maps.event.addListener(this.map, "idle", this.searchPlaces);

      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
      this.contentNode.className = "placeinfo_wrap";

      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(this.contentNode, "mousedown", kakao.maps.event.preventMap);
      this.addEventHandle(this.contentNode, "touchstart", kakao.maps.event.preventMap);

      // 커스텀 오버레이 컨텐츠를 설정합니다
      this.placeOverlay.setContent(this.contentNode);
    },

    // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },

    // 카테고리 검색을 요청하는 함수입니다
    searchPlaces() {
      if (!this.currCategory) {
        return;
      }

      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      this.ps.categorySearch(this.currCategory, this.placesSearchCB, { useMapBounds: true });
    },
    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.displayPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
      }
    },

    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      var order = document.getElementById(this.currCategory).getAttribute("data-order");

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        (function (marker, place) {
          kakao.maps.event.addListener(marker, "click", function () {
            this.displayPlaceInfo(place);
          });
        })(marker, places[i]);
      }
    },

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, order) {
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },

    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },

    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수
    displayPlaceInfo(place) {
      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          this.markers;
        place.address_name + '">(지번 : ' + place.address_name + ")</span>";
      } else {
        content += '    <span title="' + place.address_name + '">' + place.address_name + "</span>";
      }

      content += '    <span class="tel">' + place.phone + "</span>" + "</div>" + '<div class="after"></div>';

      this.contentNode.innerHTML = content;
      this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.map);
    },

    // 카테고리를 클릭했을 때 호출되는 함수입니다
    onClickCategory(chd_id) {
      var id = chd_id,
        className = document.getElementById(id).className;
      this.placeOverlay.setMap(null);
      if (className === "on") {
        this.currCategory = "";
        this.changeCategoryClass();
        this.removeMarker();
      } else {
        this.currCategory = id;
        this.changeCategoryClass(id);
        this.searchPlaces();
      }
    },

    // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수
    changeCategoryClass(chd_id) {
      var category = document.getElementById("category"),
        children = category.children;
      //클래스이름 공백으로 바꾸고
      for (var i = 0; i < children.length; i++) {
        children[i].className = "";
      }
      document.getElementById(chd_id).className = "on";
    },
  },
};
</script>
<style>
.mapArea {
  width: 83%;
  height: 75vh;
  font-family: "NanumSquareRound";
  display: inline-block;
}
.infoWindow {
  padding: 5px;
  text-align: center;
  float: center;
  width: 150px;
  border: 1px solid black;
  background: white;
  border-radius: 3px;
  margin-bottom: 2.7em;
}
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 350px;
}
#category {
  position: absolute;
  top: 10px;
  right: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png) no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png) no-repeat right 14px
    center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
ul {
  padding-inline-start: 0px;
}
.info-title {
  display: block;
  background: #50627f;
  color: #fff;
  text-align: center;
  height: 24px;
  line-height: 22px;
  border-radius: 4px;
  padding: 0px 10px;
}
</style>
