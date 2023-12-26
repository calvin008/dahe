export type goodsType ={
    id: null|number,
    goodsCategoryName: string,
    name: string,
    displayOrder: number,
    defaultPrice: number,
    isSell: null | boolean,
    image: null|string,
    description: string
}

export type goodsPropertyType = {
    id: null|number,
    goodsId: null|number,
    category: string,
    propertyOption: string,
    isDefault: null |boolean,
    rebasePrice: null|number,
    extraPrice: null|number,
    stock: null|number
}