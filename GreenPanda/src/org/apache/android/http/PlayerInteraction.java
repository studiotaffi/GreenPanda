package org.apache.android.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;



public class PlayerInteraction implements Runnable {
	//public static Logger log = Logger.getLogger(PlayerInteraction.class);

	private String remoteUrl="http://cloud.taffi.it/vink/xmlrpc.php";
	private XmlRpcClientConfigImpl config;
	private Handler msgHandler;


	private static PlayerInteraction INSTANCE;

	private PlayerInteraction(){ }

	public static PlayerInteraction getInstance(Handler mHandler){
		if (INSTANCE == null) {
			INSTANCE = new PlayerInteraction();
		}
		INSTANCE.msgHandler = mHandler;
		return INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while (true) {
			try {
				this.constructConfig();
				Thread.sleep(1500);

				XmlRpcClient xmlreq=new XmlRpcClient();
				Log.v("PlayerInteraction.run","Configurazione server: " + this.config);
				xmlreq.setConfig(this.config);
				Object[] params = new Object[1];
				params[0] = Integer.valueOf(9);
				Object result = xmlreq.execute("player.tabletinteraction_receive", params);
				Log.v("PlayerInteraction.run", "result: " + result);
				HashMap<String, Object> mess = (HashMap<String, Object>) result;
				if ("NOP".equals(mess.get("command"))) {
					Log.v("PlayerInteraction.run", "No operations");
				} else if ("CHAT".equals(mess.get("command"))) {
					String txt = (String) mess.get("string");
					Message msg = msgHandler.obtainMessage();
					msg.obj = txt;
					msgHandler.sendMessage(msg);
				}
			} catch (Throwable e) {
				Log.e("PlayerInteraction.run", "Error getting messages", e);
			}

		}
	}



	private void constructConfig() throws MalformedURLException {
		this.config = new XmlRpcClientConfigImpl();
		Log.v("PlayerInteraction.constructConfig","Configuro url: " + this.remoteUrl);
		this.config.setServerURL(new URL(this.remoteUrl));
		this.config.setConnectionTimeout(1000);
	}

}
