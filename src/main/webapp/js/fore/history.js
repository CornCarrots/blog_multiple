$(
    function () {
        var operations = {
            uri:"/foreHistories",
            pages:[],
            histories:[]
        };
        var operationVue = new Vue(
            {
                el:".main",
                data:operations,
                mounted:function () {
                    this.list(0);
                },
                methods:{
                    list: function (start) {
                        var url = getPath() + this.uri + "?start=" + start;
                        axios.get(url).then(
                            function (value) {
                                console.log(value)
                                if(value.data.content.length>0)
                                {
                                    operationVue.pages = value.data;
                                    operationVue.histories = value.data.content;
                                    $(".historyTable").show();
                                    $(".notfound_list").hide();
                                    $(".pageDiv").show();

                                }
                                else
                                {
                                    $(".historyTable").hide();
                                    $(".notfound_list").show();
                                    $(".pageDiv").hide();
                                }
                                Vue.nextTick(
                                    function () {
                                        checkListener();
                                    }
                                );
                            }
                        )
                    },
                    jump:function (page) {
                        jump(page,operationVue);
                    },
                    jumpByNumber:function (start) {
                        jumpByNumber(start,operationVue);
                    },
                    deleteAllButton:function(){
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除历史记录',
                            theme:'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    $("input[name='moduleCheckbox']:checked").each(
                                        function () {
                                            var input = $(this);
                                            var url = getPath()+operationVue.uri+"/"+input.val();
                                            axios.delete(url).then(function(value){
                                                if(0!=value.data.length){
                                                    $.alert('系统异常，请重试!');
                                                }
                                                else{
                                                    $.alert('成功删除!');
                                                    input.prop("checked",false);
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
                    deleteHistory:function (id) {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除历史记录',
                            theme:'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {

                                    var url = getPath()+operationVue.uri+"/"+id;
                                    axios.delete(url).then(function(value){
                                        if(0!=value.data.length){
                                            $.alert('系统异常，请重试!');
                                        }
                                        else{
                                            $.alert('成功删除!');
                                            operationVue.list(0);
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