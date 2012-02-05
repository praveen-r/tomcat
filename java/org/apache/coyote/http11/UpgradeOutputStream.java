/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.coyote.http11;

import java.io.IOException;
import java.io.OutputStream;


import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.ByteChunk.ByteOutputChannel;

public class UpgradeOutputStream extends OutputStream
        implements ByteOutputChannel{

    private AbstractOutputBuffer<?> ob = null;
    private ByteChunk bb = new ByteChunk(8192);

    public UpgradeOutputStream(AbstractOutputBuffer<?> ob) {
        this.ob = ob;
        bb.setByteOutputChannel(this);
    }


    @Override
    public void realWriteBytes(byte[] cbuf, int off, int len)
            throws IOException {
        ob.committed = true;
        ob.doWrite(bb, null);
    }

    @Override
    public void write(int b) throws IOException {
        bb.append((byte) b);
    }

    @Override
    public void flush() throws IOException {
        bb.flushBuffer();
        ob.flush();
    }
}