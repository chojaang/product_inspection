<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko" data-bs-theme="light">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Inspection Editor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/luckysheet@2.1.13/dist/plugins/css/pluginsCss.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/luckysheet@2.1.13/dist/plugins/plugins.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/luckysheet@2.1.13/dist/css/luckysheet.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/luckysheet@2.1.13/dist/assets/iconfont/iconfont.css" />
    <link href="assets/css/style.css" rel="stylesheet" />
</head>
<body>
<div class="container-fluid py-3">
    <div class="d-flex justify-content-between mb-2">
        <h1 class="h4">점검표 편집</h1>
        <button id="saveBtn" class="btn btn-success">변경 데이터 저장</button>
    </div>
    <div id="luckysheet" style="height: 78vh;"></div>
</div>

<script src="https://cdn.jsdelivr.net/npm/luckysheet@2.1.13/dist/plugins/js/plugin.js"></script>
<script src="https://cdn.jsdelivr.net/npm/luckysheet@2.1.13/dist/luckysheet.umd.js"></script>
<script>
const changedCells = {};

luckysheet.create({
    container: 'luckysheet',
    data: [{name: 'Inspection', celldata: []}],
    hook: {
        cellUpdated: function (r, c, oldValue, newValue) {
            const cellRef = String.fromCharCode(65 + c) + (r + 1);
            changedCells[cellRef] = newValue?.v ?? '';

            const isRange = /^[B-D](?:[5-9]|[1-3][0-9])$/.test(cellRef);
            const numeric = Number(changedCells[cellRef]);
            if (isRange && !Number.isNaN(numeric) && (numeric < 0 || numeric > 200)) {
                luckysheet.setCellFormat(r, c, 'bg', '#ffcccc');
            }
        }
    }
});

async function saveInspection() {
    const payload = {
        templateName: 'Template-001.xlsx',
        category: 'Assembly',
        workerName: 'Operator',
        changedCells: changedCells
    };

    const response = await fetch('api/inspection/save', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(payload)
    });

    const result = await response.json();
    alert(result.ok ? `저장 완료 (#${result.id}, 상태=${result.status})` : `오류: ${result.message}`);
}

document.getElementById('saveBtn').addEventListener('click', saveInspection);
</script>
</body>
</html>
