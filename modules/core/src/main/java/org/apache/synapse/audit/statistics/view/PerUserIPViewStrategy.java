/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.synapse.audit.statistics.view;

import org.apache.synapse.audit.statistics.StatisticsRecord;

import java.util.List;
import java.util.Map;

/**
 * PerUserViewStrategy , user IP is used
 */
public class PerUserIPViewStrategy extends PerUserViewStrategy {

    public Map<String, Map<String, Statistics>> determineView(List<StatisticsRecord> statisticsRecords, int type) {
        return determineView(statisticsRecords, type, IP);
    }

    public Map<String, Statistics> determineView(String id, List<StatisticsRecord> statisticsRecords, int type) {
        return determineView(id, statisticsRecords, type, IP);
    }
}