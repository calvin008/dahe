"use strict";
const common_vendor = require("../common/vendor.js");
const userStore = common_vendor.defineStore("user", {
  state: () => ({
    appProperties: {
      "shopNotice": ""
    },
    userInfo: null
  }),
  actions: {
    login(userInfo) {
      this.userInfo = userInfo;
      common_vendor.index.setStorageSync("token", userInfo.token);
      common_vendor.index.setStorageSync("userInfo", userInfo);
    },
    logout() {
      common_vendor.index.removeStorageSync("token");
      common_vendor.index.removeStorageSync("userInfo");
      this.userInfo = null;
    },
    setToken(token) {
      common_vendor.index.setStorageSync("token", token);
    },
    setUserInfo(userInfo) {
      this.userInfo = userInfo;
      common_vendor.index.setStorageSync("userInfo", userInfo);
    }
  }
});
exports.userStore = userStore;
