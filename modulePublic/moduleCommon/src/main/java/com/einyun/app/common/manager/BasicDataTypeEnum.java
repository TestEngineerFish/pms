package com.einyun.app.common.manager;


import com.einyun.app.library.resource.workorder.model.ResourceTypeBean;

import java.util.List;

public enum BasicDataTypeEnum {
	RESOURCE("RESOURCE"),LINE("LINE"),LINE_TYPES("LINE_TYPES"),
	COMPLAIN_TYPES("COMPLAIN_TYPES"),COMPLAIN_PROPERTYS("COMPLAIN_PROPERTYS"),
	REPAIR_AREA("REPAIR_AREA"),PREVIEW_SELECT("PREVIEW_SELECT");

	String typeName;

	BasicDataTypeEnum(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}