function drawLineChart(labels, data, lineNames) {
    const canvas = document.getElementById('myChart');
    const ctx = canvas.getContext('2d');

    const chartData = {
        labels: labels,
        datasets: data.map((lineData, i) => {
            return {
                label: lineNames[i],
                data: lineData,
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