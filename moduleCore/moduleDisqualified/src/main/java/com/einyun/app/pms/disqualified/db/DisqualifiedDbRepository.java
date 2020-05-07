package com.einyun.app.pms.disqualified.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.einyun.app.base.db.AppDatabase;
import com.einyun.app.base.db.entity.CreateUnQualityRequest;
import com.einyun.app.base.db.entity.QualityRequest;
import com.einyun.app.common.application.CommonApplication;
import com.einyun.app.common.repository.CommonRepository;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据处理类，网络、数据库、本地数据等
 */
public class DisqualifiedDbRepository extends CommonRepository {
    AppDatabase db;
    Gson gson;

    public DisqualifiedDbRepository() {
        db = AppDatabase.getInstance(CommonApplication.getInstance());
        gson = new Gson();
    }

    private static final String FeedBackRequest = "1";
    private static final String VerificationRequest = "1";

    public LiveData<UnQualityFeedBackRequest> loadFeedBackRequest(String orderId) {
        MutableLiveData list = new MutableLiveData();
        Observable.just(1).subscribeOn(Schedulers.io()).doOnError(throwable -> {
        }).subscribe(integer -> {
            QualityRequest request = db.qualityRequestDao().queryRequest(orderId + FeedBackRequest);
            if(request == null){
                return;
            }
            UnQualityFeedBackRequest feedBackRequest = new UnQualityFeedBackRequest();
            feedBackRequest.getBizData().setReason(request.getReason());
            feedBackRequest.getBizData().setCorrective_action(request.getCorrective_action());
            feedBackRequest.getBizData().setFeedback_date(request.getFeedback_date());
            feedBackRequest.getBizData().setFeedback_enclosure(request.getFeedback_enclosure());
            feedBackRequest.getDoNextParamt().setTaskId(request.getTaskId());

            //缓存工单基本信息
            feedBackRequest.getBizData().setOrder_info_state(request.getOrder_info_state());
            feedBackRequest.getBizData().setOrder_info_code(request.getOrder_info_code());
            feedBackRequest.getBizData().setOrder_info_create_time(request.getOrder_info_create_time());
            feedBackRequest.getBizData().setOrder_info_divide(request.getOrder_info_divide());
            feedBackRequest.getBizData().setOrder_info_check_date(request.getOrder_info_check_date());
            feedBackRequest.getBizData().setOrder_info_qus_desc(request.getOrder_info_qus_desc());
            feedBackRequest.getBizData().setOrder_info_line(request.getOrder_info_line());
            feedBackRequest.getBizData().setOrder_info_serv(request.getOrder_info_serv());
            feedBackRequest.getBizData().setOrder_info_ver_date(request.getOrder_info_ver_date());
            feedBackRequest.getBizData().setOrder_info_checked_person(request.getOrder_info_checked_person());
            feedBackRequest.getBizData().setOrder_info_enclosure(request.getOrder_info_enclosure());
            list.postValue(feedBackRequest);
        });
        return list;
    }

