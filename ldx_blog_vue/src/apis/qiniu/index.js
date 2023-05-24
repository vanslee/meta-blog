import request from '@/utils/request'
// 上传单个文件
const configs = {
  headers: { 'Content-Type': 'multipart/form-data' }
}
// 上传多个文件
export const uploadFilesApi = async formData => {
  return request.post('upload/upload_files', formData, configs)
}
