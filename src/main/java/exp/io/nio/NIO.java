package exp.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.*;

//import sun.nio.ch.pollSelectorProvide;

/**
 * Created by dengwei on 2016/3/15.
 */
public class NIO {
    public static class Server {

        public static int PORT = 8000;

        Selector selector;

        ServerSocketChannel serverChannel;

        int port = PORT;

        int readSize = 100;

        int writeSize = 100;

        BlockingQueue<Runnable> readQueue = new ArrayBlockingQueue<Runnable>(readSize);

        BlockingQueue<Runnable> writeQueue = new ArrayBlockingQueue<Runnable>(writeSize);

        ExecutorService reader = new ThreadPoolExecutor(1, 50, 1000, TimeUnit.MINUTES,
                readQueue);

        ExecutorService writer = new ThreadPoolExecutor(1, 50, 1000, TimeUnit.MINUTES,
                writeQueue);

        public void init() {
            try {
                selector = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                serverChannel = ServerSocketChannel.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 设置通道为非阻塞
            try {
                serverChannel.configureBlocking(false);
                serverChannel.socket().bind(new InetSocketAddress(port));
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void listen() {

            while(serverChannel.isOpen()) {

                try {
                    int num = selector.select();
                    System.out.println("select " + num);

                    if(num < 1) {
                        continue;
                    }
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                    while(iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if(key.isAcceptable()) {

                            ServerSocketChannel channel = (ServerSocketChannel) key.channel();

                            SocketChannel socket = channel.accept();

                            socket.configureBlocking(false);

                            socket.register(selector, SelectionKey.OP_READ);

                        } else if (key.isReadable()) {
                            if(readQueue.size() < readSize ) {
                                reader.submit(new Reader(key));
                            } else {
                                System.out.println("size over flow");
                            }
                        } else if(key.isWritable()) {
                            if(writeQueue.size() < writeSize) {
                                writer.submit(new Writer(key));
                            } else {
                                System.out.println("write over flow");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void read(SelectionKey key) throws IOException {
            // 服务器可读取消息:得到事件发生的Socket通道
            SocketChannel channel = (SocketChannel) key.channel();
            // 创建读取的缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            StringBuilder sb = new StringBuilder();

            int read = channel.read(buffer);
            if(read == 0) {
                return;
            }
            sb.append(new String(buffer.array()));
            while((read = channel.read(buffer)) > 0) {
                byte[] data = buffer.array();
                sb.append(new String(data));
            }
            System.out.println((channel).getRemoteAddress() + "read msg is " + sb.toString());

            key.attach(new Message(sb.toString()));
            channel.register(selector, SelectionKey.OP_WRITE);
        }

        public void write(SelectionKey key) throws IOException {
            Message attachment = (Message) key.attachment();
            SocketChannel channel = (SocketChannel) key.channel();

            int write = channel.write(ByteBuffer.wrap(attachment.body.getBytes()));

            System.out.println((channel).getRemoteAddress() + "write msg" + write);
        }

        public class Reader implements Runnable {

            SelectionKey key;
            Reader(SelectionKey key) {
                this.key = key;
            }
            public void run() {
                try {
                    read(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public class Writer implements Runnable {

            SelectionKey key;
            Writer(SelectionKey key) {
                this.key = key;
            }
            public void run() {
                try {
                    write(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Message {
        String body;

        public Message(String body) {
            this.body = body;
        }
    }

    public static class Client {

        SocketChannel socketChannel;

        Selector selector;

        public void init() {
            try {

                selector = Selector.open();
                socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress(Server.PORT));
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_WRITE);
                socketChannel.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void work() throws IOException {
            while (true) {
//                    int select = selector.select(1000);
//
//                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
//
//                    while (it.hasNext()) {
//                        SelectionKey key = it.next();
//                        it.remove();
//                        if (key.isReadable()) {
//                            SocketChannel channel = (SocketChannel) key.channel();
//                            ByteBuffer buffer = ByteBuffer.allocate(10);
//                            StringBuilder sb = new StringBuilder();
//
//                            int read;
//                            while ((read = channel.read(buffer)) > 0) {
//                                byte[] data = buffer.array();
//                                sb.append(data);
//                            }
//                            System.out.println("client=read msg is " + sb.toString());
//                        }
//                    }
                    byte[] bs = ("test " + new Random().nextInt()).getBytes();
                    System.out.println("-------------------" + new String(bs));

                    socketChannel.write(ByteBuffer.wrap(bs));

                    try {
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }



    public static void main(String[] args) throws IOException {
//        System.out.println(System.getProperty("java.nio.channels.spi.SelectorProvider"));
//        SelectorProvider provider = SelectorProvider.provider();
//        System.out.println(provider.getClass().getName());
//
//        AbstractSelector selector = provider.openSelector();
//        ServerSocketChannel socketChannel = provider.openServerSocketChannel();
//        System.out.println(selector.getClass().getName());
//        System.out.println(socketChannel.getClass().getName());

        new Thread(new Runnable() {
            public void run() {
                Server server = new Server();
                server.init();
                server.listen();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                Client client = new Client();
                client.init();
                try {
                    client.work();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
