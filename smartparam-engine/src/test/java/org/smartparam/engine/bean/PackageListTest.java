package org.smartparam.engine.bean;

import org.junit.Before;
import org.junit.Test;
import static org.fest.assertions.api.Assertions.*;

/**
 *
 * @author Adam Dubiel <dubiel.adam@gmail.com>
 * @since 0.1.0
 */
public class PackageListTest {

    private PackageList packageList = null;

    @Before
    public void setUp() {
        packageList = new PackageList();
    }

    @Test
    public void shouldReturnEmptyListWhenNoPackagesAdded() {
        // given

        // when

        // then
        assertThat(packageList.getPackages()).isNotNull();
    }
}
