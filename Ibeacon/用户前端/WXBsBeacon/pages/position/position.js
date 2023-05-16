// pages/position/position.js

var app = getApp();
var number = 0;
var BeaconArray = new Array();
var foundDevices = new Array();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        sapceNum: '',
        left: 315,
        top: 650,
        devices: [],
        isopen: false, //蓝牙适配器是否已打开
        inter: '',
        destinationleft:0,
        destinationTop:30

    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
      var that = this;
      var spaceNum = options.sapceNum;
      console.log(spaceNum);
      if (spaceNum == 'A01') {
        that.setData({
          destinationleft: 15,
          destinationTop: 30,
        })
      }else if (spaceNum == 'A02') {
        that.setData({
          destinationleft: 135,
          destinationTop: 30,
        })
      }else if (spaceNum == 'A03') {
        that.setData({
          destinationleft: 260,
          destinationTop: 30,
        })
      } else if (spaceNum == 'B01') {
        that.setData({
          destinationleft: 15,
          destinationTop: 380,
        }) 
      }else if (spaceNum == 'B02') {
        that.setData({
          destinationleft: 135,
          destinationTop: 380,
        }) 
      }
        that.startScan();
        that.getFingerPrinter();
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
        // var that = this;
        // that.startInter();
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {
      var that  = this;
      that.closeBluetoothAdapter();
      wx.stopBluetoothDevicesDiscovery({
        success: (res) => {},
      })

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {
      var that  = this;
      that.closeBluetoothAdapter();
      wx.stopBluetoothDevicesDiscovery({
        success: (res) => {},
      })
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
    //开始时取出指纹
    getFingerPrinter: function() {
      wx.request({
        url: 'http://192.168.199.173:8081/position/get/fingerPrinter',
        method: "GET",
        header: {'Content-Type': 'application/json'},  // http 请求是 JSON 数据格式
        data: "",
        success: res => {
          console.log("获取成功")
          
         
        },
        fail: err => {
          
        }
      })
    },


    //启动定时器
    startInter: function(){
        var that = this;
        that.data.inter = setInterval(function() {
          console.log("发送数据至后台")
          wx.request({
            url: 'http://192.168.199.173:8081/position/actual/time',
            method: "POST",
            header: {'Content-Type': 'application/json'},  // http 请求是 JSON 数据格式
            data: JSON.stringify(BeaconArray.pop()),
            success: res => {
              console.log("发送成功")
              that.setData({
                left: res.data.left,
                top: res.data.top
            })
              
              // that.startScan();
            },
            fail: err => {
              wx.showToast({
                title: '新增记录失败',
              })
            }
          })
            
        },1000);
    },

    //发送实时RSSI至后台
     

    //关闭蓝牙模块，使其进入未初始化状态
    closeBluetoothAdapter: function() {
        wx.closeBluetoothAdapter({
          success: (res) => {
            this._discoveryStarted = false
            this.setData({
                isopen: false
            })
          },
        })
      },

    startScan: function() {
        var that = this;
        that._discoveryStarted = false;
        if (that.data.isopen) { //如果已初始化小程序蓝牙模块，则直接执行扫描
          console.log("直接开始扫描，执行getBluetoothAdapterState（）方法");
          that.getBluetoothAdapterState();
        } else {
          console.log("初始化小程序蓝牙模块，执行openBluetoothAdapter（）方法");
          that.openBluetoothAdapter();
        }
        
      },

       //获取本机蓝牙适配状态
    getBluetoothAdapterState: function() {
        console.log("执行getBluetoothAdapterState（）函数");
        var that = this;
        wx.getBluetoothAdapterState({
          success: (res) => {
            var isDiscov = res.discovering;//是否正在搜索设备
            var isAvailbale = res.available;//蓝牙适配器是否可用
            if (isAvailbale) {
              console.log("蓝牙适配器状态可用")
                if (!isDiscov) {
                  that.startBluetoothDevicesDiscovery();
                }    
            }
          }
        })
      },

      //开始扫描附近的蓝牙信标
    //该方法比较耗费系统资源，建议搜索到设备后调用stop方法停止搜索
    startBluetoothDevicesDiscovery: function() {
        console.log("执行startBluetoothDevicesDiscovery（）函数");
        var that = this;
        if (that._discoveryStarted) {
          return
        }
        that._discoveryStarted = true;
        // wx.showLoading({
        //   title: '正在扫描',
        // });
        // setTimeout(function() {
        //   wx.hideLoading();
        // },3000);
        wx.startBluetoothDevicesDiscovery({
          allowDuplicatesKey: true,//允许重复上报同一设备
          interval: 0,
          // powerLevel: high,
          success: function(res) {
            that.onBluetoothDeviceFound();//监听寻找新设备的事件
          }
        })
      },

      //监听寻找新设备的事件
    onBluetoothDeviceFound: function() {
      
        console.log("执行onBluetoothDeviceFound（）函数");
        var that = this;
        var beacon = {
          rssiOne: 0,
          rssiTwo: 0,
          rssiThree: 0,
        //   BeaconName: ""
        };
         wx.onBluetoothDeviceFound((res) => {
          res.devices.forEach(function(device) {
            // if (!device.name && !device.localName) {
            //   return
            // }
            //过滤掉其他的蓝牙设备，只接收Beacon的信号
            if(device.name == 'MBeacon') {
              
              if (device.deviceId == "AC:23:3F:72:36:74") {
                beacon["rssiOne"] = device.RSSI;
              }else if (device.deviceId == "AC:23:3F:72:37:FF" ){
                beacon["rssiTwo"] = device.RSSI;
              } else {
                beacon["rssiThree"] = device.RSSI;
              }
              // number = foundDevices.push(device);
              if (beacon.rssiOne != 0 && beacon.rssiTwo !=0 && beacon.rssiThree != 0) {
                //   that.closeBluetoothAdapter();
                console.log("beaconOne为: "+beacon.rssiOne);
                console.log("beaconTwo为: "+beacon.rssiTwo);
                console.log("beaconThree为: "+beacon.rssiThree);
                BeaconArray.push(beacon);
                that.startInter();
                // that.upload();
                // number = 0;
                // BeaconArray = [];
                // foundDevices = [];
                beacon = {
                    rssiOne: 0,
                    rssiTwo: 0,
                    rssiThree: 0,
                  //   BeaconName: ""
                  };
               };

             }    
          })
        })
      },

      //初始化小程序蓝牙模块
    openBluetoothAdapter: function() {
        console.log("执行openBluetoothAdapter（）函数");
        var that = this;
        wx.openBluetoothAdapter({
          success: function(res) {
            var log = that.data.textLog + "打开蓝牙适配器成功！\n";
            console.log("打开蓝牙适配器成功！"),
            that.setData({
              isopen: true
            });
            that.getBluetoothAdapterState();
          },
          fail: function(err) {
            isopen: true;
            // app.showMOdal1("蓝牙开关未开启");
            var log = that.data.textLog + "蓝牙开关未开启 \n";
            that.setData({
              textLog: log
            });
          }
        })
        //监听蓝牙适配器状态变化事件
        wx.onBluetoothAdapterStateChange(function(res) {
          console.log("执行onBluetoothAdapterStateChange（）函数");
          console.log('onBluetoothAdapterStateChange的变化状态为：', res)
          var isAvailbale = res.available;//蓝牙适配器是否可用
          if (isAvailbale) {
            that.getBluetoothAdapterState();
          } else {
            that.stopBluetoothDevicesDiscovery();//停止搜索(待删除)
            that.setData({
              devices: []
            });
            app.showMOdal1("蓝牙开关未开启");
          }
        })
      }
})