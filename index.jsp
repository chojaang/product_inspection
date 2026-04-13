<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko" data-bs-theme="light">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Inspection Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet" />
    <link href="assets/css/style.css" rel="stylesheet" />
</head>
<body>
<div class="container-fluid py-3">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0">Excel-Based Inspection Dashboard</h1>
        <button id="darkModeToggle" class="btn btn-outline-secondary"><i class="fa-solid fa-moon"></i> Dark Mode</button>
    </div>

    <div class="row g-3">
        <div class="col-lg-3">
            <div class="card h-100">
                <div class="card-body">
                    <h2 class="h6">점검표 템플릿</h2>
                    <input id="templateSearch" class="form-control form-control-sm mb-2" placeholder="템플릿 검색" />
                    <div id="templateList" class="template-list"></div>
                </div>
            </div>
        </div>

        <div class="col-lg-9">
            <div class="row g-3">
                <div class="col-md-6"><div class="card"><div class="card-body"><canvas id="passFailChart"></canvas></div></div></div>
                <div class="col-md-6"><div class="card"><div class="card-body"><canvas id="abnormalTrendChart"></canvas></div></div></div>
                <div class="col-12"><a href="inspection_view.jsp" class="btn btn-primary">점검 입력 화면 이동</a></div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.2/dist/chart.umd.min.js"></script>
<script src="assets/js/dashboard.js"></script>
</body>
</html>
