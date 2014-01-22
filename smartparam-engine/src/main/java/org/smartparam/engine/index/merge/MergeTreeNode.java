/*
 * Copyright 2014 Adam Dubiel, Przemek Hertel.
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

package org.smartparam.engine.index.merge;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Adam Dubiel
 */
public class MergeTreeNode<T> {

    private Map<String, MergeTreeNode<T>> children = new HashMap<String, MergeTreeNode<T>>();

    private T value;

    public boolean add(String[] levels, T entry, int currentDepth) {
        if(currentDepth >= levels.length) {
            // negotiate which one is better - there can be only one
            value = entry;
            return true;
        }

        String levelValue = levels[currentDepth];
        if("*".equals(levelValue)) {
        }
        if(children.containsKey(levelValue)) {
            return add(levels, entry, currentDepth + 1);
        }

        return false;
    }

}
