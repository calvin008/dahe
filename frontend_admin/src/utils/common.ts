// 表格拖拽排序
const lightLine = document.createElement('div')
lightLine.style.cssText += ';height: 1px; position: absolute; top: 0; left: 0; right: 0; background-color: skyblue; z-index: 999; display: none'
export function useTableDragSort () {
  let callback: (data: any[]) => void
  function initDragSort (tableBodyDom: HTMLElement, tableData: any[]) {
    setTimeout(() => {
      const trList = tableBodyDom.querySelectorAll<HTMLTableRowElement>('tr')
      const trHeight = trList[0].clientHeight
      const dragEnterTextList = tableBodyDom.querySelectorAll<HTMLElement>('tr .dragEnterText')
      const moveIconDoms = tableBodyDom.querySelectorAll<HTMLElement>('.moveIcon')
      tableBodyDom.appendChild(lightLine)

      // 一维数据
      const flatData = getChildrenItem(tableData, [])
      // console.log(flatData)

      // 当前拖拽对象
      let currentDragDom: HTMLTableRowElement
      let currentDragIndex: number
      for (let i = 0; i < trList.length; i++) {
        moveIconDoms[i].onmousedown = () => {
          trList[i].setAttribute('draggable', 'true')
          currentDragDom = trList[i]
          currentDragIndex = i
        }
        moveIconDoms[i].onmouseup = () => {
          trList[i].removeAttribute('draggable')
        }
        // 进入tr，展示高亮线
        trList[i].ondragenter = (e: DragEvent) => {
          if (isDragInChildNode(flatData[currentDragIndex], flatData[i])) {
            lightLine.style.cssText += ';display: none'
            return
          }
          const dragEnterText = trList[i].querySelector('.dragEnterText') as HTMLElement
          const parentDom = dragEnterText.offsetParent as HTMLElement
          const left = dragEnterText.offsetLeft + parentDom.offsetLeft
          const top = Math.max(Math.abs(Math.round(e.offsetY / trHeight)) * trHeight + trList[i].offsetTop - 1, 0)
          lightLine.style.cssText += `;left: ${left}px; top: ${top}px; display: block`
        }
        trList[i].ondragover = (e: Event) => {
          e.preventDefault()
        }
        trList[i].ondrop = (e: DragEvent) => {
          currentDragDom.removeAttribute('draggable')
          if (isDragInChildNode(flatData[currentDragIndex], flatData[i])) return
          const positionValue = Math.abs(Math.round(e.offsetY / trHeight)) // 0：在前面插入；1：在后面插入
          const newTableData = changeData(tableData, flatData[currentDragIndex], flatData[i], positionValue)
          newTableData && callback(newTableData)
        }
        trList[i].ondragend = () => {
          lightLine.style.cssText += ';display: none'
        }
        // 进入文本，数据嵌套
        dragEnterTextList[i].ondrop = (e: Event) => {
          if (currentDragIndex === i) return
          e.stopPropagation()
          console.log('text:', dragEnterTextList[i], i)
        }
      }
    }, 0)
  }
  // 获取所有子项处理为一维数组
  function getChildrenItem (arr: any[], res: any[]) {
    if (!arr) return res
    arr.forEach(checkItem => {
      res.push({
        ...checkItem
      })
      getChildrenItem(checkItem.children, res)
    })
    return res
  }
  // 判断父节点是否拖入其子节点中
  function isDragInChildNode (dragData: any, enterData: any) {
    if (dragData.itemId === enterData.itemId) return true
    if (!dragData.children) return false
    let children = JSON.parse(JSON.stringify(dragData.children))
    while (children.length > 0) {
      let cur = children.pop()
      if (cur.itemId === enterData.itemId) {
        return true
      } else if (cur.children){
        children.push(...cur.children)
      }
    }
    return false
  }
  // 处理拖拽排序前后的数据
  function changeData (parentData: any[], dragData: any, enterData: any, positionValue: number) {
    const res = JSON.parse(JSON.stringify(parentData))
    const stack = [res]
    let addOk = false
    let removeOk = false
    while (stack.length > 0) {
      const cur = stack.pop() || []
      const ids = cur.map((item: any) => item.itemId)
      let dragIndex = ids.indexOf(dragData.itemId)
      let enterIndex = ids.indexOf(enterData.itemId)
      // 同级情况
      if (dragIndex >= 0 && enterIndex >= 0) {
        if ((dragIndex - enterIndex === -1 && positionValue === 0)) return
        if ((dragIndex - enterIndex === 1 && positionValue === 1)) return
      }
      if (dragIndex >= 0 && !removeOk) {
        cur.splice(dragIndex, 1)
        removeOk = true
      }
      if (enterIndex >= 0 && !addOk) {
        addOk = true
        if ( dragIndex >= 0 && dragIndex < enterIndex) {
          if (positionValue === 0) {
            cur.splice(enterIndex-1, 0, dragData)
          } else {
            cur.splice(enterIndex, 0, dragData)
          }
        } else {
          if (positionValue === 0) {
            cur.splice(enterIndex, 0, dragData)
          } else {
            cur.splice(enterIndex+1, 0, dragData)
          }
        }
      }
      if (!addOk || !removeOk) {
        cur.forEach((item:any) => stack.push(item.children))
      } else {
        return res
      }
    }
  }
  function dragEnd (cb: (data: any[]) => void) {
    callback = cb
  }
  return {
    initDragSort,
    dragEnd
  }
}
