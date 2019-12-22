<template>
    <div>
        <div style="position: relative;">
            <div id="china" style="display: none;width: 50%;height:400px;position: absolute;left: 0;top: 0;"></div>
            <div id="province" style="width: 50%;height:400px;position: absolute;right: 0;top: 0;"></div>
        </div>
    </div>
</template>

<script>
    import $ from 'jquery';
    import _ from 'lodash';
    import G2 from '@antv/g2';
    const DataSet = require('@antv/data-set');

    export default {
        mounted() {
            $.getScript('https://webapi.amap.com/maps?v=1.4.1&key=0d78256ea89beeb8c25d1cd047549d1f')
                .then(()=>$.getScript('https://webapi.amap.com/ui/1.0/main.js?v=1.0.11'))
                .then(()=>{
                    // 调用高德 api 绘制底图以及获取 geo 数据
                    var map = new AMap.Map('china', {
                        zoom: 4
                    });
                    //.......这里省略官网案例代码
                })
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
