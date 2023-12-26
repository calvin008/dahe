"use strict";
Object.defineProperties(exports, { __esModule: { value: true }, [Symbol.toStringTag]: { value: "Module" } });
const common_vendor = require("./common/vendor.js");
const config = require("./config.js");
if (!Math) {
  "./pages/index/index.js";
  "./pages/order/order.js";
  "./pages/my/my.js";
  "./pages/login/login.js";
}
const _sfc_main = {
  onLaunch: function() {
    console.log("App Launch");
  },
  onShow: function() {
    console.log("App Show");
  },
  onHide: function() {
    console.log("App Hide");
  }
};
const App = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "E:/video/food-vue3/food3/App.vue"]]);
const request = function(path, method, data, failCallback, completeCallback) {
  common_vendor.index.showLoading({
    mask: true
  });
  return new Promise((resolve) => {
    common_vendor.index.request({
      url: config.configDev.baseUrl + path,
      data,
      method,
      header: {
        "Content-Type": "application/json;charset=UTF-8",
        "token": common_vendor.index.getStorageSync("token")
      },
      success: (res) => {
        common_vendor.index.hideLoading();
        if (res.data.code === 200) {
          resolve(res.data);
        }
      }
    });
  });
};
function createApp() {
  const app = common_vendor.createSSRApp(App);
  app.use(common_vendor.createPinia());
  app.config.globalProperties.$request = request;
  return {
    app,
    Pinia: common_vendor.Pinia
  };
}
createApp().app.mount("#app");
exports.createApp = createApp;
