
$(function () {
    var count = true;
    var bean = {
        uri:"/foreLikes",
        pages: [],
        articles: [{user:{nickName:''}, category:{name:''}}],
        order:'title'
    };
    var homeVue = new Vue(
        {
            el:".main",
            data:bean,
            mounted:function () {
                this.list(0);
            },
            methods:{
                list: function (start) {
                    var url = getPath() + this.uri + "?start=" + start+"&order="+this.order+"&sort="+count;
                    axios.get(url).then(
                        function (value) {

                            if(value.data.pages.content.length>0)
                            {
                                homeVue.pages = value.data.pages;
                                homeVue.articles = value.data.pages.content;
                                $(".myarticle").show();
                                $(".notfound_list").hide();
                            }
                            else
                            {
                                $(".myarticle").hide();
                                $(".notfound_list").show();
                            }
                        }
                    )
                },
                jump: function (page) {
                    jump(page, homeVue);
                },
                jumpByNumber: function (start) {
                    jumpByNumber(start, homeVue);
                },
                get:function (order) {
                    this.order = order;
                    this.list(0);
                    count = !count;
                },
                comment:function (id) {
                    var param = window.btoa("aid="+id+"#comment");
                    var url = getPath()+"/article?"+param;
                    return url;
                },
                getImage: function (type,id) {
                    if(id==null||id==0)
                        return;
                    var url = getPath() + "/image/"+type+"/" + id + ".jpg";
                    return url;
                },
                sort: function (order, e) {
                    $(".category_sort").removeClass("active");
                    $(e.target).parent().addClass("active");
                    homeVue.get(order);
                }
            }
        }
    );

});

