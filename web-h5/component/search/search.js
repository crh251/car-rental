let search_outer = new Vue({
    el: '#search-outer',
    data: {
        inputWord: '',
        //保存的是临时的搜索关键字，主要是为了上下键能回到原来关键字
        tempWord: '',
        //设置搜索框的宽度
        width: 300,
        left: (document.documentElement.clientWidth - 300) / 2,
        top: 120
    },
    watch: {
        width: function () {
            suggest_outer.width = this.width;
        },
        left: function () {
            suggest_outer.left = this.left;
        },
        top: function () {
            suggest_outer.top = this.top;
        }
    },
    methods: {
        inputFn: function (event) {
            let searchKey = event.target.value;
            console.log(event.target.value);
            console.log(event.key + " " + event.code);
            if (event.key === 'ArrowUp') {
                //按下了上键
                if (suggest_outer.isDisplay) {
                    suggest_outer.liIndex--;
                } else {
                    suggest_outer.isDisplay = true;
                    suggest_outer.liIndex = -1;
                }
            } else if (event.key === 'ArrowDown') {
                //按下了下键
                if (suggest_outer.isDisplay) {
                    suggest_outer.liIndex++;
                } else {
                    suggest_outer.isDisplay = true;
                    suggest_outer.liIndex = -1;
                }
            } else if (event.key === 'Escape') {
                //按下了ESC键
                console.log("按下了ESC键");
                suggest_outer.isDisplay = false;
                suggest_outer.liIndex = -1;
                this.tempWord = this.inputWord;
            } else if (event.key === 'Enter') {
                //按下了回车键
                if (suggest_outer.liIndex === -1) {
                    if (this.inputWord.trim().length === 0) {
                        return;
                    }
                    //搜索结果
                    window.open('./result.html?w=' + this.inputWord, '_self');
                } else if (suggest_outer.liIndex < suggest_outer.songs.length) {
                    //歌曲结果
                    alert("还没有写歌曲详情页");
                } else if (suggest_outer.liIndex < suggest_outer.songs.length + suggest_outer.singers.length) {
                    //歌手结果
                    window.open('./singer.html?p=1&mid=' + suggest_outer.singers[suggest_outer.liIndex - suggest_outer.songs.length].mid, '_self');
                }
            } else {
                //按下了可显示的键
                this.tempWord = this.inputWord;
                if (searchKey.length === 0) {
                    suggest_outer.songs = [];
                    suggest_outer.singers = [];
                    return;
                }

                suggest_outer.liIndex = -1;
                suggest_outer.isDisplay = true;

                axios({
                    method: 'get',
                    url: webUrl + '/suggest',
                    params: {
                        w: searchKey,
                    }
                }).then(response => {
                    console.log("ajax请求：" + searchKey);
                    let songsTemp = [];
                    for (let i = 0; i < response.data.data.song.itemlist.length; i++) {
                        let song = response.data.data.song.itemlist[i];
                        songsTemp.push(song);
                    }
                    suggest_outer.songs = songsTemp;
                    let singersTemp = [];
                    for (let i = 0; i < response.data.data.singer.itemlist.length; i++) {
                        let singer = response.data.data.singer.itemlist[i];
                        singersTemp.push(singer);
                    }
                    suggest_outer.singers = singersTemp;
                }).catch(error => console.log(error));
            }
        },
        btnClick: function () {
            window.open('./result.html?w=' + this.inputWord, '_self');
        },
        inputClick: function () {
            suggest_outer.isDisplay = true;
            // console.log("show");
        }
    },
    mounted: function () {
        this.$el.style.display = "block";
        window.addEventListener("click", function (event) {
            // console.log(event.target);
            // console.log("click");
            suggest_outer.isDisplay = false;
            suggest_outer.liIndex = -1;
        })
    }
});

let suggest_outer = new Vue({
    el: '#suggest-outer',
    data: {
        isDisplay: true,
        width: search_outer.width,
        left: search_outer.left,
        top: search_outer.top,
        //上下键位置
        liIndex: -1,
        //鼠标选中位置，-1表示未选中任何<li>
        mouseIndex: -1,
        songs: [
            // {
            //     name: '我还想她',
            //     singer: '林俊杰'
            // }
        ],
        singers: [
            // {
            //     name: '林俊杰'
            // }
        ]
    },
    methods: {
        songClick: function (id, mid) {
            console.log(id + "  " + mid);
            alert("还没有写歌曲详情页");
        },
        singerClick: function (id, mid) {
            console.log(id + "  " + mid);
            window.open('./singer.html?p=1&mid=' + mid, '_self');
        }
    },
    watch: {
        width: function () {
            search_outer.width = this.width;
        },
        left: function () {
            search_outer.left = this.left;
        },
        top: function () {
            search_outer.top = this.top;
        },
        liIndex: function () {

            if (this.liIndex < -1) {
                this.liIndex = this.songs.length + this.singers.length - 1;
            } else if (this.liIndex > this.songs.length + this.singers.length - 1) {
                this.liIndex = -1;
            }

            if (this.liIndex === -1) {
                search_outer.inputWord = search_outer.tempWord;
            } else if (this.liIndex < this.songs.length) {
                search_outer.inputWord = this.songs[this.liIndex].name + " -" + this.songs[this.liIndex].singer;
            } else {
                search_outer.inputWord = this.singers[this.liIndex - this.songs.length].name;
            }
        }
    }
});