
$(function () {
    var count = true;
    var bean = {
        uri:"/foreSearch",
        pages: [],
        articles: [{user:{nickName:''}, category:{name:''}}],
        order:'title',
        key:''
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
                    var key = getUrlParms("key");
                    var cid = getUrlParms("cid");
                    var url = getPath() + this.uri + "?key="+key+ "&cid="+ cid + "&start=" + start+"&order="+this.order+"&sort="+count;
                    axios.get(url).then(
                        function (value) {
                            if (value.code != '0') {
                                location.href = getPath() + "/error";
                            }
                            homeVue.pages = value.data.pages;
                            homeVue.articles = value.data.pages.content;
                            homeVue.key = value.data.key;
                            if(value.data.pages.content.length>0)
                            {
                                $(".notfound_list").hide();
                                $(".myarticle").show();
                            }
                            else {
                                $(".notfound_list").show();
                                $(".myarticle").hide();
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
                getImage: function (id, uid) {
                    if(id==null||id==0 || uid == null)
                        return;
                    return getPath() + "/image/category/"+ uid +"/" +  id + ".jpg";
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

