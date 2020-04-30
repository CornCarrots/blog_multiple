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
            pasteType: 1,
            resizeType: 0,
            filterMode: true,
            allowImageUpload: true,//允许上传图片
            formatUploadUrl: false,
            // allowFileManager : true,//是否允许浏览服务器上传文件
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
            uri: "/user/writing",
            article: {
                id: 0,
                title: '',
                summary: '',
                text: '',
                secret: '',
                cid: 0,
                status: 0,
                createDate: null,
                updateDate: null,
                type: 'TYPE_PUBLISH',
                view: 0,
                like: 0,
                start: 0,
                comment: 0,
                uid:0,
                isSpecialty:false
            },
            categories: [],
            cid: 0,
            uri_tag: "/user/tags",
            pages: {},
            pages_tag:{},
            temp: [],
            tags: [],
            syms: [],
            all: [],
            key: '',
            issearch: false,
            tids: [],
            start: 0,
            tag: {id: 0, name: '', status: 0, count: 0, uid:0},
            isadd:"no",
            start_search:0,
            pages_tag_search:{}
        };
        var articleVue = new Vue(
            {
                el: ".user",
                data: articles,
                mounted: function () {
                    this.get();
                    this.list(0);
                },
                methods: {
                    get: function () {
                        $(".notfound_list_user").hide();
                        // var user = JSON.parse(sessionStorage.getItem("user"));
                        // console.log(sessionStorage.getItem("user"))
                        var url = getPath() + this.uri;
                        axios.get(url).then(
                            function (value) {
                                articles.categories = value.data.categories;
                                Vue.nextTick(function () {
                                    editor = KindEditor.create("#editor_id", options);
                                    $("#addForm").validationEngine({
                                        promptPosition: 'centerRight',
                                        showArrow: true,
                                    });

                                    var html = '';
                                    $(articleVue.categories).each(
                                        function (i, data) {
                                            if (i == 0) {
                                                articleVue.cid = data.id;
                                            }
                                            html += '<option value=\"' + data.id + '\">' + data.name + '</option>';
                                        }
                                    );
                                    $("#addSelect").html(html);
                                    $("#addSelect").selectpicker('val', articleVue.cid);
                                    $("#addSelect").selectpicker('render');
                                    $("#addSelect").selectpicker('refresh');
                                });
                            }
                        );

                    },
                    list: function (start) {
                        if (!this.issearch) {
                            $("#liketag").hide();
                            $(".pageDiv3").hide();
                        }
                        // else{
                        //     $("#liketag").show();
                        // }
                        this.start = start;
                        var tidArr = ",";
                        $(this.tids).each(
                            function (i, data) {
                                tidArr += data;
                                tidArr += ",";
                            });
                        var url = getPath()+ "/user/writing/tag?start=" + start + "&tid=" + tidArr;
                        axios.get(url).then(
                            function (value) {
                                articleVue.all = value.data.all;
                                if (value.data.page.content.length > 0) {
                                    articleVue.pages = value.data.page;
                                    articleVue.pages_tag  = value.data.page;
                                    articleVue.temp = value.data.page.content;
                                    $("#findtag").show();
                                    $(".notfound_list2").hide();
                                    $(".pageDiv2").show();
                                    var arr = new Array();
                                    $(articleVue.temp).each(
                                        function (i, data) {
                                            var tag = {id: data.id, name: data.name, status: data.status, count: data.count, uid:data.uid, has: false};
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
                                    $("#findtag").hide();
                                    $(".notfound_list2").show();
                                    $(".pageDiv2").hide();
                                }
                            }
                        );
                    },
                    jump: function (page, id) {
                        if (id == '2')
                            jump(page, articleVue);
                        else if (id == '3'){
                            if ('first' == page && !articleVue.pages_tag_search.first)
                                articleVue.search(0);

                            else if ('pre' == page && articleVue.pages_tag_search.hasPrevious)
                                articleVue.search(articleVue.pages_tag_search.number - 1);

                            else if ('next' == page && articleVue.pages_tag_search.hasNext)
                                articleVue.search(articleVue.pages_tag_search.number + 1);

                            else if ('last' == page && !articleVue.pages_tag_search.last)
                                articleVue.search(articleVue.pages_tag_search.totalPages - 1);
                        }
                    },
                    jumpByNumber: function (start, id) {
                        if (id == '2')
                            jumpByNumber(start, articleVue);
                        else if (id == '3'){
                            if(start!=articleVue.pages_tag_search.number)
                                articleVue.search(start);
                        }
                    },
                    closeButton: function () {
                        location.reload();
                    },
                    backButton:function(){
                        articleVue.issearch = false;
                        articleVue.key = '';
                        articleVue.list(0);
                    },
                    addArticle: function () {
                        if (!$("#addForm").validationEngine("validate")) return false;
                        articleVue.article.text = editor.html();
                        if (articleVue.article.text.length == 0) {
                            $.alert("文章内容不可为空!");
                            return;
                        }
                        articleVue.article.isSpecialty =  $("#specialtyBox").prop("checked");
                        var url = getPath() + articleVue.uri;
                        axios.post(url, {article: articleVue.article, tags: articleVue.tids}).then(
                            function (value) {
                                if (value.data == 'yes') {
                                    $.alert(
                                        {
                                            title: '恭喜你!',
                                            content: '添加文章成功',
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
                    search: function (start) {
                        this.start_search =start;
                        if (!checkEmpty(this.key, '关键词')) {
                            return;
                        }
                        if (this.key.length >= 10) {
                            alert("关键词长度不能大于十，请重新搜索")
                            return;
                        }
                        articleVue.issearch = true;
                        var key = this.key;
                        var url = getPath() + articleVue.uri_tag + "/search/?key=" + key + "&start=" + start;
                        axios.post(url).then(
                            function (value) {
                                console.log(value);
                                $(".pageDiv2").hide();
                                var likes = value.data.likes;
                                var finds = value.data.tags.content;
                                // articleVue.all = value.data.all;

                                $("#liketag").hide();
                                $("#findtag").hide();
                                if (likes.length > 0 || finds.length > 0) {
                                    if (value.data.tag == null) {
                                        $(".notfound_list2").show();
                                    }else{
                                        $(".notfound_list2").hide();
                                    }
                                    articleVue.syms = [];
                                    if (likes.length > 0) {
                                        $("#liketag").show();
                                        articleVue.temp = likes;
                                        var arr = new Array();
                                        $(articleVue.temp).each(
                                            function (i, data) {
                                                var tag = {id: data.id, name: data.name, status: data.status, count: data.count, uid:data.uid, has: false};
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
                                        articleVue.syms = arr;
                                    }
                                    articleVue.tags = [];
                                    if (finds.length > 0) {
                                        articleVue.pages_tag_search = value.data.tags;
                                        articleVue.temp = finds;
                                        $("#findtag").show();
                                        $(".pageDiv3").show();
                                        var arr = new Array();
                                        $(articleVue.temp).each(
                                            function (i, data) {
                                                var tag = {id: data.id, name: data.name, status: data.status, count: data.count, uid:data.uid, has: false};
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
                                    }else {
                                        $(".pageDiv3").hide();
                                    }
                                } else {
                                    $(".pageDiv3").hide();
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
                            else{
                                articleVue.search(articleVue.start_search);
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
                            else{
                                articleVue.search(articleVue.start_search);
                            }
                        }
                    },
                    addTag: function () {
                        var name = this.key;
                        articleVue.tag.name = name;
                        var url = getPath() + articleVue.uri_tag;
                        axios.post(url, {tag:articleVue.tag,isadd:articleVue.isadd}).then(
                            function (value) {
                                if (value.data.res == 'has') {
                                    var temptag = value.data.tag.name;
                                    $.alert(
                                        {
                                            title: '抱歉!',
                                            content: '当前已存在类似的标签:'+ temptag,
                                            theme: 'modern',
                                            icon: 'fa fa-meh-o',
                                            buttons: {
                                                再看看: {
                                                },
                                                添加并反馈:{
                                                    action: function () {
                                                        articleVue.isadd = "yes";
                                                        var url = getPath() + "/foreMessage?timeStamp="+new Date().getTime();
                                                        var message = {
                                                            id: 0,
                                                            uid: 0,
                                                            createDate: null,
                                                            text: '标签:'+ articleVue.tag.name + ',存在同义词:' + temptag +',不合理',
                                                            reply: null,
                                                            replyDate: null,
                                                            status: 1,
                                                            type: 'type_suggestion'
                                                        };
                                                        axios.post(url, message).then(function (value) {
                                                            if (value.code == '500301') {
                                                                $.alert({
                                                                    title: '我们已收到您的反馈!',
                                                                    content: '请留意您的邮箱，等待我们的工作人员答复',
                                                                    onClose: function () {
                                                                        // before the modal is hidden.
                                                                        articleVue.addTag();
                                                                    }
                                                                });
                                                            }
                                                            else {
                                                                $.alert({
                                                                    title: '抱歉!'+ value.msg,
                                                                    content: '请尝试重新提交'
                                                                });
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        }
                                    );
                                }
                                else if (value.data.res == 'ok') {
                                    articleVue.isadd = "no";
                                    articleVue.tag={id:0,name:'',status:0,count:0};
                                    articleVue.search(articleVue.start_search);
                                    // $(".pageDiv2").hide();
                                    // $("#findtag").show();
                                    // $(".notfound_list2").hide();
                                    // articleVue.tags = [];
                                    // if (value.data.tags.length > 0) {
                                    //     articleVue.temp = value.data.tags;
                                    //     $(".back_art_category_list_table").show();
                                    //     $(".notfound_search").hide();
                                    //     var arr = new Array();
                                    //     $(articleVue.temp).each(
                                    //         function (i, data) {
                                    //             var tag = {id: data.id, name: data.name, status: data.status, count: data.count, uid:data.uid, has: false};
                                    //             arr.push(tag);
                                    //         }
                                    //     );
                                    //     articleVue.tags = arr;
                                    // }
                                    // else {
                                    //     $(".back_art_category_list_table").hide();
                                    //     $(".notfound_search").show();
                                    // }
                                }else if (value.data.res == 'no') {
                                    $.alert({
                                        title: '标签只能添加10个!',
                                        content: '先删除一些标签吧'
                                    });
                                }else {
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
