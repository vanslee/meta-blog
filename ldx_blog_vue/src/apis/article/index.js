import request from '@/utils/request'
export function getArticleListApi (params) {
  const { current, size } = params
  return request.get(`article/list?current=${current}&size=${size}`)
}
export function getArticleDetailsApi (articleId) {
  return request.get(`article/details/${articleId}`)
}
export function publishArticleApi (article) {
  return request.put('article/publish', article)
}
export async function getMarkdownTextApi (markdownUrl) {
  const response = await fetch(markdownUrl)
  const text = await response.text()
  return { text }
}
