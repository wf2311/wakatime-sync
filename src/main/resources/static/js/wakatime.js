const API_DOMAIN = "";
// const colors = ["#FF0000", "#FFFF00", "#008B8B", "#7FFFD4", "#FFFAFA", "#0000FF", "#8A2BE2", "#A52A2A", "#000000", "#7FFF00", "#80000040", "#FF7F50", "#6495ED", "#DC143C", "#00FFFF", "#B8860B", "#A9A9A9", "#006400", "#FFDAB9", "#8B008B", "#FF00FF", "#483D8B", "#2F4F4F", "#D2B48C"];
window.projects = [];
const CHART_WIDTH = 1200;

function groupBy(list, keyGetter) {
    const map = new Map();
    list.forEach((item) => {
        const key = keyGetter(item);
        const collection = map.get(key);
        if (!collection) {
            map.set(key, [item]);
        } else {
            collection.push(item);
        }
    });
    return map;
}

function formatDay(date) {
    return moment(date).format('YYYY-MM-DD')
}

Array.prototype.sum = function (prop) {
    let total = 0;
    for (let i = 0, _len = this.length; i < _len; i++) {
        total += this[i][prop];
    }
    return total
}

function formatDurations(seconds) {
    let date = new Date(null);
    date.setSeconds(seconds);
    return date.toISOString().substr(11, 8);
}

function getEveryProjectDayDurations(data) {
    let dataMap = groupBy(data, d => d.project);
    const duMap = new Map();
    dataMap.forEach((v, k) => {
        if (window.projects.indexOf(k) < 0) {
            window.projects.push(k);
        }
        const seconds = v.sum("duration");
        let text = formatDurations(seconds);
        duMap.set(k, {
            "duration": seconds,
            "text": text
        })
    });
    return duMap;
}

let durationChart;
let activityChart;
let circleGroupEditorChart;
let circleGroupLanguageChart;
let circleGroupActionChart;
let circleGroupSystemChart;

function getDayDurations(date) {
    let data = [];
    $.ajax({
        url: API_DOMAIN + '/api/v1/durations',
        dataType: 'json',
        type: 'get',
        data: {
            date: date
        },
        async: false,
        headers: {
            Accept: "application/json; charset=utf-8"
        }, success: function (rs) {
            data = rs.data;
        }
    });
    const day = moment(date);

    let duMap = getEveryProjectDayDurations(data);
    if (duMap.size > 1) {
        var t = [];
        let p = '合计';
        data.forEach(function (o) {
            t.push({
                project: p,
                startTime: o.startTime,
                duration: o.duration
            })
        });
        t.forEach(function (o) {
            data.push(o);
        });
        const ss = t.sum("duration");
        let text = formatDurations(ss);
        duMap.set(p, {
            "duration": ss,
            "text": text
        });

    }

    data.forEach(function (obj) {
        const start = moment(obj.startTime).unix() - day.unix();
        obj.range = [start / 3600, (obj.duration + start) / 3600];
        obj.text = obj.project + "  " + duMap.get(obj.project).text;
        // obj.index = window.projects.indexOf(obj.project) % 24;
    });
    durationChart.changeHeight(50 + duMap.size * 24);
    return data;
}

function getRangeDurations(start, end, showAll) {
    let data = [];
    $.ajax({
        url: API_DOMAIN + '/api/v1/range/durations',
        dataType: 'json',
        type: 'get',
        data: {
            start: start,
            end: end,
            showAll: showAll,
        },
        async: false,
        headers: {
            Accept: "application/json; charset=utf-8"
        }, success: function (rs) {
            data = rs.data;
        }
    });

    var dd = [];
    data.forEach(function (obj) {
        let duration = obj.total / 3600;
        dd.push([obj.date, duration.toFixed(2)]);
    });
    return dd;
}

