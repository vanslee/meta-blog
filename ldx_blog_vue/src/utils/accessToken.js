import { storage, userInfoName, tokenTableName } from '@/config'
import { isEmpty } from '@/utils'
export function isLogin() {
  return isEmpty(getAccessToken()) ? false : true;
}
/**
 * @author https://vue-admin-beautiful.com （不想保留author可删除）
 * @description 获取accessToken
 * @returns {string|ActiveX.IXMLDOMNode|Promise<any>|any|IDBRequest<any>|MediaKeyStatus|FormDataEntryValue|Function|Promise<Credential | null>}
 */
export function getAccessToken() {
  if (storage) {
    if ('localStorage' === storage) {
      return localStorage.getItem(tokenTableName)
    } else if ('sessionStorage' === storage) {
      return sessionStorage.getItem(tokenTableName)
    } else {
      return localStorage.getItem(tokenTableName)
    }
  } else {
    return localStorage.getItem(tokenTableName)
  }
}

/**
 * @author https://vue-admin-beautiful.com （不想保留author可删除）
 * @description 存储accessToken
 * @param accessToken
 * @returns {void|*}
 */
export function setAccessToken(accessToken) {
  if (storage) {
    if ('localStorage' === storage) {
      return localStorage.setItem(tokenTableName, accessToken)
    } else if ('sessionStorage' === storage) {
      return sessionStorage.setItem(tokenTableName, accessToken)
    } else {
      return localStorage.setItem(tokenTableName, accessToken)
    }
  } else {
    return localStorage.setItem(tokenTableName, accessToken)
  }
}

/**
 * @author https://vue-admin-beautiful.com （不想保留author可删除）
 * @description 移除accessToken
 * @returns {void|Promise<void>}
 */
export function removeAccessToken() {
  if (storage) {
    if ('localStorage' === storage) {
      localStorage.removeItem(userInfoName)
      localStorage.removeItem(tokenTableName)
      return
    } else if ('sessionStorage' === storage) {
      return sessionStorage.clear()
    } else {
      localStorage.removeItem(userInfoName)
      localStorage.removeItem(tokenTableName)
      return
    }
  } else {
    localStorage.removeItem(userInfoName)
    localStorage.removeItem(tokenTableName)
    return
  }
}
