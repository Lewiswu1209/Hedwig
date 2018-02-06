package org.hedwig.net.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Random;

public class ProxyPool extends ArrayList<Proxy> {

	private static final long serialVersionUID = 7819310686939327103L;

    public static Random random = new Random();
    
    public Proxy nextRandom(){
        int r=random.nextInt(this.size());
        return this.get(r);
    }
    
    public void addEmpty(){
        Proxy nullProxy=null;
        this.add(nullProxy);
    }

    public void add(String ip, int port) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        this.add(proxy);
    }

    public void add(String proxyStr) throws Exception {
        try {
            String[] infos = proxyStr.split(":");
            String ip = infos[0];
            int port = Integer.valueOf(infos[1]);

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            this.add(proxy);
        } catch (Exception ex) {
        }
    }

}
