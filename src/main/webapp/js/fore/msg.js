$(function () {
    var bean = {
        uri: "/foreMsg",
        comments: [{text:'',user:{nickName:''},article:{title:''}}],
        pages: [],
        // map:[],
        // users:[],
        pages_msg:[],
        msgs:[{text:'',send:{nickName:''},reveive:{nickName:''}}],
        start_msg:0,
        tab:0,
        start:0
    };
    var memberVue = new Vue(
        {
            el: ".main",
            data: bean,
            filters:{
                statusFilter:function (value) {
                    if(value==0)
                        return '已读';
                    if(value==2)
                        return '未读';
                }
            },
            mounted: function () {
                this.list(0);
            },
            methods: {
                list: function (start) {
                    if (this.tab == 1)
                        memberVue.start = start;
                    else if (this.tab == 2)
                        memberVue.start_msg = start;
                    var url = getPath() + this.uri + "?start=" + start + "&timeStamp=" + new Date().getTime();
                    axios.get(url).then(
                        function (value) {
                            if (value.code != '0') {
                                location.href = getPath() + "/error";
                            }
                            // var arr
                            if(value.data.comments.content.length>0)
                            {
                                memberVue.pages = value.data.comments;
                                memberVue.comments = value.data.comments.content;
                                $(".back_message_list_table").show();
                                $(".notfound_list").hide();
                                $(".pageDiv").show();

                            }
                            else
                            {
                                $(".back_message_list_table").hide();
                                $(".notfound_list").show();
                                $(".pageDiv").hide();
                            }
                            if(value.data.msgs.content.length>0)
                            {
                                memberVue.pages_msg = value.data.msgs;
                                memberVue.msgs = value.data.msgs.content;
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
                        }
                    );
                },
                jump: function (page, tab) {
                    memberVue.tab = tab;
                    if (tab == 1) {
                        if ('first' == page && !memberVue.pages.first)
                            memberVue.list(0);

                        else if ('pre' == page && memberVue.pages.hasPrevious)
                            memberVue.list(memberVue.pages.number - 1);

                        else if ('next' == page && memberVue.pages.hasNext)
                            memberVue.list(memberVue.pages.number + 1);

                        else if ('last' == page && !memberVue.pages.last)
                            memberVue.list(memberVue.pages.totalPages - 1);
                    }
                    else if (tab == 2) {
                        if ('first' == page && !memberVue.pages_msg.first)
                            memberVue.list(0);

                        else if ('pre' == page && memberVue.pages_msg.hasPrevious)
                            memberVue.list(memberVue.pages_msg.number - 1);

                        else if ('next' == page && memberVue.pages_msg.hasNext)
                            memberVue.list(memberVue.pages_msg.number + 1);

                        else if ('last' == page && !memberVue.pages_msg.last)
                            memberVue.list(memberVue.pages_msg.totalPages - 1);
                    }
                },
                jumpByNumber: function (start, tab) {
                    memberVue.tab = tab;
                    if (tab == 1) {
                        if(start!=memberVue.pages.number)
                            memberVue.list(start);
                    }
                    else if (tab == 2) {
                        if(start!=memberVue.pages_msg.number)
                            memberVue.list(start);
                    }
                },
                getImage: function (id) {
                    var url = getPath() + "/image/profile_user/" + id + ".jpg";
                    return url;
                },
                checkButton:function (id,status, type) {
                    var url = getPath() + memberVue.uri+"/check/" + id;
                    axios.post(url,{status:status,type:type}).then(
                        function (value) {
                            if (value.code == '500305') {
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '标记成功',
                                        theme:'modern',
                                        icon: 'fa fa-smile-o',
                                        buttons: {
                                            ok: {
                                                action: function () {
                                                    if (type == 1){
                                                        memberVue.list(memberVue.start);
                                                    }
                                                    else if (type == 2){
                                                        memberVue.list(memberVue.start_msg);
                                                    }

                                                }
                                            }
                                        }
                                    }
                                );
                            }else {
                                $.alert("抱歉!" + value.msg);
                            }

                        }
                    );
                }
            }
        }
    );
});