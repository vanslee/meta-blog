<template>
    <div style="width: 100%;height: 100%;overflow-y: scroll;">
        <el-skeleton :rows="6" animated v-if="!tags" />
        <el-timeline v-else>
            <template v-for="(tag) in tags">
                <el-timeline-item timestamp="2018/4/12" placement="top">
                    <el-card :key="tag.id">
                        <h4>{{ tag.tagName }}</h4>
                        <p>标签创建于 {{ formatTimeStamp(tag.createTime) }}</p>
                    </el-card>
                </el-timeline-item>
            </template>
        </el-timeline>
        <el-pagination style="text-align: center;" background layout="prev, pager, next" :total="totalTags"
            :page-size="params.size" @current-change="changePage" />
    </div>
</template>
<script>
import { useArticleStore } from '@/stores/article';
import { mapState, mapActions } from 'pinia';
import { formatTimeStamp } from '@/utils/time'
export default {
    data() {
        const params = {
            size: 5,
            current: 0,
        }
        return {
            params,

        }
    },
    created() {
        this.fetchData()
    },
    computed: {
        ...mapState(useArticleStore, ['tags', 'totalTags'])

    },
    methods: {
        ...mapActions(useArticleStore, ['getArticleTags']),
        async fetchData() {
            this.getArticleTags(this.params)
        },
        changePage(current) {
            this.params.current = current
            this.fetchData()
        },
        formatTimeStamp,
    },
}
</script>
<style  scoped></style>
