package com.einyun.app.library.resource.workorder.model

enum class PatrolWorkSate constructor(value: Int){
    NEW(1), HANDING(2), CLOSED(3);
    //必须增加一个构造函数,变量,得到该变量的值
    /**
     * @return 枚举变量实际返回值
     */
    var state = 0

    init {
        state = value
    }
}
