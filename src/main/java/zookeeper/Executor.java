package zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * It fetches the data associated with the znode and starts the executable. 
 * If the znode changes, the client refetches the contents and restarts the executable.
 * If the znode disappears, the client kills the executable.
 */
public class Executor implements Watcher {
	DataMonitor dataMonitor;
	Process child;

	public static void main(String[] args) throws Exception {
		String hostPort = "192.168.0.150:2181";
		String znode = "/mpbsit/demo";
		Executor executor = new Executor();

		//executor作为Watcher传入zk客户端，监听特定znode并处理其事件
		ZooKeeper zk = new ZooKeeper(hostPort, 5000, executor);

		//executor把事件的解析交给dataMonitor，dataMonitor解析后再委托executor处理（DataMonitorListener）
		executor.dataMonitor = new DataMonitor(zk, znode, executor);

		//挂起主线程，直到监听的znode已经被删除。
		executor.run();
	}

	public void process(WatchedEvent event) {
		dataMonitor.process(event);//交由dataMonitor解析事件
	}

	public void run() {
		try {
			synchronized (this) {
				while (!dataMonitor.dead) {
					wait();
				}
			}
		} catch (InterruptedException e) {
		}
	}

	public void closing(int rc) {
		synchronized (this) {
			notifyAll();
		}
	}

	public void exists(byte[] data) {
		if (data == null) {
			if (child != null) {
				System.out.println("Killing process");
				child.destroy();
				try {
					child.waitFor();
				} catch (InterruptedException e) {
				}
			}
			child = null;
		} else {
			if (child != null) {
				System.out.println("Stopping child");
				child.destroy();
				try {
					child.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				System.out.println(new String(data));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}