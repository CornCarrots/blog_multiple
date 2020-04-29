$(
    function () {
        var carousels = {
            uri: "/admin/carousels",
            addCarousel: {id: 0, status: 0},
            editCarousel: {id: 0, status: 0},
            carousels: [],
            image: null
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
                                carouselVue.carousels = value.data;
                                Vue.nextTick(
                                    function () {
                                        checkListener();
                                    }
                                );
                            }
                        );
                    },
                    getImage: function (id) {
                        if (id == 0)
                            return;
                        var url = getPath() + "/image/carousel/" + id + ".jpg";
                        return url;
                    },
                    deleteAllButton: function () {
                        $.confirm({
                            title: '确定吗？',
                            content: '您正在删除轮播图',
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
                        if (!checkEmpty(this.image, '产品图片')) {
                            return;
                        }
                        var url = getPath() + this.uri;
                        var formData = new FormData();
                        formData.append("image", this.image);
                        formData.append("status", carouselVue.addCarousel.status);
                        axios.post(url, formData).then(
                            function (value) {
                                $.alert(
                                    {
                                        title: '恭喜你!',
                                        content: '添加轮播图成功',
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
                        var url = getPath() + this.uri + "/" + id;
                        axios.get(url).then(function (value) {
                            carouselVue.editCarousel = value.data
                            $("#editCarouselModel").modal("show");
                        });
                    },
                    updateCarousel: function (id) {
                        var url = getPath() + this.uri + "/" + id;
                        var formData = new FormData();
                        formData.append("image", this.image);
                        formData.append("status", carouselVue.editCarousel.status);
                        $("#editCarouselModel").modal("hide");
                        axios.put(url, formData).then(function (value) {
                            $.alert(
                                {
                                    title: '恭喜你!',
                                    content: '修改轮播图成功',
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
                            content: '您正在删除轮播图',
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