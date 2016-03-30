package com.mricefox;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Author:zengzifeng email:zeng163mail@163.com
 * Description:
 * Date:2016/3/30
 */
public class Client {
    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            Socket socket = new Socket(inetAddress, 8088);
            OutputStream os = socket.getOutputStream();
            os.write(new String("aaa").getBytes());
            os.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        BufferedOutputStream bos = new BufferedOutputStream(baos);
//
//        try {
//            bos.write(new String("aaa").getBytes());
//
////            bos.flush();
//            baos.close();
//
//            System.out.println("result:" + baos.toString());
//
//            baos.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        byte[] bytes = compress("dfgdfgge4f@#$BBRdf1848{}>1784818df1gb8".getBytes());
//
//        String s = null;
//        try {
//            s = new String(bytes, "ISO-8859-1");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println("result:" + s);
//
//        try {
//            byte[] result = decompress(s.getBytes("ISO-8859-1"));
//            System.out.println("result:" + new String(result));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }


    private static String compress(String source) {
        if (source != null && source.length() > 0) {
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                GZIPOutputStream gzipos = new GZIPOutputStream(baos);
                gzipos.write(source.getBytes());
                gzipos.close();
                return baos.toString("ISO-8859-1");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeSilently(baos);
            }
        }
        return null;
    }

    private static byte[] compress(byte[] source) {
        if (source != null && source.length > 0) {
            BufferedInputStream bis = null;
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                GZIPOutputStream gzipos = new GZIPOutputStream(new BufferedOutputStream(baos));
                bis = new BufferedInputStream(new ByteArrayInputStream(source));
                byte[] buf = new byte[512];
                int offset = -1;
                while ((offset = bis.read(buf)) != -1) {
                    gzipos.write(buf, 0, offset);
                }
                gzipos.close();
                return baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeSilently(bis);
                closeSilently(baos);
            }
        }
        return null;
    }

    /**
     * @param source
     * @return
     * @throws IOException
     */
    public static byte[] decompress(byte[] source) {
        if (source == null || source.length == 0) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        GZIPInputStream gzipis = null;
        try {
            baos = new ByteArrayOutputStream();
            gzipis = new GZIPInputStream(new ByteArrayInputStream(source));
            byte[] buffer = new byte[512];
            int offset = -1;
            while ((offset = gzipis.read(buffer)) != -1) {
                baos.write(buffer, 0, offset);
            }
            return baos.toByteArray();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilently(gzipis);
            closeSilently(baos);
        }
        return null;
    }


    private static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}
