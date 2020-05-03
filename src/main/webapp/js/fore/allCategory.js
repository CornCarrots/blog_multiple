$(
    function () {
        var operations = {
            uri:"/user/categories",
            pages: {},
            categories: [{name:'',parent:{name:''}}],
            category: {id: 0, name: '', pid: 0, icon: '', status: 0,uid:0},
            key:'',
            all:[],
            image: null
        };
        var operationVue = new Vue(
            {
                el:".main",
                data:operations,
                mounted:function () {
                    this.list(0);
                },
                methods: {
                    list: function (start) {
                        var url = getPath() + this.uri + "?start=" + start;
                        axios.get(url).then(
                            function (value) {
                                if (value.code != '0') {
                                    location.href = getPath() + "/error";
                                }
                                if (value.data.page.content.length > 0) {
                                    operationVue.pages = value.data.page;
                                    operationVue.categories = value.data.page.content;
                                    $(".historyTable").show();
                                    $(".notfound_list").hide();
                                    $(".pageDiv").show();

                                }
                                else {
                                    $(".historyTable").hide();
                                    $(".notfound_list").show();
                                    $(".pageDiv").hide();
                                }
                                operationVue.all = value.data.all;
                                Vue.nextTick(
                                    function () {
                                        var html = '<option value=\"0\" selected>无</option>';
                                        $(operationVue.all).each(
                                            function (i, data) {
                                                html+='<option value=\"'+data.id+'\">'+data.name+'</option>';
                                            }
                                        );
                                        $("#addSelect").html(html);
                                        $("#addSelect").selectpicker('refresh');
                                        $("#addSelect").selectpicker('render');
                                        $("#addForm").validationEngine({promptPosition:'centerRight', showArrow:true});
                                        $("#updateForm").validationEngine({promptPosition:'centerRight', showArrow:true});
                                    }
                                );
                            }
                        )
                    },
                    jump: function (page) {
                        jump(page, operationVue);
                    },
                    jumpByNumber: function (start) {
                        jumpByNumber(start, operationVue);
                    },
                    getImage: function (id) {
                        if (id == null || id == 0)
                            return;
                        var url = getPath() + "/image/category/" + id + ".jpg";
                        return url;
                    },
                    setFile: function (e) {
                        this.image = e.target.files[0];
                    },
                    addCategoryButton: function () {
                        $("#addCategoryModel").modal("show");
                    },
                    deleteAllButton: function () {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除分类',
                            theme: 'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    $("input[name='categoryCheckbox']:checked").each(
                                        function () {
                                            var input = $(this);
                                            var url = getPath() + operationVue.uri + "/" + input.val();
                                            axios.delete(url).then(function (value) {
                                                if (0 != value.data.length) {
                                                    $.alert('系统异常，请重试!');
                                                }
                                                else {
                                                    $.alert('成功删除!');
                                                    input.prop("checked", false);
                                                    operationVue.list(0);
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
                    search: function () {
                        if (!checkEmpty(this.key, '关键词')) {
                            return;
                        }
                        if (this.key.length >= 10) {
                            alert("关键词长度不能大于十，请重新搜索")
                            return;
                        }
                        var key = this.key;
                        var url = getPath() + operationVue.uri + "/search/?key=" + key;
                        axios.post(url).then(
                            function (value) {
                                if (value.code != '0') {
                                    location.href = getPath() + "/error";
                                }
                                $(".pageDiv").hide();
                                if (value.data.length > 0) {
                                    operationVue.categories = value.data;
                                    $(".back_art_category_list_table").show();
                                    $(".notfound_search").hide();
                                }
                                else {
                                    $(".back_art_category_list_table").hide();
                                    $(".notfound_search").show();
                                }
                            }
                        );
                    },
                    cancel_: function () {
                        operationVue.category = {id: 0, name: '', pid: 0, icon: '', status: 0};
                    },
                    addCategory: function () {
                        if (!$("#addForm").validationEngine("validate")) return false;
                        if (this.category.pid == 0){
                            $.alert("所属分类不可为空!");
                            return;
                        }
                        if (this.image == null){
                            $.alert("分类图片不可为空!");
                            return;
                        }
                        var uri = getPath() + this.uri;
                        var formData = new FormData();
                        formData.append("image", this.image);
                        formData.append("name", this.category.name);
                        formData.append("pid", this.category.pid);
                        formData.append("icon", this.category.icon);
                        formData.append("status", this.category.status);
                        axios.post(uri, formData).then(
                            function (value) {
                                if (value.code == "500803"){
                                    $.alert(
                                        {
                                            title: '恭喜你!',
                                            content: '添加分类成功',
                                            theme: 'modern',
                                            icon: 'fa fa-smile-o',
                                            buttons: {
                                                ok: {
                                                    action: function () {
                                                        operationVue.image = null;
                                                        operationVue.category = {id: 0, name: '', pid: 0, icon: '', status: 0};
                                                        $("#addCategoryModel").modal("hide");
                                                        location.reload();
                                                    }
                                                }
                                            }
                                        }
                                    );
                                } else if (value.code == "500802"){
                                    $.alert({
                                        title: '抱歉!' + value.msg,
                                        content: '先删除一些分类吧'
                                    });
                                }else {
                                    $.alert({
                                        title: '抱歉!' + value.msg,
                                        content: '再试试吧'
                                    });
                                }
                            }
                        );
                    },
                    deleteCategory: function (id) {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除分类',
                            theme: 'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    var url = getPath() + operationVue.uri + "/" + id;
                                    axios.delete(url).then(function (value) {
                                        if (value.code == '500806') {
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
                    editCategory: function (id) {
                        var url = getPath() + this.uri + "/" + id;
                        axios.get(url).then(function (value) {
                            if (value.code != '0') {
                                location.href = getPath() + "/error";
                            }
                            operationVue.category = value.data;
                            Vue.nextTick(
                                function () {
                                    var html = '<option value=\"0\">无</option>';
                                    $(operationVue.all).each(
                                        function (i, data) {
                                            if (data.id != operationVue.category.id) {
                                                if (data.id == operationVue.category.pid)
                                                    html += '<option value=\"' + data.id + '\" selected>' + data.name + '</option>';
                                                else
                                                    html += '<option value=\"' + data.id + '\">' + data.name + '</option>';
                                            }

                                        });
                                    $("#editSelect").html(html);
                                    $("#editSelect").selectpicker('refresh');
                                    $("#editSelect").selectpicker('render');
                                }
                            );
                            $("#editCategoryModel").modal("show");

                        });
                    },
                    updateCategory: function (id) {
                        if (!$("#updateForm").validationEngine("validate")) return false;
                        if (this.category.pid == 0){
                            $.alert("所属分类不可为空!");
                            return;
                        }
                        var url = getPath() + this.uri + "/" + id;
                        var formData = new FormData();
                        formData.append("image", this.image);
                        formData.append("name", this.category.name);
                        formData.append("pid", this.category.pid);
                        formData.append("icon", this.category.icon);
                        formData.append("status", this.category.status);
                        formData.append("uid", this.category.uid);
                        axios.put(url, formData).then(function (value) {
                            if (value.code == '500808'){
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '修改分类成功',
                                        theme: 'modern',
                                        icon: 'fa fa-smile-o',
                                        buttons: {
                                            ok: {
                                                action: function () {
                                                    operationVue.image = null;
                                                    operationVue.category = {id: 0, name: '', pid: 0, icon: '', status: 0,uid:0};
                                                    $("#editCategoryModel").modal("hide");
                                                    location.reload();

                                                }
                                            }
                                        }
                                    }
                                );
                            } else {
                                $.alert("抱歉!" + value.msg);
                            }
                        });
                    }
                }
            }
        );
    }
);