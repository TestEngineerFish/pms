package com.einyun.app.base.event;

import android.view.View;

/**
 * @ProjectName: android-framework
 * @Package: com.einyun.app.base
 * @ClassName: ItemClickListener
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/10/16 15:10
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/10/16 15:10
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public  interface ItemClickListener<M> {
    void onItemClicked(View veiw, M data);
}