package zookeeper;

import java.util.Arrays;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * A simple class that monitors the data and existence of a ZooKeeper
 * node. It uses asynchronous ZooKeeper APIs.
 */
public class DataMonitor implements Watcher, StatCallback {

	ZooKeeper zk;

	String znode;

	boolean dead;

	Executor executor;

	byte prevData[];

	public DataMonitor(ZooKeeper zk, String znode, Executor executor) {
		this.zk = zk;
		this.znode = znode;
		this.executor = executor;
		// 异步调用exists(),传入this设置回调方法（StatCallback）
		zk.exists(znode, true, this, null);
	}

	//解析znode事件event
	public void process(WatchedEvent event) {
		String path = event.getPath();
		if (event.getType() == Event.EventType.None) {
			// We are are being told that the state of the connection has changed
			switch (event.getState()) {
			case SyncConnected:
				// In this particular example we don't need to do anything
				// here - watches are automatically re-registered with 
				// server and any watches triggered while the client was 
				// disconnected will be delivered (in order of course)
				break;
			case Expired:
				// It's all over
				dead = true;
				executor.closing(KeeperException.Code.SessionExpired);
				break;
			}
		} else {
			if (path != null && path.equals(znode)) {
				// Something has changed on the node, let's find out
				zk.exists(znode, true, this, null);
			}
		}
	}

	//exists()返回之后的回调方法。
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		boolean exists;
		switch (rc) {
		case Code.Ok:
			exists = true;
			break;
		case Code.NoNode:
			exists = false;
			break;
		case Code.SessionExpired:
		case Code.NoAuth:
			dead = true;
			executor.closing(rc);
			return;
		default:
			// Retry errors
			zk.exists(znode, true, this, null);
			return;
		}

		byte b[] = null;
		if (exists) {
			try {
				b = zk.getData(znode, false, null);
			} catch (KeeperException e) {
				// We don't need to worry about recovering now. The watch
				// callbacks will kick off any exception handling
				e.printStackTrace();
			} catch (InterruptedException e) {
				return;
			}
		}
		if ((b == null && b != prevData) || (b != null && !Arrays.equals(prevData, b))) {
			executor.exists(b);
			prevData = b;
		}
	}
}