// IEMV.aidl
package com.vfi.smartpos.deviceservice.aidl;
import com.vfi.smartpos.deviceservice.aidl.CheckCardListener;
import com.vfi.smartpos.deviceservice.aidl.UPCardListener;
import com.vfi.smartpos.deviceservice.aidl.EMVHandler;
import com.vfi.smartpos.deviceservice.aidl.OnlineResultHandler;
import com.vfi.smartpos.deviceservice.aidl.CandidateAppInfo;

/**
 * \_en_
 * @brief EMV object for processing EMV
 *
 * \en_e
 * @author Kai.L@verifone.cn, Chao.L@verifone.cn
 */
interface IEMV {
    /**
     * \_en_
     * @brief check card, non-block method
     *
     * @param cardOption the card type (list)
     * <ul>
     * <li>supportMagCard(boolean) support magnetic card</li>
     * <li>supportSmartCard(boolean) support Smart card</li>
     * <li>supportCTLSCard(boolean) support CTLS card</li>
     * </ul>
     * @param timeout the time out(seconds)
     * @param listener the listerner while found card
     * @version
     * @see stopCheckCard startEMV CheckCardListener
     */

	void checkCard(in Bundle cardOption, int timeout, CheckCardListener listener);

    /**
     * \_en_
     * @brief stop check card
     *
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see checkCard startEMV CheckCardListener
     */
	void stopCheckCard();

    /**
     * \_en_
     * @brief start EMV process
     *
     * @param transflow processing type
     * <ul>
     * <li>1：EMV processing</li>
     * <li>2：EMV simplified processing</li>
     * </ul>
     * @param intent request setting
     * <ul>
     * <li>isPanConfirmOnSimpeProcess(boolean):simple process need card confirm callback [yes or not]</li>
     * <li>cardType(int): card type
     *      * CARD_INSERT(0)- smart IC card
     *      * CARD_RF(1)- CTLS card </li>
     * <li>transProcessCode(byte): (1Byte) Translation type (9C first two digits of the ISO 8583:1987 Processing Code)</li>
     * <li>authAmount(long): auth-amount (transaction amount)</li>
     * <li>isSupportSM(boolean): is support SM </li>
     * <li>isForceOnline(boolean): is force online </li>
     * <li>merchantName(String):merchant Name </li>
     * <li>merchantId(String): merchant ID </li>
     * <li>terminalId(String):terminal ID </li>
     * <li>transCurrCode(String):set Transaction Currency Code(5F2A) value</li>
     * <li>otherAmount(String): set Other Amount (9F03) value</li>
     * </ul>
     * @param handler the call back handler, please refer EMVHandler
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see startEMV abortEMV
     */
	void startEMV(int transflow, in Bundle intent, EMVHandler handler);

    /**
     * \_en_
     * @brief stop EMV
     *
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     * */
	void abortEMV();

    /**
     * \_en_
     * @brief update AID parameter
     *
     * @param operation the setting
     * <ul>
     * <li>1：append</li>
     * <li>2：remove</li>
     * <li>3：clear all</li>
     * </ul>
     * @param aidType type of AID parameter
     * <ul>
     * <li>1：contact(smart card)</li>
     * <li>2：contactless</li>
     * </ul>
     * @param aid the AID parameter
     * @return result, true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see getAID
     */
    boolean updateAID(int operation,int aidType, String aid);

    /**
     * \_en_
     * @brief update CA public KEY
     *
     * @param operation the setting
     * <ul>
     * <li>1：append</li>
     * <li>2：remove</li>
     * <li>3：clear all</li>
     * </ul>
     * @param rid the CA public KEY
     * @return true on success, false on failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see getRID
     */
    boolean updateRID(int operation, String rid);

    /**
     * @Deprecated
     * \_en_
     * @brief import amount
     *
     * There is nothing in this method. The amount should be set while call the startEMV.
     * @param amount the amount
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see startEMV
     */
    void importAmount(long amount);

    /**
     * \_en_
     * @brief select application (multi-application card)
     *
     * @param index the index of application, start from 1, and 0 means cancel
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see EMVHandler#onSelectApplication
     */
    void importAppSelection(int index);

