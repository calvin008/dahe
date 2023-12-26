"use strict";
const common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  __name: "popup-layer",
  props: {
    showPop: {
      type: Boolean,
      default: false
    },
    direction: {
      type: String,
      default: ""
    }
  },
  setup(__props) {
    const props = __props;
    const ifshow = common_vendor.ref(false);
    const translateVale = common_vendor.ref(-100);
    const _translate = common_vendor.computed$1(() => {
      const transformObj = {
        "top": `transform:translateY(${-translateVale.value}%)`,
        "bottom": `transform:translateY(${translateVale.value}%)`,
        "left": `transform:translateX(${-translateVale.value}%)`,
        "right": `transform:translateX(${translateVale.value}%)`
      };
      return transformObj[props.direction];
    });
    const _location = common_vendor.computed$1(() => {
      const positionValue = {
        "top": "bottom:0px;width:100%;",
        "bottom": "top:0px;width:100%;",
        "left": "right:0px;height:100%;",
        "right": "left:0px;height:100%;"
      };
      return positionValue[props.direction] + _translate.value;
    });
    common_vendor.onMounted(() => {
      if (props.showPop) {
        show();
      }
    });
    common_vendor.watch(() => props.showPop, () => {
      if (props.showPop) {
        show();
      } else {
        close();
      }
    });
    const show = () => {
      ifshow.value = true;
      translateVale.value = 0;
    };
    const close = () => {
      ifshow.value = false;
      translateVale.value = -100;
    };
    return (_ctx, _cache) => {
      return {
        a: ifshow.value,
        b: common_vendor.o(() => {
        }),
        c: common_vendor.o(close),
        d: common_vendor.s(common_vendor.unref(_location))
      };
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "E:/video/food-vue3/food3/components/popup-layer/popup-layer.vue"]]);
wx.createComponent(Component);
