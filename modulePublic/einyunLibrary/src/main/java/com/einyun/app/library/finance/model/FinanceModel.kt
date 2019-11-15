package com.einyun.app.library.finance.model

/**
 *
 * @ProjectName:    android-framework
 * @Package:        com.einyun.app.library.finance.model
 * @ClassName:      FinanceModel
 * @Description:     java类作用描述
 * @Author:         chumingjun
 * @CreateDate:     2019/11/5 0005 上午 10:26
 * @UpdateUser:     更新者
 * @UpdateDate:     2019/11/5 0005 上午 10:26
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */
class FinanceModel {
     val typeName//分摊类型名称
            : String? = null
     val budgetAmount//预算金额
            = 0.0
     val factAmount//实际金额
            = 0.0
     val amountRatio//完成率/超标率
            = 0.0
}