    /**
     * \_en_
     * @brief import the PIN
     *
     * @param option(int) - 操作选项 | the option
     * <ul>
     * <li> CANCEL(0) cancel</li>
     * <li> CONFIRM(1) confirm</li>
     * </ul>
     * @param pin the PIN data
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see EMVHandler#onRequestInputPIN
     */
    void importPin(int option, in byte[] pin);

    /**
     * \_en_
     * @brief import the result of card hodler verification
     *
     * @param option the option
     * <ul>
     * <li> CANCEL(0) cancel ( BYPASS )</li>
     * <li> CONFIRM(1) confirm</li>
     * <li> NOTMATCH(2) not match</li>
     * </ul>
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see EMVHandler#onConfirmCertInfo
     */
    void importCertConfirmResult(int option);

    /**
     * \_en_
     * @brief import the result of card verification
     *
     * @param pass true on pass, false on error
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see EMVHandler#onConfirmCardInfo
     */
    void importCardConfirmResult(boolean pass);


    /**
     * \_en_
     * @brief input the online response
     *
     * @param onlineResult  set the result ( response )
     * <ul>
     * <li> isOnline(boolean)is online</li>
     * <li> respCode(String) the response code</li>
     * <li> authCode(String) the authorize code</li>
     * <li> field55(String) the response of field 55 data</li>
     * </ul>
     * @param handler the result , please refer OnlineResultHandler
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see EMVHandler#onRequestOnlineProcess
     */
    void inputOnlineResult(in Bundle onlineResult, OnlineResultHandler handler);

    /**
     * \_en_
     * @brief set EMV (kernel) data
     *
     * before start emv flow, you can set the data
     * @param tlvList the TLV list
     * Support set following tag. If AID list have the same tag, aid list priority over this interface.
     * 9F33：Terminal Capabilities
     * 9F15：Merchant Category Code
     * 9F16：Merchant Identifier
     * 9F4E：Merchant Name
     * 9F1C：Terminal Identification
     * 9F35：Terminal Type
     * 9F3C：Transaction Reference Currency Code
     * 9F3D：Transaction Reference Currency Exponent
     * 5F2A：Transaction Currency Code
     * 5F36：Transaction Currency Exponent
     * 9F1A：Terminal Country Code
     * 9F40：Additional Terminal Capabilities
     * \en_e
     * \code{.java}
    IEMV iemv;
    EMVL2 emvl2;
    public void K12001() {
        byte[] acquirerID = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        byte[] termCap = {(byte) 0xe0, (byte) 0xf1, (byte) 0xc8};
        byte[] addTermCap = {(byte) 0xe0, (byte) 0x00, (byte) 0xF0, (byte) 0xA0, (byte) 0x01};
        byte[] countryCode = {(byte) 0x01, (byte) 0x56};
        byte[] currencyCode = {(byte) 0x01, (byte) 0x56};
        byte[] termType = {(byte) 0x22};

        Collection<String> tlvList = new ArrayList<String>();

        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F15020000")));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F160F")) + Utils.byte2HexStr("123456789012345".getBytes()));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F4E0D")) + Utils.byte2HexStr("Verifone Test".getBytes()));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F1C08")) + Utils.byte2HexStr("12345678".getBytes()));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F0106")) + Utils.byte2HexStr(acquirerID));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F1E08")) + Utils.byte2HexStr("50342027".getBytes()));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F3501")) + Utils.byte2HexStr(termType));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F3303")) + Utils.byte2HexStr(termCap));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F4005")) + Utils.byte2HexStr(addTermCap));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F1A02")) + Utils.byte2HexStr(countryCode));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("5F2A02")) + Utils.byte2HexStr(currencyCode));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("5F3601" + "02")));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F3C02")) + Utils.byte2HexStr(currencyCode));
        tlvList.add(Utils.byte2HexStr(Utils.asc2Bcd("9F3D0102")));
        iemv.setEMVData(tlvList);
    }
     * \endcode
     * @version
     * @see
     */
    void setEMVData(in List<String> tlvList);

