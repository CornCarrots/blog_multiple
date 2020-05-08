$(
    function () {
        var operations = {
            uri:"/foreStart",
            pages:[],
            articles:[]
        };
        var operationVue = new Vue(
            {
                el:".main",
                data:operations,
                mounted:function () {
                    this.list(0);
                },
                methods:{
                    list: function (start) {
                        var url = getPath() + this.uri + "?start=" + start;
                        axios.get(url).then(
                            function (value) {
                                if (value.code != '0') {
                                    location.href = getPath() + "/error";
                                }
                                if(value.data.content.length>0)
                                {
                                    operationVue.pages = value.data;
                                    operationVue.articles = value.data.content;
                                    $(".historyTable").show();
                                    $(".notfound_list").hide();
                                    $(".pageDiv").show();

                                }
                                else
                                {
                                    $(".historyTable").hide();
                                    $(".notfound_list").show();
                                    $(".pageDiv").hide();
                                }
                            }
                        )
                    },
                    jump:function (page) {
                        jump(page,operationVue);
                    },
                    jumpByNumber:function (start) {
                        jumpByNumber(start,operationVue);
                    },
                    deleteHistory:function (id) {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在取消收藏',
                            theme:'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    var url = getPath()+operationVue.uri+"/"+id;
                                    axios.delete(url).then(function(value){
                                        if (value.code == '500604') {
                                            $.alert('成功删除!');
                                            operationVue.list(0);
                                        }
                                        else {
                                            $.alert('抱歉!' + value.msg);
                                        }
                                    });
                                },
                                '取消': {
                                    action: function () {
                                        $.alert('已取消!');
                                    }
                                }
                            }
                        });
                    },
                    getArticle:function (id) {
                        var param = window.btoa("aid="+id+"&timeStamp="+new Date().getTime());
                        var url = getPath()+"/article?"+param;
                        return url;
                    },
                    checkArticle:function(id){
                        return checkArticle(id);
                    }
                }
            }
        );
    }
);