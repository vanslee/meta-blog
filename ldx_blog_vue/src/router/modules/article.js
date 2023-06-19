import Layout from '@/layout'

const articleRouter = {
  path: '/article/:id',
  component: Layout,
  children: [
    {
      path: '',
      name: 'Article',
      components: {
        main: () => import('@/layout/components/Mains/ArticleDetails.vue'),
        left_aside: () =>
          import('@/layout/components/Sidebar/DetailsLeftAside.vue')
      }
    }
  ]
}

export default articleRouter
