<template>
  <div style="background-color:#f8f8f8;">
    <!--头部-->
    <div class="front-header">
      <a href="/front/home">
      <div class="front-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">跨境电商</div>
      </div>
      </a>
      <div class="front-header-center">
        <div @click="$router.push(item.path)" class="menu" v-for="item in menus" :key="item.path"
             :class="{'menu-active' : item.path === $route.path }">{{ item.text }}</div>
      </div>
      <div class="front-header-right">
        <div v-if="!user.username">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown>
            <div class="front-header-dropdown">
              <img :src="user.avatar" alt="" style="border-radius: 50%">
              <div style="margin-left: 10px ;color: #eeeeee;cursor: pointer;">
                <span>{{ user.name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
              </div>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <div style="text-decoration: none" @click="$router.push('/front/frontGoods')">我的商品</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="$router.push('/front/orders')">我的订单</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="$router.push('/front/address')">收货地址</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="$router.push('/front/person')">个人信息</div>
              </el-dropdown-item>
              <el-dropdown-item>
                <div style="text-decoration: none" @click="$router.push('/front/collect')">购物车</div>
              </el-dropdown-item>

              <el-dropdown-item>
                <div style="text-decoration: none" @click="logout">退出登录</div>
              </el-dropdown-item>

            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>
    <!--主体-->
    <div class="main-body">
      <router-view ref="child" @update:user="updateUser" />
    </div>

  </div>

</template>

<script>

export default {
  name: "FrontLayout",

  data () {
    return {
      notice: [],
      user: JSON.parse(localStorage.getItem("xm-user") || '{}'),
      menus: [
        { text: '热卖专区', path: '/front/home' },
        { text: '系统公告', path: '/front/notice' },
      ]
    }
  },

  mounted() {
    this.loadNotice()
  },
  methods: {
    loadNotice() {
      this.$request.get('/notice/selectAll').then(res => {
        this.notice = res.data
        let i = 0
        if (this.notice && this.notice.length) {
          this.top = this.notice[0].content
          setInterval(() => {
            this.top = this.notice[i].content
            i++
            if (i === this.notice.length) {
              i = 0
            }
          }, 2500)
        }
      })
    },
    updateUser() {
      this.user = JSON.parse(localStorage.getItem('xm-user') || '{}')   // 重新获取下用户的最新信息
    },
    // 退出登录
    logout() {
      localStorage.removeItem("xm-user");
      this.$router.push("/login");
    },

  }

}
</script>

<style scoped>
  @import "@/assets/css/front.css";

  .menu {
    color: #74d6e8;
    font-size: 16px;
    padding: 0 20px;
    cursor: pointer;
  }

  .menu:hover{
    color: #fda1bd;
  }
  .menu-active{
    color: #fda1bd;
  }

</style>