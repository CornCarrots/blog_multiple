$(
    function () {
        var bean = {
            uri: "/foreNotice",
            pages:[],
            notices: []
        };
        var noticeVue = new Vue(
            {
                el: ".main",
                data: bean,
                mounted: function () {
                    this.list(0);
                },
                methods: {
                    list: function (start) {
                        var url = getPath() + this.uri + "?start="+start+"&timeStamp=" + new Date().getTime();
                        axios.get(url).then(
                            function (value) {
                                noticeVue.pages = value.data;
                                noticeVue.notices = value.data.content;
                            }
                        );
                    },
                    jump: function (page) {
                        jump(page, noticeVue);
                    },
                    jumpByNumber: function (start) {
                        jumpByNumber(start, noticeVue);
                    },
                    getNotice:function (id) {
                        var param = window.btoa("nid="+id+"&timeStamp="+new Date().getTime());
                        var url = getPath()+"/notice?"+param;
                        return url;
                    }
                }
            });


    }
);