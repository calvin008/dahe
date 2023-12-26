"use strict";
const common_vendor = require("../../common/vendor.js");
const store_user = require("../../store/user.js");
const store_goods = require("../../store/goods.js");
if (!Math) {
  (modal + popupLayer)();
}
const modal = () => "../../components/modal/modal.js";
const popupLayer = () => "../../components/popup-layer/popup-layer.js";
const _sfc_main = {
  __name: "index",
  setup(__props) {
    const {
      proxy
    } = common_vendor.getCurrentInstance();
    common_vendor.ref("hello");
    const useUserStore = store_user.userStore();
    const useGoodsStore = store_goods.goodsStore();
    const {
      appProperties
    } = common_vendor.storeToRefs(useUserStore);
    const { cart } = common_vendor.storeToRefs(useGoodsStore);
    const goods = common_vendor.ref([]);
    const categoryScrollTop = common_vendor.ref(0);
    const currentCategoryName = common_vendor.ref("\u76D6\u6D47\u996D\u7CFB\u5217");
    const goodDetailModalVisible = common_vendor.ref(false);
    const current = common_vendor.ref([]);
    const currentGoods = common_vendor.computed$1(() => {
      return current.value;
    });
    const cartPopupVisible = common_vendor.ref(false);
    const getConfig = async () => {
      await proxy.$request("/api-app/config", "get").then((res) => {
        useUserStore.appProperties = res.data;
        console.log(useUserStore.appProperties);
      });
      await proxy.$request("/api-app/goods/goodsMenu/list", "get").then((result) => {
        goods.value = result.data;
      });
    };
    const calcSize = () => {
      let h = 0;
      let view = common_vendor.index.createSelectorQuery().select("#ads");
      view.fields({
        size: true
      }, (data) => {
        h += Math.floor(data.height);
      }).exec();
      goods.value.forEach((item) => {
        let view2 = common_vendor.index.createSelectorQuery().select(`#products-${item.displayOrder}`);
        view2.fields({
          size: true
        }, (data) => {
          item.top = h;
          h += Math.floor(data.height);
          item.bottom = h;
        }).exec();
      });
    };
    common_vendor.onMounted(async () => {
      await getConfig();
      await calcSize();
    });
    const handleMenuTap = (goodsCategoryName) => {
      categoryScrollTop.value = goods.value.find((item) => item.goodsCategoryName == goodsCategoryName).top;
      currentCategoryName.value = goodsCategoryName;
    };
    const handleGoodsScroll = ({
      detail
    }) => {
      const {
        scrollTop
      } = detail;
      let tabs = goods.value.filter((item) => item.top <= scrollTop).reverse();
      if (tabs.length > 0) {
        currentCategoryName.value = tabs[0].goodsCategoryName;
      }
    };
    const getGoodsRealPrice = (goods2) => {
      let realPrice = goods2.realPrice;
      if (goods2.goodsPropertyVos && goods2.goodsPropertyVos.length) {
        let vos = goods2.goodsPropertyVos;
        for (let i = 0; i < vos.length; i++) {
          let propertyList = vos[i].propertyList;
          for (let j = 0; j < propertyList.length; j++) {
            if (propertyList[j].isDefault && propertyList[j].extraPrice) {
              realPrice += propertyList[j].extraPrice;
              break;
            }
          }
        }
      }
      return realPrice;
    };
    const showGoodDetailModal = (currentGoods2) => {
      currentGoods2.number = 1;
      currentGoods2.realPrice = getGoodsRealPrice(currentGoods2);
      console.log(currentGoods2.realPrice);
      goodDetailModalVisible.value = true;
      current.value = currentGoods2;
    };
    const closeGoodDetailModal = () => {
      goodDetailModalVisible.value = false;
      proxy.$request("/api-app/goods/goodsMenu/list", "get").then((result) => {
        goods.value = result.data;
        console.log(goods.value);
      });
    };
    const changePropertyDefault = (categoryIndex, propertyIndex) => {
      current.value.goodsPropertyVos[categoryIndex].category;
      let propertyList = current.value.goodsPropertyVos[categoryIndex].propertyList;
      if (current.value.goodsPropertyVos[categoryIndex].required) {
        propertyList.forEach((property) => property.isDefault = false);
        propertyList[propertyIndex].isDefault = true;
        if (propertyList[propertyIndex].rebasePrice) {
          current.value.realPrice = current.value.realPrice - current.value.defaultPrice + propertyList[propertyIndex].rebasePrice;
          current.value.defaultPrice = propertyList[propertyIndex].rebasePrice;
          current.value.number = 1;
          current.value.propertyStr = getGoodSelectedProps(current.value);
        }
      } else {
        if (propertyList[propertyIndex].isDefault) {
          propertyList[propertyIndex].isDefault = false;
          current.value.realPrice -= propertyList[propertyIndex].extraPrice;
        } else {
          propertyList[propertyIndex].isDefault = true;
          current.value.realPrice += propertyList[propertyIndex].extraPrice;
        }
      }
    };
    const getGoodSelectedProps = (goods2) => {
      if (goods2.goodsPropertyVos) {
        let propertyStr = "";
        goods2.goodsPropertyVos.forEach((goodsPropertyVo) => {
          goodsPropertyVo.propertyList.forEach((property) => {
            if (property.isDefault) {
              propertyStr += property.propertyOption + " ";
            }
          });
        });
        return propertyStr;
      }
      return "";
    };
    const changeCurrentGoodsNumber = (number) => {
      current.value.number += number;
      if (current.value.number < 0) {
        current.value.number = 0;
      }
    };
    const handleAddCart = (goods2) => {
      let propertyStr = getGoodSelectedProps(goods2);
      goods2.propertyStr = propertyStr;
      for (let i = useGoodsStore.cart.length - 1; i >= 0; i--) {
        if (useGoodsStore.cart[i].id == goods2.id && useGoodsStore.cart[i].propertyStr == propertyStr) {
          useGoodsStore.cart[i].number++;
          return;
        }
      }
      if (!goods2.number)
        goods2.number = 1;
      useGoodsStore.setCart(goods2);
    };
    const addCurrentGoodsToCart = () => {
      if (current.value && current.value.number) {
        handleAddCart(current.value);
      }
      closeGoodDetailModal();
    };
    const handleReduceFormCart = (goods2) => {
      let propertyStr = getGoodSelectedProps(goods2);
      for (let i = useGoodsStore.cart.length - 1; i >= 0; i--) {
        if (useGoodsStore.cart[i].id == goods2.id && useGoodsStore.cart[i].propertyStr == propertyStr) {
          if (useGoodsStore.cart[i].number <= 1)
            useGoodsStore.cart.splice(i, 1);
          else
            useGoodsStore.cart[i].number -= 1;
          return;
        }
      }
    };
    const goodsCartNum = common_vendor.computed$1(() => {
      return (id) => useGoodsStore.cart.reduce((acc, cur) => {
        if (cur.id === id) {
          return acc += cur.number;
        }
        return acc;
      }, 0);
    });
    const getCartGoodsNumber = common_vendor.computed$1(() => {
      return useGoodsStore.cart.reduce((acc, cur) => acc + cur.number, 0);
    });
    const getCartGoodsPrice = common_vendor.computed$1(() => {
      let totalPrice = 0;
      useGoodsStore.cart.forEach((goods2) => {
        totalPrice += goods2.realPrice * goods2.number;
      });
      return totalPrice;
    });
    const handleCartClear = () => {
      common_vendor.index.showModal({
        title: "\u63D0\u793A",
        content: "\u786E\u5B9A\u8981\u6E05\u7A7A\u8D2D\u7269\u8F66\u5417?",
        success: ({ confirm }) => {
          if (confirm) {
            cartPopupVisible.value = false;
            useGoodsStore.cart = [];
          }
        }
      });
    };
    const handleCartItemAdd = (index) => {
      useGoodsStore.cart[index].number += 1;
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: _ctx.orderType == "\u5230\u5E97\u81EA\u53D6" ? 1 : "",
        b: _ctx.orderType == "\u5916\u5356\u914D\u9001" ? 1 : "",
        c: common_vendor.t(common_vendor.unref(appProperties).shopNotice),
        d: common_vendor.f(goods.value, (item, index, i0) => {
          return {
            a: common_vendor.t(item.goodsCategoryName),
            b: index,
            c: common_vendor.o(($event) => handleMenuTap(item.goodsCategoryName), index),
            d: item.goodsCategoryName === currentCategoryName.value ? 1 : ""
          };
        }),
        e: common_vendor.f(goods.value, (category, index, i0) => {
          return common_vendor.e({
            a: common_vendor.t(category.goodsCategoryName),
            b: category.goodsCategoryShow
          }, category.goodsCategoryShow ? {
            c: common_vendor.f(category.goodsList, (goods2, key, i1) => {
              return common_vendor.e({
                a: goods2.image,
                b: common_vendor.t(goods2.name),
                c: common_vendor.t(goods2.description),
                d: common_vendor.t(goods2.defaultPrice / 100),
                e: goods2.isSell
              }, goods2.isSell ? common_vendor.e({
                f: goods2.goodsPropertyVos && goods2.goodsPropertyVos.length
              }, goods2.goodsPropertyVos && goods2.goodsPropertyVos.length ? {
                g: common_vendor.o(($event) => showGoodDetailModal(goods2), key)
              } : {
                h: common_vendor.o(($event) => handleReduceFormCart(goods2), key),
                i: common_vendor.unref(goodsCartNum)(goods2.id),
                j: common_vendor.t(common_vendor.unref(goodsCartNum)(goods2.id)),
                k: common_vendor.unref(goodsCartNum)(goods2.id),
                l: common_vendor.o(($event) => handleAddCart(goods2), key)
              }) : {}, {
                m: key
              });
            })
          } : {}, {
            d: index,
            e: `products-${category.displayOrder}`
          });
        }),
        f: categoryScrollTop.value,
        g: common_vendor.o(handleGoodsScroll),
        h: common_vendor.unref(cart) && common_vendor.unref(cart).length
      }, common_vendor.unref(cart) && common_vendor.unref(cart).length ? common_vendor.e({
        i: common_vendor.t(common_vendor.unref(getCartGoodsNumber)),
        j: common_vendor.t(common_vendor.unref(getCartGoodsPrice) / 100),
        k: !common_vendor.unref(appProperties).shopStatus
      }, !common_vendor.unref(appProperties).shopStatus ? {} : {
        l: common_vendor.t(_ctx.disablePay ? `` : "\u53BB\u7ED3\u7B97"),
        m: _ctx.disablePay
      }, {
        n: common_vendor.o(($event) => cartPopupVisible.value = !cartPopupVisible.value)
      }) : {}, {
        o: common_vendor.unref(currentGoods).image
      }, common_vendor.unref(currentGoods).image ? {
        p: common_vendor.unref(currentGoods).image
      } : {}, {
        q: common_vendor.o(closeGoodDetailModal),
        r: common_vendor.t(common_vendor.unref(currentGoods).name),
        s: common_vendor.t(common_vendor.unref(currentGoods).description),
        t: common_vendor.unref(currentGoods).goodsPropertyVos && common_vendor.unref(currentGoods).goodsPropertyVos.length
      }, common_vendor.unref(currentGoods).goodsPropertyVos && common_vendor.unref(currentGoods).goodsPropertyVos.length ? {
        v: common_vendor.f(common_vendor.unref(currentGoods).goodsPropertyVos, (goodsPropertyVo, index, i0) => {
          return common_vendor.e({
            a: goodsPropertyVo.required
          }, goodsPropertyVo.required ? {} : {}, {
            b: common_vendor.t(goodsPropertyVo.category),
            c: common_vendor.f(goodsPropertyVo.propertyList, (propery, index2, i1) => {
              return {
                a: common_vendor.t(propery.propertyOption),
                b: common_vendor.t(propery.rebasePrice ? propery.rebasePrice / 100 : propery.extraPrice ? propery.extraPrice / 100 : ""),
                c: index2,
                d: propery.isDefault ? 1 : "",
                e: common_vendor.o(($event) => changePropertyDefault(index, index2), index2)
              };
            }),
            d: index
          });
        })
      } : {}, {
        w: common_vendor.t(common_vendor.unref(currentGoods).realPrice / 100),
        x: getGoodSelectedProps(common_vendor.unref(currentGoods))
      }, getGoodSelectedProps(common_vendor.unref(currentGoods)) ? {
        y: common_vendor.t(getGoodSelectedProps(common_vendor.unref(currentGoods)))
      } : {}, {
        z: common_vendor.o(($event) => changeCurrentGoodsNumber(-1)),
        A: common_vendor.unref(currentGoods).number,
        B: common_vendor.t(common_vendor.unref(currentGoods).number),
        C: common_vendor.unref(currentGoods).number,
        D: common_vendor.o(($event) => changeCurrentGoodsNumber(1)),
        E: common_vendor.o(($event) => addCurrentGoodsToCart()),
        F: common_vendor.p({
          show: goodDetailModalVisible.value
        }),
        G: common_vendor.o(handleCartClear),
        H: common_vendor.f(common_vendor.unref(cart), (goods2, index, i0) => {
          return {
            a: common_vendor.t(goods2.name),
            b: common_vendor.t(getGoodSelectedProps(goods2)),
            c: common_vendor.t(goods2.realPrice / 100),
            d: common_vendor.o(($event) => _ctx.handleCartItemReduce(index), index),
            e: common_vendor.t(goods2.number),
            f: common_vendor.o(($event) => handleCartItemAdd(index), index),
            g: index
          };
        }),
        I: common_vendor.p({
          direction: "top",
          ["show-pop"]: cartPopupVisible.value
        })
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__file", "E:/video/food-vue3/food3/pages/index/index.vue"]]);
wx.createPage(MiniProgramPage);
