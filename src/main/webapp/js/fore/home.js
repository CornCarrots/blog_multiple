
$(function () {
    function listener() {
        $(window).load(function () {
            $('.post-module').hover(function () {
                $(this).find('.description').stop().animate({
                    height: 'toggle',
                    opacity: 'toggle'
                }, 300);
            });
        });
    }
    var bean = {
        uri:"/foreHome",
        articles:[{user:{nickName:''},category:{id:'',name:''}}],
        pages:[]
        // carousels:[]
    };
    var homeVue = new Vue(
        {
            el:".main",
            data:bean,
            computed:{
              formatTime:function (date) {

              }
            },
            mounted:function () {
                this.list(0);
            },
            methods:{
                list: function (start) {
                    var url = getPath() + this.uri+"?start="+start+"&timeStamp="+new Date().getTime();
                    axios.get(url).then(
                        function (value) {
                            if (value.code != '0') {
                                location.href = getPath() + "/error";
                            }
                            homeVue.pages = value.data.pages;
                            homeVue.articles = value.data.pages.content;
                            // homeVue.carousels = value.data.carousels;
                            Vue.nextTick(function () {
                                listener();
                            });
                        }
                    )
                },
                jump: function (page) {
                    jump(page, homeVue);
                },
                jumpByNumber: function (start) {
                    jumpByNumber(start, homeVue);
                },
                getCarousel:function (id) {
                    var url = getPath()+"/image/carousel/"+id+".jpg";
                    return url;
                },
                comment:function (id) {
                    var param = window.btoa("aid="+id);
                    var url = getPath()+"/article?"+param+"#comment";
                    return url;
                },
                getImage: function (type,id) {
                    if(id==null||id==0)
                        return;
                    var url = getPath() + "/image/"+type+"/" + id + ".jpg";
                    return url;
                }
            }
        }
    );

});

