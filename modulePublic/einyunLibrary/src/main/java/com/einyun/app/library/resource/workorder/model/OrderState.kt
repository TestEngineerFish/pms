package com.einyun.app.library.resource.workorder.model

enum class OrderState private constructor(value: Int) {
    NEW(1), HANDING(2), APPLY(3), CLOSED(4), PENDING(5), OVER_DUE(6);
    //必须增加一个构造函数,变量,得到该变量的值
    /**
     * @return 枚举变量实际返回值
     */
    var state = 0

    init {
        state = value
    }

}
