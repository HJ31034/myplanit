let latitude;
let longitude;
let geocoder = new kakao.maps.services.Geocoder();
let weatherApiKey = 'baa44d84d3909b9d72cc1c43038d6858'

function getLatAndLong() {
    if (navigator.geolocation) { // GPS를 지원하면
        navigator.geolocation.getCurrentPosition(function(position) {
            console.log(position.coords.latitude + ' ' + position.coords.longitude);

            latitude = position.coords.latitude;
            longitude = position.coords.longitude;

            setLocation();
            setWeather();
        }, function(error) {
            console.error(error);
        });
    } else {
        alert('GPS를 지원하지 않습니다');
    }
}

function setLatAndLong(lat, lng) {
    if (latitude !== undefined) { // GPS를 지원하면
        latitude = lat;
        longitude = lng;
    }
}

function setLocation() {
    let coord = new kakao.maps.LatLng(latitude, longitude);
    let addr = document.getElementById('addr');

    geocoder.coord2Address(coord.getLng(), coord.getLat(), function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
            addr.innerText = result[0].address.region_3depth_name + ',';
        }
    });
}

function setWeather(){
    fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=${weatherApiKey}`)
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            let weather = document.getElementById('weather');
            weather.innerText = data.weather[0].main;
        })
        .catch((error) => {
            console.error('실패:', error);
            alert('날씨 정보 조회에 실패했습니다');
        });
}