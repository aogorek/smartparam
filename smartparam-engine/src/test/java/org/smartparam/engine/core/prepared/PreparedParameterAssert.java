/*
 * Copyright 2013 Adam Dubiel, Przemek Hertel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartparam.engine.core.prepared;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.data.MapEntry;
import org.smartparam.engine.test.ParamEngineAssertions;

/**
 *
 * @author Adam Dubiel
 */
public class PreparedParameterAssert extends AbstractAssert<PreparedParameterAssert, PreparedParameter> {

    private PreparedParameterAssert(PreparedParameter actual) {
        super(actual, PreparedParameterAssert.class);
    }

    public static PreparedParameterAssert assertThat(PreparedParameter actual) {
        return new PreparedParameterAssert(actual);
    }

    public PreparedParameterAssert hasIndex() {
        ParamEngineAssertions.assertThat(actual.getIndex()).isNotNull();
        return this;
    }

    public PreparedParameterAssert hasNoIndex() {
        ParamEngineAssertions.assertThat(actual.getIndex()).isNull();
        return this;
    }

    public PreparedParameterAssert hasLevelNameEntry(String levelName, int index) {
        ParamEngineAssertions.assertThat(actual.getLevelNameMap()).contains(MapEntry.entry(levelName, index));
        return this;
    }

    public PreparedParameterAssert hasName(String name) {
        ParamEngineAssertions.assertThat(actual.getName()).isSameAs(name);
        return this;
    }

    public PreparedParameterAssert hasInputLevels(int inputLevels) {
        ParamEngineAssertions.assertThat(actual.getInputLevelsCount()).isEqualTo(inputLevels);
        return this;
    }

    public PreparedParameterAssert hasArraySeparator(char separator) {
        ParamEngineAssertions.assertThat(actual.getArraySeparator()).isSameAs(separator);
        return this;
    }

    public PreparedLevelAssert level(int index) {
        return PreparedLevelAssert.assertThat(actual.getLevels()[index]);
    }
}