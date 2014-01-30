/*
 * Copyright 2014 Adam Dubiel.
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
package org.smartparam.engine.index;

import org.smartparam.engine.core.matcher.Matcher;

/**
 *
 * @author Adam Dubiel
 */
public class IndexTraversalOverrides {

    private final boolean[] greedinessMatrix;

    private final Matcher[] overridenMatchers;

    private final String[] matcherCodes;

    private final String[] levelNames;

    public IndexTraversalOverrides(boolean[] greedinessMatrix, Matcher[] overridenMatchers,
            String[] matcherCodes, String[] levelNames) {
        this.greedinessMatrix = greedinessMatrix;
        this.overridenMatchers = overridenMatchers;
        this.matcherCodes = matcherCodes;
        this.levelNames = levelNames;
    }

    public boolean isGreedy(int levelDepth) {
        return greedinessMatrix[levelDepth];
    }

    public boolean overrideMatcher(int levelDepth) {
        return overridenMatchers[levelDepth] != null;
    }

    public Matcher overridenMatcher(int levelDepth) {
        return overridenMatchers[levelDepth];
    }

    public String matcherCode(int levelDepth) {
        return matcherCodes[levelDepth];
    }

    public String levelName(int levelDepth) {
        return levelNames[levelDepth];
    }
}
