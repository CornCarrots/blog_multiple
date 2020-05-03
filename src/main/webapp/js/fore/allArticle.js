$(
    function () {
        var articles = {
            uri: "/user/articles",
            pages: [],
            articles: [],
            userCategories: [{name: '', child: [{name: '', id: ''}]}],
            key: '',
            has: false,
            cid: 0,
            uid: 0
        };
        var articleVue = new Vue(
            {
                el: ".user",
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
                        var id = getUrlParms('uid');
                        if (id == undefined)
                            id = 0;
                        this.uid = id;
                        var url = getPath() + this.uri + "?start=" + start + "&uid=" + this.uid + "&cid=" + this.cid + "&key=" + this.key + "&timeStamp=" + new Date().getTime();
                        axios.get(url).then(
                            function (value) {
                                if (value.code != '0') {
                                    location.href = getPath() + "/error";
                                }
                                if (value.data.pages.content.length > 0) {
                                    articleVue.pages = value.data.pages;
                                    articleVue.articles = value.data.pages.content;
                                    $(".back_article_list_table").show();
                                    $(".notfound_list").hide();
                                    $(".pageDiv").show();
                                    $(".notfound_list2").hide();
                                    Vue.nextTick(function () {
                                        checkListener();
                                    });
                                }
                                else {
                                    $(".back_article_list_table").hide();
                                    $(".pageDiv").hide();
                                    if (value.data.issearch) {
                                        $(".notfound_list2").show();

                                    } else {
                                        $(".notfound_list").show();
                                    }
                                }
                                articleVue.has = value.data.has;
                                articleVue.userCategories = value.data.userCategories;

                            }
                        )
                    },
                    getCategoryButton: function (id) {
                        this.cid = id;
                        this.key = '';
                        this.list(0);
                    },
                    jump: function (page) {
                        jump(page, articleVue);
                    },
                    back: function () {
                        window.history.back();
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
                                        if (value.code == '500216') {
                                            $.alert('成功删除!');
                                            articleVue.list(0, articleVue.cid);
                                        }
                                        else {
                                            $.alert('抱歉!'+ value.msg);
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
                    getButton: function (id) {
                        var param = window.btoa("aid=" + id + "&timeStamp=" + new Date().getTime());
                        var url = getPath() + "/editArticle?" + param;
                        return url;
                    },
                    search: function () {
                        if (!checkEmpty(this.key, '关键词')) {
                            return;
                        }
                        if (this.key.length >= 10) {
                            alert("关键词长度不能大于十，请重新搜索");
                            return;
                        }
                        this.list(0);
                    },
                    PDFButton: function () {
                        var input = $("input[name='articleCheckbox']:checked");
                        if (input.length == 0) {
                            $.alert('请先选择文章!');
                            return;
                        }
                        $("#js_print_content").print({
                            // title: helpVue.article.title+"_by"+helpVue.article.user.nickName
                            // prepend:pre
                        });
                    }
                }
            });

        function checkListener() {
            $("#checkAllTH input").click(
                function () {
                    checkAll();
                    if(check1)
                    {
                        $(".text_main").removeClass("no-print")
                    }
                    else
                    {
                        $(".text_main").addClass("no-print")
                    }
                }
            );
            $(".checkOne input").click(
                function () {
                    checkOne();
                    var e = event.target;
                    console.log($(e))
                    if ($(e).prop("checked")) {
                        $("#text_main_"+ $(e).attr("id")).removeClass("no-print");
                    }
                    else {
                        $("#text_main_"+ $(e).attr("id")).addClass("no-print");
                    }
                }
            );
        }
    }
)
;
