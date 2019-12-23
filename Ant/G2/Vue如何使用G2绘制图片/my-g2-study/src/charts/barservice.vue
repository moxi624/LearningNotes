<template>
  <MyEcharts ref="chart" width="100%" height="100%" :id="chartId" :option="option"></MyEcharts>
</template>

<script>
import MyEcharts from "@/components/echarts/Index.vue";
export default {
  name: "BarService",
  components: {
    MyEcharts
  },
  data(){
      return{
          chartId: "bar-service",
      }
  },
  computed:{
      option(){
          var builderJson = {
            "all": 100,
            "charts": {
                "桂林营业部": 45,
                "南宁分行": 42,
                "柳州分行": 38,
                "梧州分行": 32,
                "防城港分行": 27
            }
        };
          return {
            title: {
                text: '服务点建设覆盖率TOP5',
                left: 0,
                textAlign: 'left',
                textStyle:{
                    color: '#44bbe9'
                }
            },
            xAxis: {
                type: 'value',
                show: false,
                max: builderJson.all,
                splitLine: {
                    show: false
                }
            },
            yAxis: {
                type: 'category',
                show: false,
                data: Object.keys(builderJson.charts).reverse(),
                axisLabel: {
                    interval: 0,
                    rotate: 30
                },
                splitLine: {
                    show: false
                }
            },
            series: [
                {
                    name: 'showName',
                    type: 'bar',
                    z: 3,
                    label: {
                        normal: {
                            show: true,
                            color: '#44bbe9',
                            position: 'insideLeft',
                            distance: 20,
                            align: 'left',
                            verticalAlign: 'middle',
                            rotate: 0,
                            formatter: '{b}',
                            fontSize: 16,
                            rich: {
                                name: {
                                    textBorderColor: '#fff'
                                }
                            }
                        }
                    },
                    itemStyle: {
                        color: 'transparent'
                    },
                    data: Object.keys(builderJson.charts).map(function () {
                        //return builderJson.charts[key] + 1;
                        return builderJson.all;
                    }).reverse()
                },
                {
                    name: 'showValue',
                    type: 'bar',
                    stack: 'chart',
                    z: 3,
                    label: {
                        normal: {
                            position: 'right',
                            show: false,
                            color: '#fff'
                        }
                    },
                    itemStyle: {
                        color: '#ed7d31'
                    },
                    barWidth: 15,
                    data: Object.keys(builderJson.charts).map(function (key) {
                        return builderJson.charts[key];
                    }).reverse()
                },
                {
                    name: 'showValue',
                    type: 'bar',
                    stack: 'chart',
                    silent: true,
                    itemStyle: {
                        normal: {
                            color: '#44bbe9'
                        }
                    },
                    label: {
                        normal: {
                            position: 'right',
                            show: true,
                            color: '#fff',
                            offset: [-100, 0],
                            formatter: function (data) {
                                return (builderJson.all - data.value) + "%";
                            }
                        }
                    },
                    barWidth: 15,
                    data: Object.keys(builderJson.charts).map(function (key) {
                        return builderJson.all - builderJson.charts[key];
                    }).reverse()
                }
            ],
            backgroundColor: '#032338' // 图表背景色
        };
      }
  }
};
</script>

<style lang='less' scoped>
</style>
