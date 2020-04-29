$(
    function () {
        var bean = {
            uri: "/foreMessage",
            message: {
                id: 0,
                uid: 0,
                createDate: null,
                text: '',
                reply: null,
                replyDate: null,
                status: 1,
                type: 'type_complaint'
            }
        };
        var messageVue = new Vue(
            {
                el: ".main",
                data: bean,
                methods: {
                    sendButton: function () {
                        if(messageVue.message.text.length==0)
                        {
                            $.alert({
                                title: '内容不能为空!',
                                content: '请检查您的问卷并尝试重新提交'
                            });
                            return;
                        }
                        var url = getPath() + messageVue.uri+"?timeStamp="+new Date().getTime();
                        axios.post(url, messageVue.message).then(function (value) {
                            if (value.data == 'ok')
                            {
                                $.alert({
                                    title: '我们已收到您的问卷!',
                                    content: '请留意您的邮箱，等待我们的工作人员答复'
                                });
                            }
                            else {
                                $.alert({
                                    title: '抱歉，出错了!',
                                    content: '请检查您的问卷并尝试重新提交'
                                });
                            }

                        });
                    }
                }
            }
        );
    }
);