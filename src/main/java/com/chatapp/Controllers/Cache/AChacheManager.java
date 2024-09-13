package com.chatapp.Controllers.Cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.chatapp.Services.IValidateItem;

public abstract class AChacheManager<Key,Value> extends ACacheController<Key,Value> implements IValidateItem<Key> {

    public AChacheManager(Function<Value, Boolean> clearCallBack, long interval,ConcurrentHashMap<Key,Value> sessionContainer) {
        super(clearCallBack, interval,sessionContainer);
        //TODO Auto-generated constructor stub
    }


}
