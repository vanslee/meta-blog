import request from '@/utils/request'

export function getArticlesByUidApi(params) {
  const { uid } = params
  return request({
    url: `/article/list/${uid}`,
    method: 'get',
    params,
  })
}
export function deleteArticleByIdApi(id) {
  return request({
    url: `/article/delete/${id}`,
    method: 'delete',
  })
}
export function deleteBatchArticleApi(ids) {
  return request({
    url: `/article/delete/batch`,
    method: 'delete',
    params: ids,
  })
}
export function getArticleByIdApi(id) {
  return request({
    url: `/article/${id}`,
    method: 'get',
  })
}
export async function getMarkdownTextApi(markdownUrl) {
  const response = await fetch(markdownUrl)
  const text = await response.text()
  return { text }
}
export function updateArticleApi(article) {
  return request.put('article/update', article)
}
