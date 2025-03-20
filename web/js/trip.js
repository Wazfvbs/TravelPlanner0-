document.addEventListener("DOMContentLoaded", loadSelectedAttractions);

var map = new AMap.Map('map', { resizeEnable: true, zoom: 10 });

function loadSelectedAttractions() {
    fetch("TripServlet?action=selectedAttractions&ids=" + getSelectedIds())
        .then(response => response.json())
        .then(data => {
            if (Array.isArray(data)) {
                displaySelectedAttractions(data);
                plotMarkersOnMap(data);
                drawRoute(data); // 调用路径规划
            } else {
                alert("加载失败，请稍后重试！");
            }
        })
        .catch(err => console.error("加载景点数据出错:", err));
}

function getSelectedIds() {
    // 假设景点 ID 通过 query 参数传递
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.getAll("id").join(",");
}

function displaySelectedAttractions(attractions) {
    const list = document.getElementById("selectedAttractionsList");
    list.innerHTML = "";
    attractions.forEach(attraction => {
        const listItem = document.createElement("li");
        listItem.textContent = `${attraction.name} (${attraction.cityName})`;
        listItem.onclick = () => map.setCenter([attraction.latitude - 0.0063, attraction.longitude - 0.00605]);
        list.appendChild(listItem);
    });
}

function plotMarkersOnMap(attractions) {
    attractions.forEach(attraction => {
        new AMap.Marker({
            position: [attraction.latitude - 0.0063, attraction.longitude - 0.00605],
            title: attraction.name,
            map: map
        });
    });

    if (attractions.length > 0) {
        map.setCenter([attractions[0].latitude- 0.0063, attractions[0].longitude- 0.00605]);
    }
}

// 初始化地图
function initializeMap(attractions) {
    var map = new AMap.Map('map', {
        resizeEnable: true,
        zoom: 10
    });

    if (attractions.length > 0) {
        var markers = [];
        attractions.forEach(attraction => {
            const marker = new AMap.Marker({
                position: [attraction.lat- 0.0063, attraction.lng- 0.00605],
                title: attraction.name,
                map: map
            });
            markers.push(marker);
        });

        // 设置地图视野覆盖所有点
        map.setFitView(markers);

        // 默认定位到第一个景点
        map.setCenter([attractions[0].lat, attractions[0].lng]);
    } else {
        console.error("No attractions data provided.");
    }
}



// 绘制路径
function drawRoute(attractions) {
    if (attractions.length < 2) {
        console.warn("至少需要两个点才能规划路径");
        return;
    }

    const waypoints = attractions.slice(1, -1).map(attraction => ({
        location: [attraction.longitude, attraction.latitude]
    }));

    const driving = new AMap.Driving({
        map: map,
        panel: "routePanel" // 可选，显示详细路径信息
    });

    driving.search(
        [attractions[0].longitude, attractions[0].latitude], // 起点
        [attractions[attractions.length - 1].longitude, attractions[attractions.length - 1].latitude], // 终点
        { waypoints: waypoints } // 途经点
    );
}

// 聚焦某个点
function focusMarker(lat, lng) {
    map.setCenter([lng, lat]);
}
