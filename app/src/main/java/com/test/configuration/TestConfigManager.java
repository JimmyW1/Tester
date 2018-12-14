package com.test.configuration;

import com.test.ui.activities.R;

/**
 * Created by CuncheW1 on 2017/8/4.
 */

public class TestConfigManager {
    private ModuleUIConfig moduleUICfg;
    private ServiceAccessUIConfig servAccessUICfg;
    private static TestConfigManager instance;
    private String serviceAccesserPackagePath;

    public TestConfigManager() {
        moduleUICfg = ModuleUIConfig.getInstance();
        servAccessUICfg = ServiceAccessUIConfig.getInstance();
        addDefaultModuleConfig();
        addDefaultServiceAccesserConfig();
        addDefaultServiceAccesserPackagePath();
    }

    public static synchronized TestConfigManager getInstance() {
        if (instance == null) {
            instance = new TestConfigManager();
        }

        return instance;
    }

    /**
     * 如果想在按钮上显示非类名需要在此处做配置，addConfig参数说明如下：
     * 参数一：类名
     * 参数二：手动测试进入此模块测试按钮显示的文字，如果没有在此处进行配置直接显示为类名
     * 参数三：自动测试进入此模块测试的按钮显示的文字，如果没有在此处配置直接显示为类名
     */
    private void addDefaultModuleConfig() {
        moduleUICfg.addConfig("ModuleBeeper", R.string.beeper, R.string.auto_beeper);
        moduleUICfg.addConfig("ModuleCardReader", R.string.cardreader, R.string.auto_cardreader);
        moduleUICfg.addConfig("ModuleDeviceInfo", R.string.dev_info, R.string.auto_dev_info);
        moduleUICfg.addConfig("ModuleICCard", R.string.iccard, R.string.auto_iccard);
        moduleUICfg.addConfig("ModuleLed", R.string.led, R.string.auto_led);
        moduleUICfg.addConfig("ModulePBOC", R.string.pboc, R.string.auto_pboc);
        moduleUICfg.addConfig("ModulePinpad", R.string.pinpad, R.string.auto_pinpad);
        moduleUICfg.addConfig("ModulePrinter", R.string.printer, R.string.auto_printer);
        moduleUICfg.addConfig("ModuleRFCard", R.string.rfcard, R.string.auto_rfcard);
        moduleUICfg.addConfig("ModuleScanner", R.string.scanner, R.string.auto_scanner);
        moduleUICfg.addConfig("ModuleSwiper", R.string.swiper, R.string.auto_swiper);
        moduleUICfg.addConfig("ModuleTerminalManage", R.string.terminal_manage, R.string.auto_terminal_manage);
        moduleUICfg.addConfig("ModuleAll", R.string.auto_all, R.string.auto_all);

        moduleUICfg.addConfig("BeepTestCase", R.string.beeper, R.string.auto_beeper);
        moduleUICfg.addConfig("CardReaderTestCase", R.string.cardreader, R.string.auto_cardreader);
        moduleUICfg.addConfig("DeviceInfoTestCase", R.string.dev_info, R.string.auto_dev_info);
        moduleUICfg.addConfig("ICCardTestCase", R.string.iccard, R.string.auto_iccard);
        moduleUICfg.addConfig("LedTestCase", R.string.led, R.string.auto_led);
        moduleUICfg.addConfig("PinpadTestCase", R.string.pinpad, R.string.auto_pinpad);
        moduleUICfg.addConfig("PrinterTestCase", R.string.printer, R.string.auto_printer);
        moduleUICfg.addConfig("RFCardTestCase", R.string.rfcard, R.string.auto_rfcard);
        moduleUICfg.addConfig("ScannerTestCase", R.string.scanner, R.string.auto_scanner);
        moduleUICfg.addConfig("SwiperTestCase", R.string.swiper, R.string.auto_swiper);
        moduleUICfg.addConfig("TerminalManageTestCase", R.string.terminal_manage, R.string.auto_terminal_manage);
        moduleUICfg.addConfig("DeviceServiceTestCase", R.string.deviceservice, R.string.auto_deviceservice);

        moduleUICfg.addConfig("ModuleExternalSerial", R.string.externserial, R.string.auto_externserial);
    }

    /**
     * 如果想在按钮上显示非类名需要在此处做配置，addUIConfig参数说明如下：
     * 参数一：类名
     * 参数二：主界面入口想要显示的名字,即提示测试哪个服务，如果没有在此处进行配置直接显示为类名
     *
     * 配置服务对应的测试模块的包路径，addPackagePath参数说明如下：
     * 参数一：类名
     * 参数二：服务对于的测试模块对应的包路径
     */
    private void addDefaultServiceAccesserConfig() {
        servAccessUICfg.addUIConfig("ADCServiceAccesser", R.string.adc_test);
        servAccessUICfg.addPackagePath("ADCServiceAccesser", "com.test.logic.testmodules.adc_modules");

        servAccessUICfg.addUIConfig("BOCServiceAccesser", R.string.boc_test);
        servAccessUICfg.addPackagePath("BOCServiceAccesser", "com.test.logic.testmodules.boc_modules");

        servAccessUICfg.addUIConfig("CBCServiceAccesser", R.string.cbc_test);
        servAccessUICfg.addPackagePath("CBCServiceAccesser", "com.test.logic.testmodules.cbc_modules");

        servAccessUICfg.addUIConfig("ICBCServiceAccesser", R.string.icbc_test);
        servAccessUICfg.addPackagePath("ICBCServiceAccesser", "com.test.logic.testmodules.icbc_modules");

        servAccessUICfg.addUIConfig("UMSServiceAccesser", R.string.ums_test);
        servAccessUICfg.addPackagePath("UMSServiceAccesser", "com.test.logic.testmodules.ums_modules");

        servAccessUICfg.addUIConfig("RKServiceAccesser", R.string.rk_test);
        servAccessUICfg.addPackagePath("RKServiceAccesser", "com.test.logic.testmodules.rk_modules");
    }

    private void addDefaultServiceAccesserPackagePath() {
        this.serviceAccesserPackagePath = "com.test.logic.service_accesser";
    }

    public String getModuleManaulTestUIName(String moduleName) {
        String UIName = moduleUICfg.getManualTestUIName(moduleName);
        return (UIName == null ? moduleName : UIName);
    }

    public String getModuleAutoTestUIName(String moduleName) {
        String UIName = moduleUICfg.getAutoTestUIName(moduleName);
        return (UIName == null ? moduleName : UIName);
    }

    public String getServiceAccesserUIName(String servAccesserName) {
        String UIName = servAccessUICfg.getUIName(servAccesserName);
        return (UIName == null ? servAccesserName : UIName);
    }

    public String getServiceTestPackageName(String servAccesserName) {
        return servAccessUICfg.getTestModulesPackagePath(servAccesserName);
    }

    public String getServiceAccesserPackagePath() {
        return serviceAccesserPackagePath;
    }
}
