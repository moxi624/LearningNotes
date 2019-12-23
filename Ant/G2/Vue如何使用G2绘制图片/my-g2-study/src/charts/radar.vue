<template>
  <MyEcharts ref="chart" width="100%" height="100%" :id="chartId" :option="option"></MyEcharts>
</template>

<script>
import MyEcharts from "@/components/echarts/Index.vue";
export default {
  name: "Radar",
  components: {
    MyEcharts
  },
  data(){
      return{
          chartId: "radar",
      }
  },
  computed:{
      option(){
          return {
            legend: {
                data: ['全区经济总量', '我行占比'],
                textStyle: {
                    color: '#44bbe9'
                }
            },
            calculable : true,
            radar: {
                splitNumber: 5, // 雷达图圈数设置
                name: {
                    textStyle: {
                        color: '#44bbe9',
                    },
                },
                // 设置雷达图中间射线的颜色
                axisLine: {
                    lineStyle: {
                        color: 'rgba(131,141,158,0)',
                    },
                },
                //雷达图背景的颜色，在这儿随便设置了一个颜色，完全不透明度为0，就实现了透明背景
                splitArea: {
                    show: false,
                    areaStyle: {
                        color: 'rgba(255,0,0,0)', // 图表背景的颜色
                    },
                },
                //分割线
                splitLine: {
                    lineStyle: {
                        color: "#44bbe9",
                        width: 1
                    }
                },
                indicator: [{
                        name: '客户数占有率',
                        max: 100
                    },
                    {
                        name: '行政村建设占比',
                        max: 100
                    },
                    {
                        name: '乡镇村机构覆盖率',
                        max: 100
                    },
                    {
                        name: '负债总量市场占比',
                        max: 100
                    },
                    {
                        name: '资产总量市场占比',
                        max: 100
                    }
                ]
            },
            series: [{
                name: 'cc',
                type: 'radar',
                data: [{
                        value: [100, 100, 100, 100, 100, 100],
                        name: '全区经济总量',
                        // 设置区域边框和区域的颜色
                        itemStyle: {
                            normal: {
                                color: '#1483b0'
                            }
                        },
                        //在拐点处显示数值
                        label: {
                            normal: {
                                show: false,
                                formatter: (params) => {
                                    return params.value;
                                },
                            },
                        },
                        lineStyle: {
                            color: '#1483b0',
                            width: 3
                        }
                    },
                    {
                        value: [50, 74, 68, 55, 62, 51],
                        name: '我行占比',
                        // 设置区域边框和区域的颜色
                        itemStyle: {
                            normal: {
                                color: '#ed7d31',                                
                            },
                        },
                        //在拐点处显示数值
                        label: {
                            normal: {
                                show: true,
                                color: '#fff',
                                formatter: (params) => {
                                    return params.value + "%"
                                },
                            },
                        },
                        lineStyle: {
                                    color: '#ed7d31',
                                    width: 3
                                }
                    }
                ]
            }],
            backgroundColor: '#032338', // 图表背景色
        };
      }
  }
};
</script>

<style lang='less' scoped>
</style>