    /**
     * \_en_
     * @brief get kernal data list in Tag-Length-Value format
     *
	 * @param taglist the tag list want query
	 * @return the response in TLV format, null means no response got
     * \en_e
     * \code{.java}
     {
         String[] strlist = {"9F33", "9F40", "9F10", "9F26", "95", "9F37", "9F1E", "9F36",
                 "82", "9F1A", "9A", "9B", "50", "84", "5F2A", "8F"};

         String strs = iemv.getAppTLVList(strlist);
      }
     * \endcode
     * @version
     * @see
	 */
	String getAppTLVList(in String[] taglist);

    /**
     * \_en_
     * @brief get card (emv) data by tag
     *
	 * @param tagName the tag name
	 * @return the emv data got
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
	 */
	byte[] getCardData(String tagName);

	/**
     * \_en_
     * @brief get EMV data
     *
     * such as card number, valid dtae, card serial number, etc.
     * <em> will return null if the data is not avalible at the current EMV process</em>
	 * @param tagName tag name
	 * <ul>
     * <li> PAN card No.</li>
     * <li> TRACK2 track No.2</li>
     * <li> CARD_SN card SN (Serial Number)</li>
     * <li> EXPIRED_DATE expried date</li>
     * <li> DATE date</li>
     * <li> TIME time</li>
     * <li> BALANCE balance</li>
     * <li> CURRENCY currency</li>
     * </ul>
	 * @return the return data of EMV
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see getCardData
	 *
	 */
	String getEMVData(String tagName);

    /**
     * \_en_
     * @brief get the AID parameter
     *
	 * @param type - 1-contact aid  2-contactless aid
     * @return null if the AID is unavailable
     * \en_e
     * \code
      demo returns from getAID(1)
{"9F0607A0000000031010DF0101009F09020140DF1105C000000000DF12050000000000DF130500000000009F1B0400000000DF1504000000009F7B06000000000000DF1906000000000000DF20060099999999995F2A0201569F1A0201569F3303E0F9C89F4005FF00F0A0019F6604260000809F350122DF150400000000DF160101DF170101DF14039F3704DF1801009F1D00",
"9F0607A0000000032010DF0101009F09020140DF1105D84004A800DF1205D84000F800DF130500100000009F1B0400000000DF1504000000009F7B06000000000000DF1906000000000000DF20060099999999995F2A0201569F1A0201569F3303E0F9C89F4005FF00F0A0019F6604260000809F350122DF150400000000DF160101DF170101DF14039F3704DF1801009F1D00"
     * \endcode
     * @version
     * @see updateAID
     */
	String[] getAID(int type);

    /**
     * \_en_
     * @brief get the RID
     *
     * @return, null if the RID is unavailable
     * \en_e
     * \code{.java}
     demo returns from getRID()
     {"9F0605A0000000039F220199DF050420291231DF03144ABFFD6B1C51212D05552E431C5B17007D2F5E6DDF070101DF060101DF028180AB79FCC9520896967E776E64444E5DCDD6E13611874F3985722520425295EEA4BD0C2781DE7F31CD3D041F565F747306EED62954B17EDABA3A6C5B85A1DE1BEB9A34141AF38FCF8279C9DEA0D5A6710D08DB4124F041945587E20359BAB47B7575AD94262D4B25F264AF33DEDCF28E09615E937DE32EDC03C54445FE7E382777DF0403000003"}
     * \endcode
     * @version
     * @see updateRID
     */
	String[] getRID();

	/**
     * \_en_
     * @brief Obtain the CTLS card type
     *
     * In onRequestOnlineProcess callback you can use this interface to obtain the CTLS card type
	* @return:
	*   0-No Type
	*   1-JCB_CHIP
	*   2-JCB_MSD
	*   3-JCB_Legcy
	*   4-VISA_w1
	*   5-VISA_w3
	*   6-Master_EMV
	*   7-Master_MSD
	*   8-qPBOC_qUICS
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see EMVHandler#onRequestOnlineProcess
	*
	*/
	int getProcessCardType();
}
