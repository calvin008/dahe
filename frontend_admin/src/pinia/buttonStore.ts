import {defineStore} from 'pinia'


export interface ButtonState {
    buttonList:string[]
}
export const buttonStore = defineStore('button',{
    state:():ButtonState =>({
        buttonList:[]
    }),
    getters:{
        getButtons:state=>state.buttonList
    },
    actions:{
        generateButtons(buttons:string[]) {
            let bList:string[] = []
            buttons.forEach(button=>{
                if(button.match(/:/g)?.length === 3) {
                        bList.push(button)
                }   
            })
           
            this.buttonList = bList

        }
    }
})