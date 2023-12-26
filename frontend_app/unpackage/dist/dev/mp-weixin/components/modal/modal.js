"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "modal",
  props: {
    show: {
      type: Boolean,
      default: false
    },
    width: {
      type: String,
      default: "84%"
    },
    padding: {
      type: String,
      default: "40rpx"
    },
    radius: {
      type: String,
      default: "24rpx"
    }
  },
  setup(__props) {
    common_vendor.ref("");
    return (_ctx, _cache) => {
      return {
        a: __props.width,
        b: __props.padding,
        c: __props.radius,
        d: common_vendor.n(__props.show ? "modal-normal" : "modal-scale"),
        e: common_vendor.o(() => {
        })
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "E:/video/food-vue3/food3/components/modal/modal.vue"]]);
wx.createComponent(Component);
