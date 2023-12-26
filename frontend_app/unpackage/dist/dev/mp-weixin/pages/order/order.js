"use strict";
const common_vendor = require("../../common/vendor.js");
const store_user = require("../../store/user.js");
const _sfc_main = {
  __name: "order",
  setup(__props) {
    const useUserStore = store_user.userStore();
    common_vendor.onMounted(() => {
      console.log(useUserStore.appProperties);
    });
    return (_ctx, _cache) => {
      return {};
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "E:/video/food-vue3/food3/pages/order/order.vue"]]);
wx.createPage(MiniProgramPage);
