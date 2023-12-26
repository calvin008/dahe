import { createI18n } from "vue-i18n";
import CN from './ZH-CN/cn'
import EN from './en/en'


const message = {
    chs: {
       ...CN
    },
    en:{
      ...EN
    }
}
const getLang = ()=>{
    if(localStorage.getItem('lang')) {
            return localStorage.getItem('lang')
    } else {
          switch(navigator.language)  {
                case 'zh-CN':
                localStorage.setItem('lang','chs')
                    return 'chs'
                case 'en-US':
                    localStorage.setItem('lang','en')
                    return 'en'
          }
    }
}

const i18n = createI18n({
    legacy:false,
    locale:getLang() || 'chs',
    globalInjection:true, //$t
    messages:message
})

export default i18n