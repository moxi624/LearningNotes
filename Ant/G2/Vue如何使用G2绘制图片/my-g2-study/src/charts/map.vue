<template>
  <MyEcharts ref="chart" width="100%" height="100%" :id="chartId" :option="option"></MyEcharts>
</template>

<script>
import MyEcharts from "@/components/echarts/Index.vue";
import guangxi from "echarts/map/json/province/guangxi";
import echarts from 'echarts'
export default {
  name: "Map",
  components: {
    MyEcharts
  },
  data() {
    return {
      chartId: "map",
      mapType: "guangxi",
      geoJSON: guangxi
    };
  },
  computed: {
    option() {
        var randomCoord = function () {
            let minLongitude = 107.01; //经度
            let maxLongitude = 110.54; //经度

            let minLatitude = 22.34; //纬度
            let maxLatitude = 25.07; //纬度


            let deltaLong = Math.random() * (maxLongitude - minLongitude);
            let deltaLat = Math.random() * (maxLatitude - minLatitude);

            return {
                lat: minLatitude + deltaLat,
                long: minLongitude + deltaLong
            }
        }

        var getSeriesData = function (prefix, value, count) {
            let data = [];
            for (let i = 0; i < count; ++i) {
                let r = randomCoord();
                let d = {
                    name: prefix + (i + 1).toString(),
                    value: [r.long, r.lat, value]
                }
                data.push(d);
            }
            return data;
        }

      return {
            geo: {
                map: 'guangxi',
                itemStyle: {
                    normal: {
                        areaColor: '#032338',
                        borderColor: '#9eb4ca'
                    },
                    emphasis: {
                        areaColor: '#2a333d'
                    }
                },
                label: { //不显示地区
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                }
            },
            series: [{ //添加一个系列来显示城市，值不要在视觉组件范围内
                    name: 'city',
                    type: 'scatter', //散点图
                    coordinateSystem: 'geo',
                    data: [{
                            name: "桂林市",
                            value: [110.20, 25.24, 99]
                        },
                        {
                            name: "南宁市",
                            value: [108.37, 22.82, 99]
                        }
                    ],
                    symbolSize: 10, //半径大小？
                    label: {
                        normal: {
                            show: true,
                            //formatter: '{b}：{c}[2]'    //显示内容{a}为系列，b为data的name属性，c为data的value属性
                            formatter: function (data) {
                                let n = data.name;
                                // let v = data.value;
                                return n;
                            },
                            position: 'right',
                            color: '#fff'
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle: {
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        },
                        color: {
                            type: 'radial',
                            x: 0.5,
                            y: 0.5,
                            r: 0.5,
                            colorStops: [{
                                offset: 0,
                                color: 'white' // 0% 处的颜色
                            }, {
                                offset: 0.5,
                                color: 'transparent' // 100% 处的颜色
                            }, {
                                offset: 1,
                                color: 'white' // 100% 处的颜色
                            }],
                            global: false // 缺省为 false
                        }
                    }
                },
                {
                    name: '已建分行',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: getSeriesData('分行', 150, 5),
                    symbolSize: 15, //半径大小
                    itemStyle: {
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                },
                {
                    name: '分行筹备组',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: getSeriesData('分行筹备组', 250, 5),
                    symbolSize: 15, //半径大小
                    itemStyle: {
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                },
                {
                    name: '县域支行',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: getSeriesData('县域支行', 350, 5),
                    symbolSize: 15, //半径大小
                    itemStyle: {
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                },
                {
                    name: '社区小微',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: getSeriesData('社区小微', 450, 5),
                    symbolSize: 15, //半径大小
                    itemStyle: {
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                },
                {
                    name: '服务点',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: getSeriesData('服务点', 550, 5),
                    symbolSize: 15, //半径大小
                    itemStyle: {
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                },
                {
                    name: '村镇银行',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: getSeriesData('村镇银行', 650, 5),
                    symbolSize: 15, //半径大小
                    itemStyle: {
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                }
            ],
            visualMap: {
                type: 'piecewise', //分段型视觉映射组件
                pieces: [ //自定义『分段式视觉映射组件（visualMapPiecewise）』的每一段的范围，以及每一段的文字，以及每一段的特别的样式。
                {
                        min: 100,
                        max: 200,
                        label: '已建分行',
                        symbol: 'circle',
                        color: 'red'
                    },
                    {
                        min: 200,
                        max: 300,
                        label: '分行筹备组',
                        symbol: 'circle',
                        color: 'white'
                    },
                    {
                        min: 300,
                        max: 400,
                        label: '县域支行',
                        symbol: 'circle',
                        color: 'blue'
                    },
                    {
                        min: 400,
                        max: 500,
                        label: '社区小微',
                        symbol: 'circle',
                        color: 'purple'
                    },
                    {
                        min: 500,
                        max: 600,
                        label: '服务点',
                        symbol: 'circle',
                        color: 'yellow'
                    },
                    {
                        min: 600,
                        max: 700,
                        label: '村镇银行',
                        symbol: 'circle',
                        color: 'brown'
                    }
                ],
                textStyle: {
                    color: '#fff'
                },
                outOfRange: [{
                    // 径向渐变，前三个参数分别是圆心 x, y 和半径，取值同线性渐变
                    name: 'city',
                    symbol: 'circle',
                    color: {
                        type: 'radial',
                        x: 0.5,
                        y: 0.5,
                        r: 0.5,
                        colorStops: [{
                            offset: 0,
                            color: 'white' // 0% 处的颜色
                        }, {
                            offset: 0.5,
                            color: 'transparent' // 100% 处的颜色
                        }, {
                            offset: 1,
                            color: 'white' // 100% 处的颜色
                        }],
                        global: false // 缺省为 false
                    }
                }]
            },
            backgroundColor: '#032338', // 图表背景色
        };
    }
  },
  beforeMount() {
    echarts.registerMap(this.mapType, this.geoJSON);
  }
};
</script>

<style lang='less' scoped>
</style>
