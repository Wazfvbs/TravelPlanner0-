document.addEventListener("DOMContentLoaded", () => {loadProvinces();});

var map = new AMap.Map('map', {
    resizeEnable: true,
    zoom:14,
    center: [126.634844,45.7208]

});

//点标记的创建与添加
var marker = new AMap.Marker({
    position: [126.634844,45.7208],
    map:map
});

// 加载省份数据
function loadProvinces() {
    fetch("MapServlet?action=provinces")
        .then(response => response.json())
        .then(data => {
            const provinceSelect = document.getElementById("province");
            provinceSelect.innerHTML = "<option value=''>选择省份</option>";
            data.forEach(province => {
                const option = document.createElement("option");
                option.value = province.id;
                option.textContent = province.name;
                provinceSelect.appendChild(option);
            });
        })
        .catch(err => console.error("加载省份数据出错:", err));
}

// 加载城市数据
function loadCities(provinceId) {
    fetch(`MapServlet?action=cities&province_id=${provinceId}`)
        .then(response => response.json())
        .then(data => {
            const citySelect = document.getElementById("city");
            citySelect.innerHTML = "<option value=''>选择城市</option>";
            data.forEach(city => {
                const option = document.createElement("option");
                option.value = city.id;
                option.textContent = city.name;
                citySelect.appendChild(option);
            });
        })
        .catch(err => console.error("加载城市数据出错:", err));
}

// 定位到选定城市
function locateCity() {
    const citySelect = document.getElementById("city");
    const cityName = citySelect.options[citySelect.selectedIndex]?.text || "";
    if (!cityName) {
        alert("请选择城市！");
        return;
    }

    AMap.plugin('AMap.Geocoder', function () {
        const geocoder = new AMap.Geocoder();
        geocoder.getLocation(cityName, (status, result) => {
            if (status === 'complete' && result.geocodes.length) {
                const location = result.geocodes[0].location;
                map.setCenter([location.lng, location.lat]);
            } else {
                alert("定位失败！");
            }
        });
    });
}

// 显示景点
function showAttractions() {
    map.clearMap();
    const citySelect = document.getElementById("city");
    const cityName = citySelect.options[citySelect.selectedIndex]?.text || "";
    const limit = document.getElementById("limit").value || 10; // 默认显示10条

    if (!cityName) {
        alert("请选择城市！");
        return;
    }

    fetch(`AttractionServlet?city=${encodeURIComponent(cityName)}&limit=${limit}`)
        .then(response => response.json())
        .then(data => {
            console.log("Loaded attractions:", data); // 验证返回数据
            displayAttractions(data);
        })
        .catch(err => console.error("加载景点数据出错:", err));

}


// 展示景点数据
// 展示景点数据
function displayAttractions(attractions) {
    const listContainer = document.getElementById("attractionsList");
    if (!listContainer) {
        console.error("无法找到景点列表容器，请检查 HTML 中是否存在 id='attractionsList' 的元素。");
        return;
    }

    if (!Array.isArray(attractions)) {
        console.error("景点数据格式错误：", attractions);
        alert("加载景点失败，请稍后重试！");
        return;
    }

    listContainer.innerHTML = ""; // 清空列表
    attractions.forEach(attraction => {
        const item = document.createElement("div");
        item.className = "attraction-item";
        item.innerHTML = `
            <div class="attraction-title">${attraction.name}</div>
            <div>评论数: ${attraction.commentsCount}</div>
            <div>攻略数: ${attraction.guidesCount}</div>
            <div>排名: ${attraction.rank || "暂无"}</div>
            <div>星级: ${attraction.star || "暂无"}</div>
        `;

        // 鼠标悬停事件
        item.onmouseover = () => {
            map.setCenter([attraction.latitude - 0.0063, attraction.longitude - 0.00605]);
        };

        // 点击查看详情
        item.onclick = () => {
            window.location.href = `AttractionDetailServlet?id=${attraction.id}`;
        };

        listContainer.appendChild(item);

        // 在地图上标记
        new AMap.Marker({
            position: [attraction.latitude - 0.0063, attraction.longitude - 0.00605],
            title: attraction.name,
            map: map
        });
    });

    if (attractions.length > 0) {
        const first = attractions[0];
        map.setCenter([first.latitude - 0.0063, first.longitude - 0.00605]);
    }
}

