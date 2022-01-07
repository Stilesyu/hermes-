/*
 *
 *  * Copyright (c) 2011-2021, Stiles Yu(yuxiaochen886@gmail.com),Southern Tree(wutianhuan@qq.com)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */


package com.github.www.transportation.netty.handler;

import com.github.www.hermes.common.utils.jackson.JacksonUtils;
import com.github.www.transportation.protocol.AbstractRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author Stiles yu
 * @since 1.0
 */
public class JSONDecode extends ByteToMessageDecoder {


    public static String NAME = "convert bytes to abstractRequest";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        AbstractRequest request = JacksonUtils.deserialize(new String(bytes, CharsetUtil.UTF_8), AbstractRequest.class);
        out.add(request);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            //TODO record log
            ctx.channel().close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}