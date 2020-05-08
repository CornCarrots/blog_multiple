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
            pasteType : 1,
            resizeType: 0,
            filterMode: true,
            allowImageUpload: true,//允许上传图片
            formatUploadUrl:false,
            // allowFileManager : true,//是否允许浏览服务器上传文件
            items: [
                'source', 'fullscreen', '|', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'emoticons', 'image', 'link'
            ],
            filePostName: "image",
            uploadJson: getPath() + "/user/writing/image",
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
            uri: "/admin/writing",
            article: {id: 0, title: '', summary: '', text:'',secret:'',cid: 0, status: 0,  createDate: null,updateDate:null,type:'TYPE_PUBLISH',view:0,like:0,start:0,comment:0,uid:0},
            categories: [],
            tags:[],
            cid: 0
        };
        var articleVue = new Vue(
            {
                el: ".container",
                data: articles,
                mounted: function () {
                    this.get();
                },
                methods: {
                    get: function () {
                        axios.get(getPath()+articles.uri).then(
                            function (value) {
                                articles.tags = value.data.tags;
                                var html = '<option value=\"0\" selected>无</option>';
                                if (value.data.categories.length > 0){
                                    articles.categories = value.data.categories;
                                    $(articleVue.categories).each(
                                        function (i, data) {
                                            if (i == 0) {
                                                articleVue.cid = data.id;
                                            }
                                            html += '<option value=\"' + data.id + '\">' + data.name + '</option>';
                                        }
                                    );
                                }
                                Vue.nextTick(function () {
                                    $("#addSelect").html(html);
                                    $("#addSelect").selectpicker('val', articleVue.cid);
                                    $("#addSelect").selectpicker('refresh');
                                    $("#addSelect").selectpicker('render');
                                    editor = KindEditor.create("#editor_id", options);
                                    $("#addForm").validationEngine({promptPosition: 'centerRight', showArrow: true});
                                });
                            }
                        );

                    },
                    cancel_:function(){
                        location.reload();
                    },
                    addArticle: function () {
                        if (!$("#addForm").validationEngine("validate")) return false;
                        if (articleVue.cid == 0) {
                            $.alert("分类不可为空!");
                            return;
                        }
                        articleVue.article.text = editor.html();
                        if (articleVue.article.text.length == 0) {
                            $.alert("文章内容不可为空!");
                            return;
                        }
                        var arr = new Array();
                        $("input[name='tagCheckBox']").each(function (i,data) {
                            var check = $(data).prop("checked");
                            if(check)
                                arr.push($(data).val())
                        });
                        var url = getPath() + articleVue.uri;
                        axios.post(url, {article:articleVue.article,tags:arr}).then(
                            function (value) {
                                if(value.data == 'yes' )
                                {
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
                                                        location.href = getPath()+'/admin/article';
                                                    }
                                                }
                                            }
                                        }
                                    );
                                }

                            }
                        );
                    },
                }
            }
            )
        ;

    }
)
;
