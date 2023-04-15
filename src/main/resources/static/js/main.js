function drawLineChartWithRandomBorderColor(labels, chartLines) {
    const canvas = document.getElementById('myChart');
    const ctx = canvas.getContext('2d');

    const chartData = {
        labels: labels,
        datasets: chartLines.map((lineData, i) => {
            return {
                label: lineData.name,
                data: lineData.data,
                borderColor: `rgb(${Math.random() * 255}, ${Math.random() * 255}, ${Math.random() * 255})`,
                fill: false
            };
        })
    };

    const chartOptions = {
        plugins: {
            legend: {
                display: false
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    };

    new Chart(ctx, {
        type: 'line',
        data: chartData,
        options: chartOptions
    });
}

function drawLineChartForCentroidClusters(labels, chartLines) {
    const canvas = document.getElementById('myChart');
    const ctx = canvas.getContext('2d');

    const chartData = {
        labels: labels,
        datasets: chartLines.map((lineData, i) => {
            return {
                label: lineData.name,
                data: lineData.data,
                borderColor: lineData.color,
                fill: false
            };
        })
    };

    const chartOptions = {
        plugins: {
            legend: {
                display: false
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    };

    new Chart(ctx, {
        type: 'line',
        data: chartData,
        options: chartOptions
    });
}