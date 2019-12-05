package com.einyun.app.pms.patrol.repository;

import androidx.paging.DataSource;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.dao.PatrolDao;
import com.einyun.app.base.db.dao.PatrolInfoDao;
import com.einyun.app.base.db.entity.Patrol;
import com.einyun.app.base.db.entity.PatrolInfo;
import com.einyun.app.base.event.CallBack;
import com.einyun.app.common.application.CommonApplication;

import java.util.List;

public class PatrolListRepo {
    //巡查列表简介数据
    PatrolDao patrolDao;
    //巡查详情数据
    PatrolInfoDao infoDao;
    AppDatabase db;

    public PatrolListRepo(){
        db=AppDatabase.getInstance(CommonApplication.getInstance());
        patrolDao = db.patrolDao();
        infoDao=db.patrolInfoDao();
    }

    /**
     * 分页查询
     * @param current
     * @param pageSize
     * @return
     */
    public List<Patrol> queryPage(int current, int pageSize) {
        return patrolDao.pageDigest(current,pageSize);
    }

    /**
     * 获取数据源
     * @return
     */
    public DataSource.Factory<Integer,Patrol> queryAll(){
        return patrolDao.queryAll();
    }


    /**
     * 更新缓存状态
     */
    public void updateCachedStates(){
        db.runInTransaction(() -> patrolDao.updateCachedStates());
    }


    /**
     * 转化ids[]
     * @param list
     * @return
     */
    public String [] loadIds(List<Patrol> list){
        String []taskIds=null;
        if(list!=null&&list.size()>0){
            taskIds=new String[list.size()];
            for(int i=0;i<list.size();i++){
                taskIds[i]=list.get(i).getTaskId();
            }
        }
        return taskIds;
    }

    /**
     * 数据同步
     * @param patrols
     */
    public void sync(List<Patrol> patrols, CallBack<Boolean> callBack){
        db.runInTransaction(() -> {
            /**
             * 清空原数据，插入最新数据
             */
            initData(patrols);
            String taskIds[]=loadIds(patrols);
            if(taskIds!=null){
                //删除已关闭巡查信息数据
                infoDao.sync(taskIds);
                infoDao.syncLocal(taskIds);
            }
            //更新已缓存状态
            updateCachedStates();
            callBack.call(true);
        });
    }

    /**
     * 初始化数据
     * @param patrols
     */
    public void initData(List<Patrol> patrols){
        db.runInTransaction(() -> {
            patrolDao.deleteAll();
            patrolDao.insertDigest(patrols);
        });

    }
}
