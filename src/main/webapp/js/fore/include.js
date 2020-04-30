$(
    function () {


        $(".categoryButton").click(
            function () {
                var cid = $(this).attr("cid");
                var param = window.btoa("cid=" + cid );
                var url = getPath() + "/category?" + param;
                location.href = url;
                return false;
            }
        );

        noticeClick = function(e){
            var nid = $(e).attr("nid");

            var param = window.btoa("nid=" + nid );
            var url = getPath() + "/notice?" + param;
            location.href = url;
        };

        articleClick = function(e){
            var aid = $(e).attr("id");
            var param = window.btoa("aid=" + aid );
            var url = getPath() + "/article?" + param;
            location.href = url;
        };

         tagClick = function(e){
            var tid = $(e).attr("id");
            var param = window.btoa("tid=" + tid );
            var url = getPath() + "/tag?" + param;
            location.href = url;
        };

        userClick = function(e){
            var uid = $(e).attr("uid");
            var param = window.btoa("uid=" + uid );
            var url = getPath() + "/user?" + param;
            location.href = url;
        };

        commentClick = function(e){
            var aid = $(e).attr("aid");
            var param = window.btoa("aid=" + aid );
            // var url = getPath() + "/article?" + param+"#comment_"+cid;
            var url = getPath() + "/article?" + param;
            location.href = url;
        };

        $("#keyword").keypress(function (e) {
            if (e.which == 13) {
                search();
            }
        });

        $("#search").click(
            function () {
                search();
            }
        );

        $.goup({
            trigger: 100,
            bottomOffset: 10,
            locationOffset: 30,
            // title: 'Top',
            titleAsText: true
        });

        $("#categorySelect").selectpicker('refresh');
        $("#categorySelect").selectpicker('render');

        $(".closeButton").click(
            function(){
                $(this).hide();
                $(".top_adv").hide(1500);
            }
        );

        $(".indexNav_mycate").mouseenter(
            function(){
                $(".indexNav_mycate").removeClass("active");
                $(this).addClass("active");
            }
        );
        // $(".indexNav_mycate").click(
        //     function(){
        //         var href = $(this).attr("href");
        //         $(".indexNav_mycate").removeClass("active");
        //         $(this).addClass("active");
        //         return true;
        //     }
        // );
    }
);
//过滤器
Vue.filter("addressFilter",function (value) {
    if (!value)
        return '';
    var reg = new RegExp("-","g");//g,表示全部替换。
    return value.replace(reg,"")
})
Vue.filter("splitFilter",function (value) {
    if(!value)
        return '';
    var str = value.replace(/[\ |\~|\`|\!|\@|\#|\$|\%|\^|\&|\*|\(|\)|\-|\_|\+|\=|\||\\|\[|\]|\{|\}|\;|\:|\"|\'|\,|\<|\.|\>|\/|\?]/g," ");
    str.replace(/[：|— |——|?|【|】|“|”|！|，|。|？|、|~|@|#|￥|%|…|……|&|*|（|）]/g, " ");
    return str.split(" ")[0];
})
Vue.filter("subStringFilter", function(value, start, end){
    if (!value)
        return '';
    return value.substring(start,end);
});

Vue.filter("formatMoneyFilter", function(value){
    return formatMoney(value);
});

Vue.filter('formatDateFilter', function (value, formatString) {
    if(null==value)
        return "";
    formatString = formatString || 'YYYY-MM-DD HH:mm:ss';
    return moment(value).format(formatString);
});
axios.interceptors.response.use(
    function (response) {
        return response.data;
    },function (error) {
        return Promise.reject(error);;
    }
);
//搜索
function search() {
    var key =$("#keyword").val();
    key = encodeURI(key);
    var param = "key="+key+"&timeStamp="+new Date().getTime();
    var url = window.btoa(param);
    location.href = getPath()+"/search?"+url;
}
//格式化
function formatMoney(num){
    num = new String(num);
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        num = num.substring(0,num.length-(4*i+3))+','+
            num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num + '.' + cents);
}
//判断是否为空
function checkEmpty(value,text){

    if(null==value || value.length==0){
        alert(text+ "不能为空");
        return false;
    }
    return true;
}
function getPath() {
    var path = location.pathname;
    path = path.substring(0,path.substring(1).indexOf("/")+1);
    return path
}
function getArticle(id) {
    if (id == 0)
        return;
    var param = window.btoa("aid=" + id + "&timeStamp=" + new Date().getTime());
    var url = getPath() + "/article?" + param;
    return url;
}
function getCategory(id) {
    if (id == 0)
        return;
    var param = window.btoa("cid=" + id + "&timeStamp=" + new Date().getTime());
    var url = getPath() + "/category?" + param;
    return url;
}
function getUser(id) {
    if (id == 0)
        return;
    var param = window.btoa("uid=" + id + "&timeStamp=" + new Date().getTime());
    var url = getPath() + "/user?" + param;
    return url;
}
function getTag(id) {
    if (id == 0)
        return;
    var param = window.btoa("tid=" + id + "&timeStamp=" + new Date().getTime());
    var url = getPath() + "/tag?" + param;
    return url;
}
function checkLogin() {
    var url = getPath() + "/foreLoginCheck" + "?timeStamp=" + new Date().getTime();
    var res = false;
    $.ajax({
        type:"GET",
        url:url,
        async:false,
        success:function (value) {
            if (value.code == "500501") {
                $("#loginModel").modal("show");
                res = false;
            }else if (value.data == '500505') {
                res = true;
            }else {
                $.alert("抱歉!"+ value.msg);
            }
        }
    });
    return res;
    // axios.get(url).then(function (value) {
    //     console.log(value)
    //     if (value.data == "fail") {
    //         $("#loginModel").modal("show");
    //         return false;
    //     }else {
    //         return true;
    //     }
    //
    // });
}
function checkArticle(id) {
    var href = getArticle(id);
    var url = getPath() + "/foreArticleCheck" + "?aid="+ id + "&timeStamp=" + new Date().getTime();
    axios.get(url).then(
        function (value) {
            if (value.code == "500503"){
                location.href = href;
            } else if (value.code == "500501") {
                $.alert('这是权威认证文章，请您先登录再阅读');
            }else if (value.code == "500502") {
                $.alert('这是权威认证文章，您的会员等级不足无法阅读');
            }else {
                $.alert('抱歉!'+ value.msg);
            }
        }
    );
}
function loginUser(vue) {
    if (!$("#loginForm").validationEngine("validate"))
        return false;
    var url = getPath() + "/foreLoginUser" + "?timeStamp=" + new Date().getTime();
    axios.post(url, vue.user).then(function (value) {
        if (value.code == "500510") {
            location.reload();
        }
        else if (value.code == "500507") {
            $("#nameField").validationEngine("showPrompt", "没有此用户", "error");
        } else if (value.code == "500508") {
            $("#passField").validationEngine("showPrompt", "密码错误", "error");
        }else {
            $.alert("抱歉!" + value.msg);
        }
    })
}
function forgetPass() {
    $("#loginModel").modal("hide");
    $("#forgetModel").modal("show");
}
function cleanName(vue) {
    vue.user.name = '';
    $("#nameField").val('');
    $("#nameField").focus();
}
function togglePass(vue) {
    vue.flag = !vue.flag;
    if (vue.flag) {
        $("#passField").attr("type", "text");
        $("#seePass").toggleClass("fa-eye");
        $("#seePass").toggleClass("fa-eye-slash");
    }
    else {
        $("#passField").attr("type", "password");
        $("#seePass").toggleClass("fa-eye-slash");
        $("#seePass").toggleClass("fa-eye");
    }
}
function changePass(vue, random) {
    var key = this.$content.find('.key').val();
    var pass = this.$content.find('.pass').val();
    var res = true;
    if (!key || !pass) {
        $.alert('请输入一个有效的值');
        return false;
    }
    var bean = {random: random, key: key, email: vue.email, pass: pass};
    var url = getPath() + "/foreUserPass?timeStamp=" + new Date().getDate();
    axios.post(url, bean).then(
        function (value) {
            if (value.data == 'ok') {
                $.dialog({
                    title: '恭喜您,密码修改成功!',
                    content: '快去登录吧',
                    theme: 'modern',
                    icon: 'fa fa-smile-o'
                });
            }
            else {
                $.alert({
                    title: '抱歉!',
                    content: '验证码错误',
                    theme: 'modern',
                    icon: 'fa fa-close'
                });
                res = false;
            }
        }
    );
    return res;
}
function getRandom(e,vue) {
    var random = '';
    var self = e;
    var url = getPath() + "/forgetUser?email=" + vue.email + "&timeStamp=" + new Date().getDate();
    axios.get(url).then(
        function (value) {
            if (value.data.result == 'yes') {
                $("#forgetModel").modal("hide");
                random = value.data.random;
                self.setContent(
                    '<div class="form-group">' +
                    '<label>验证码已发送到您的邮箱，请查看</label>' +
                    '<input type="text" placeholder="请输入验证码" class="key form-control"/>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label>请输入您要修改的密码</label>' +
                    '<input type="text" placeholder="请输入密码" class="pass form-control"/>' +
                    '</div>'
                );
                self.setTitle("修改密码");
                self.buttons['formSubmit'].show();
                self.buttons['取消'].show();
            }
            else if (value.data.result == 'noUser') {
                self.setContent(
                    '<div>该邮箱没有绑定用户, 请重新输入' +
                    '</div>'
                );
                self.buttons['ok'].show();
                self.buttons['取消'].show();
            }else {
                self.setContent(
                    '<div>系统错误, 再试试吧' +
                    '</div>'
                );
                self.buttons['ok'].show();
                self.buttons['取消'].show();
            }
        });
    return random;
}
//获取地址栏参数的函数
function getUrlParms(para){
    var search=location.search; //页面URL的查询部分字符串
    var arrPara=new Array(); //参数数组。数组单项为包含参数名和参数值的字符串，如“para=value”
    var arrVal=new Array(); //参数值数组。用于存储查找到的参数值

    if(search!=""){
        var index=0;
        search=search.substr(1); //去除开头的“?”
        search = window.atob(search);
        arrPara=search.split("&");

        for(i in arrPara){
            var paraPre=para+"="; //参数前缀。即参数名+“=”，如“para=”
            if(arrPara[i].indexOf(paraPre)==0&& paraPre.length<arrPara[i].length){
                arrVal[index]=decodeURI(arrPara[i].substr(paraPre.length)); //顺带URI解码避免出现乱码
                index++;
            }
        }
    }

    if(arrVal.length==1){
        return arrVal[0];
    }else if(arrVal.length==0){
        return null;
    }else{
        return arrVal;
    }
}

//判断是否数字 (小数和整数)
function checkNumber(value, text){

    if(value.length==0){
        alert(text+ "不能为空");
        return false;
    }
    if(isNaN(value)){
        alert(text+ "必须是数字");
        return false;
    }
    return true;
}
//判断是否整数
function checkInt(value, text){

    if(value.length==0){
        alert(text+ "不能为空");
        return false;
    }
    if(parseInt(value)!=value){
        alert(text+ "必须是整数");
        return false;
    }
    return true;
}

//确实是否要删除
function checkDeleteLink(){
    var confirmDelete = confirm("确认要删除");
    if(confirmDelete)
        return true;
    return false;
}

//分页跳转函数，向前跳或者向后跳，或者跳转到第一页或者最后一页。
function jump(page,vue){
    if('first'== page && !vue.pages.first)
        vue.list(0);

    else if('pre'== page &&	vue.pages.hasPrevious )
        vue.list(vue.pages.number-1);

    else if('next'== page && vue.pages.hasNext)
        vue.list(vue.pages.number+1);

    else if('last'== page && !vue.pages.last)
        vue.list(vue.pages.totalPages-1);
}
//分页跳转函数，跳转到指定页
function jumpByNumber(start,vue){
    if(start!=vue.pages.number)
        vue.list(start);
}
function replace_em(str){
    str = str.replace(/\</g,'&lt;');
    str = str.replace(/\>/g,'&gt;');
    str = str.replace(/\n/g,'<br/>');
    str = str.replace(/\[qq_([0-9]*)\]/g,"<img src="+getPath()+"/image/emoji/qq/$1.gif>");
    str = str.replace(/\[emoji_([0-9]*)\]/g,"<img src="+getPath()+"/image/emoji/emoji/$1.png>");
    str = str.replace(/\[tieba_([0-9]*)\]/g,"<img src="+getPath()+"/image/emoji/tieba/$1.jpg>");
    return str;
}

var controlId = new Array();  //保存验证不通过的控件ID
var errors = new Array() ;  //保存验证不通过的提示信息
var successes = new Array() ;  //保存验证不通过的提示信息
//表单需要验证的控件
function valControls(ajaxForm2Controls) {
    //获取需要使用ajax验证的控件
    var controlsStr = ajaxForm2Controls.attr("control") ;
    //属性未定义时返回
    if(typeof(controlsStr) === "undefined" || controlsStr.length <= 0) return ;
    //分隔获取控件ID
    var controls = controlsStr.split(/,/g) ;
    for(var i in controls) {
        //添加焦点离开事件
        $("#" + controls[i]).blur(function() {
            if($(this).val().length <= 0) return false;
            //重新设置数组
            controlId.length = 0;
            errors.length = 0 ;
            successes.length = 0 ;
            //错误信息
            var error = $(this).attr("error") ;
            var success = $(this).attr("success") ;
            $.ajax({
                type: "GET",
                url: $(this).attr("url"),
                data: $(this).serialize(),
                dataType: "text",
                success: function(data){
                    if(data=="true") {
                        //验证不通过将错误信息放入数组中
                        controlId.push(controls[i]);
                        errors.push(error) ;
                        //提示信息
                        alertinfo(1) ;
                    }
                    if(data=="false"){
                        successes.push(success) ;
                        //提示信息
                        alertSuccess() ;
                    }
                }
            });
        }) ;
    }
}
function alertinfo(j) {
    if(controlId.length > 0) {
        for(var i in controlId) {
            //validationEngine方法,为指定ID弹出提示.
            //用法:<span style="">$("#id").validationEngine("showPrompt","提示内容","load");
            //<span style="">在该元素上创建一个提示，3 种状态："pass", "error", "load"</span></span>
            $("#" + controlId[i]).validationEngine("showPrompt", errors[i], "error");
            if(j==0)
                $("#" + controlId[i]).focus();
        }
    }
}
function alertSuccess() {
    if(controlId.length <= 0) {
        for(var i in controlId) {
            //validationEngine方法,为指定ID弹出提示.
            //用法:<span style="">$("#id").validationEngine("showPrompt","提示内容","load");
            //<span style="">在该元素上创建一个提示，3 种状态："pass", "error", "load"</span></span>
            $("#" + controlId[i]).validationEngine("showPrompt", successes[i], "pass");
        }
    }
}
var check1 = false;
var check2 = false;
var check4 = false;
//全选
function checkAll(){
    if(!check1)
    {
        $(".checkOne input").prop("checked",true);
        $(".checkAllTH_ input").prop("checked",true);
        check1 = true;
    }
    else
    {
        $(".checkOne input").prop("checked",false);
        $(".checkAllTH_ input").prop("checked",false);
        check1 = false;
    }
}
function checkAll_(j){
    if(!checkArray[j])
    {
        $("input[name=checkbox"+j+"]").prop("checked",true);
        checkArray[j] = true;
    }
    else
    {
        $("input[name=checkbox"+j+"]").prop("checked",false);
        checkArray[j] = false;
    }
}
//选择一个
function checkOne()
{
    $(".checkOne input").each(function (i,data) {
        check2 = $(data).prop("checked");
        // console.log(check2);
        if(check2==false)
            return false;
    });
    if(check2==true)
    {
        $("#checkAllTH input").prop("checked",true);
        check2 = false;
        check1 = true;
    }
    else
    {
        $("#checkAllTH input").prop("checked",false);
        check1 = false;
    }
}
//选择一个
function checkOne_(j)
{
    $("input[name=checkbox"+j+"]").each(function (i,data) {
        check4 = $(data).prop("checked");
        // console.log(check2);
        if(check4==false)
            return false;
    });
    if(check4==true)
    {
        $("#checkAllTH"+j+" input").prop("checked",true);
        check4 = false;
        check3 = true;
    }
    else
    {
        $("#checkAllTH"+j+" input").prop("checked",false);
        check3 = false;
    }
}