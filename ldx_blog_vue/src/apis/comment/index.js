import request from '@/utils/request'
export function getCommentBriefApi (params) {
  return request.post('comment/comment_brief', params)
}
export function publishCommentApi (params) {
  return request.put('comment/add_article_comment', params)
}
