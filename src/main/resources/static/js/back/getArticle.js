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
            uri: "/admin/articles",
            article: {id: 0, title: '', summary: '', secret:'',text:'',cid: 0, status: 0,  createDate: null,updateDate:null,type:'TYPE_PUBLISH',view:0,like:0,start:0,comment:0},
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
                        var aid = getUrlParms("aid");
                        var url = getPath()+articles.uri+"/"+aid;
                        axios.get(url).then(
                            function (value) {
                                console.log(value.data)
                                articles.categories = value.data.categories;
                                articles.tags = value.data.tags;
                                articles.article = value.data.article;
                                Vue.nextTick(function () {
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
                                    $(value.data.tag).each(function (i,data) {
                                        var id = data.id;
                                        $("input[id="+id+"]").prop("checked",true);
                                    });
                                    editor = KindEditor.create("#editor_id", options);
                                    editor.html(articleVue.article.text);
                                    $("#addForm").validationEngine({promptPosition: 'centerRight', showArrow: true});
                                });
                            }
                        );

                    },
                    cancel_:function(){
                        location.reload();
                    },
                    editArticle: function () {
                        if (!$("#addForm").validationEngine("validate")) return false;
                        articleVue.article.text = editor.html();
                        if (articleVue.article.text.length == 0) {
                            $.alert("文章内容不可为空!");
                            return;
                        }
                        var arr = new Array();
                        $("input[name='tagCheckBox']").each(function (i,data) {
                            var check = $(data).prop("checked");
                            if(check)
                                arr.push($(data).val());
                        });
                        var url = getPath()+articles.uri+"/"+getUrlParms("aid");
                        axios.put(url, {article:articleVue.article,tags:arr}).then(
                            function (value) {
                                if(value.data == 'yes' )
                                {
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
