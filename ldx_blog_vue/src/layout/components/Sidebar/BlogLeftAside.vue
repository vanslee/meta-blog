<template>
  <el-card class="card_box" shadow="hover">
    <!-- <video src="./横版-原神-昼.mp4" muted loop autoplay></video> -->
    <div class="shell">
      <div class="cover">
        <img src="@/assets/logo.png" alt="" />
      </div>
      <div class="info">
        <div class="title">只因你太美</div>
        <div class="singer">坤坤</div>
      </div>
      <div class="volume-box">
        <span class="volume-down"><i class="material-icons">remove</i></span>
        <input
          type="range"
          class="volume-range"
          step="1"
          value="80"
          min="0"
          max="100"
          oninput="music.volume = this.value/100"
        />
        <span class="volume-up"><i class="material-icons">add</i></span>
      </div>
      <div class="btn-box">
        <i class="el-icon-refresh-left" onclick="handleRepeat()" />
        <i class="el-icon-star-off" onclick="handleFavorite()" />
        <i class="el-icon-mic" onclick="handleVolume()" />
      </div>
      <div class="music-box">
        <input type="range" class="seekbar" step="1" value="0" min="0" max="100" oninput="handleSeekBar()" />
        <audio class="music-element">
          <source src="https://lidengxiang.top/Chat/11582.mp3" />
        </audio>
        <span class="current-time">0:0</span>
        <span class="duration">0:0</span>
        <span class="play" onclick="handlePlay()">
          <i class="el-icon-video-play" />
        </span>
      </div>
    </div>
  </el-card>
</template>
<script>
export default {
  data() {
    return {}
  },
  created() {},
  computed: {},
  methods: {}
}
</script>
<style src="@/assets/styles/mp3_player.css" scoped></style>
