package co.edu.icesi;

/**
 * Pair
 */
public class Service {

    private String ip;
    private int port;
    private int work;

    public Service(String ip, int port) {
        this.ip=ip;
        this.port=port;
        this.work=0;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Service){
            return false;
        }
        else{
            Service objService = (Service) obj;
            return this.ip.equals(objService.ip);
        }
    }

    @Override
    public String toString() {
        return ip+":"+port;
    }
    
    
}