/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.oozie.dbencdec;

import java.util.Calendar;

public enum TimeUnit {
    MINUTE(Calendar.MINUTE), HOUR(Calendar.HOUR), DAY(Calendar.DATE), WEEK(Calendar.WEEK_OF_YEAR), MONTH(
            Calendar.MONTH), YEAR(Calendar.YEAR), END_OF_DAY(
                    Calendar.DATE), END_OF_MONTH(Calendar.MONTH), END_OF_WEEK(Calendar.WEEK_OF_YEAR), CRON(0), NONE(-1);

    private int calendarUnit;

    private TimeUnit(int calendarUnit) {
        this.calendarUnit = calendarUnit;
    }

    public int getCalendarUnit() {
        return calendarUnit;
    }
}
