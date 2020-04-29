$(function () {
    var bean = {
        uri: "/foreFellow",
        pages_following: [],
        pages_fellow: [],
        start_following:0,
        start_fellow:0,
        followings:[],
        fellows:[],
        tab:0,
        has:false
        // map:[],
        // users:[],
        // msgs:[]
    };
    var memberVue = new Vue(
        {
            el: ".main",
            data: bean,
            mounted: function () {
                this.list(0);
            },
            methods: {
                list: function (start) {
                    if (this.tab == 1)
                        this.start_following = start;
                    else if (this.tab == 2)
                        this.start_fellow = start;
                    var uid = getUrlParms('uid');
                    if (uid == undefined) {
                        uid = 0;
                    }

                    var url = getPath() + this.uri + "?uid="+ uid +"&start_following=" + this.start_following + "&start_fellow=" + this.start_fellow +"&timeStamp=" + new Date().getTime();
                    axios.get(url).then(
                        function (value) {
                            console.log(value.data)
                            if(value.data.pages_following.content.length>0)
                            {
                                memberVue.pages_following = value.data.pages_following;
                                memberVue.followings = value.data.pages_following.content;
                                $(".back_message_list_table1").show();
                                $(".notfound_list1").hide();
                                $(".pageDiv1").show();

                            }
                            else
                            {
                                $(".back_message_list_table1").hide();
                                $(".notfound_list1").show();
                                $(".pageDiv1").hide();
                            }
                            if(value.data.pages_fellow.content.length>0)
                            {
                                memberVue.pages_fellow = value.data.pages_fellow;
                                memberVue.fellows = value.data.pages_fellow.content;
                                $(".back_message_list_table2").show();
                                $(".notfound_list2").hide();
                                $(".pageDiv2").show();

                            }
                            else
                            {
                                $(".back_message_list_table2").hide();
                                $(".notfound_list2").show();
                                $(".pageDiv2").hide();
                            }
                            memberVue.has = value.data.has;
                        }
                    );
                },
                jump: function (page, tab) {
                    memberVue.tab = tab;
                    if (tab == 1) {
                        if ('first' == page && !memberVue.pages_following.first)
                            memberVue.list(0);

                        else if ('pre' == page && memberVue.pages_following.hasPrevious)
                            memberVue.list(memberVue.pages_following.number - 1);

                        else if ('next' == page && memberVue.pages_following.hasNext)
                            memberVue.list(memberVue.pages_following.number + 1);

                        else if ('last' == page && !memberVue.pages_following.last)
                            memberVue.list(memberVue.pages_following.totalPages - 1);
                    }
                    else if (tab == 2) {
                        if ('first' == page && !memberVue.pages_fellow.first)
                            memberVue.list(0);

                        else if ('pre' == page && memberVue.pages_fellow.hasPrevious)
                            memberVue.list(memberVue.pages_fellow.number - 1);

                        else if ('next' == page && memberVue.pages_fellow.hasNext)
                            memberVue.list(memberVue.pages_fellow.number + 1);

                        else if ('last' == page && !memberVue.pages_fellow.last)
                            memberVue.list(memberVue.pages_fellow.totalPages - 1);
                    }
                },
                jumpByNumber: function (start, tab) {
                    memberVue.tab = tab;
                    if (tab == 1) {
                        if(start!=memberVue.pages_following.number)
                            memberVue.list(start);
                    }
                    else if (tab == 2) {
                        if(start!=memberVue.pages_fellow.number)
                            memberVue.list(start);
                    }
                },
                getImage: function (img) {
                    if (img == undefined)
                        return;
                    var url = getPath() + img;
                    return url;
                }
            }
        }
    );
});