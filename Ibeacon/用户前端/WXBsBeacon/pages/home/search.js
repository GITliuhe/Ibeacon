// pages/home/search.js

Page({

    /**
     * 页面的初始数据
     */
    data: {
      searchWords: "",
      parkingLot: []

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

    inputSearch: function (e) {
      wx.setStorageSync('searchWord', e.detail.value)
      this.setData({
        searchWords: e.detail.value
      });
	},
	doSearch: function() {
    var that = this;
    that.searchParkingLot();
		
  },
  
  searchParkingLot: function() {
    var that = this;
    var info = {
      Word: ""
    };
    info["Word"] = wx.getStorageSync('searchWord');
    wx.request({
      url: 'http://192.168.199.173:8081/findParkingLot',
      method: "POST",
      header: {'Content-Type': 'application/json'},  // http 请求是 JSON 数据格式
      data: JSON.stringify(info),
      success: res => {
        console.log("获取成功")
        that.setData({
          parkingLot: res.data
        })
        wx.removeStorage({
          key: 'searchWord',
        })
        
       
      },
      fail: err => {
        
      }
    })
  },
})