package com.einyun.app.library.resource.workorder.model

enum class ComplainNodeIdState  constructor(value: String) {
    NewComplain("NewComplain"), Confirm("Confirm"), Response("Response"),
    Handle("Handle"), ReturnVisit("ReturnVisit");
    //必须增加一个构造函数,变量,得到该变量的值
    /**
     * @return 枚举变量实际返回值
     */
    var state = "added"

    init {
        state = value
    }

}
