import request from '@/apis/index'
export function getArticleListApi(params) {
  const { current, size } = params
  return request.get(`article/list?current=${current}&size=${size}`)
}
export function getArticleDetailsApi(article_id) {
  return request.get(`article/details/${article_id}`)
}
