package exercise.ex01;

import ex01.pyrmont.HttpServer;
import ex02.pyrmont.Constants;

import javax.servlet.Servlet;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class TestBaidu {
    public static void main(String[] args) throws Exception{
        URL[] urls = new URL[1];
        //urls[0] = new URL("file:E:\\go\\workspace\\HowTomcatWorks\\webroot\\PrimitiveServlet");
        //URLStreamHandler streamHandler = null;
        File classPath = new File("E:\\go\\workspace\\HowTomcatWorks\\webroot");
        //String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
        System.out.println("file:"+classPath);
        urls[0] = new URL("file:"+classPath+"\\");
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        Class myClass = urlClassLoader.loadClass("PrimitiveServlet");
        Servlet servlet = (Servlet) myClass.newInstance();
        servlet.destroy();
    }
}
