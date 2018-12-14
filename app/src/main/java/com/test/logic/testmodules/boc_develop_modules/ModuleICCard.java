package com.test.logic.testmodules.boc_develop_modules;

import com.boc.aidl.iccard.AidlICCard;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;

/**
 * Created by CuncheW1 on 2017/3/1.
 */

public class ModuleICCard extends BaseModule {
    AidlICCard iICCard;

    public ModuleICCard() {
        this.iICCard = DeviceService.getICCard();
    }


}
