package jdk.object;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {

	//	private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

	public static byte[] serialize(Object obj) throws IOException { //封装序列化步骤

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);

		objectOutputStream.writeObject(obj);

		return byteStream.toByteArray();

	}

	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {

		Object result = null;

		ByteArrayInputStream byteStream = null;
		try {
			byteStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objectInputStream = null;
			try {
				objectInputStream = new ObjectInputStream(byteStream);
				try {
					result = objectInputStream.readObject();
				} catch (ClassNotFoundException ex) {
					throw new Exception("Failed to deserialize object type", ex);
				}
			} catch (Throwable ex) {
				throw new Exception("Failed to deserialize", ex);
			} finally {
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			}
		} catch (Exception e) {
			//			logger.error("Failed to deserialize", e);
			System.out.println("Failed to deserialize");
		} finally {
			if (byteStream != null) {
				try {
					byteStream.close();
				} catch (IOException e) {
					;
				}
			}
		}
		return result;
	}

	public static boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	/*public static byte[] serialize(Object object) {
	
		byte[] result = null;
	
		if (object == null) {
			return new byte[0];
		}
	
		ByteArrayOutputStream byteStream = null;
		try {
			byteStream = new ByteArrayOutputStream(128);
			ObjectOutputStream objectOutputStream = null;
			try {
				if (!(object instanceof Serializable)) {
					throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload " + "but received an object of type [" + object.getClass().getName() + "]");
				}
	
				objectOutputStream = new ObjectOutputStream(byteStream);
				objectOutputStream.writeObject(object);
				objectOutputStream.flush();
				result = byteStream.toByteArray();
			} catch (Throwable ex) {
				throw new Exception("Failed to serialize", ex);
			} finally {
				objectOutputStream.close();
			}
		} catch (Exception ex) {
			//			logger.error("Failed to serialize", ex);
			System.out.println("Failed to serialize");
		} finally {
			try {
				byteStream.close();
			} catch (IOException e) {
				;
			}
		}
		return result;
	}*/
}
