package com.wty.flowengine.sdk;

import org.junit.jupiter.api.Test;

import java.net.URL;

class ProcessLoaderTest {

    @Test
    public void testLoadFromFolder() {
        URL root = getClass().getClassLoader().getResource("processes/");
        ProcessLoader processLoader = new ProcessLoader(root);
        Process process = processLoader.loadByName("updateDoc");
        System.out.println(process);
    }

}