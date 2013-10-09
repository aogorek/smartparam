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
package org.smartparam.repository.jdbc.dao;

import java.util.Arrays;
import java.util.Set;
import org.polyjdbc.core.query.QueryRunner;
import org.smartparam.engine.model.ParameterEntry;
import org.smartparam.repository.jdbc.config.DefaultConfigurationBuilder;
import org.smartparam.repository.jdbc.integration.DatabaseTest;
import org.smartparam.repository.jdbc.model.JdbcParameterEntry;
import org.testng.annotations.Test;
import static org.smartparam.engine.test.Sets.onlyElement;
import static org.smartparam.engine.test.assertions.Assertions.assertThat;
import static org.smartparam.engine.test.builder.ParameterEntryTestBuilder.parameterEntry;

/**
 *
 * @author Adam Dubiel
 */
@Test(groups = "integration")
public class ParameterEntryDAOTest extends DatabaseTest {

    @Override
    protected void customizeConfiguraion(DefaultConfigurationBuilder builder) {
        builder.withExcessLevelSeparator('|').withLevelColumnCount(2);
    }

    @Test
    public void shouldInsertNewParameterEntry() {
        // given
        database().withParameter(1).build();
        ParameterEntryDAO parameterEntryDAO = get(ParameterEntryDAO.class);
        ParameterEntry entry = parameterEntry().withLevels("1", "2").build();
        QueryRunner runner = queryRunner();

        // when
        parameterEntryDAO.insert(runner, Arrays.asList(entry), 1);
        runner.commit();

        Set<JdbcParameterEntry> entries = parameterEntryDAO.getParameterEntries(runner, 1);
        runner.close();

        // then
        assertThat(entries).hasSize(1);
    }

    @Test
    public void shouldConcatenateContentsOfExcessLevelsInLastLevelWhenInserting() {
        // given
        database().withParameter(1).build();
        ParameterEntryDAO parameterEntryDAO = get(ParameterEntryDAO.class);
        ParameterEntry entry = parameterEntry().withLevels("1", "2", "3", "4").build();
        QueryRunner runner = queryRunner();

        // when
        parameterEntryDAO.insert(runner, Arrays.asList(entry), 1);
        runner.commit();

        Set<JdbcParameterEntry> entries = parameterEntryDAO.getParameterEntries(runner, 1);
        runner.close();

        // then
        assertThat(onlyElement(entries)).hasLevels(4).levelAtEquals(2, "3").levelAtEquals(3, "4");
    }

    @Test
    public void shouldListAllEntriesForParameter() {
        // given
        database().withParameter(1).withParameterEntries(1, 5).build();
        ParameterEntryDAO parameterEntryDAO = get(ParameterEntryDAO.class);
        QueryRunner runner = queryRunner();

        // when
        Set<JdbcParameterEntry> entries = parameterEntryDAO.getParameterEntries(runner, 1);
        runner.close();

        // then
        assertThat(entries).hasSize(5);
    }
}