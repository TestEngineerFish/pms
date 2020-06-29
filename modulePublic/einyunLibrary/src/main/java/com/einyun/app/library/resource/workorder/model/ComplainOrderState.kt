package com.einyun.app.library.resource.workorder.model

enum class ComplainOrderState  constructor(value: String) {
    ADD("added"), RESPONSE("for_response"), DEALING("dealing"),
    RETURN_VISIT("return_visit"), CLOSED("closed"),Confirm("for_confirm");
    //必须增加一个构造函数,变量,得到该变量的值
    /**
     * @return 枚举变量实际返回值
     */
    var state = "added"

    init {
        state = value
    }

}
