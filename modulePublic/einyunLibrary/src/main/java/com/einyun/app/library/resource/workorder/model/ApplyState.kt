package com.einyun.app.library.resource.workorder.model

enum class ApplyState constructor(value: Int){
    APPLYING(1), PASS(2), REJECT(3);
    //必须增加一个构造函数,变量,得到该变量的值
    /**
     * @return 枚举变量实际返回值
     */
    var state = 0

    init {
        state = value
    }
}
