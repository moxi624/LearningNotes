<template>
  <MyEcharts ref="chart" width="100%" height="100%" :id="chartId" :option="option"></MyEcharts>
</template>

<script>
import MyEcharts from "@/components/echarts/Index.vue";
export default {
  name: "PieDeposit",
  components: {
    MyEcharts
  },
  data(){
      return{
          chartId: "pie-deposit",
      }
  },
  computed:{
      option(){
          
          return  {
            title: {
                text: '存款结构',
                left: 0,
                textAlign: 'left',
                textStyle: {
                    color: '#44bbe9'
                }
            },
            series: [
                {
                    type: 'pie',
                    label:{            //饼图图形上的文本标签
                            normal:{
                                show:true,
                                position:'inner', //标签的位置
                                textStyle : {
                                    fontWeight : 300 ,
                                    fontSize : 16    //文字的字体大小
                                },
                                formatter:'{d}%'                                
                            }
                        },
                    data: [
                        { 
                            name: '表外存款', 
                            value: 46.29,
                            itemStyle:{
                                color:'#91c7ae'
                            }
                         },
                        { name: '公司存款', value: 4.66,
                            itemStyle:{
                                color:'#ed7d31'
                            } },
                        { name: '储蓄存款', value: 49.06,
                            itemStyle:{
                                color:'#5b9bd5'
                            } }
                    ]
                },
                {
                    type: 'pie',
                    data: [
                        { 
                            name: '表外存款', 
                            value: 46.29,
                            itemStyle:{
                                color:'#91c7ae'
                            }
                         },
                        { name: '公司存款', value: 4.66,
                            itemStyle:{
                                color:'#ed7d31'
                            } },
                        { name: '储蓄存款', value: 49.06,
                            itemStyle:{
                                color:'#5b9bd5'
                            } }
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