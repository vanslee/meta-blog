import request from '@/utils/request'

export function getArticlesByUidApi(uid, params) {
  return request({
    url: `/article/list/${uid}`,
    method: 'get',
    params,
  })
}
