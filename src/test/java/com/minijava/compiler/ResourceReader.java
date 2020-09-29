package com.minijava.compiler;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ResourceReader {
    protected String getPath(String resourceName) {
        try {
            URL resource = this.getClass().getClassLoader().getResource(resourceName);
            return URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException exception) {
            return null;
        }
    }
}