function getSummaries(start, end) {
    var summaries = {};
    $.ajax({
        url: API_DOMAIN + '/api/v1/summaries',
        dataType: 'json',
        type: 'get',
        data: {
            start: start,
            end: end
        },
        async: false,
        headers: {
            Accept: "application/json; charset=utf-8"
        }, success: function (rs) {
            summaries = rs.data;
        }
    });
    return summaries;
}

function initDurations(date) {
    if (!window.projects) {
        window.projects = [];
    }
    durationChart = new G2.Chart({
        container: 'durations',
        forceFit: false,
        height: 300,
        width: CHART_WIDTH,
        padding: {top: 20, right: 50, bottom: 20, left: 260},
        theme: 'default'
    });
    let data = getDayDurations(date);
    $('#durations').parents('.graph-item').css('display', 'block');
    durationChart.source(data, {
        range: {
            type: 'linear',
            min: 0,
            max: 24,
            tickCount: 25
        }
    });
    durationChart.axis('range', {
        grid: {
            type: 'line',
            lineStyle: {
                stroke: '#d9d9d9',
                lineWidth: 1,
                lineDash: [3, 999999] // 网格线的虚线配置，第一个参数描述虚线的实部占多少像素，第二个参数描述虚线的虚部占多少像素
            },
        },
        line: {
            lineWidth: 1
        }, label: {
            formatter(text, item, index) {
                if (text < 10) {
                    return '0' + text;
                }
                if (text === '24') {
                    return '00';
                }
                return text;
            },
        }
    });
    durationChart.axis('text', {
        grid: {
            align: 'center',
            type: 'line'
        }
    });
    durationChart.coord().transpose().scale(1, -1);
    durationChart.interval().tooltip(false).size(20).position('text*range').color('project');
    durationChart.render();
}

function initSummaries(start, end) {
    if (!window.projects) {
        window.projects = [];
    }
    let summaries = getSummaries(start, end);
    if (summaries && summaries.projects.length > 0) {
        $('#coding-activity').parents('.graph-item').css('display', 'block');
    }
    activityChart = createActivityChart();
    var ads = new DataSet();
    var adv = ads.createView().source(summaries.projects);
    adv.transform({
        type: 'map',
        callback(row) { // 加工数据后返回新的一行，默认返回行数据本身
            if (window.projects.indexOf(row.name) < 0) {
                window.projects.push(row.name);
            }
            row.index = window.projects.indexOf(row.name);
            row.duration = row.seconds / 3600;
            return row;
        }
    }).transform({
        type: 'sort-by',
        fields: ['day', 'duration'], // 根据指定的字段集进行排序，与lodash的sortBy行为一致
        order: 'DESC'        // 默认为 ASC，DESC 则为逆序
    });
    let dayMap = groupBy(summaries.projects, p => p.day);
    let size = (CHART_WIDTH) * 0.93 / dayMap.size;
    let tickCount = dayMap.size < 15 ? dayMap.size + 1 : 8;
    activityChart.source(adv, {
        day: {
            type: 'timeCat', // 声明该数据的类型
            tickCount: tickCount
        }
    });

    activityChart.legend(false);
    activityChart.interval().tooltip('name*seconds', function (name, seconds) {
        return {
            name: name,
            value: formatDurations(seconds)
        };
    }).position('day*duration').size(size).color('name').adjust([{
        type: 'stack',
    }]);
    activityChart.axis('duration', {
        grid: {
            type: 'line',
            lineStyle: {
                stroke: '#d9d9d9',
                lineWidth: 1,
                lineDash: [3, 999999] // 网格线的虚线配置，第一个参数描述虚线的实部占多少像素，第二个参数描述虚线的虚部占多少像素
            },
        },
        line: {
            lineWidth: 1
        }
    });
    activityChart.render();
    circleGroupEditorChart = createCircleChart('group-editor');
    circleGroupLanguageChart = createCircleChart('group-language');
    circleGroupActionChart = createCircleChart('group-action');
    circleGroupSystemChart = createCircleChart('group-system');
    initDayTypeCircleChart(circleGroupEditorChart, summaries.editors);
    initDayTypeCircleChart(circleGroupLanguageChart, summaries.languages);
    initDayTypeCircleChart(circleGroupActionChart, summaries.categories);
    initDayTypeCircleChart(circleGroupSystemChart, summaries.operatingSystems);
}

