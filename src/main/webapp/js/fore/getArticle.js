$(
    function () {
        var editor = null;
        var options = {
            cssPath: getPath() + '/js/include/kindeditor/plugins/code/prettify.css',
            cssData: 'body {font-family: "微软雅黑"; font-size: 14px}',
            // autoHeightMode: true,
            width: "100%",
            // minheight: 30,
            height: "400%",
            resizeType: 0,
            filterMode: true,
            allowImageUpload: true,//允许上传图片
            items: [
                'source', 'fullscreen', '|', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'emoticons', 'image', 'link'
            ],
            filePostName: "image",
            uploadJson: getPath() + "/admin/articles/image",
            dir: "image",
            afterUpload: function (value) {//图片上传后，将上传内容同步到textarea中
                this.sync();
            },
            afterFocus: function () {      //得到焦点事件
                // self.edit = edit = this; var strIndex = self.edit.html().indexOf("@我也说一句"); if (strIndex != -1) { self.edit.html(self.edit.html().replace("@我也说一句", "")); }
            },
            afterBlur: function () {
                this.sync();//失去焦点时，将上传内容同步到textarea中
            },
            afterCreate: function () {
                var self = this;
                self.html("请输入：");
                KindEditor.ctrl(document, 13, function () {
                    articleVue.addArticle_();
                });
                KindEditor.ctrl(self.edit.doc, 13, function () {
                    self.sync();
                    articleVue.addArticle_();
                });
            }
        };
        var articles = {
            uri: "/user/articles",
            article: {
                id: 0,
                title: '',
                summary: '',
                secret: '',
                text: '',
                cid: 0,
                status: 0,
                createDate: null,
                updateDate: null,
                type: 'TYPE_PUBLISH',
                view: 0,
                like: 0,
                start: 0,
                comment: 0
            },
            categories: [],
            cid: 0,
            uri_tag: "/user/tags",
            pages: {},
            pages_tag: {},
            temp: [],
            tags: [],
            all: [],
            key: '',
            issearch: false,
            tids: [],
            start: 0,
            tag: {id: 0, name: '', status: 0, count: 0,uid:0}
        };
        var articleVue = new Vue(
            {
                el: ".user",
                data: articles,
                mounted: function () {
                    this.get();
                },
                methods: {
                    get: function () {
                        var aid = getUrlParms("aid");
                        var url = getPath() + articles.uri + "/" + aid;
                        axios.get(url).then(
                            function (value) {
                                console.log(value)
                                articles.categories = value.data.categories;
                                articles.article = value.data.article;
                                Vue.nextTick(function () {
                                    $(value.data.tag).each(
                                        function (i, data) {
                                            articleVue.tids.push(data.id);
                                        });
                                    articleVue.list(0);
                                    var html = '';
                                    var found = '';
                                    $(articleVue.categories).each(
                                        function (i, data) {
                                            if (articleVue.article.cid == data.id)
                                                found = data.id;
                                            html += '<option value=\"' + data.id + '\">' + data.name + '</option>';
                                        }
                                    );
                                    $("#addSelect").html(html);
                                    $("#addSelect").selectpicker('val', found);
                                    $("#addSelect").selectpicker('refresh');
                                    $("#addSelect").selectpicker('render');
                                    $(value.data.tag).each(function (i, data) {
                                        var id = data.id;
                                        $("input[id=" + id + "]").prop("checked", true);
                                    });
                                    editor = KindEditor.create("#editor_id", options);
                                    editor.html(articleVue.article.text);
                                    $("#addForm").validationEngine({
                                        promptPosition: 'centerRight',
                                        showArrow: true,
                                        // scroll:false,
                                        // focusFirstField:false
                                    });
                                });
                            }
                        );

                    },
                    list: function (start) {
                        this.start = start;
                        var tidArr = ",";
                        $(this.tids).each(
                            function (i, data) {
                                tidArr += data;
                                tidArr += ",";
                            });
                        var url = getPath() + "/user/writing/tag?start=" + start + "&tid=" + tidArr;
                        axios.get(url).then(
                            function (value) {
                                articleVue.all = value.data.all;
                                if (value.data.page.content.length > 0) {
                                    articleVue.pages = value.data.page;
                                    articleVue.pages_tag = value.data.page;
                                    articleVue.temp = value.data.page.content;
                                    $(".info_tag_myTag").show();
                                    $(".notfound_list2").hide();
                                    $(".pageDiv2").show();
                                    var arr = new Array();
                                    $(articleVue.temp).each(
                                        function (i, data) {
                                            var tag = {
                                                id: 0, name: '', status: 0, count: 0, uid: 0, has: false
                                            };
                                            tag.id = data.id;
                                            tag.name = data.name;
                                            tag.status = data.status;
                                            tag.count = data.count;
                                            tag.uid = data.uid;
                                            $(articleVue.tids).each(
                                                function (j, res) {
                                                    if (res == tag.id) {
                                                        tag.has = true;
                                                        return;
                                                    }
                                                }
                                            );
                                            arr.push(tag);
                                        }
                                    );
                                    articleVue.tags = arr;
                                }
                                else {
                                    $(".info_tag_myTag").hide();
                                    $(".notfound_list2").show();
                                    $(".pageDiv2").hide();
                                }
                            }
                        );
                    }, jump: function (page, id) {
                        jump(page, articleVue);
                    },
                    jumpByNumber: function (start, id) {
                        jumpByNumber(start, articleVue);
                    },
                    closeButton: function () {
                        location.reload();
                    },
                    editArticle: function () {
                        if (!$("#addForm").validationEngine("validate")) return false;
                        articleVue.article.text = editor.html();
                        if (articleVue.article.text.length == 0) {
                            $.alert("文章内容不可为空!");
                            return;
                        }
                        var url = getPath() + articles.uri + "/" + getUrlParms("aid");
                        axios.put(url, {article: articleVue.article, tags: articleVue.tids}).then(
                            function (value) {
                                if (value.data == 'yes') {
                                    $.alert(
                                        {
                                            title: '恭喜你!',
                                            content: '修改文章成功',
                                            theme: 'modern',
                                            icon: 'fa fa-smile-o',
                                            buttons: {
                                                ok: {
                                                    action: function () {
                                                        editor.html('');
                                                        location.href = getPath() + '/allArticle';
                                                    }
                                                }
                                            }
                                        }
                                    );
                                }

                            }
                        );
                    },
                    addCategoryButton: function () {
                        $("#addCategoryModel").modal("show");
                    },
                    cancel_: function () {
                        articleVue.issearch = false;
                        articleVue.key = '';
                        articleVue.list(0);
                    },
                    search: function () {
                        if (!checkEmpty(this.key, '关键词')) {
                            return;
                        }
                        if (this.key.length >= 10) {
                            alert("关键词长度不能大于十，请重新搜索")
                            return;
                        }
                        articleVue.issearch = true;
                        var key = this.key;
                        var url = getPath() + articleVue.uri_tag + "/search/?key=" + key;
                        axios.post(url).then(
                            function (value) {
                                console.log(value)
                                $(".pageDiv2").hide();
                                articleVue.tags = [];
                                if (value.data.tags.length > 0) {
                                    articleVue.temp = value.data.tags;
                                    $(".info_tag_myTag").show();
                                    $(".notfound_list2").hide();
                                    var arr = new Array();
                                    $(articleVue.temp).each(
                                        function (i, data) {
                                            var tag = {
                                                id: 0, name: '', status: 0, count: 0, has: false
                                            };
                                            tag.id = data.id;
                                            tag.name = data.name;
                                            tag.status = data.status;
                                            tag.count = data.count;
                                            $(articleVue.tids).each(
                                                function (j, res) {
                                                    if (res == tag.id) {
                                                        tag.has = true;
                                                        return;
                                                    }
                                                }
                                            );
                                            arr.push(tag);
                                        }
                                    );
                                    articleVue.tags = arr;
                                }
                                else {
                                    $(".info_tag_myTag").hide();
                                    $(".notfound_list2").show();
                                }
                            }
                        );
                    },
                    addTags: function (id, e) {
                        var button = e.target;
                        var index = $.inArray(id, articleVue.tids);
                        if (index == -1) {
                            articleVue.tids.push(id);
                            $(button).removeClass("btn-danger");
                            $(button).addClass("btn-success");
                            if (!articleVue.issearch) {
                                articleVue.list(articleVue.start);
                            }
                            else {
                                var tidArr = ",";
                                $(articleVue.tids).each(
                                    function (i, data) {
                                        tidArr += data;
                                        tidArr += ",";
                                    });
                                var url = getPath() + "/user/writing/tag?tid=" + tidArr;
                                axios.get(url).then(
                                    function (value) {
                                        articleVue.all = value.data.all;
                                    });
                            }
                        }
                        else {
                            articleVue.tids.splice(index, 1);
                            $(button).removeClass("btn-success");
                            $(button).addClass("btn-danger");
                            if (!articleVue.issearch) {
                                articleVue.list(articleVue.start);
                            }
                            else {
                                var tidArr = ",";
                                $(this.tids).each(
                                    function (i, data) {
                                        tidArr += data;
                                        tidArr += ",";
                                    });
                                var url = getPath() + "/user/writing/tag?tid=" + tidArr;
                                axios.get(url).then(
                                    function (value) {
                                        articleVue.all = value.data.all;
                                    });
                            }
                        }
                    },
                    addTag: function () {
                        var name = this.key;
                        articleVue.tag.name = name;
                        var url = getPath() + articleVue.uri_tag;
                        axios.post(url, articleVue.tag).then(
                            function (value) {
                                if (value.data.res == 'ok') {
                                    articleVue.tag = {id: 0, name: '', status: 0, count: 0};
                                    $(".pageDiv2").hide();
                                    $(".info_tag_myTag").show();
                                    $(".notfound_list2").hide();
                                    articleVue.tags = [];
                                    if (value.data.tags.length > 0) {
                                        articleVue.temp = value.data.tags;
                                        $(".back_art_category_list_table").show();
                                        $(".notfound_search").hide();
                                        var arr = new Array();
                                        $(articleVue.temp).each(
                                            function (i, data) {
                                                var tag = {
                                                    id: 0, name: '', status: 0, count: 0, uid: 0, has: false
                                                };
                                                tag.id = data.id;
                                                tag.name = data.name;
                                                tag.status = data.status;
                                                tag.count = data.count;
                                                tag.uid = data.uid;
                                                arr.push(tag);
                                            }
                                        );
                                        articleVue.tags = arr;
                                    }
                                    else {
                                        $(".back_art_category_list_table").hide();
                                        $(".notfound_search").show();
                                    }
                                } else {
                                    $.alert({
                                        title: '抱歉，出错了!',
                                        content: '再试试吧'
                                    });
                                }
                            });
                    }
                }
            });

    }
)
;
