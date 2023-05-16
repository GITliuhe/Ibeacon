// index.js
// 获取应用实例
const app = getApp()

Page({
  data:{
    userInfo:''
  },
  onLoad(){
    
    // let user=wx.getStorageSync('user')
    // console.log('进入小程序的页面获取缓存',user)
    // this.setData({
    //   userInfo:user
    // })
  },

  /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {
      
    },

  //授权登录
  login(){
    wx.getUserProfile({
      desc: '用于完善会员资料',//声明获取用户个人信息后的用途，后续会展示在弹窗中
      success:res =>{
        let user = res.userInfo
        //把用户信息缓存到本地
        wx.setStorageSync('user', user)
        console.log("用户信息",user)
        this.setData({
          userInfo:user
        })
        wx.navigateTo({
          url: '../../pages/home/home',
        })
      },
      fail: res=>{
        console.log('授权失败',res)
      }
    })
  },
  //退出登录
  loginOut(){
    this.setData({
      userInfo:''
    })
    wx.setStorageSync('user', null)
  }
})
  