function createActivityChart() {
    return new G2.Chart({
        container: 'coding-activity',
        forceFit: false,
        height: 360,
        padding: {top: 20, right: 40, bottom: 20, left: 40},
        width: CHART_WIDTH,
    });
}

function createCircleChart(id) {
    return new G2.Chart({
        container: id,
        forceFit: false,
        height: 300,
        width: 300,
        padding: 50,
    });
}

function initDayMap(start, end, showAll) {
    var myChart = echarts.init(document.getElementById('day-map'));
    var data = getRangeDurations(start, end, showAll);
    if (data.length > 1) {
        $('#day-map').parents('.graph-item').css('display', 'block');
    } else {
        return;
    }
    var range = [data[0][0], data[data.length - 1][0]];
    let days = moment(range[1]).diff(moment(range[0]), 'days');
    let width = days / 7 * 20 + 200;
    $('#day-map').css('width', width + 'px');
    $('#day-map').children('div:first-child').css('width', width + 'px');
    myChart.resize();
    option = {
        tooltip: {
            position: 'top',
            formatter: function (p) {
                var format = echarts.format.formatTime('yyyy-MM-dd', p.data[0]);
                return format + ': ' + p.data[1] + "小时";
            }
        },

        visualMap: {
            show: false,
            min: 0,
            max: 24,
            calculable: true,
            orient: 'horizontal',
            left: 'center',
            top: 'top'
        },
        calendar: [
            {
                range: range,
                orient: 'horizontal',
                dayLabel: {
                    firstDay: 1,
                    nameMap: 'cn'

                },
                monthLabel: {
                    nameMap: 'cn'
                },
                cellSize: [20, 20]
            }],

        series: [{
            type: 'heatmap',
            coordinateSystem: 'calendar',
            data: data
        }]

    };

    myChart.setOption(option);
}

function updateDurations(date) {
    let data = getDayDurations(date);
    durationChart.changeData(data);
}

function updateSummaries(start, end) {
    if (activityChart) {
        activityChart.destroy()
    }
    circleGroupEditorChart.destroy();
    circleGroupLanguageChart.destroy();
    circleGroupActionChart.destroy();
    circleGroupSystemChart.destroy();
    initSummaries(start, end);
}

function initDayTypeCircleChart(chart, data) {
    var ds = new DataSet();
    var dv = ds.createView().source(data);
    dv.transform({
        type: 'percent',
        field: 'seconds',
        dimension: 'id',
        as: 'percent'
    });
    chart.source(dv);
    chart.legend(false);
    chart.coord('theta');
    chart.tooltip({
        showTitle: false
    });

    formatDurationAndPercent = function (seconds, percent) {
        let hour = seconds / 3600;
        return hour.toFixed(2) + '小时(' + percent + ')';
    };
    chart.intervalStack().position('seconds').color('id').opacity(1)
        .label('percent*id', {
            offset: -5,
            textStyle: {
                fill: 'white',
                fontSize: 11,
                shadowBlur: 2,
                shadowColor: 'rgba(0, 0, 0, .45)'
            },
            formatter: function formatter(percent, obj) {
                let p = parseInt(percent * 100);
                if (p < 3) {
                    return " ";
                }
                return obj.point.id + '(' + p + '%)';
            }
        }).tooltip('id*seconds*percent', function (id, seconds, percent) {
        percent = (percent * 100).toFixed(2) + '%';
        return {
            name: id,
            value: formatDurationAndPercent(seconds, percent)
        };
    });
    chart.render();
}