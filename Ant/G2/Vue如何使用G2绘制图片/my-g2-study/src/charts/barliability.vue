<template>
  <MyEcharts ref="chart" width="100%" height="100%" :id="chartId" :option="option"></MyEcharts>
</template>

<script>
import MyEcharts from "@/components/echarts/Index.vue";
export default {
  name: "BarLiability",
  components: {
    MyEcharts
  },
  data(){
      return{
          chartId: "bar-liability",
      }
  },
  computed:{
      option(){
          
          return {
            title: {
                text: '总负债时点',
                left: 0,
                textAlign: 'left',
                textStyle: {
                    color: '#44bbe9'
                }
            },
            xAxis: {
                type: 'category',
                axisLabel: {
                    color: '#fff'
                },
                axisLine: {  //坐标轴轴线相关设置
                    show: false
                },
                axisTick: {  //坐标轴刻度相关设置
                    show: false
                },
                splitLine: {  //坐标轴在 grid 区域中的分隔线
                    show: false
                },
                data: ['桂林', '南宁', '梧州', '柳州', '贺州', '防城港', '玉林', '北海', '百色', '钦州']
            },
            yAxis: {
                type: 'value',
                axisLabel: {
                    color: '#fff'
                },
                axisLine: {  //坐标轴轴线相关设置
                    show: false
                },
                axisTick: {  //坐标轴刻度相关设置
                    show: false
                },
                splitLine: {  //坐标轴在 grid 区域中的分隔线
                    show: false
                },
                min: 0,
                max: 1200000
            },
            series: [
                {
                    name: '时点',
                    type: 'bar',
                    label: {
                        show: true,
                        position: 'top',
                        color: '#fff'
                    },
                    itemStyle: {
                        color: '#ed7d31'
                    },
                    barMinHeight: 1,
                    data: [
                        { name: '桂林', value: 999227 },
                        { name: '南宁', value: 624371 },
                        { name: '梧州', value: 213086 },
                        { name: '柳州', value: 339431 },
                        { name: '贺州', value: 153509 },
                        { name: '防城港', value: 737735 },
                        { name: '玉林', value: 91052 },
                        { name: '北海', value: 16713 },
                        { name: '百色', value: 60 },
                        { name: '钦州', value: 644 }
                    ]
                }
            ],
            backgroundColor: '#032338', // 图表背景色
        };
      }
  }
};
</script>

<style lang='less' scoped>
</style>