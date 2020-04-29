$(function () {
    var home = {
        article_sum: 0,
        view_sum: 0,
        user_sum: 0,
        comment_sum: 0,
        publish_sum: 0,
        draft_sum: 0,
        category_sum: 0,
        tag_sum: 0,
        messages: [],
        articles: []
    };
    var homeVue = new Vue(
        {
            el: ".container",
            data: home,
            mounted: function () {
                this.get();
            },
            methods: {
                get: function () {
                    var url = getPath() + "/admin/home";
                    axios.get(url).then(function (value) {
                            homeVue.article_sum=value.data.article_sum;
                            homeVue.view_sum=value.data.view_sum;
                            homeVue.user_sum=value.data.user_sum;
                            homeVue.comment_sum=value.data.comment_sum;
                            homeVue.publish_sum=value.data.publish_sum;
                            homeVue.draft_sum=value.data.draft_sum;
                            homeVue.category_sum=value.data.category_sum;
                            homeVue.tag_sum=value.data.tag_sum;
                            homeVue.messages=value.data.messages;
                            homeVue.articles=value.data.articles;
                    });
                }
            }
        }
    );
});