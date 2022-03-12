let song_list_outer = new Vue({
    el: '#song-list-outer',
    data: {
        width: document.documentElement.clientWidth * 0.8,
        height: 800,
        left: 100,
        top: 100,
        isSelectDisplay: false,
        isShowBorder: false,
        mouseIndex: -1,
        songs: [
            /*
            {
                id: '123456',
                mid: 'X6515',
                name: '我还想她',
                singers: [
                    {
                        name: '林俊杰',
                        id: '4563221',
                        mid: 'ASD785'
                    },
                    {
                        name: '周杰伦',
                        id: '4563421',
                        mid: 'A2D785'
                    }
                ],
                album: 'JJ陆',
                time: '4:05'
            }
            */
        ]
    },
    mounted: function () {
        getSongListData();
    },
    methods: {
        playAllSong: function () {
            alert("这个功能还没写好");
            console.log("播放全部音乐");
        },
        playSelect: function () {
            alert("这个功能还没写好");
        },
        playSong: function (id, mid) {
            window.open("player.html?id=" + id + "&mid=" + mid, "_blank");
            console.log(id + " " + mid);
        },
        addSong: function (id, mid) {
            console.log(id + " " + mid);
            alert("添加音乐还没写好");
        },
        openSongInfo: function (id, mid) {
            alert("点击旁边的播放按钮播放音乐");
            console.log(id + " " + mid);
        },
        openSingerInfo: function (id, mid) {
            window.open("./singer.html?p=1&mid=" + mid, "_self");
            console.log(id + " " + mid);
        },
        showSongList: function () {
            this.$el.style.display = "block";
            this.left = (document.documentElement.clientWidth - this.width) / 2;
            window.addEventListener("resize", function () {
                song_list_outer.width = document.documentElement.clientWidth * 0.8;
                song_list_outer.left = (document.documentElement.clientWidth - song_list_outer.width) / 2;
            });
        }
    }
});