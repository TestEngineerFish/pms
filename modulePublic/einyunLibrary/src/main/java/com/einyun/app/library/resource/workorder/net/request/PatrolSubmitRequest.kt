package com.einyun.app.library.resource.workorder.net.request

class PatrolSubmitRequest {
     companion object{
          const val ACTION_AGREE="agree"
     }

     var taskId: String?=null
     var zhipaiRemark: String?=null
     var actionName: String?=null
     var data: String?=null
     var id: String?=null
     var remark: String?=null
     constructor()
     constructor(taskId:String,zhipaiRemark:String,actionName:String,data:String,id:String){
          this.taskId=taskId
          this.zhipaiRemark=zhipaiRemark
          this.actionName=actionName
          this.data=data
          this.id=id
     }
     constructor(taskId:String,actionName:String,data:String,id:String){
          this.taskId=taskId
          this.actionName=actionName
          this.data=data
          this.id=id
     }
}
