/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package javax.net.websocket;

/**
 * A general exception that occurs when trying to encode a custom object to a string or binary message.
 * @author dannycoward
 * @since DRAFT 002
 */
public class EncodeException extends Exception {
    private Object object;
        private static final long serialVersionUID = 006;

    /* Constructor with the object being encoded, and the reason why it failed to be.*/
    public EncodeException(Object object, String message) {
        super(message);
        this.object = object;
    }
    /* Constructor with the object being encoded, and the reason why it failed to be, and the cause.*/
    public EncodeException(Object object, String message, Throwable cause) {
        super(message, cause);
        this.object = object;
    }
    
    
    /* Object being encoded. */

    public Object getObject() { return this.object;}
}
