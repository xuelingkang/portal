package com.xzixi.self.portal.framework.swagger2.extension.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 获取response返回值
 *
 * @author 薛凌康
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream buffer;

    private final ServletOutputStream out;

    public ResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        buffer = new ByteArrayOutputStream();
        out = new WrapperOutputStream(buffer);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return out;
    }

    @Override
    public void flushBuffer()
            throws IOException {
        if (out!=null) {
            out.flush();
        }
    }

    public byte[] getContent()
            throws IOException {
        flushBuffer();
        return buffer.toByteArray();
    }

    public static class WrapperOutputStream extends ServletOutputStream {

        private final ByteArrayOutputStream bos;

        public WrapperOutputStream(ByteArrayOutputStream bos) {
            this.bos = bos;
        }

        @Override
        public void write(int b) {
            bos.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }
}
