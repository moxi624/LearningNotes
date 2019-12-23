<template>
  <MyEcharts ref="chart" width="100%" height="100%" :id="chartId" :option="option"></MyEcharts>
</template>

<script>
import MyEcharts from "@/components/echarts/Index.vue";
export default {
  name: "BarDeposit",
  components: {
    MyEcharts
  },
  data() {
    return {
      chartId: "bar-deposit"
    };
  },
  computed: {
    option() {
      return {
        title: {
          text: "存储存款占比",
          left: 0,
          textAlign: "left",
          textStyle: {
            color: "#44bbe9"
          }
        },
        xAxis: {
          type: "category",
          axisLabel: {
            color: "#fff"
          },
          axisLine: {
            //坐标轴轴线相关设置
            show: false
          },
          axisTick: {
            //坐标轴刻度相关设置
            show: false
          },
          splitLine: {
            //坐标轴在 grid 区域中的分隔线
            show: false
          },
          data: ["活期", "盈盈", "漓江", "两品", "理财", "月得利"]
        },
        yAxis: {
          type: "value",
          axisLabel: {
            color: "#fff",
            formatter: function(value) {
              return value + "%";
            }
          },
          axisLine: {
            //坐标轴轴线相关设置
            show: false
          },
          axisTick: {
            //坐标轴刻度相关设置
            show: false
          },
          splitLine: {
            //坐标轴在 grid 区域中的分隔线
            show: false
          },
          min: 0,
          max: 20
        },
        series: [
          {
            name: "时点",
            type: "bar",
            label: {
              show: true,
              position: "top",
              color: "#fff",
              formatter: "{c}%"
            },
            itemStyle: {
              color: "#ed7d31"
            },
            barMinHeight: 1,
            data: [
              { name: "活期", value: 17.39 },
              { name: "盈盈", value: 6.14 },
              { name: "漓江", value: 6.17 },
              { name: "两品", value: 1.48 },
              { name: "理财", value: 0.03 },
              { name: "月得利", value: 17.84 }
            ]
          }
        ],
        backgroundColor: "#032338" // 图表背景色
      };
    }
  }
};
</script>

<style lang='less' scoped>
</style>
