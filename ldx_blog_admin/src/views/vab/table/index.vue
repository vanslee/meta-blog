<template>
  <div class="table-container">
    <vab-query-form>
      <vab-query-form-left-panel>
        <!-- <el-button icon="el-icon-plus" type="primary" @click="handleAdd">
          添加
        </el-button> -->
        <el-button
          icon="el-icon-delete"
          type="danger"
          @click="handleBatchDelete"
        >
          删除
        </el-button>
        <!-- <el-button type="primary" @click="testMessage">baseMessage</el-button>
        <el-button type="primary" @click="testALert">baseAlert</el-button>
        <el-button type="primary" @click="testConfirm">baseConfirm</el-button>
        <el-button type="primary" @click="testNotify">baseNotify</el-button> -->
      </vab-query-form-left-panel>
      <vab-query-form-right-panel>
        <el-input
          v-model="keyword"
          placeholder="标题"
          style="width: 10vw; margin: 0 1vw"
        />
        <el-button
          icon="el-icon-search"
          type="primary"
          native-type="submit"
          @click="handleKeywordSearch"
        >
          查询
        </el-button>
        <!-- <el-form
          ref="form"
          :model="keyword"
          :inline="true"
          @submit.native.prevent
        >
          <el-form-item>
            <el-input v-model="keyword" placeholder="标题" />
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-search"
              type="primary"
              native-type="submit"
              @click="handleKeywordSearch"
            >
              查询
            </el-button>
          </el-form-item>
        </el-form> -->
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table
      ref="tableSort"
      v-loading="listLoading"
      :data="tableData"
      :element-loading-text="elementLoadingText"
      :height="height"
      @selection-change="setSelectRows"
      @sort-change="tableSortChange"
    >
      <el-table-column
        align="center"
        show-overflow-tooltip
        type="selection"
        width="55"
      ></el-table-column>
      <el-table-column
        align="center"
        show-overflow-tooltip
        label="序号"
        width="60"
      >
        <template #default="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        prop="articleTitle"
        label="标题"
        width="200"
        align="center"
      ></el-table-column>
      <el-table-column
        label="作者"
        prop="authorName"
        width="200"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="封面"
        prop="imgUrl"
        align="center"
      >
        <template #default="{ row }">
          <el-image v-if="imgShow" :src="row.imgUrl"></el-image>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="点击量"
        prop="views"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="状态"
        prop="delete"
        align="center"
      >
        <template #default="{ row }">
          <el-tooltip
            :content="row.delete ? '已删除' : '未删除'"
            class="item"
            effect="dark"
            placement="top-start"
          >
            <el-tag :type="row.delete | statusFilter">
              {{ row.delete }}
            </el-tag>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="发布时间~更新时间"
        prop="publishDate"
        width="200"
        align="center"
      >
        <template #default="{ row }">
          {{ parseTime(row.publishDate, '{y}-{m}-{d}') }} ~
          {{ parseTime(row.updateDate, '{y}-{m}-{d}') }}
          <!-- <el-image v-if="imgShow" :src="row.imgUrl"></el-image> -->
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        show-overflow-tooltip
        label="操作"
        width="180px"
      >
        <template #default="{ row }">
          <el-button type="text" @click="handleEdit(row.id)">编辑</el-button>
          <el-button type="text" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :background="background"
      :current-page="params.current"
      :layout="layout"
      :page-size="params.size"
      :total="params.total"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    ></el-pagination>
    <table-edit ref="edit"></table-edit>
  </div>
</template>

<script>
  // import { getList, doDelete } from '@/api/table'
  import { parseTime } from '@/utils/'
  import TableEdit from './components/TableEdit'
  export default {
    name: 'ComprehensiveTable',
    components: {
      TableEdit,
    },
    filters: {
      statusFilter(status) {
        if (status) {
          return 'success'
        } else {
          return 'dander'
        }
        // const statusMap = ['success', 'danger', 'gray']
        // return statusMap[status]
      },
    },
    props: {
      tableData: {
        type: Array,
        default: () => [],
      },
      params: {
        type: Object,
        default: () => {},
      },
    },
    data() {
      return {
        imgShow: true,
        list: [],
        imageList: [],
        listLoading: false,
        layout: 'total, sizes, prev, pager, next, jumper',
        background: true,
        selectRows: '',
        elementLoadingText: '正在加载...',
        keyword: '',
      }
    },
    computed: {
      height() {
        return this.$baseTableHeight()
      },
    },
    created() {
      console.log(this.params)
      // this.fetchData()
    },
    beforeDestroy() {},
    mounted() {},
    methods: {
      parseTime,
      tableSortChange() {
        const imageList = []
        this.$refs.tableSort.tableData.forEach((item, index) => {
          imageList.push(item.img)
        })
        this.imageList = imageList
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$refs['edit'].showEdit()
      },
      handleEdit(id) {
        this.$emit('handle-edit', id)
      },
      handleDelete(row) {
        if (row.id) {
          this.$baseConfirm(
            `你确定要删除${row.articleTitle}吗？`,
            null,
            async () => {
              this.$emit('delete-row', row.id)
              this.$baseMessage(msg, 'success')
              this.fetchData()
            }
          )
        } else {
          if (this.selectRows.length > 0) {
            const ids = this.selectRows.map((item) => item.id).join()
            this.$baseConfirm('你确定要删除选中项吗', null, async () => {
              const { msg } = await doDelete({ ids: ids })
              this.$baseMessage(msg, 'success')
              this.fetchData()
            })
          } else {
            this.$baseMessage('未选中任何行', 'error')
            return false
          }
        }
      },
      handleBatchDelete(ids) {
        console.log('ids', ids)
      },
      handleSizeChange(val) {
        this.$emit('size-change', val)
        // this.queryForm.pageSize = val
        // this.fetchData()
      },
      handleCurrentChange(val) {
        this.$emit('current-change', val)
        // this.queryForm.pageNo = val
        // this.fetchData()
      },
      handleKeywordSearch() {
        this.$emit('search-keyword', this.keyword)
      },
      async fetchData() {
        // this.listLoading = true
        // const { data, totalCount } = await getList(this.queryForm)
        // this.list = data
        // const imageList = []
        // data.forEach((item, index) => {
        //   imageList.push(item.img)
        // })
        // this.imageList = imageList
        // this.total = totalCount
        // setTimeout(() => {
        //   this.listLoading = false
        // }, 500)
      },
      testMessage() {
        this.$baseMessage('test1', 'success')
      },
      testALert() {
        this.$baseAlert('11')
        this.$baseAlert('11', '自定义标题', () => {
          /* 可以写回调; */
        })
        this.$baseAlert('11', null, () => {
          /* 可以写回调; */
        })
      },
      testConfirm() {
        this.$baseConfirm(
          '你确定要执行该操作?',
          null,
          () => {
            /* 可以写回调; */
          },
          () => {
            /* 可以写回调; */
          }
        )
      },
      testNotify() {
        this.$baseNotify('测试消息提示', 'test', 'success', 'bottom-right')
      },
    },
  }
</script>
