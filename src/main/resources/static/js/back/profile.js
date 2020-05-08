$(
    function () {
        var carousels = {
            uri: "/admin/userProfiles",
            image: null,
            num:0,
            id:0
        };
        var carouselVue = new Vue(
            {
                el: ".container",
                data: carousels,
                mounted: function () {
                    this.list();
                },
                methods: {
                    list: function () {
                        var url = getPath() + this.uri;
                        axios.get(url).then(
                            function (value) {
                                carouselVue.num = value.data;
                                Vue.nextTick(
                                    function () {
                                        checkListener();
                                    }
                                );
                            }
                        );
                    },
                    getImage: function (id) {
                        console.log(id)
                        if (id == 0)
                            return;
                        var url = getPath() + "/image/profile_user/" + id + ".jpg";
                        return url;
                    },
                    deleteAllButton: function () {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除用户头像',
                            theme: 'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    $("input[name='carouselCheckbox']:checked").each(
                                        function () {
                                            var input = $(this);
                                            var url = getPath() + carouselVue.uri + "/" + input.val();
                                            axios.delete(url).then(function (value) {
                                                if (0 != value.data.length) {
                                                    $.alert('系统异常，请重试!');
                                                }
                                                else {
                                                    $.alert('成功删除!');
                                                    input.prop("checked", false);
                                                    carouselVue.list();
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
                    setFile: function (e) {
                        this.image = e.target.files[0];
                    },
                    getFile: function (e) {
                        this.image = e.target.files[0];
                        if (!checkEmpty(this.image, '头像图片')) {
                            return;
                        }
                        var url = getPath() + this.uri;
                        var formData = new FormData();
                        formData.append("image", this.image);
                        formData.append("num", this.num);
                        axios.post(url, formData).then(
                            function (value) {
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '添加头像成功',
                                        theme: 'modern',
                                        icon: 'fa fa-smile-o',
                                        buttons: {
                                            ok: {
                                                action: function () {
                                                    carouselVue.image = null;
                                                    location.reload();
                                                }
                                            }
                                        }
                                    }
                                );

                            }
                        );
                    },
                    editCarousel_: function (id) {
                            carouselVue.id = id
                            $("#editCarouselModel").modal("show");
                    },
                    updateCarousel: function (id) {
                        var url = getPath() + this.uri + "/" + id;
                        var formData = new FormData();
                        formData.append("image", this.image);
                        $("#editCarouselModel").modal("hide");
                        axios.put(url, formData).then(function (value) {
                            $.alert(
                                {
                                    title: '恭喜你!',
                                    content: '修改头像成功',
                                    theme:'modern',
                                    icon: 'fa fa-smile-o',
                                    buttons: {
                                        ok: {
                                            action: function () {
                                                location.reload();
                                                carouselVue.image = null;
                                            }
                                        }
                                    }
                                }
                            );

                        })
                    },
                    deleteCarousel: function (id) {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除头像',
                            theme:'modern',
                            icon: 'fa fa-question',
                            buttons: {
                                '确认': function () {
                                    var url = getPath() + carouselVue.uri + "/" + id;
                                    axios.delete(url).then(function (value) {
                                        if (0 != value.data.length)
                                            $.alert('系统异常，请重试!');
                                        else
                                        {
                                            $.alert('成功删除!');
                                            carouselVue.list();
                                        }
                                    })
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

        function checkListener() {
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