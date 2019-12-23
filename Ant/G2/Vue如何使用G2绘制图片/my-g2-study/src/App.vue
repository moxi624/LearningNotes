<template>
  <div class="heat-map">
    <div class="header">
      <div class="left">
        <label>地区：</label>
        <select>
          <option value="all">全区</option>
        </select>
      </div>
      <div class="center">营业时间：2019-08-25 22:22:22</div>
      <div class="right">总负债时点余额：36,861,234,569</div>
    </div>
    <div class="content">
      <div class="layout-left">
        <div class="top">
          <Radar></Radar>
        </div>
        <div class="bottom">
          <BarService></BarService>
        </div>
      </div>
      <div class="layout-center">
        <div class="top">
          <Map></Map>
        </div>
        <div class="bottom">
          <BarLiability></BarLiability>
        </div>
      </div>
      <div class="layout-right">
        <div class="top">
          <TextData></TextData>
        </div>
        <div class="middle">
          <PieDeposit></PieDeposit>
        </div>
        <div class="bottom">
          <BarDeposit></BarDeposit>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Radar from './charts/radar.vue'
  import BarService from './charts/barservice.vue'
  import Map from './charts/map.vue'
  import BarLiability from './charts/barliability.vue'
  import TextData from './charts/textdata.vue'
  import BarDeposit from './charts/bardeposit.vue'
  import PieDeposit from './charts/piedeposit.vue'

  export default {
    name: "HeatMap",
    components:{
      Radar,
      BarService,
      Map,
      BarLiability,
      BarDeposit,
      PieDeposit,
      TextData
    }
  };
</script>

<style lang='less' scoped>
  @textColor: #44bbe9;
  @headerHeight: 60px;
  .heat-map {
    height: 100%;

    .header {
      height: @headerHeight;
      background-color: #061228;

      > div {
        float: left;
        line-height: @headerHeight;
        font-size: 18px;
      }
      .left {
        width: 30%;
        font-size: 22px;

        select {
          height: 35px;
          background: transparent;
          color: @textColor;
          width: 80px;
          font-size: 23px;
          border: 1px solid @textColor;
        }
      }
      .center {
        width: 40%;
      }
      .right {
        width: 30%;
      }
    }
    .content {
      position: absolute;
      top: @headerHeight;
      bottom: 0;
      left: 0;
      right: 0;

      > div {
        float: left;
        height: 100%;
      }
      .layout-left {
        width: 30%;

        .top {
          height: 45%;
        }
        .bottom {
          height: 55%;
        }
      }
      .layout-right {
        width: 30%;

        .top {
          height: 33%;
          background: #032338;
        }
        .middle {
          height: 33%;
        }
        .bottom {
          height: 34%;
        }
      }
      .layout-center {
        width: 40%;

        .top {
          height: 66%;
        }
        .bottom {
          height: 34%;
        }
      }
    }
  }
</style>
