<template>
  <div style="display: flex; align-items: center">
    <el-input
      v-if="inputVisible"
      ref="inputRef"
      v-model="inputVal"
      style="width: 100px"
      @keyup.enter="handleInputConfirm()"
      @blur="handleInputConfirm()"
    />
    <el-tag
      effect="dark"
      :type="type"
      v-else
      style="cursor: pointer; display: flex; align-items: center"
      @click="showInput()"
    >
      {{ text }}
      <i class="iconfont" :class="icon" />
    </el-tag>
    <div>
      <el-tag
        v-for="(item, index) in data"
        :key="item + index"
        style="margin: 5px"
        closable
        @close="removeItem(item)"
        :type="type"
      >
        <i class="iconfont" :class="icon" />
        {{ item }}
      </el-tag>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    text: {
      type: String,
      default: ''
    },
    data: {
      type: Array,
      default: () => []
    },
    icon: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      inputVal: '',
      inputVisible: false
    }
  },
  created() {
    console.log('text', this.text)
  },
  computed: {},
  methods: {
    showInput() {
      this.inputVisible = true
      this.$nextTick(() => {
        this.$refs['inputRef'].focus()
      })
    },
    handleInputConfirm() {
      if (typeof this.inputVal === 'string' && this.inputVal.length > 0) {
        if (!Array.isArray(this.items)) {
          this.items = []
        }
        this.$emit('set-items', this.inputVal)
        this.inputVal = ''
        this.inputVisible = false
      }
    },
    removeItem(value) {
      this.$emit('rm-item', value)
      this.inputVal = ''
    }
  }
}
</script>
<style scoped></style>
