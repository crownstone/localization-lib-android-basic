/**
 * Author: Crownstone Team
 * Copyright: Crownstone (https://crownstone.rocks)
 * Date: Sep 16, 2020
 * License: LGPLv3+, Apache License 2.0, and/or MIT (triple-licensed)
 */

package rocks.crownstone.localization.library

import rocks.crownstone.localization.library.structs.Fingerprint

interface Localization {
    /**
     * Start accepting measurements to estimate the location.
     *
     * @param callback Callback which will be called with location updates.
     */
    fun startLocalization(callback: LocalizationCallback)

    /**
     * Stop localization.
     */
    fun stopLocalization()

    /**
     * Clear all stored fingerprints
     */
    fun clear()

    /**
     * Remove all stored fingerprints of a given sphere.
     *
     * @param sphereId    the id of the sphere (from the cloud) to clear
     */
    fun removeFingerprints(sphereId: String)

    /**
     * Feed measurements to the localization in order to get location updates.
     *
     * @param rssi        the rssi of the device which was scanned
     * @param id          the id of the device that was scanned
     * @param timeStampMs the time stamps in ms when the device was scanned. if null is provided,
     * the current time now() will be used
     */
    fun track(rssi: Int, id: String, timeStampMs: Long?)

    /**
     * Feed measurements to the localization to be used as fingerprint.
     *
     * @param rssi        the rssi of the device which was scanned
     * @param id          the id of the device that was scanned
     * @param timeStampMs the time stamps in ms when the device was scanned. if null is provided,
     * the current time now() will be used
     * @param fingerprint the fingerprint to which the measurements should be added. if null is
     * provided as fingerprint the current fingerprint (which is created at
     * startFingerprint) will be used
     */
    fun feedMeasurement(rssi: Int, id: String, timeStampMs: Long?, fingerprint: Fingerprint?)

    /**
     * Create a fingerprint from the fed measurements
     *
     * @param fingerprint the fingerprint to which the measurements should be added. if null is
     * provided as fingerprint the current fingerprint (which is created at
     * startFingerprint) will be used
     */
    fun createFingerprintSample(fingerprint: Fingerprint?)

    /**
     * Remove an existing fingerprint from the trained fingerprints
     *
     * @param sphereId    the id of the sphere (from the cloud) this fingerprint belongs to
     * @param locationId  the id of the location (from the cloud)
     */
    fun removeFingerprint(sphereId: String, locationId: String)

    /**
     * Start collecting a fingerprint.
     *
     * @return Boolean
     */
    fun startFingerprint(): Boolean

    /**
     * Stop collecting a fingerprint without loading it into the classifier.
     *
     * @return Boolean
     */
    fun abortFingerprint(): Boolean

    /**
     * Finalize collecting a fingerprint and store it in the appropriate classifier based on
     * the groupId and the locationId.
     *
     * @param sphereId    the id of the sphere (from the cloud)
     * @param locationId  the id of the location (from the cloud)
     * @param fingerprint the fingerprint to be finalized/trained. if null is provided as fingerprint
     * the current fingerprint (which is created at startFingerprint) will be used
     * @return true if successfully trained, false otherwise
     */
    fun finalizeFingerprint(sphereId: String, locationId: String, fingerprint: Fingerprint?): Boolean

    /**
     * Obtain the fingerprint for this groupId and locationId. usually done after collecting it.
     * The user is responsible for persistently storing and loading the fingerprints.
     *
     * @param sphereId    the id of the sphere (from the cloud)
     * @param locationId  the id of the location (from the cloud)
     * @return return the trained fingerprint defined by sphereId and locationId or null
     * if none is found
     */
    fun getFingerprint(sphereId: String, locationId: String): Fingerprint?

    /**
     * import a fingerprint into the classifier(s) for the specified groupId and locationId.
     * The fingerprint can be constructed from a string by using the initializer when creating
     * the Fingerprint object
     *
     * @param sphereId    the id of the sphere (from the cloud)
     * @param locationId  the id of the location (from the cloud)
     * @param fingerprint the fingerprint to be imported
     */
    fun importFingerprint(sphereId: String, locationId: String, fingerprint: Fingerprint)
}
