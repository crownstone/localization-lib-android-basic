package rocks.crownstone.localization;

import android.support.annotation.Nullable;

/**
 * Copyright (c) 2015 Bart van Vliet <bart@dobots.nl>. All rights reserved.
 * <p/>
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3, as
 * published by the Free Software Foundation.
 * <p/>
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * <p/>
 * Created on 21-6-16
 *
 * @author Bart van Vliet
 */
public interface Localization {

	/**
	 * Start accepting measurements to estimate the location.
	 *
	 * @param callback Callback which will be called with location updates.
	 */
	void startLocalization(LocalizationCallback callback);

	/**
	 * Stop localization.
	 */
	void stopLocalization();

	/**
	 * Clear all stored fingerprints
	 */
	void clear();

	/**
	 * Remove all stored fingerprints of a given sphere.
	 *
	 * @param sphereId    the id of the sphere (from the cloud) to clear
	 */
	void removeFingerprints(String sphereId);

	/**
	 * Feed measurements to the localization in order to get location updates.
	 *
	 * @param rssi        the rssi of the device which was scanned
	 * @param id          the id of the device that was scanned
	 * @param timeStampMs the time stamps in ms when the device was scanned. if null is provided,
	 *                    the current time now() will be used
	 */
	void track(int rssi, String id, @Nullable Long timeStampMs);

	/**
	 * Feed measurements to the localization to be used as fingerprint.
	 *
	 * @param rssi        the rssi of the device which was scanned
	 * @param id          the id of the device that was scanned
	 * @param timeStampMs the time stamps in ms when the device was scanned. if null is provided,
	 *                    the current time now() will be used
	 * @param fingerprint the fingerprint to which the measurements should be added. if null is
	 *                    provided as fingerprint the current fingerprint (which is created at
	 *                    startFingerprint) will be used
	 */
	void feedMeasurement(int rssi, String id, @Nullable Long timeStampMs, @Nullable Fingerprint fingerprint);

	/**
	 * Create a fingerprint from the fed measurements
	 *
	 * @param fingerprint the fingerprint to which the measurements should be added. if null is
	 *                    provided as fingerprint the current fingerprint (which is created at
	 *                    startFingerprint) will be used
	 */
	void createFingerprintSample(@Nullable Fingerprint fingerprint);

	/**
	 * Remove an existing fingerprint from the trained fingerprints
	 *
	 * @param sphereId    the id of the sphere (from the cloud) this fingerprint belongs to
	 * @param locationId  the id of the location (from the cloud)
	 */
	void removeFingerprint(String sphereId, String locationId);

	/**
	 * Start collecting a fingerprint.
	 */
	boolean startFingerprint();

	/**
	 * Stop collecting a fingerprint without loading it into the classifier.
	 */
	boolean abortFingerprint();

	/**
	 * Finalize collecting a fingerprint and store it in the appropriate classifier based on
	 * the groupId and the locationId.
	 *
	 * @param sphereId    the id of the sphere (from the cloud)
	 * @param locationId  the id of the location (from the cloud)
	 * @param fingerprint the fingerprint to be finalized/trained. if null is provided as fingerprint
	 *                    the current fingerprint (which is created at startFingerprint) will be used
	 * @return true if successfully trained, false otherwise
	 */
	boolean finalizeFingerprint(String sphereId, String locationId, @Nullable Fingerprint fingerprint);

	/**
	 * Obtain the fingerprint for this groupId and locationId. usually done after collecting it.
	 * The user is responsible for persistently storing and loading the fingerprints.
	 *
	 * @param sphereId    the id of the sphere (from the cloud)
	 * @param locationId  the id of the location (from the cloud)
	 * @return return the trained fingerprint defined by sphereId and locationId or null
	 * if none is found
	 */
	Fingerprint getFingerprint(String sphereId, String locationId);

	/**
	 * import a fingerprint into the classifier(s) for the specified groupId and locationId.
	 * The fingerprint can be constructed from a string by using the initializer when creating
	 * the Fingerprint object
	 *
	 * @param sphereId    the id of the sphere (from the cloud)
	 * @param locationId  the id of the location (from the cloud)
	 * @param fingerprint the fingerprint to be imported
	 */
	void importFingerprint(String sphereId, String locationId, Fingerprint fingerprint);
}
