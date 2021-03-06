package com.einyun.app.pms.patrol.repository;

import androidx.annotation.NonNull;
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
    public List<Patrol> queryPage(int current, int pageSize,String userId) {
        return patrolDao.pageDigest(current,pageSize,userId);
    }

    /**
     * 获取数据源
     * @return
     */
    public DataSource.Factory<Integer,Patrol> queryAll(@NonNull String userId, int listType){
        return patrolDao.queryAll(userId,listType);
    }


    /**
     * 更新缓存状态
     */
    public void updateCachedStates(String userId){
        db.runInTransaction(() -> patrolDao.updateCachedStates(userId));
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
                taskIds[i]=list.get(i).getID_();
            }
        }
        return taskIds;
    }

    /**
     * 数据同步
     * @param patrols
     */
    public void sync(List<Patrol> patrols,String userId,int listType, CallBack<Boolean> callBack){
        db.runInTransaction(() -> {
            /**
             * 清空原数据，插入最新数据
             */
            initData(patrols,userId,listType);
            String taskIds[]=loadIds(patrols);
            if(taskIds!=null){
                //删除已关闭巡查信息数据
                infoDao.sync(userId,taskIds);
                infoDao.syncLocal(userId,taskIds);
            }
            //更新已缓存状态
            updateCachedStates(userId);
            if(callBack!=null){
                callBack.call(true);
            }
        });
    }



    /**
     * 初始化数据
     * @param patrols
     */
    public void initData(List<Patrol> patrols,String userId,int listType){
        db.runInTransaction(() -> {
            patrolDao.deleteAll(userId,listType);
            patrolDao.insertDigest(patrols);
        });

    }

    /**
     * 搜索
     * @return
     */
    public DataSource.Factory<Integer,Patrol> search(@NonNull String userId, int listType,String search){
        return patrolDao.search(userId,listType,search);
    }

    /**
     * 清空数据
     * @param userId
     * @param listType
     */
    public void clearAll(String userId,int listType){
        db.runInTransaction(() -> {
            patrolDao.deleteAll(userId,listType);
        });
    }
}
