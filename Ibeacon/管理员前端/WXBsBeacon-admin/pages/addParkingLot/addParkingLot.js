// pages/addParkingLot/addParkingLot.js
var app = getApp();

var parkingLotInfo = {
    parkingLotName: "",
    address: "",
    beaconName: "",
  };
Page({

    /**
     * 页面的初始数据
     */
    data: {

    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    },
    
    InputParkingLotName: function(e) {
        wx.setStorageSync('parkingLotName', e.detail.value)
        console.log("获取停车场名称为："+e.detail.value);
    },
    
    InputAddress: function(e) {
        wx.setStorageSync('address', e.detail.value)
        console.log("获取停车场地址为："+e.detail.value);
    },

    InputBeaconName: function(e) {
        wx.setStorageSync('beaconName', e.detail.value)
        console.log("获取蓝牙信标名称为："+e.detail.value);
    },

    upload: function() {
        parkingLotInfo["parkingLotName"] = wx.getStorageSync('parkingLotName');
        parkingLotInfo["address"] = wx.getStorageSync('address');
        parkingLotInfo["beaconName"] = wx.getStorageSync('beaconName')
        wx.request({
          url: 'http://192.168.199.173:8081/addParkingLot',
          method: "POST",
          header: {'Content-Type': 'application/json'},  // http 请求是 JSON 数据格式
          data: JSON.stringify(parkingLotInfo),
          success: res => {
            wx.showToast({
              title: '新增记录成功',
            })

            wx.navigateTo({
                url: '/pages/parkingLot/parkingLot',
              })
          },
          fail: err => {
            wx.showToast({
              title: '新增记录失败',
            })
          }
        })
          
      },
    


})