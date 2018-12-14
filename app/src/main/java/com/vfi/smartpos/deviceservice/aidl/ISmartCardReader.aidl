// ISmartCardReader.aidl
package com.vfi.smartpos.deviceservice.aidl;


/**
 * \_en_
 * @brief the object of smart card (contact card, or IC card)
 *
 * \en_e
 * \code{.java}
 * \endcode
 * @version
 * @see
 */
interface ISmartCardReader {
	/**
     * \_en_
     * @brief power up the card
     *
	 * @return true for success, false for failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see powerDown
     *
	 */
	boolean powerUp();

	/**
     * \_en_
     * @brief power down the card
     *
	 * @return true for success, false for failure
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see powerUp
	 *
	 */
	boolean powerDown();

	/**
     * \_en_
     * @brief check if the card avalible
     *
	 * @return true for available, false for unavailable
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
	 */
	boolean isCardIn();

	/**
     * \_en_
     * @brief execute an apdu command
     *
	 * @param apdu apdu command input
	 * @return response of the command, null means no response got
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
	 */
	byte[] exchangeApdu(in byte[] apdu);

	/**
     * \_en_
     * @brief
     *
     * \en_e
     * \code{.java}
     * \endcode
     * @version
     * @see
     */
    boolean isPSAMCardExists();
}