    public void deleteFeedBackRequest(String orderId) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            db.qualityRequestDao().delete(orderId + FeedBackRequest);
        });
    }

    public void insertFeedBackRequest(String orderId, UnQualityFeedBackRequest request) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            QualityRequest qualityRequest = new QualityRequest();
            if(qualityRequest == null){
                return;
            }
            qualityRequest.setId(orderId + FeedBackRequest);
            qualityRequest.setCorrective_action(request.getBizData().getCorrective_action());
            qualityRequest.setFeedback_date(request.getBizData().getFeedback_date());
            qualityRequest.setFeedback_enclosure(request.getBizData().getFeedback_enclosure());
            qualityRequest.setReason(request.getBizData().getReason());
            qualityRequest.setTaskId(request.getDoNextParamt().getTaskId());

            //缓存工单基本信息
            qualityRequest.setOrder_info_state(request.getBizData().getOrder_info_state());
            qualityRequest.setOrder_info_code(request.getBizData().getOrder_info_code());
            qualityRequest.setOrder_info_create_time(request.getBizData().getOrder_info_create_time());
            qualityRequest.setOrder_info_divide(request.getBizData().getOrder_info_divide());
            qualityRequest.setOrder_info_check_date(request.getBizData().getOrder_info_check_date());
            qualityRequest.setOrder_info_qus_desc(request.getBizData().getOrder_info_qus_desc());
            qualityRequest.setOrder_info_line(request.getBizData().getOrder_info_line());
            qualityRequest.setOrder_info_serv(request.getBizData().getOrder_info_serv());
            qualityRequest.setOrder_info_ver_date(request.getBizData().getOrder_info_ver_date());
            qualityRequest.setOrder_info_checked_person(request.getBizData().getOrder_info_checked_person());
            qualityRequest.setOrder_info_enclosure(request.getBizData().getOrder_info_enclosure());

            db.qualityRequestDao().insert(qualityRequest);
        });
    }

    public LiveData<UnQualityVerificationRequest> loadVerificationRequest(String orderId) {
        MutableLiveData list = new MutableLiveData();
        Observable.just(1).subscribeOn(Schedulers.io()).doOnError(throwable -> {
        }).subscribe(integer -> {
            QualityRequest request = db.qualityRequestDao().queryRequest(orderId + VerificationRequest);
            if(request == null){
                return;
            }
            UnQualityVerificationRequest feedBackRequest = new UnQualityVerificationRequest();
            feedBackRequest.getBizData().setIs_pass(request.getIs_pass());
            feedBackRequest.getBizData().setVerification_date(request.getVerification_date());
            feedBackRequest.getBizData().setVerification_enclosure(request.getVerification_enclosure());
            feedBackRequest.getBizData().setVerification_situation(request.getVerification_situation());
            feedBackRequest.getDoNextParamt().setTaskId(request.getTaskId());
            //缓存工单基本信息
            feedBackRequest.getBizData().setOrder_info_state(request.getOrder_info_state());
            feedBackRequest.getBizData().setOrder_info_code(request.getOrder_info_code());
            feedBackRequest.getBizData().setOrder_info_create_time(request.getOrder_info_create_time());
            feedBackRequest.getBizData().setOrder_info_divide(request.getOrder_info_divide());
            feedBackRequest.getBizData().setOrder_info_check_date(request.getOrder_info_check_date());
            feedBackRequest.getBizData().setOrder_info_qus_desc(request.getOrder_info_qus_desc());
            feedBackRequest.getBizData().setOrder_info_line(request.getOrder_info_line());
            feedBackRequest.getBizData().setOrder_info_serv(request.getOrder_info_serv());
            feedBackRequest.getBizData().setOrder_info_ver_date(request.getOrder_info_ver_date());
            feedBackRequest.getBizData().setOrder_info_checked_person(request.getOrder_info_checked_person());
            feedBackRequest.getBizData().setOrder_info_enclosure(request.getOrder_info_enclosure());
            //反馈信息
            feedBackRequest.getBizData().setFed_info_date(request.getFed_info_date());
            feedBackRequest.getBizData().setFed_info_reason(request.getFed_info_reason());
            feedBackRequest.getBizData().setFed_info_cor_action(request.getFed_info_cor_action());
            feedBackRequest.getBizData().setFed_info_del_time(request.getFed_info_del_time());
            feedBackRequest.getBizData().setFed_info_enclosure(request.getFed_info_enclosure());
            list.postValue(feedBackRequest);
        });
        return list;
    }

    public void deleteVerificationRequest(String orderId) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            db.qualityRequestDao().delete(orderId + VerificationRequest);
        });
    }

    public void insertVerificationRequest(String orderId, UnQualityVerificationRequest request) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            QualityRequest qualityRequest = new QualityRequest();
            if (qualityRequest==null) {
                return;
            }
            qualityRequest.setId(orderId + VerificationRequest);
            qualityRequest.setVerification_date(request.getBizData().getVerification_date());
            qualityRequest.setVerification_enclosure(request.getBizData().getVerification_enclosure());
            qualityRequest.setVerification_situation(request.getBizData().getVerification_situation());
            qualityRequest.setIs_pass(request.getBizData().getIs_pass());
            qualityRequest.setTaskId(request.getDoNextParamt().getTaskId());
            //缓存工单基本信息
            qualityRequest.setOrder_info_state(request.getBizData().getOrder_info_state());
            qualityRequest.setOrder_info_code(request.getBizData().getOrder_info_code());
            qualityRequest.setOrder_info_create_time(request.getBizData().getOrder_info_create_time());
            qualityRequest.setOrder_info_divide(request.getBizData().getOrder_info_divide());
            qualityRequest.setOrder_info_check_date(request.getBizData().getOrder_info_check_date());
            qualityRequest.setOrder_info_qus_desc(request.getBizData().getOrder_info_qus_desc());
            qualityRequest.setOrder_info_line(request.getBizData().getOrder_info_line());
            qualityRequest.setOrder_info_serv(request.getBizData().getOrder_info_serv());
            qualityRequest.setOrder_info_ver_date(request.getBizData().getOrder_info_ver_date());
            qualityRequest.setOrder_info_checked_person(request.getBizData().getOrder_info_checked_person());
            qualityRequest.setOrder_info_enclosure(request.getBizData().getOrder_info_enclosure());
            //反馈信息
            qualityRequest.setFed_info_date(request.getBizData().getFed_info_date());
            qualityRequest.setFed_info_reason(request.getBizData().getFed_info_reason());
            qualityRequest.setFed_info_cor_action(request.getBizData().getFed_info_cor_action());
            qualityRequest.setFed_info_del_time(request.getBizData().getFed_info_del_time());
            qualityRequest.setFed_info_enclosure(request.getBizData().getFed_info_enclosure());

            db.qualityRequestDao().insert(qualityRequest);
        });
    }

    public DataSource.Factory<Integer, CreateUnQualityRequest> loadAllCreateRequest() {
        return db.createQualityRequestDao().loadAllRequest();
    }

    public void deleteCreateRequest(String code) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            db.createQualityRequestDao().delete(code);
        });
    }

    public void insertCreateRequest(CreateUnQualityRequest request) {
        Observable.just(1).subscribeOn(Schedulers.io()).subscribe(integer -> {
            request.setCode(request.getBizData().getCode());
            db.createQualityRequestDao().insert(request);
        });
    }

    public LiveData<CreateUnQualityRequest> queryCreateRequest(String code) {
        MutableLiveData list = new MutableLiveData();
        Observable.just(1).subscribeOn(Schedulers.io()).doOnError(throwable -> {
        }).subscribe(integer -> {
            list.postValue(db.createQualityRequestDao().queryRequest(code));
        });
        return list;
    }
}
