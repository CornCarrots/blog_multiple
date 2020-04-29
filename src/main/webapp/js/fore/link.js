$(function () {
    var bean = {
        uri:"/foreLink",
        link:{id:0,name:'',url:'',describe:'',email:'',createDate:null,status:1}
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
              });
            },
            methods:{
                register:function () {
                    if(!$("#registerForm").validationEngine("validate")) return false;  //验证没有使用ajax验证的控件(不是ajax验证的字段是可以正常验证的, 不通过就返回)
                    var url = getPath()+this.uri;
                    axios.post(url,registerVue.link).then(function (value) {
                            location.href=getPath()+"/applySuccess"+"?timeStamp="+new Date().getTime();
                    });
                    return false;
                },
                reset:function () {
                    registerVue.link= {id:0,name:'',url:'',desc:'',email:'',createDate:null,status:1};

                }
            }
        }
    );

});