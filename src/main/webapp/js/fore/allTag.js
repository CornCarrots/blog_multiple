$(
    function () {
        var operations = {
            uri:"/user/tags",
            pages: {},
            tags: []
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
                                console.log(value)
                                if (value.data.content.length > 0) {
                                    operationVue.pages = value.data;
                                    operationVue.tags = value.data.content;
                                    $(".historyTable").show();
                                    $(".notfound_list").hide();
                                    $(".pageDiv").show();
                                }
                                else {
                                    $(".historyTable").hide();
                                    $(".notfound_list").show();
                                    $(".pageDiv").hide();
                                }
                            }
                        )
                    },
                    jump: function (page) {
                        jump(page, operationVue);
                    },
                    jumpByNumber: function (start) {
                        jumpByNumber(start, operationVue);
                    },
                    deleteCategory: function (id) {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除标签',
                            theme: 'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    var url = getPath() + operationVue.uri + "/" + id;
                                    axios.delete(url).then(function (value) {
                                        if (value.code == '500707') {
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

                    }
                }
            }
        );
    }
);