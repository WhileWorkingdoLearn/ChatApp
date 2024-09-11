package com.chatapp.Controllers.Cache;

import java.util.function.Function;

import com.chatapp.Services.IValidateItem;

public abstract class AChacheManager<Key,Value> extends ACacheController<Key,Value> implements IValidateItem<Key> {

    public AChacheManager(Function<Value, Boolean> clearCallBack, long interval) {
        super(clearCallBack, interval);
        //TODO Auto-generated constructor stub
    }


}
