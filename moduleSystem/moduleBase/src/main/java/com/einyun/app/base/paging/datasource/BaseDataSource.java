package com.einyun.app.base.paging.datasource;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;
import com.einyun.app.base.paging.bean.PageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: pms
 * @Package: com.einyun.app.base.paging.bean
 * @ClassName: BaseDataSource
 * @Description: java类作用描述
 * @Author: chumingjun
 * @CreateDate: 2019/11/20 0020 下午 14:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/11/20 0020 下午 14:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class BaseDataSource<M> extends PositionalDataSource<M> {
    List<M> cached=new ArrayList<>();
    int cacheTotal;

    protected void saveCached(List<M> list,int total){
        cached.clear();
        cached.addAll(list);
        this.cacheTotal=total;
    }

    protected List<M> loadCache(){
        return cached;
    }

    protected int getCacheTotal(){
        return cacheTotal;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<M> callback) {
        loadData(new PageBean(),callback);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<M> callback) {
        int page=params.startPosition/params.loadSize+1;
        if(params.startPosition<params.loadSize){
            page++;
        }
        loadData(new PageBean(page,params.loadSize),callback);
    }

    //根据页数获取数据
    public abstract <T> void loadData(PageBean pageBean,@NonNull T callback);

}
