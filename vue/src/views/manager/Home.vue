<template>
  <div>
    <div class="card" style="padding: 15px">
      您好，{{ user?.name }}！欢迎使用跨境电商后台系统
    </div>

    <div style="display: flex; margin: 10px 0">
      <div style="flex: 1;height: 500px" id="line" class="card">

      </div>
      <div style="flex: 1;height: 500px" id="bar" class="card">

      </div>

    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'

const lineOption = {
  title: {
    text: '销售统计',
    left: 'center',
    subtext:"趋势图"
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    left: 'left'
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [],
      type: 'line',
      smooth: true
    },
  ]
}

const barOption = {
  title: {
    text: '销售统计',
    subtext: '柱状图',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    left: 'left'
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [],
      type: 'bar',
      smooth: true,
      itemStyle: {
        normal: {
          color: function(params) { // 根据索引返回对应的颜色
            let colorList = ['#ffaa2e','#32C5E9','#fa4c4c','#08b448','#FFDB5C','#ff9f7f','#fb7293','#E062AE','#E690D1','#e7bcf3']
            return colorList[params.dataIndex];
          }
        }
      },
    }
  ]
}
export default {
  name: 'Home',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      notices: []
    }
  },
  mounted() {
    // 折线图
    let linetDom = document.getElementById('line');
    let lineChart = echarts.init(linetDom);
// 柱状图
    let barDom = document.getElementById('bar');
    let barChart = echarts.init(barDom);
    // 参考
    this.$request.get('/orders/selectLine').then(res => {
      lineOption.xAxis.data = res.data?.map(v => v.name) || []
      lineOption.series[0].data = res.data?.map(v => v.value) || []
      lineChart.setOption(lineOption)
    })

    this.$request.get('/orders/selectBar').then(res => {
      barOption.xAxis.data = res.data?.map(v => v.name) || []
      barOption.series[0].data = res.data?.map(v => v.value) || []
      barChart.setOption(barOption)
    })
  },
  created() {
    this.$request.get('/notice/selectAll').then(res => {
      this.notices = res.data || []
    })
  }
}
</script>
