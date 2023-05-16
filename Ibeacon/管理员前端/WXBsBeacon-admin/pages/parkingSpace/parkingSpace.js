// pages/parkingSpace/parkingSpace.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
      parkingLotName: "",
      spaceList: [
        {
        id: 1,
        name: '停车位',
        sold: "A01",
        price: 3
      },
      
      {
        id: 2,
        name: '金针菇',
        pic: 'http://wxapp.im20.com.cn/impublic/waimai/imgs/goods/2.jpg',
        sold: "A02",
        price: 3
      },
      {
        id: 3,
        name: '方便面',
        pic: 'http://wxapp.im20.com.cn/impublic/waimai/imgs/goods/2.jpg',
        sold: 'A03',
        price: 2
      },
      {
        id: 4,
        name: '粉丝',
        pic: 'http://wxapp.im20.com.cn/impublic/waimai/imgs/goods/2.jpg',
        sold: 'B01',
        price: 1
      },
      {
        id: 5,
        name: '生菜',
        pic: 'http://wxapp.im20.com.cn/impublic/waimai/imgs/goods/2.jpg',
        sold: 'B02',
        price: 2
      }
    ]
              
    },
  
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
      console.log(options)
      this.setData({
        parkingLotName: options.parkingLotName
      })
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
  
    addParkingSpace: function(e) {
        let parkingLotName = e.currentTarget.dataset.name;
        console.log(parkingLotName)
  
        wx.navigateTo({
          url: '/pages/addParkingSpace/addParkingSpace?parkingLotName='+parkingLotName,
        })
    }
  })