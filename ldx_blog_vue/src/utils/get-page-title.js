import defaultSettings from '@/settings'

const title = defaultSettings.title || '李图报个人博客'

export default function getPageTitle (pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
