import request from '@/utils/request'
export function getArticleListApi(params) {
  const { current, size, cid } = params
  return request.get(`article/list?current=${current}&size=${size}&cid=${cid}`)
}
export function getSeftArticleApi(cid, params) {
  return request.get(``)
}

export function getArticleDetailsApi(articleId) {
  return request.get(`article/details/${articleId}`)
}
export function publishArticleApi(article) {
  return request.put('article/publish', article)
}
export async function getMarkdownTextApi(markdownUrl) {
  const response = await fetch(markdownUrl)
  const text = await response.text()
  return { text }
}
export function getArticleTagsApi(params) {
  const { current, size } = params
  return request.get(`tag/list?current=${current}&size=${size}`)
}
export function getArticleCategoriesApi(params) {
  const { current, size } = params
  return request.get(`category/list?current=${current}&size=${size}`)
}
export function getArticlesByCid(params, categoryId) {
  const { current, size } = params;
  return request.get(`article/category/${categoryId}?current=${current}&size=${size}`)
}
export function deleteAritcleApi(id) {
  return request.delete(`article/delete/${id}`)
}