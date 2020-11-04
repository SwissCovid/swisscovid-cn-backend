/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */

package ch.ubique.n2step.sdk.backend.data;

import ch.ubique.n2step.sdk.backend.model.TraceKey;
import java.time.LocalDateTime;
import java.util.List;

public interface N2StepDataService {

    /**
     * Inserts the given trace key into the db
     *
     * @param traceKey
     */
    public void insertTraceKey(TraceKey traceKey);

    /**
     * Returns trace keys that where submitted (/created) after the given date. Returns all trace
     * keys if after == null.
     *
     * @param after
     * @return
     */
    public List<TraceKey> findTraceKeys(LocalDateTime after);
}
