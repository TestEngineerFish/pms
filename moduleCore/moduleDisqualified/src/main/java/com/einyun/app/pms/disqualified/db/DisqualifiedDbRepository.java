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
