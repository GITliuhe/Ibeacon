// index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
      parkingLot:[],
     

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      var that = this;
      that.getParkingLot();


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

  onScroll: function (e) {
  if (e.detail.scrollTop > 100 && !this.data.scrollDown) {
    this.setData({
      scrollDown: true
    });
  } else if (e.detail.scrollTop < 100 && this.data.scrollDown) {
    this.setData({
      scrollDown: false
    });
  }
  },
  
  tapSearch: function () {
    wx.navigateTo({url: 'search'});
    
  },

  inParkingLot: function (e) {
    let parkingLotName = e.currentTarget.dataset.name;
    console.log(parkingLotName)

    wx.navigateTo({
      url: '/pages/parkingLotSpace/parkingLotSpace?parkingLotName='+parkingLotName,
    })


  },
  
  //取出数据库
  getParkingLot: function() {
      var that = this
      wx.request({
        url: 'http://192.168.199.173:8081/parkingLot',
        method: "GET",
        header: {'Content-Type': 'application/json'},  // http 请求是 JSON 数据格式
        data: "",
        success: res => {
          console.log("获取成功")
          that.setData({
              parkingLot: res.data
          })
          
         
        },
        fail: err => {
          
        }
      })
    },
})