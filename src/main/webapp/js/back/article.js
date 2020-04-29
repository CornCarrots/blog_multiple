$(
    function () {
        var articles = {
            uri: "/admin/articles",
            pages: [],
            articles: [],
            key: ''
        };
        var articleVue = new Vue(
            {
                el: ".container",
                data: articles,
                mounted: function () {
                    this.list(0);
                },
                filters: {
                    typeFilter: function (value) {
                        if (value == 'TYPE_PUBLISH')
                            return '发布';
                        if (value == 'TYPE_DRAFT')
                            return '草稿';
                    }
                },
                methods: {
                    list: function (start) {
                        var url = getPath() + this.uri + "?start=" + start;
                        axios.get(url).then(
                            function (value) {
                                console.log(value)
                                if (value.data.content.length > 0) {
                                    articleVue.pages = value.data;
                                    articleVue.articles = value.data.content;
                                    $(".back_article_list_table").show();
                                    $(".notfound_list").hide();
                                    $(".pageDiv").show();
                                    $(".notfound_search").hide();
                                }
                                else {
                                    $(".back_article_list_table").hide();
                                    $(".notfound_list").show();
                                    $(".pageDiv").hide();
                                    $(".notfound_search").hide();
                                }
                                Vue.nextTick(function () {
                                    checkListener();
                                });
                            }
                        )
                    },
                    jump: function (page) {
                        jump(page, articleVue);
                    },
                    jumpByNumber: function (start) {
                        jumpByNumber(start, articleVue);
                    },
                    deleteButton: function (id) {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除文章',
                            theme: 'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    var url = getPath() + articleVue.uri + "/" + id;
                                    axios.delete(url).then(function (value) {
                                        if (0 != value.data.length) {
                                            $.alert('系统异常，请重试!');
                                        }
                                        else {
                                            $.alert('成功删除!');
                                            articleVue.list(0);
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
                    deleteAllButton: function () {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除文章',
                            theme: 'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    $("input[name='articleCheckbox']:checked").each(
                                        function () {
                                            var input = $(this);
                                            var url = getPath() + articleVue.uri + "/" + input.val();
                                            axios.delete(url).then(function (value) {
                                                if (0 != value.data.length) {
                                                    $.alert('系统异常，请重试!');
                                                }
                                                else {
                                                    $.alert('成功删除!');
                                                    input.prop("checked", false);
                                                    articleVue.list(0);
                                                }
                                            });
                                        }
                                    );
                                },
                                '取消': {
                                    action: function () {
                                        $.alert('已取消!');
                                    }
                                }
                            }
                        });

                    },
                    getButton: function (id) {
                        var param = window.btoa("aid="+id+"&timeStamp="+new Date().getTime());
                        var url = getPath() + "/admin/getArticle?"+param;
                        return url;
                    },
                    editButton: function (id) {
                        var url = getPath()+articles.uri+"/"+ id;
                        axios.put(url).then(
                            function (value) {
                                if(value.data == 'yes' )
                                {
                                    $.alert(
                                        {
                                            title: '恭喜你!',
                                            content: '修改文章成功',
                                            theme: 'modern',
                                            icon: 'fa fa-smile-o'
                                        }

                                    );
                                    articleVue.list(0);
                                }

                            }
                        );
                    },
                    search:
                        function () {
                            if (!checkEmpty(this.key, '关键词')) {
                                return;
                            }
                            if (this.key.length >= 10) {
                                alert("关键词长度不能大于十，请重新搜索")
                                return;
                            }
                            var key = this.key;
                            var url = getPath() + this.uri + "/search?key=" + key;
                            axios.post(url).then(
                                function (value) {
                                    $(".pageDiv").hide();
                                    if (value.data.length > 0) {
                                        articleVue.articles = value.data;
                                        $(".back_article_list_table").show();
                                        $(".notfound_search").hide();
                                    }
                                    else {
                                        $(".back_article_list_table").hide();
                                        $(".notfound_search").show();
                                    }
                                }
                            );
                        }
                }
            }
            )
        ;

        function checkListener() {
            $("#checkAllTH input").click(
                function () {
                    checkAll();
                }
            );
            $(".checkOne input").click(
                function () {
                    checkOne();
                }
            );
        }
    }
)
;
