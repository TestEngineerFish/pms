package com.einyun.app.pms.create.viewmodel.contract;

import androidx.lifecycle.LiveData;

import com.einyun.app.library.portal.dictdata.model.DictDataModel;
import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;
import com.einyun.app.library.resource.workorder.net.request.CreateSendOrderRequest;
import com.einyun.app.library.uc.usercenter.model.OrgModel;
import com.einyun.app.library.upload.model.PicUrl;

import java.util.List;

public interface CreateViewModelContract {
    LiveData<List<DictDataModel>> getByTypeKey();

    LiveData<List<DictDataModel>> getTypesListByKey();

    LiveData<Boolean> createSendOrder(CreateSendOrderRequest request, List<PicUrl> images);

    LiveData<List<ResourceTypeBean>> getResourceInfos(CreateSendOrderRequest request);

    LiveData<List<OrgModel>> getDisposePerson(String orgId, String dimCode);
}
