package com.nhnacademy.http.response;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public interface HttpResponse {

    PrintWriter getWriter() throws IOException;

    Charset getCharacterEncoding();

    void setCharacterEncoding(String charset);

    void setCharacterEncoding(Charset charset);
}
