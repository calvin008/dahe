export const isEmpty = (str: any): boolean => {
    if (
      str === null ||
          str === '' ||
          str === undefined ||
          str.length === 0
    ) {
      return true
    } else {
      return false
    }
  };