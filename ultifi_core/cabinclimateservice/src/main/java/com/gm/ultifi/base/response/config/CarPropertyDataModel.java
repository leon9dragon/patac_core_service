package com.gm.ultifi.base.response.config;
/**
 * TODO: 2023/4/26 框架代码抽离
 */
public class CarPropertyDataModel {
    private int mAreaId;

    private Class<?> mClassType;

    private String mFieldName;

    private boolean mIsValid;

    private int mPropertyId;

    private Object mValue;

    public CarPropertyDataModel(String fieldName, Class<?> classType, int propertyId, int areaId, Object value, boolean isValid) {
        mClassType = classType;
        mPropertyId = propertyId;
        mAreaId = areaId;
        mValue = value;
        mIsValid = isValid;
        mFieldName = fieldName;
    }

    public int getAreaId() {
        return mAreaId;
    }

    public Class<?> getClassType() {
        return mClassType;
    }

    public String getFieldName() {
        return mFieldName;
    }

    public int getPropertyId() {
        return mPropertyId;
    }

    public Object getValue() {
        return mValue;
    }

    public boolean isValid() {
        return mIsValid;
    }

    public void setAreaId(int areaId) {
        mAreaId = areaId;
    }
}