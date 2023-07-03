import request from '@/utils/request'
import qs from 'qs';
import _ from 'lodash'
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
  const temp = _.cloneDeep(article);
  console.log(JSON.stringify(temp));
  // temp.categories += ""
  // temp.tags += ""
  const { categories, tags } = article
  // temp.tags = JSON.stringify(tags)
  // temp.categories = JSON.stringify(categories)
  console.log(qs.stringify(article));
  console.log('temp', temp);
  return request.post('article/publish', article)
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