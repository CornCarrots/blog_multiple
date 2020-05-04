$(function () {
    var bean = {
        uri:"/foreRegister",
        user:{id:0,name:'',nickName:'',password:'',salt:'',sex:0,img:'',registerDate:null,loginDate:null,
            mobile:'',email:'',mid:0,score:0,status:0}
    };
    var registerVue = new Vue(
        {
            el:".main",
            data:bean,
            mounted:function(){
              Vue.nextTick(function () {
                  $("#registerForm").validationEngine(
                      {
                          promptPosition:'centerRight',
                          showArrow:true
                      }
                  );
                  valControls( $("#registerForm")) ;
              });
            },
            methods:{
                register:function () {
                    if(controlId.length > 0) {
                        alertinfo(0) ;
                        return false ;
                    }
                    if(!$("#registerForm").validationEngine("validate")) return false;  //验证没有使用ajax验证的控件(不是ajax验证的字段是可以正常验证的, 不通过就返回)
                    var url = getPath()+this.uri;
                    console.log(registerVue.user)
                    axios.post(url,registerVue.user).then(function (value) {
                        if (value.code == '500418'){
                            location.href=getPath()+"/registerSuccess"+"?timeStamp="+new Date().getTime();
                        } else {
                            $.alert("抱歉!" + value.msg);
                        }
                    });
                    return false;
                },
                reset:function () {
                    registerVue.user = {id:0,name:'',nickName:'',password:'',salt:'',sex:0,img:'',registerDate:null,loginDate:null,
                        mobile:'',email:'',mid:0,score:0,status:0}
                },
                getUrl:function (key) {
                    return getPath()+key;
                }
            }
        }
    );

});