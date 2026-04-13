const root = document.documentElement;
const modeKey = 'inspection-dark-mode';

function renderTemplates() {
    const list = document.getElementById('templateList');
    if (!list) return;

    const templates = Array.from({ length: 120 }, (_, i) => `Template-${String(i + 1).padStart(3, '0')}.xlsx`);

    const refresh = (keyword = '') => {
        list.innerHTML = '';
        templates
            .filter(name => name.toLowerCase().includes(keyword.toLowerCase()))
            .forEach(name => {
                const item = document.createElement('div');
                item.className = 'template-item';
                item.textContent = name;
                item.onclick = () => {
                    window.location.href = `inspection_view.jsp?template=${encodeURIComponent(name)}`;
                };
                list.appendChild(item);
            });
    };

    refresh();
    document.getElementById('templateSearch')?.addEventListener('input', (e) => refresh(e.target.value));
}

function initDarkMode() {
    const toggle = document.getElementById('darkModeToggle');
    if (!toggle) return;

    const saved = localStorage.getItem(modeKey) || 'light';
    root.setAttribute('data-bs-theme', saved);

    toggle.addEventListener('click', () => {
        const current = root.getAttribute('data-bs-theme') === 'dark' ? 'light' : 'dark';
        root.setAttribute('data-bs-theme', current);
        localStorage.setItem(modeKey, current);
    });
}

function drawCharts() {
    const passFailCtx = document.getElementById('passFailChart');
    const trendCtx = document.getElementById('abnormalTrendChart');
    if (!passFailCtx || !trendCtx) return;

    new Chart(passFailCtx, {
        type: 'pie',
        data: {
            labels: ['Pass', 'Fail'],
            datasets: [{ data: [84, 16], backgroundColor: ['#198754', '#dc3545'] }]
        }
    });

    new Chart(trendCtx, {
        type: 'line',
        data: {
            labels: ['D-6', 'D-5', 'D-4', 'D-3', 'D-2', 'D-1', 'Today'],
            datasets: [{ label: 'Abnormal', data: [3, 2, 5, 4, 6, 2, 1], borderColor: '#ffc107' }]
        }
    });
}

renderTemplates();
initDarkMode();
drawCharts();
