$(
    function () {

        var categories = {
            uri: '/admin/tags',
            pages: {},
            tags: [],
            tag:{id:0,name:'',status:0,count:0,uid:0},
            key:''
        };

        var vue = new Vue(
            {
                el: ".container",
                data: categories,
                mounted: function () {
                    this.list(0);
                },
                methods: {
                    list: function (start) {
                        var uri = getPath() + this.uri + "?start=" + start;
                        axios.get(uri).then(
                            function (value) {
                                if(value.data.content.length>0)
                                {
                                    vue.pages = value.data
                                    vue.tags = value.data.content;
                                    $(".back_art_category_list_table").show();
                                    $(".notfound_list").hide();
                                    $(".pageDiv").show();
                                    $(".notfound_search").hide();

                                }
                                else
                                {
                                    $(".back_art_category_list_table").hide();
                                    $(".notfound_list").show();
                                    $(".pageDiv").hide();
                                    $(".notfound_search").hide();
                                }
                                Vue.nextTick(
                                    function () {
                                        $("#addForm").validationEngine({promptPosition:'centerRight', showArrow:true});
                                        $("#updateForm").validationEngine({promptPosition:'centerRight', showArrow:true});

                                        checkListener();
                                    }
                                );
                            }
                        )
                    },
                    jump:function (page) {
                        jump(page,vue);
                    },
                    jumpByNumber:function (start) {
                        jumpByNumber(start,vue);
                    },
                    addTagButton:function()
                    {
                        $("#addTagModel").modal("show");
                    },
                    deleteAllButton:function(){
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除标签',
                            theme:'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    $("input[name='tagCheckbox']:checked").each(
                                        function () {
                                            var input = $(this);
                                            var url = getPath()+vue.uri+"/"+input.val();
                                            axios.delete(url).then(function(value){
                                                if(0!=value.data.length){
                                                    $.alert('系统异常，请重试!');                                                }
                                                else{
                                                      $.alert('成功删除!');
                                                    input.prop("checked",false);
                                                    vue.list(0);
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
                    search:function()
                    {
                        if(!checkEmpty(this.key,'关键词'))
                        {
                            return;
                        }
                        if(this.key.length>=10)
                        {
                            alert("关键词长度不能大于十，请重新搜索")
                            return;
                        }
                        var key = this.key;
                        var url = getPath()+vue.uri+"/search/?key="+key;
                        axios.post(url).then(
                            function (value) {
                                $(".pageDiv").hide();
                                if(value.data.length>0)
                                {
                                    vue.tags=value.data;
                                    $(".back_art_category_list_table").show();
                                    $(".notfound_search").hide();
                                }
                                else
                                {
                                    $(".back_art_category_list_table").hide();
                                    $(".notfound_search").show();
                                }
                            }
                        );
                    },
                    addTag:function () {
                        if(!$("#addForm").validationEngine("validate")) return false;

                        var uri = getPath() + this.uri;
                        var bean = this.tag;
                        axios.post(uri,bean).then(
                            function (value) {
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '添加标签成功',
                                        theme:'modern',
                                        icon: 'fa fa-smile-o',
                                        buttons: {
                                            ok: {
                                                action: function () {
                                                    vue.list(0);
                                                    vue.tag={id:0,name:'',status:0,count:0};
                                                    $("#addTagModel").modal("hide");
                                                }
                                            }
                                        }
                                    }
                                );

                            }
                        );
                    },
                    deleteTag:function (id) {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除标签',
                            theme:'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    var url = getPath()+vue.uri+"/"+id;
                                    axios.delete(url).then(function(value){
                                        if(0!=value.data.length){
                                            $.alert('系统异常，请重试!');
                                        }
                                        else{
                                            $.alert('成功删除!');
                                            vue.list(0);
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
                    editTag:function (id) {
                        var url = getPath()+this.uri+"/"+id;
                        axios.get(url).then(function(value){
                            vue.tag=value.data;
                        });
                        $("#editTagModel").modal("show");
                    },
                    cancel_:function(){
                        vue.tag={id:0,name:'',status:0};
                    },
                    updateTag:function (id) {
                        if(!$("#updateForm").validationEngine("validate")) return false;

                        var url = getPath()+this.uri+"/"+id;
                        axios.put(url,this.tag).then(function(value){
                            $.alert(
                                {
                                    title: '恭喜你!',
                                    content: '修改标签成功',
                                    theme:'modern',
                                    icon: 'fa fa-smile-o',
                                    buttons: {
                                        ok: {
                                            action: function () {
                                                vue.tag={id:0,name:'',status:0,count:0};
                                                vue.list(0);
                                                $("#editTagModel").modal("hide");
                                            }
                                        }
                                    }
                                }
                            );

                        });

                    }
                }
            }
        );
        function checkListener(){
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
);