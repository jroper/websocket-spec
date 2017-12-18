/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package javax.websocket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.util.concurrent.Flow;

/**
 * This method level annotation can be used to make a Java method receive and produce web socket messages via reactive
 * streams {@link Flow.Subscriber} and {@link Flow.Publisher}.
 * <p/>
 * Developers have a number of different options as to how to use this annotation. The developer may return a
 * {@link Flow.Subscriber}, {@link Flow.Publisher} or {@link Flow.Processor} that the container should use to pass
 * incoming messages, consume outgoing messages, or both, respectively. The developer may also accept a
 * {@link Flow.Publisher}, {@link Flow.Subscriber} or {@link Flow.Processor} which then may be subscribed to, published
 * to or both to consume incoming messages, produce outgoing messages or both, respectively.
 * <p/>
 * Combinations of the above may be used, as long as they make sense. For example, a method may accept a
 * {@link Flow.Publisher} to consume incoming messages, and return a {@link Flow.Publisher} to produce outgoing
 * messages. However, a method may not accept a {@link Flow.Publisher} and return a {@link Flow.Subscriber}, since both
 * of these are used to consuming incoming messages, and the incoming messages can't be consumed by two streams.
 * <p/>
 * The <tt>OnStream</tt> annotation may also be combined with other methods for sending and receiving messages and
 * signals, as long as each direction is interacted with by only one mechanism. For example, it is illegal to return
 * a {@link Flow.Subscriber} in addition to defining an {@link OnMessage}, {@link OnError} or {@link OnClose} annotated
 * method, as the subscriber would already handle all these signals. Likewise it is illegal to return a
 * {@link Flow.Publisher}, as well as send via the {@link RemoteEndpoint.Async} or {@link RemoteEndpoint.Basic} classes,
 * as this would undermine the backpressure that the {@link Flow.Publisher} receives.
 * <p/>
 * <p>For a subscriber or publisher that receives or produces incoming messages, the message type received or consumed
 * must be one of the following:
 * <ol>
 * <li>if the method is handling text messages:
 * <ul>
 * <li> {@link String} to receive the whole message</li>
 * <li> Java boxed primitive to receive the whole message converted to that type</li>
 * <li> {@link Flow.Publisher<String>} to receive the whole message as a non blocking stream</li>
 * <li>any object parameter for which the endpoint has a text decoder ({@link Decoder.Text} or
 * {@link Decoder.AsyncTextStream}).</li>
 * </ul>
 * </li>
 * <li>if the method is handling binary messages:
 * <ul>
 * <li> byte[] or {@link java.nio.ByteBuffer} to receive the whole message</li>
 * <li> {@link Flow.Publisher<java.nio.ByteBuffer>} or {@link Flow.Publisher<byte[]>} to receive the whole message as
 *      a non blocking stream</li>
 * <li> any object parameter for which the endpoint has a binary decoder ({@link Decoder.Binary} or
 * {@link Decoder.AsyncBinaryStream}).</li>
 * </ul>
 * </li>
 * <li>if the method is handling pong messages:
 * <ul>
 * <li> {@link PongMessage} for handling pong messages</li>
 * </ul>
 * </li>
 * </ul>
 * </li>
 * </ol>
 * </p>
 *
 * <p>For a subscriber or publisher that receives or produces outgoing messages, the message type received or consumed
 * must be one of the following:
 * <ol>
 * <li>if the method is producing text messages:
 * <ul>
 * <li> {@link String} to receive the whole message</li>
 * <li> Java boxed primitive to receive the whole message converted to that type</li>
 * <li> {@link Flow.Publisher<String>} to send the whole message as a non blocking stream</li>
 * <li>any object parameter for which the endpoint has a text encoder ({@link Encoder.Text} or
 * {@link Encoder.AsyncTextStream}).</li>
 * </ul>
 * </li>
 * <li>if the method is handling binary messages:
 * <ul>
 * <li> byte[] or {@link java.nio.ByteBuffer} to receive the whole message</li>
 * <li> {@link Flow.Publisher<java.nio.ByteBuffer>} or {@link Flow.Publisher<byte[]>} to send the whole message as
 *      a non blocking stream</li>
 * <li> any object parameter for which the endpoint has a binary encoder ({@link Encoder.Binary} or
 * {@link Encoder.AsyncBinaryStream}).</li>
 * </ul>
 * </li>
 * <li>if the method is sending ping messages:
 * <ul>
 * <li> {@link PingMessage} for sending ping messages</li>
 * </ul>
 * </li>
 * </ul>
 * </li>
 * </ol>
 * </p>
 *
 * <p>The method may also accept the following parameters:</p>
 * <ol>
 * <li> Zero to n String or Java primitive parameters
 * annotated with the {@link javax.websocket.server.PathParam} annotation for server endpoints.</li>
 * <li> an optional {@link Session} parameter</li>
 * </ol>
 * <p/>
 * The parameters may be listed in any order.
 *
 * @author James Roper
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnStream {

    /**
     * Specifies the maximum size of message in bytes that the method
     * this annotates will be able to process, or -1 to indicate
     * that there is no maximum. The default is -1. This attribute only
     * applies when the annotation is used to process whole messages, not to
     * those methods that process messages in parts or use a stream or reader
     * parameter to handle the incoming message.
     * If the incoming whole message exceeds this limit, then the implementation
     * generates an error and closes the connection using the reason that
     * the message was too big.
     *
     * @return the maximum size in bytes.
     */
    public long maxMessageSize() default -1;
}
