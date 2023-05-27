import request from '@/utils/request'
export function getArticleListApi(params) {
  const { current, size } = params
  return request.get(`article/list?current=${current}&size=${size}`)
}
export function getArticleDetailsApi(article_id) {
  return request.get(`article/details/${article_id}`)
}
export function publishArticleApi(article) {
  return request.put('article/publish', article)
}
