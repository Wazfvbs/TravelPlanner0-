const selectedAttractions = new Set(); // 用于记录选中的景点ID

// 加载数据并渲染景点列表
async function fetchCityDetails(cityId) {
    try {
        const response = await fetch('CityServlet', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `cityId=${encodeURIComponent(cityId)}`,
        });

        if (!response.ok) {
            console.error("Failed to fetch data:", response.statusText);
            return;
        }

        const data = await response.json();
        if (data.error) {
            alert(data.error);
            return;
        }

        // 更新城市和省份信息
        document.getElementById("city-name").textContent = data.city.name;
        document.getElementById("province-name").textContent = data.province.name;

        const attractionList = document.getElementById("attraction-list");
        attractionList.innerHTML = '';

        data.attractions.forEach(attraction => {
            const card = createCard(attraction);
            attractionList.appendChild(card);

            // 如果已保存选中，设置卡片状态
            if (selectedAttractions.has(attraction.id)) {
                card.classList.add("selected");
            }
        });

        updateStatus(); // 渲染状态
    } catch (error) {
        console.error('Error fetching city details:', error);
    }
}

// 创建景点卡片
function createCard(attraction) {
    const card = document.createElement("div");
    card.className = "card";
    card.innerHTML = `
        <h3 class="attraction-name">${attraction.name}</h3>
        <p>星级: ${attraction.rank}</p>
        <p>喜爱数: ${attraction.guidesCount}</p>
    `;

    // 点击卡片切换选中状态
    card.addEventListener("click", () => toggleSelection(card, attraction.id));

    // 点击名称跳转详情页面
    const attractionName = card.querySelector(".attraction-name");
    attractionName.style.cursor = "pointer";
    attractionName.onclick = event => {
        event.stopPropagation(); // 阻止父元素点击事件
        saveSelection(); // 保存当前选择状态
        window.location.href = `AttractionDetailServlet?id=${attraction.id}`;
    };

    return card;
}

// 切换景点选中状态
function toggleSelection(card, attractionId) {
    if (card.classList.contains("selected")) {
        card.classList.remove("selected");
        selectedAttractions.delete(attractionId);
    } else {
        card.classList.add("selected");
        selectedAttractions.add(attractionId);
    }
    updateStatus(); // 更新状态
}

// 更新顶部状态信息和按钮显示
function updateStatus() {
    const visitStatus = document.getElementById("visit-status");
    const submitButton = document.getElementById("submit-btn");
    const selectedCount = selectedAttractions.size;

    visitStatus.textContent = selectedCount > 0 ? "我还想去：" : "我想去：";
    submitButton.style.display = selectedCount > 0 ? "block" : "none";
}

// 返回按钮逻辑
document.getElementById("back-btn").addEventListener("click", () => {
    history.back();
});

// 提交按钮逻辑
document.getElementById("submit-btn").addEventListener("click", () => {
    const selectedIds = Array.from(selectedAttractions);
    if (selectedIds.length === 0) {
        alert("请先选择景点！");
        return;
    }
    const queryString = `trip.jsp?attractions=${encodeURIComponent(selectedIds.join(','))}`;
    window.location.href = queryString;
    selectedAttractions.clear(); // 提交后清空选择
    updateStatus(); // 更新状态
});

// 保存当前选择到本地存储
function saveSelection() {
    localStorage.setItem('selectedAttractions', JSON.stringify(Array.from(selectedAttractions)));
}

// 加载本地存储的选择
function loadSelection() {
    const savedSelection = JSON.parse(localStorage.getItem('selectedAttractions'));
    if (savedSelection) {
        savedSelection.forEach(id => selectedAttractions.add(id));
    }
    updateStatus(); // 恢复状态后更新界面
}

document.addEventListener("DOMContentLoaded", () => {
    loadSelection();
    const cityId =request.getAttribute('cityId'); // 从请求中获取城市ID
    fetchCityDetails(cityId);
});
