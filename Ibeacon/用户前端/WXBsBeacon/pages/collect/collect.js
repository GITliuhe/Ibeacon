// pages/collect/collect.js

var app = getApp();
var RssiArray = new Array();
var RssiArrayLength = 0;//数组初始长度
var UuidArray = new Array();
var UuidArrayLength = 0;
var RssiFingerPrinterInfo = {
  areaNum: 0,
  coordinateX: 0,
  coordinateY: 0,
};
var BeaconArray = new Array();
var BeaconArrayLength = new Array();


function inArray(arr, key, val) {
  for (let i = 0; i < arr.length; i++) {
    if (arr[i][key] === val) {
      return i;
    }
  }
  return -1;
}
Page({

    /**
     * 页面的初始数据
     */
    data: {
      textLog: "",
      isopen: false, //蓝牙适配器是否已打开
      devices: [],
      Frequency: 0,
      connected: false,
      chs: [],
      collectnum: "",
      CoordinateX: "",
      CoordinateY: "",
      searchinput: '',
      beacon_id:"",
      form_info:"",
      form_infos:""
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
        console.log("生命周期函数--监听页面卸载");
        // this.closeBluetoothAdapter(); //关闭蓝牙模块，使其进入未初始化状态。
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

    //蓝牙信标Rssi值的采集

     //获取当前采集的区域编号
     InputAreaNum: function(e) {
       wx.setStorageSync('areaNum', e.detail.value)
       console.log("获取到区域编号为："+e.detail.value);
     },

    //获取输入的x坐标的事件，
    InputX: function(e) {
        //wx.setStorageSync(‘key’, ‘value’)将获取的值缓存到本地，
        //在另一个页面用var value =wx.getStorageSync(‘key’)接受缓存的值。                                                
        wx.setStorageSync('CoordinateX', e.detail.value);
        console.log("获取到采集地点的X坐标为："+e.detail.value);
    },

    //获取输入的Y坐标的事件，
    InputY: function(e) {
        wx.setStorageSync('CoordinateY', e.detail.value);
        console.log("获取到采集地点的Y坐标为："+e.detail.value);
        RssiFingerPrinterInfo["areaNum"] = wx.getStorageSync('areaNum');
        RssiFingerPrinterInfo["coordinateX"] = wx.getStorageSync('CoordinateX');
        RssiFingerPrinterInfo["coordinateY"] = wx.getStorageSync('CoordinateY');
        console.log("RssiFingerPrinterInfo为："+RssiFingerPrinterInfo);
        BeaconArray.push(RssiFingerPrinterInfo);
    },

   
    //获取设置的采集数量的事件
    CollectNumberInput: function(e) {
        wx.setStorageSync('collectnum', e.detail.value);
        console.log("设置完成");
        console.log("设置的采集数量为："+e.detail.value);
    },

    //再次采集
    scanAgain: function() {
      var that = this;
      that.closeBluetoothAdapter(),
      
      this.setData({
        // form_infos:"",
        devices: [],
        RssiArrayLength: 0,
        isopen:false

      })
      // wx.navigateTo({
      //   url: '/pages/collect/collect'
      // });
    },

    //更换区域坐标
    changeArea: function() {
      var that = this;
      that.closeBluetoothAdapter();
      BeaconArray = [];
      RssiArray = [];
      UuidArray = [];

      wx.removeStorage({
        key: 'areaNum',
        key: 'CoordinateX',
        key: 'CoordinateY',
        key: 'collectnum'
        
      }),
      this.setData({
        form_info:"",
        devices: [],
        RssiArrayLength: 0,
        isopen:false,
        

      })

    },

    //开始采集
    startScan: function() {
        var that = this;
        that._discoveryStarted = false;
        //将区域X，Y坐标与区域编号存入数组，待提交
        // var parkingLotInfo = {
        //   parkingLotName: wx.getStorageSync('CoordinateX'),
        //   CoordinateX: wx.getStorageSync('CoordinateY'),
        //   CoordinateY: wx.getStorageSync('CoordinateY')
        // };
        // BeaconArray.push(parkingLotInfo);
        if (that.data.isopen) { //如果已初始化小程序蓝牙模块，则直接执行扫描
          console.log("直接开始扫描，执行getBluetoothAdapterState（）方法");
          that.getBluetoothAdapterState();
        } else {
          console.log("初始化小程序蓝牙模块，执行openBluetoothAdapter（）方法");
          that.openBluetoothAdapter();
        }
        
      },

      

    //取消蓝牙扫描
    cancleScan: function() {

        wx.stopBluetoothDevicesDiscovery();
        console.log("已主动停止蓝牙扫描")
        console.log("UuidArrayLength为：" + UuidArrayLength);
        console.log("RssiArrayLength为：" + RssiArrayLength);
        console.log("RssiArray为：" + RssiArray);
        console.log("UuidArray为：" + UuidArray);
        console.log("BeaconArry为：" + BeaconArray);
        console.log("信标：" +BeaconArray[0]["uuid"]+" 的rssi值为："+ BeaconArray[0]["rssi"]);

      },

    //提交数据至后台
    upload: function() {
      // UuidArray.push("parkingLotName");
      // RssiArray.push(wx.getStorageSync('CoordinateX'));
      // UuidArray.push("areaNum");
      // RssiArray.push(wx.getStorageSync('CoordinateY'));
      wx.request({
        url: 'http://192.168.199.173:8081/add//rssi/fingerprints',
        method: "POST",
        header: {'Content-Type': 'application/json'},  // http 请求是 JSON 数据格式
        data: JSON.stringify(BeaconArray),
        success: res => {
          wx.showToast({
            title: '新增记录成功',
          })
        },
        fail: err => {
          wx.showToast({
            title: '新增记录失败',
          })
        }
      })
        
    },

    //关闭蓝牙模块，使其进入未初始化状态
    closeBluetoothAdapter: function() {
      wx.closeBluetoothAdapter({
        success: (res) => {
          this._discoveryStarted = false
        },
      })
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
            var log = that.data.textLog + "本机蓝牙适配器状态：可用 \n";
            that.setData({
              textLog: log
            });
            // var frequency = wx.getStorageSync('collectnum')
            // for (var i = 1; i <= frequency; i++) {
              if (!isDiscov) {
                // console.log("第"+i+"次搜索")
              
                that.startBluetoothDevicesDiscovery();
                
              
              
              }    
              // else{
              //   console.log("第"+i+"次搜索，"+"搜索还没关掉")
              // }
              // that.setData({
              //   Frequency: i
              // })
            // }
            
            // else{
            //   var log = that.data.textLog + "已在搜索设备 \n";
            //   that.setData({
            //     textLog: log
            //   });
            // }
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
      wx.showLoading({
        title: '正在扫描',
      });
      // var log = that.data.textLog + "正在扫描...\n";
      // that.setData({
      //   textLog: log
      // });
      setTimeout(function() {
        wx.hideLoading();
      },3000);
      wx.startBluetoothDevicesDiscovery({
        allowDuplicatesKey: false,//允许重复上报同一设备
        interval: 0,
        // powerLevel: high,
        success: function(res) {
          var log = that.data.textLog + "扫描附近的蓝牙外围设备成功，准备监听寻找新设备: "+ res + "\n";
          that.setData({
            textLog: log
          });
          that.onBluetoothDeviceFound();//监听寻找新设备的事件
        }
      })
    },

    

    //监听寻找新设备的事件
    onBluetoothDeviceFound: function() {
      
      console.log("执行onBluetoothDeviceFound（）函数");
      var that = this;
      var beacon = {
        beaconOne: "",
        rssiOne: 0,
        beaconTwo: "",
        rssiTwo: 0,
        beaconThree: "",
        rssiThree: 0,
        BeaconName: ""
      };
     
        
      wx.onBluetoothDeviceFound((res) => {
        console.log("执行一次继续寻找设备")
        
        res.devices.forEach(function(device) {
          
          if (!device.name && !device.localName) {
            return
          }
          //过滤掉其他的蓝牙设备，只接收Beacon的信号
          if(device.name == 'MBeacon') {

            var foundDevices = that.data.devices;
          // //  const idx = inArray(foundDevices, 'deviceId', device.deviceId);
            const data = {};
          // //  if (idx === -1) {
            data[`devices[${foundDevices.length}]`] = device
            if (device.deviceId == "AC:23:3F:72:36:74") {
              beacon["beaconOne"] = device.deviceId;
              beacon["rssiOne"] = device.RSSI;
              beacon["BeaconName"] = device.name;
            }else if (device.deviceId == "AC:23:3F:72:37:FF" ){
              beacon["beaconTwo"] = device.deviceId;
              beacon["rssiTwo"] = device.RSSI;
            } else {
              beacon["beaconThree"] = device.deviceId;
              beacon["rssiThree"] = device.RSSI;
            }
          //    //允许上报同一个设备 上报期间将RSSI存入数组 
          //  } else {
          //    data[`decives[${idx}]`] = device
          //    ////输出的RSSI先放入数组中 20190306  模拟之后传入服务

          //    console.log("信号强度为" + device.RSSI);
          //    console.log("UUID为"+ device.deviceId);
          //    console.log("data为："+data);
          //  }

            RssiArrayLength = foundDevices.push(device);
            UuidArrayLength = (UuidArray).push(device.deviceId);
            (RssiArray).push(device.RSSI);

            var collectnum = wx.getStorageSync('collectnum');
            console.log("本次的搜索数量设置为："+collectnum)
            if (RssiArrayLength == collectnum) {
              BeaconArrayLength = (BeaconArray).push(beacon);
               //停止本次寻找蓝牙
               wx.stopBluetoothDevicesDiscovery({
                 success: (res) => {
                   console.log("是否真的停止本次搜索设备")
                  //  RssiArrayLength = 0;
                   wx.showToast({
                     title: '本次采集完成',
                   })
                 },
               })
               console.log("本次采集完成，已经停止寻找");
               console.log("UuidArrayLength为：" + UuidArrayLength);
               console.log("RssiArrayLength为：" + RssiArrayLength);
               console.log("BeaconArrayLength" + BeaconArrayLength);
               console.log("RssiArray为：" + RssiArray);
               console.log("UuidArray为：" + UuidArray);
              //  console.log("RssiArray为：" + RssiArray);
              //  console.log("BeaconIdArray为：" + BeaconIdArray);
             }
             that.setData(data)
           }
          //  console.log("device的name："+device.name);
          //  console.log("device的localName："+device.localName);
          //  console.log("UuidArrayLength为：" + UuidArrayLength);
          //  console.log("RssiArrayLength为：" + RssiArrayLength);
          //  console.log("RssiArrayLength为：" + RssiArrayLength);
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