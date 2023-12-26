"use strict";
const common_vendor = require("../common/vendor.js");
const goodsStore = common_vendor.defineStore("goods", {
  state: () => ({
    cart: [],
    orderType: "\u5916\u5356\u914D\u9001"
  }),
  actions: {
    setCart(good) {
      this.cart.push(good);
    },
    setOrderType(type) {
      this.orderType = type;
    }
  }
});
exports.goodsStore = goodsStore;
