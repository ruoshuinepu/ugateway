package com.jxx.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public ServerSocketChannel serverSocketChannel;
    public Selector selector;

    public static void main(String[] args) {

    }
    public class  TimerServer implements  Runnable{

         public TimerServer(int port) throws IOException {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
         }
        @Override
        public void run() {
            while (true){
                try {
                    selector.select(1000);//poll系统调用会把用户空间的线程挂起，也就是阻塞调用，timeout指定多长时间后必须返回。
                    Set <SelectionKey> set = selector.selectedKeys();
                    Iterator<SelectionKey>iterator = set.iterator();
                    SelectionKey selectionKey = null;
                    while (iterator.hasNext()){
                        selectionKey =  iterator.next();
                        iterator.remove();
                        if(selectionKey.isValid()){//判断selectionkey是否可用
                            if(selectionKey.isAcceptable()){//
                                ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                                SocketChannel sc=ssc.accept();
                                sc.configureBlocking(false);
                                sc.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                            }
                            if(selectionKey.isReadable()){
                                SocketChannel sc = (SocketChannel) selectionKey.channel();
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                int readBytes = sc.read(buffer);
                                if(readBytes>0){
                                    buffer.flip();//buffer变读为写
                                    byte[] bytes = new byte[buffer.remaining()];
                                    buffer.get(bytes);
                                    String body  = new String(bytes,"UTF-8");
                                    System.out.println(body);
                                    String response = new Date(System.currentTimeMillis()).toString();
                                    dowrite(sc,response);
                                }
                            }
                            if(selectionKey.isWritable()){

                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void dowrite(SocketChannel sc, String response) {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put(response.getBytes());
                buffer.flip();
                sc.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
