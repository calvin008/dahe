"use strict";
const common_vendor = require("../../common/vendor.js");
const store_user = require("../../store/user.js");
const _sfc_main = {
  __name: "login",
  setup(__props) {
    store_user.userStore();
    const {
      proxy
    } = common_vendor.getCurrentInstance();
    const logining = common_vendor.ref(false);
    common_vendor.onMounted(() => {
      handlerLocalToken();
    });
    const handlerLocalToken = () => {
      let token = common_vendor.index.getStorageSync("token");
      if (token) {
        proxy.$request("/api-app/user/info", "get").then((result) => {
          console.log(result);
          common_vendor.index.navigateBack({});
        });
      }
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.o(handlerLocalToken),
        b: logining.value
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-e4e4508d"], ["__file", "E:/video/food-vue3/food3/pages/login/login.vue"]]);
wx.createPage(MiniProgramPage);
