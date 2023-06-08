package com.wty.flowengine.sdk;

import com.wty.flowengine.converter.ProcessConverter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ProcessLoader {
    public static final String SUFFIX = ".json";
    private final URL root;

    public ProcessLoader(URL root) {
        this.root = root;
    }

    /**
     * @param name 流程文件名称，如"calc"对应"calc.json"
     * @return 解析后的 {@link Process}
     */
    public Process loadByName(String name) {
        InputStream inputStream;
        try {
            inputStream = new URL(root, name + SUFFIX).openStream();
        } catch (IOException e) {
            throw new ProcessLoadException("Load by name failed", e);
        }
        return ProcessConverter.convertToProcess(inputStream);
    }

    public static class ProcessLoadException extends RuntimeException {
        public ProcessLoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
