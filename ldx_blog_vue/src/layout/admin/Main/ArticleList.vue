<template>
    <div style="width: 100%;height: 100%;">
        <el-table ref="tableSort" v-loading="listLoading" :data="articles" :element-loading-text="elementLoadingText"
            @selection-change="setSelectRows" @sort-change="tableSortChange">
            <el-table-column align="center" show-overflow-tooltip type="selection" width="55"></el-table-column>
            <el-table-column align="center" show-overflow-tooltip label="序号" width="60">
                <template #default="scope">
                    {{ scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column prop="articleTitle" label="标题" width="200" align="center"></el-table-column>
            <el-table-column label="作者" prop="authorName" width="200" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="封面" prop="imgUrl" align="center">
                <template #default="{ row }">
                    <el-image v-if="imgShow" :src="row.imgUrl"></el-image>
                </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="点击量" prop="views" sortable align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="状态" prop="delete" align="center">
                <template #default="{ row }">
                    <el-tooltip :content="row.delete ? '已删除' : '未删除'" class="item" effect="dark" placement="top-start">
                        <el-tag :type="row.delete | statusFilter">
                            {{ row.delete }}
                        </el-tag>
                    </el-tooltip>
                </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="发布时间~更新时间" prop="publishDate" width="200" align="center">
                <template #default="{ row }">
                    {{ parseTime(row.publishDate, '{y}-{m}-{d}') }} ~
                    {{ parseTime(row.updateDate, '{y}-{m}-{d}') }}
                    <!-- <el-image v-if="imgShow" :src="row.imgUrl"></el-image> -->
                </template>
            </el-table-column>
            <el-table-column align="center" show-overflow-tooltip label="操作" width="180px">
                <template #default="{ row }">
                    <el-button type="text" @click="handleEdit(row.id)">编辑</el-button>
                    <el-button type="text" @click="handleDelete(row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination :current-page="params.current" :page-size="params.size" :total="params.total"
            @current-change="handleCurrentChange" @size-change="handleSizeChange" />
        <!-- <table-edit ref="edit"></table-edit> -->
    </div>
</template>
<script>
// import { getList, doDelete } from '@/api/table'
import { mapActions, mapState } from 'pinia'
import { useArticleStore } from '@/stores/article'
import { parseTime } from '@/utils/'
export default {
    name: 'ComprehensiveTable',
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
    data() {
        return {
            imgShow: true,
            list: [],
            listLoading: false,
            layout: 'total, sizes, prev, pager, next, jumper',
            background: true,
            selectRows: '',
            elementLoadingText: '正在加载...',
            keyword: '',
        }
    },
    computed: {
        ...mapState(useArticleStore, ['articles', 'params']),
        height() {
            return this.$baseTableHeight()
        },
    },
    created() {
        // this.fetchData()
    },
    beforeDestroy() { },
    mounted() {
        this.fetchData()
        this.$nextTick(() => {
            console.log('this.$refs.tableSort', this.$refs['tableSort']);
        })
    },
    methods: {
        parseTime,
        ...mapActions(useArticleStore, ['getArticleList', 'handleCurrentChange', 'handleSizeChange', 'deleteArticle']),
        tableSortChange() {
            // const imageList = []
            // this.$refs.tableSort.tableData.forEach((item, index) => {
            //     imageList.push(item.img)
            // })
            // this.imageList = imageList
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
            this.$confirm(`你确定要删除${row.articleTitle}吗？`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                center: true
            }).then(() => {
                this.deleteArticle(row.id)
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
            // if (row.id) {
            //     this.$confirm(
            //         `你确定要删除${row.articleTitle}吗？`,
            //         null,
            //         async () => {
            //             this.deleteArticle(row.id)
            //         }
            //     )
            // } else {
            //     if (this.selectRows.length > 0) {
            //         const ids = this.selectRows.map((item) => item.id).join()
            //         this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            //             const { msg } = await doDelete({ ids: ids })
            //             this.$baseMessage(msg, 'success')
            //             this.fetchData()
            //         })
            //     } else {
            //         this.$baseMessage('未选中任何行', 'error')
            //         return false
            //     }
            // }
        },
        handleBatchDelete(ids) {
        },
        // handleSizeChange(val) {
        //     this.$emit('size-change', val)
        //     // this.queryForm.pageSize = val
        //     // this.fetchData()
        // },
        // handleCurrentChange(val) {
        //     this.$emit('current-change', val)
        //     // this.queryForm.pageNo = val
        //     // this.fetchData()
        // },
        handleKeywordSearch() {
            this.$emit('search-keyword', this.keyword)
        },
        async fetchData() {
            await this.getArticleList(1190797313)
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

<style  scoped></style>
