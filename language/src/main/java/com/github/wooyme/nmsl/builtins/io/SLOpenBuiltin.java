package com.github.wooyme.nmsl.builtins.io;

import com.github.wooyme.nmsl.SLLanguage;
import com.github.wooyme.nmsl.builtins.SLBuiltinNode;
import com.github.wooyme.nmsl.runtime.SLContext;
import com.github.wooyme.nmsl.runtime.SLNull;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.AllocationReporter;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.DynamicObject;

import java.io.*;
import java.net.Socket;
import java.net.URL;

@NodeInfo(shortName = "open")
public abstract class SLOpenBuiltin extends SLBuiltinNode {

    @Specialization
    public Object open(String path, String op
            , @CachedContext(SLLanguage.class) SLContext context
            , @Cached("context.getAllocationReporter()") AllocationReporter reporter) {
        Object result = null;
        switch (op) {
            case "<":
                if (path.startsWith("file:"))
                    result = getFileReader(path);
                else if(path.startsWith("http:") || path.startsWith("https:"))
                    result = getReaderCommon(path);
                else if (path.startsWith("shell:"))
                    result = getShell(path);
                break;
            case ">":
                if (path.startsWith("file:")) {
                    result = getFileWriter(path, false, false);
                }
                break;
            case ">+":
                if (path.startsWith("file:")) {
                    result = getFileWriter(path, false, true);
                }
                break;
            case ">>":
                if (path.startsWith("file:")) {
                    result = getFileWriter(path, true, false);
                }
                break;
            case ">>+":
                if (path.startsWith("file:")) {
                    result = getFileWriter(path, true, true);
                }
                break;
            case "<>":
                if (path.startsWith("tcp:")) {
                    result = getBlockingTcpClient(path, context, reporter);
                }
                break;
        }
        if (result == null) {
            result = SLNull.SINGLETON;
        }
        return result;
    }
    @TruffleBoundary
    private Object getReaderCommon(String path) {
        try {
            return new BufferedReader(new InputStreamReader(new URL(path).openStream()));
        } catch (IOException e) {
            return SLNull.SINGLETON;
        }
    }

    private Object getFileReader(String path){
        String filename = path.substring(7);
        try {
            return new BufferedReader(new FileReader(filename));
        } catch (IOException e) {
            return SLNull.SINGLETON;
        }
    }

    @TruffleBoundary
    private Object getFileWriter(String path, boolean isAppend, boolean create) {
        String filename = path.substring(7);
        try {
            File file = new File(filename);
            if (create && !file.exists()) {
                if (!file.createNewFile()) {
                    return SLNull.SINGLETON;
                }
            }
            return new BufferedWriter(new FileWriter(file, isAppend));
        } catch (IOException e) {
            return SLNull.SINGLETON;
        }
    }

    private Object getShell(String path) {
        String cmd = path.substring(8);
        try {
            final Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            p.waitFor();
            return reader;
        } catch (IOException | InterruptedException e) {
            return SLNull.SINGLETON;
        }
    }

    private Object getBlockingTcpClient(String path, SLContext context, AllocationReporter reporter) {
        String address = path.substring(4, path.lastIndexOf(":"));
        int port = Integer.valueOf(path.substring(path.lastIndexOf(":") + 1));
        try {
            Socket socket = new Socket(address, port);
            DynamicObject obj = context.createObject(reporter);
            obj.define("in", new BufferedReader(new InputStreamReader(socket.getInputStream())));
            obj.define("out", new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            return obj;
        } catch (IOException e) {
            return SLNull.SINGLETON;
        }
    }
}
