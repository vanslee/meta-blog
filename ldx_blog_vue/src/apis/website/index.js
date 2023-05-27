import request from '@/utils/request'
export function getWebSiteInfoApi() {
  return request.get('website/info')
}
