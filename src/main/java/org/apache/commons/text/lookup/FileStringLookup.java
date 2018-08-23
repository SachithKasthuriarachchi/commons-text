/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package org.apache.commons.text.lookup;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Looks up keys from an XML document.
 * <p>
 * Looks up the value for a given key in the format "Charset:Path".
 * </p>
 * <p>
 * For example: "UTF-8:com/domain/document.properties".
 * </p>
 *
 * @since 1.5
 */
final class FileStringLookup extends AbstractStringLookup {

    /**
     * Defines the singleton for this class.
     */
    static final FileStringLookup INSTANCE = new FileStringLookup();

    /**
     * No need to build instances for now.
     */
    private FileStringLookup() {
        // empty
    }

    /**
     * Looks up the value for the key in the format "DocumentPath:XPath".
     * <p>
     * For example: "com/domain/document.xml:/path/to/node".
     * </p>
     * 
     * @param key
     *            the key to be looked up, may be null
     * @return The value associated with the key.
     */
    @Override
    public String lookup(final String key) {
        if (key == null) {
            return null;
        }
        final String[] keys = key.split(":");
        final int keyLen = keys.length;
        if (keyLen != 2) {
            throw IllegalArgumentExceptions
                    .format("Bad Properties key format [%s]. Expected format is DocumentPath:Key.", key);
        }
        final String charsetName = keys[0];
        final String fileName = keys[1];
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)), charsetName);
        } catch (final Exception e) {
            throw IllegalArgumentExceptions.format(e, "Error looking up File [%s] with Charset [%s].", fileName,
                    charsetName);
        }
    }

}
