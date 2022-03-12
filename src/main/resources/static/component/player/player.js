let setSongInfo = function () {
    axios({
        method: 'get',
        url: webUrl + '/songInfo',
        params: {
            songmid: songInfo.data.mid,
            songid: songInfo.data.id
        }
    }).then(response => {
        // console.log(response);
        songInfo.data.songName = response.data.songinfo.data.track_info.name;
        songInfo.data.singers = [];
        for (let i = 0; i < response.data.songinfo.data.track_info.singer.length; i++) {
            songInfo.data.singers.push(response.data.songinfo.data.track_info.singer[i].name);
        }
        songInfo.data.singers.push('林俊杰');
        songInfo.data.album = response.data.songinfo.data.track_info.album.name;
        // songInfo.data.interval = parseInt(response.data.songinfo.data.track_info.interval);
        // songInfo.data.time = interval2time(songInfo.data.interval);
        songInfo.data.photoUrl = webUrl + "/photo?songmid=" + songInfo.data.mid;
        songInfo.data.songUrl = webUrl + "/songDownload?songmid=" + songInfo.data.mid;
    }).catch(error => {
        console.log(error);
    });

    axios({
        method: 'get',
        url: webUrl + '/rawlyric',
        params: {
            songmid: songInfo.data.mid,
            songid: songInfo.data.id
        }
    }).then(response => {
        songInfo.data.rawLyric = response.data;
    }).catch(error => console.log(error));
};

let songInfo = {
    data: {
        id: getParams('id') || '212877900',
        mid: getParams('mid') || '001J5QJL1pRQYB',
        isPlay: false,
        songName: '',
        singers: [],
        album: '',
        //总时长，单位为秒
        interval: 0,
        //总时长 mm:ss
        time: '00:00',
        //播放百分比
        percent: 0,
        //已经播放了几毫秒
        currentMillisecond: 0,
        //已经播放的时间 mm:ss
        currentTime: '00:00',
        //音量大小，0-100，但是audio的音量是0-1
        voice: 50,
        isQuiet: false,
        isPress: false,
        rawLyric: [
            {
                millisecond: 10,
                lyric: ''
            }
        ],
        photoUrl: '',
        songUrl: ''
    }
};

new Vue({
    mixins: [songInfo],
    el: '#bg',
    mounted: function () {
        setSongInfo();
    }
});

let audioPlayer = new Vue({
    mixins: [songInfo],
    el: '#audio-player',
    watch: {
        isPlay: function () {
            if (this.isPlay) {
                this.$el.play();
            } else {
                this.$el.pause();
            }
        },
        isQuiet: function () {
            this.$el.muted = this.isQuiet;
        }
    },
    methods: {
        startPlay: function () {
            this.interval = this.$el.duration;
            this.time = interval2time(parseInt(this.interval));
            audioPlayer.$el.volume = this.voice / 100;
            console.log("开始播放");
        },
        musicPlay: function () {
            this.isPlay = true;
            console.log("播放");
        },
        musicStop: function () {
            this.isPlay = false;
            console.log("暂停");
        },
        updateTime: function () {
            // console.log(this.$el.currentTime);
            if (!this.isPress) {
                // console.log("update percent");
                this.currentTime = interval2time(parseInt(this.$el.currentTime));
                this.currentMillisecond = parseInt(this.$el.currentTime * 1000);
                this.percent = (this.currentMillisecond) / (this.interval * 10) || 0;
            }
        }
    }
});

let lyric_outer = new Vue({
    mixins: [songInfo],
    el: '#lyric-outer'
});

let pause_play_outer = new Vue({
    mixins: [songInfo],
    el: '#pause-play-outer',
    methods: {
        mouseDownFn: function () {
            this.isPress = true;
            console.log("down");
        },
        mouseUpFn: function () {
            audioPlayer.$el.currentTime = this.interval * this.percent / 100;
            this.isPress = false;
            console.log("up");
        },
        inputFn: function () {
            this.currentTime = interval2time(parseInt(this.interval * this.percent / 100));
            // console.log("input");
        },
        voiceFn: function () {
            if (this.isQuiet) {
                this.isQuiet = false;
            }
            audioPlayer.$el.volume = this.voice / 100;
            console.log("音量：" + audioPlayer.$el.volume);
        }
    }
});

document.addEventListener("keyup", function (event) {
    if (event.code === 'Space' || event.key === ' ') {
        audioPlayer.isPlay = !audioPlayer.isPlay;
    }
    // console.log(event.code);
});