package org.smartparam.engine.test.scan.plugins;

import org.smartparam.engine.annotations.SmartParamJavaPlugin;

/**
 *
 * @author Adam Dubiel <dubiel.adam@gmail.com>
 */
public class DummyPluginContainer {

    @SmartParamJavaPlugin("javaPlugin")
    public void javaPlugin() {
    }

    @DummyPluginAnnotation("dummyPlugin")
    public void dummyPlugin() {
    }

    @DummyPluginAnnotation("dummyPlugin")
    public void dummyPluginDuplicate() {
    }

    @DummyPluginAnnotationWithoutValue
    public void dummyPluginForAnnotationWithoutValue() {
    }
}