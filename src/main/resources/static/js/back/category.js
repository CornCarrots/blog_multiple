$(
    function () {
        var categories = {
            uri: '/admin/categories',
            pages: {},
            categories: [{name:'',parent:{name:''}}],
            category: {id: 0, name: '', pid: 0, icon: '', status: 0,uid:0},
            key:'',
            // all:[],
            image: null
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
                                console.log(value.data)
                                if(value.data.page.content.length>0)
                                {
                                    vue.pages = value.data.page;
                                    // vue.all = value.data.all;
                                    vue.categories = value.data.page.content;
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
                                        // var html = '<option value=\"0\" selected>无</option>';
                                        // $(vue.all).each(
                                        //     function (i, data) {
                                        //         html+='<option value=\"'+data.id+'\">'+data.name+'</option>';
                                        //     }
                                        // );
                                        // $("#addSelect").html(html);
                                        // $("#addSelect").selectpicker('refresh');
                                        // $("#addSelect").selectpicker('render');
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
                    getImage: function (id, uid) {
                        if(id==null||id==0 || uid ==null)
                            return;
                        return getPath() + "/image/category/"+ uid +"/" +  id + ".jpg";
                        return url;
                    },
                    setFile: function (e) {
                        this.image = e.target.files[0];
                    },
                    addCategoryButton:function()
                    {
                        $("#addCategoryModel").modal("show");
                    },
                    deleteAllButton:function(){
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除分类',
                            theme:'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    $("input[name='categoryCheckbox']:checked").each(
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
                                    vue.categories=value.data;
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
                    cancel_:function(){
                        vue.category={id:0,name:'',pid:0,icon:'',status:0};
                    },
                    addCategory:function () {
                        if(!$("#addForm").validationEngine("validate")) return false;
                        var uri = getPath() + this.uri;
                        var formData = new FormData();
                        formData.append("image", this.image);
                        formData.append("name", this.category.name);
                        formData.append("pid", this.category.pid);
                        formData.append("icon", this.category.icon);
                        formData.append("status", this.category.status);
                        axios.post(uri,formData).then(
                            function (value) {
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '添加分类成功',
                                        theme:'modern',
                                        icon: 'fa fa-smile-o',
                                        buttons: {
                                            ok: {
                                                action: function () {
                                                    vue.image = null;
                                                    vue.category={id:0,name:'',pid:0,icon:'',status:0,uid:0};
                                                    $("#addCategoryModel").modal("hide");
                                                    location.reload();
                                                }
                                            }
                                        }
                                    }
                                );

                            }
                        );
                    },
                    deleteCategory:function (id,pid) {
                        if (pid != 0){
                            $.alert("分类不可修改!");
                            return;
                        }
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除分类',
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
                    editCategory:function (id,pid) {
                        if (pid != 0){
                            $.alert("分类不可修改!");
                            return;
                        }
                        var url = getPath()+this.uri+"/"+id;
                        axios.get(url).then(function(value){
                            vue.category=value.data;
                            Vue.nextTick(
                                function () {
                                    // var html = '<option value=\"0\">无</option>';
                                    // $(vue.all).each(
                                    //     function (i, data) {
                                    //         if(data.id!=vue.category.id)
                                    //         {
                                    //             if(data.id==vue.category.pid)
                                    //                 html+='<option value=\"'+data.id+'\" selected>'+data.name+'</option>';
                                    //             else
                                    //                 html+='<option value=\"'+data.id+'\">'+data.name+'</option>';
                                    //         }
                                    //
                                    //     });
                                    // $("#editSelect").html(html);
                                    // $("#editSelect").selectpicker('refresh');
                                    // $("#editSelect").selectpicker('render');
                                    checkListener();
                                }
                            );
                            $("#editCategoryModel").modal("show");

                        });
                    },
                    updateCategory:function (id) {
                        if(!$("#updateForm").validationEngine("validate")) return false;

                        var url = getPath()+this.uri+"/"+id;
                        var formData = new FormData();
                        formData.append("image", this.image);
                        formData.append("name", this.category.name);
                        formData.append("pid", this.category.pid);
                        formData.append("icon", this.category.icon);
                        formData.append("status", this.category.status);
                        formData.append("uid", this.category.uid);
                        axios.put(url,formData).then(function(value){
                            $.alert(
                                {
                                    title: '恭喜你!',
                                    content: '修改分类成功',
                                    theme:'modern',
                                    icon: 'fa fa-smile-o',
                                    buttons: {
                                        ok: {
                                            action: function () {
                                                vue.image = null;
                                                vue.category={id:0,name:'',pid:0,icon:'',status:0,uid:0};
                                                $("#editCategoryModel").modal("hide");
                                                location.reload();

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