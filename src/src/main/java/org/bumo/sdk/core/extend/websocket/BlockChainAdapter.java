/*
Copyright Bumo Technologies Co., Ltd. 2017 All Rights Reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.bumo.sdk.core.extend.websocket;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.bumo.sdk.core.extend.protobuf.Overlay;
import org.bumo.sdk.core.extend.protobuf.Common.Ping;
import org.bumo.sdk.core.extend.protobuf.Common.Pong;
import org.bumo.sdk.core.extend.protobuf.Common.WsMessage;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;


public class BlockChainAdapter {
	private final String adapter_version_ = "bumo-1.0.0";
	private Map<Integer, BlockChainManager> blockchain_managers_ = new ConcurrentHashMap<Integer, BlockChainManager>();
	private Logger logger_ = LoggerFactory.getLogger(BlockChainAdapter.class);
	private BlockChainAdapterProc request_handler_[] = new BlockChainAdapterProc[256];
	private BlockChainAdapterProc response_handler_[] = new BlockChainAdapterProc[256];
	private final long connection_timeout_ = 60 * 1000;
	private final long check_interval = 15 * 1000;
	private final int OVERLAY_PING = 1;
	
	private class BlockChainManager implements Runnable {
		private Thread blockchain_manager_thhead;
		private BlockChain block_chain_;
		private LinkedBlockingQueue<WsMessage> send_queue_;
		private boolean is_exit = true;
		private boolean is_connected_ = false;
		private long sequence_ = 0;
		private long heartbeat_time_ = 0;
		private Integer index_ = -1;
		private Draft draft_;
		private URI uri_;
		public BlockChainManager(Integer index, String uri_address) {
			index_ = index;
			draft_ = new Draft_17();
			uri_ = URI.create(uri_address);
			send_queue_ = new LinkedBlockingQueue<WsMessage>();
			blockchain_manager_thhead = new Thread(this);
			blockchain_manager_thhead.start();
		}
		
		// start thread
		public void run() {
			if (!is_exit) {
				logger_.debug("already running");
				return;
			}
			is_exit = false;
			while (!is_exit) {
				try {
					block_chain_ = new BlockChain(draft_, uri_);
					Thread block_chain_thread = new Thread(block_chain_);
					block_chain_thread.setName("worker_thread_" + index_);
					block_chain_thread.start();
					block_chain_thread.join();
				} catch (Exception e) {
					logger_.debug("connect failed, " + e.getMessage());
				}
				
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					logger_.error("sleep 5000 failed, " + e.getMessage());
				}
			}
		}
		
		public boolean IsConnected() {
			return is_connected_;
		}
		
		public void Stop() {
			try {
				is_exit = true;
				block_chain_.close();
			} catch (Exception e) {
				logger_.error("join failed, " + e.getMessage());
			}
		}
		public void Join() {
			try {
				blockchain_manager_thhead.join();
			} catch (InterruptedException e) {
				logger_.error("BlockChainManager join error, " + e.getMessage());
			}
		}
		
		private class BlockChain extends WebSocketClient {
			
			private SendMessageThread send_message_thread_= new SendMessageThread("Send Thread");
			private HeartbeatThread heartbeat_thread_ = new HeartbeatThread("Heartbeat");
			public BlockChain(Draft d, URI uri) {
				super(uri, d);
				WebSocketImpl.DEBUG = false;
				AddChainMethod(OVERLAY_PING, new BlockChainAdapterProc() {
					public void ChainMethod (byte[] msg, int length) {
						OnRequestPing(msg, length);
					}
				});
				AddChainResponseMethod(OVERLAY_PING, new BlockChainAdapterProc() {
					public void ChainMethod (byte[] msg, int length) {
						OnResponsePing(msg, length);
					}
				});
			}
			
			@Override
			public void onError( Exception ex ) {
				logger_.error( "Error: " + ex.getMessage());
			}

			@Override
			public void onOpen( ServerHandshake handshake ) {
				is_connected_ = true;
				heartbeat_time_ = System.currentTimeMillis();
				logger_.debug("open successful");
			}

			@Override
			public void onClose( int code, String reason, boolean remote ) {
				is_connected_ = false;
				heartbeat_thread_.Stop();
				heartbeat_thread_.Join();
				send_message_thread_.Stop();
				send_message_thread_.Join();
				logger_.debug( "Closed: " + index_ + ", code:" + code + ", reason:" + reason );
			}
			
			@Override
			public void onMessage(String message) {
				try {
					WsMessage wsMessage = WsMessage.parseFrom(ByteString.copyFrom(message.getBytes()));
					int type = (int)wsMessage.getType();
					boolean request = wsMessage.getRequest();
					if (request && request_handler_[type] != null) {
						byte[] msg = wsMessage.getData().toByteArray();
						request_handler_[type].ChainMethod(msg, msg.length);
					}
					else if (!request && response_handler_[type] != null) {
						byte[] msg = wsMessage.getData().toByteArray();
						response_handler_[type].ChainMethod(msg, msg.length);
					}
					else {
						logger_.debug( "onMessage:" + (request ? " request method" : " reponse method") + " (" + type + ")" + " does not exist");
					}
				} catch (Exception e) {
					logger_.error("onMessage: the message of receive is not WsMessage format");
				}
			}
			
			@Override
			public void onMessage(ByteBuffer message) {
				try {
					WsMessage wsMessage = WsMessage.parseFrom(ByteString.copyFrom(message));
					int type = (int)wsMessage.getType();
					boolean request = wsMessage.getRequest();
					if (request && request_handler_[type] != null) {
						byte[] msg = wsMessage.getData().toByteArray();
						request_handler_[type].ChainMethod(msg, msg.length);
					}
					else if (!request && response_handler_[type] != null) {
						byte[] msg = wsMessage.getData().toByteArray();
						response_handler_[type].ChainMethod(msg, msg.length);
					}
					else {
						logger_.debug( "onMessage:" + (request ? " request method" : " reponse method") + " (" + type + ")" + " does not exist");
					}
				} catch (Exception e) {
					logger_.error("the message of receive is not WsMessage format");
				}
			}
			
			private void OnRequestPing(byte[] msg, int length) {
				WebSocket conn = getConnection();
				try {
					Ping ping = Ping.parseFrom(msg);
					
					Pong.Builder pong = Pong.newBuilder();
					pong.setNonce(ping.getNonce());
					
					SendMessage(blockchain_managers_.get(index_), Overlay.OVERLAY_MESSAGE_TYPE.OVERLAY_MSGTYPE_PING_VALUE, false, 
							sequence_, pong.build().toByteArray());
				} catch (InvalidProtocolBufferException e) {
					logger_.error("OnRequestPing: parse ping data failed" + " (" + conn.getRemoteSocketAddress().getHostName() 
							+ ":" + conn.getRemoteSocketAddress().getPort() + ")");
				}
			}
			
			private void OnResponsePing(byte[] msg, int length) {
				WebSocket conn = getConnection();
				try {
					Pong.parseFrom(msg);
					heartbeat_time_ = System.currentTimeMillis();
					logger_.debug("OnRequestPing: Recv pong from " + conn.getRemoteSocketAddress().getHostName() 
							+ ":" + conn.getRemoteSocketAddress().getPort());
				} catch (InvalidProtocolBufferException e) {
					logger_.error("OnResponsePing: parse pong data failed" + " (" + conn.getRemoteSocketAddress().getHostName() 
							+ ":" + conn.getRemoteSocketAddress().getPort() + ")");
				}
			}
			
			private class HeartbeatThread implements Runnable {
				private boolean heartbeat_enabled_ = true;
				private Thread heartbeat_message_thread_;
				
				HeartbeatThread(String thread_name) {
					heartbeat_enabled_ = true;
					heartbeat_message_thread_ = new Thread(this);
					heartbeat_message_thread_.setName(thread_name);
					heartbeat_message_thread_.start();
				}
				public void run() {
					while (heartbeat_enabled_) {
						try {
							Thread.sleep(check_interval);
						} catch (Exception ex) {
							logger_.debug("HeartbeatThread sleep failed, " + ex.getMessage());
						}
						if (is_connected_) {
							// send ping
							WebSocket conn = getConnection();
							
							Ping.Builder ping = Ping.newBuilder();
							ping.setNonce(System.currentTimeMillis() * 1000);
							SendMessage(blockchain_managers_.get(index_), Overlay.OVERLAY_MESSAGE_TYPE.OVERLAY_MSGTYPE_PING_VALUE, 
									true, sequence_++, ping.build().toByteArray());
							logger_.debug("OnRequestPing: Send ping to" + conn.getRemoteSocketAddress().getHostName() 
									+ ":" + conn.getRemoteSocketAddress().getPort());
							
							// check timeout
							long current_time = System.currentTimeMillis();
							if (current_time - heartbeat_time_ > connection_timeout_) {
								logger_.debug("connection time out"+ " (" + conn.getRemoteSocketAddress().getHostName() + 
										":" + conn.getRemoteSocketAddress().getPort() + ")");
								close();
							}
						}
					}
				}
				void Stop() {
					heartbeat_enabled_ = false;
				}
				void Join() {
					try {
						heartbeat_message_thread_.join();
					} catch (InterruptedException e) {
						logger_.error("HeartbeatThread join error, " + e.getMessage());
					}
				}
			}
			
			private class SendMessageThread implements Runnable {
				private boolean send_enabled_ = true;
				private Thread send_message_thread_;
				
				SendMessageThread(String thread_name) {
					send_enabled_ = true;
					send_message_thread_ = new Thread(this);
					send_message_thread_.setName(thread_name);
					send_message_thread_.start();
				}
				public void run() {
					while (send_enabled_) {
						WsMessage send_message = null;
						try {
							if (!is_connected_) {
								Thread.sleep(3000);
								continue;
							}
							
							send_message = send_queue_.take();
							if (send_message == null) {
								continue;
							}
							
							send(send_message.toByteArray());
						}
						catch(Exception e) {
							logger_.error("HeartbeatThread send failed, " + e.getMessage());
							send_queue_.add(send_message);
							try {
								Thread.sleep(3000);
							} catch (Exception ex) {
								logger_.error("HeartbeatThread sleep failed, " + ex.getMessage());
							}
						}
					}
				}
				
				void Stop() {
					send_enabled_ = false;
					try {
						WsMessage.Builder send_message = WsMessage.newBuilder();
						send_message.setData(ByteString.copyFrom("".getBytes()));
						send_message.setSequence(0);
						send_message.setRequest(true);
						send_message.setType(-1);
						send_queue_.put(send_message.build());
					} catch (InterruptedException e) {
						logger_.error("MessageThread Stop Error, " + e.getMessage());
					}
				}
				
				void Join() {
					try {
						send_message_thread_.join();
					} catch (InterruptedException e) {
						logger_.error("SendMessageThread join error, " + e.getMessage());
					}
				}
				
			}
		}
	}
	
	public BlockChainAdapter(String uri_address) {
		String[] uri_addresses = uri_address.split(";");
		for (Integer i = 0; i < uri_addresses.length; i++) {
			BlockChainManager blockchain_manager = new BlockChainManager(i, uri_addresses[i]);
			blockchain_managers_.put(i, blockchain_manager);
		}
	}
	
	// stop thread
	public void Stop() {
		for(int i = 0; i < blockchain_managers_.size(); i++) {
			blockchain_managers_.get(i).Stop();
			blockchain_managers_.get(i).Join();
		}
	}
	
	// start status
	public boolean IsConnected() {
		boolean is_connect = false;
		for(int i = 0; i < blockchain_managers_.size(); i++) {
			if (blockchain_managers_.get(i).IsConnected()) {
				is_connect = true;
				break;
			}
		}
		return is_connect;
	}
	
	// add callback function
	// add response method to BlockChainAdapter
	public void AddChainMethod(int type, BlockChainAdapterProc chainMethodProc) {
		request_handler_[type] = chainMethodProc;
	}
	// add request method to BlockChainAdapter
	public void AddChainResponseMethod(int type, BlockChainAdapterProc chainRequestMethodProc) {
		response_handler_[type] = chainRequestMethodProc; 
	}
	
	// send message
	public boolean Send(int type, byte[] msg) {
		boolean bret = false;
		do {
			try {
				if (!IsConnected()) {
					logger_.debug("disconnected");
					bret = false;
					break;
				}
				
				for(int i = 0; i < blockchain_managers_.size(); i++) {
					BlockChainManager blockchain_manager = blockchain_managers_.get(i);
					if (blockchain_manager.IsConnected()) {
						if (!SendMessage(blockchain_manager, type, true, blockchain_manager.sequence_, msg)) {
							logger_.debug("add send queue failed");
						}
					}
				}
				
				bret =  true;
			} catch(Exception e) {
				logger_.error("add message failed, " + e.getMessage());
			}
		} while (false);
		
		
		return bret;
	}
	
	// get adapter version
	// get message for BlockChainAdapter jar info
	public String GetVersion() {
		String adapter_version = "version: " + adapter_version_;
		return adapter_version;
	}
	
	private boolean SendMessage(BlockChainManager blockchain_manager, int type, boolean request, long sequence, byte[] data) {
		WsMessage.Builder wsMessage = WsMessage.newBuilder();
		wsMessage.setType(type);
		wsMessage.setRequest(request);
		wsMessage.setSequence(sequence);
		if (data != null) {
			wsMessage.setData(ByteString.copyFrom(data));	
		}
		
		return blockchain_manager.send_queue_.add(wsMessage.build());
	}
}
