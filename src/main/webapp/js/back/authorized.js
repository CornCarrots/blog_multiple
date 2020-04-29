$(
    function () {
        var users = {
            uri:"/admin/authorizeds",
            authorizeds:[],
            pages:[],
            authorized:{}
        };
        var userVue = new Vue(
            {
                el:".container",
                data:users,
                mounted:function () {
                    this.list(0);
                },
                filters:{
                    statusFilter:function (value) {
                        if(value==0)
                            return '待审核';
                        if(value==1)
                            return '已审核';
                        if(value==2)
                            return '已屏蔽';
                    }
                },
                methods:{
                    list: function (start) {
                        var url = getPath() + this.uri + "?start=" + start;
                        axios.get(url).then(
                            function (value) {
                                if(value.data.content.length>0)
                                {
                                    userVue.pages = value.data;
                                    console.log(value.data)
                                    userVue.authorizeds = value.data.content;
                                    $(".back_user_list_table").show();
                                    $(".notfound_list").hide();
                                    $(".pageDiv").show();
                                    $(".notfound_search").hide();

                                }
                                else
                                {
                                    $(".back_user_list_table").hide();
                                    $(".notfound_list").show();
                                    $(".pageDiv").hide();
                                    $(".notfound_search").hide();

                                }

                            }
                        )
                    },
                    jump:function (page) {
                        jump(page,userVue);
                    },
                    jumpByNumber:function (start) {
                        jumpByNumber(start,userVue);
                    },
                    getImage: function (img) {
                        var url= getPath() + "/image/authorized/" + img + ".jpg";
                        return url;
                    },
                    editButton:function (id) {
                        var url = getPath() + this.uri + "/" + id;
                        axios.put(url).then(
                            function (value) {
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '标记成功',
                                        theme:'modern',
                                        icon: 'fa fa-smile-o',
                                        buttons: {
                                            ok: {
                                                action: function () {
                                                    userVue.list(0);
                                                }
                                            }
                                        }
                                    }
                                );
                            }
                        );
                    }
                }
            }
        )
    }
);