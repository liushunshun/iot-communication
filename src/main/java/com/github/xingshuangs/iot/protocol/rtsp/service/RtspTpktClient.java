package com.github.xingshuangs.iot.protocol.rtsp.service;


import com.github.xingshuangs.iot.protocol.rtcp.service.RtcpDataStatistics;
import com.github.xingshuangs.iot.protocol.rtp.model.RtpPackage;
import com.github.xingshuangs.iot.protocol.rtp.model.frame.RawFrame;
import com.github.xingshuangs.iot.protocol.rtp.service.IPayloadParser;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * 简单TCP示例
 *
 * @author xingshuang
 */
@Slf4j
public class RtspTpktClient {

    public static final Integer BUFFER_SIZE = 4096;

    /**
     * 是否终止线程
     */
    private boolean terminal = false;

    /**
     * 数据收发前自定义处理接口
     */
    private Consumer<byte[]> commCallback;

    /**
     * 帧处理回调事件
     */
    private Consumer<RawFrame> frameHandle;

    /**
     * 负载解析器
     */
    private IPayloadParser iPayloadParser;

    /**
     * RTP和RTCP的数据统计
     */
    private final RtcpDataStatistics statistics = new RtcpDataStatistics();

    private Integer rtpChannelNumber;

    private Integer rtcpChannelNumber;

    public void setCommCallback(Consumer<byte[]> commCallback) {
        this.commCallback = commCallback;
    }

    public void setFrameHandle(Consumer<RawFrame> frameHandle) {
        this.frameHandle = frameHandle;
    }

    public RtspTpktClient(IPayloadParser iPayloadParser) {
        this.iPayloadParser = iPayloadParser;
    }

    public void setChannelNumber(Integer rtpChannelNumber, Integer rtcpChannelNumber) {
        this.rtpChannelNumber = rtpChannelNumber;
        this.rtcpChannelNumber = rtcpChannelNumber;
    }

    public void close() {
        this.terminal = true;
//        super.close();
    }

    private void waitForReceiveData() {
//        log.debug("RTP开启接收数据线程，远程的IP[{}]，端口号[{}]", this.serverAddress.getAddress().getHostAddress(), this.serverAddress.getPort());
        while (!this.terminal) {
            try {
                byte[] data = this.getReceiveData();
                if (this.commCallback != null) {
                    this.commCallback.accept(data);
                }
                RtpPackage rtp = RtpPackage.fromBytes(data);
//                log.debug("数据长度[{}], 时间戳[{}], 序列号[{}]", rtp.byteArrayLength(), rtp.getHeader().getTimestamp(), rtp.getHeader().getSequenceNumber());
                this.statistics.processRtpPackage(rtp);
                this.iPayloadParser.processPackage(rtp, this::processFrame);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 获取接收的数据
     *
     * @return 字节数组
     */
    private byte[] getReceiveData() {
        byte[] buffer = new byte[BUFFER_SIZE];
//        int length = this.read(buffer);
//        if (length < BUFFER_SIZE) {
//            byte[] data = new byte[length];
//            System.arraycopy(buffer, 0, data, 0, length);
//            return data;
//        } else {
//            return buffer;
//        }
        return buffer;
    }

    /**
     * 处理帧数据
     *
     * @param frame 帧
     */
    private void processFrame(RawFrame frame) {
        if (this.frameHandle != null) {
            this.frameHandle.accept(frame);
        }
    }

    public void triggerReceive() {
        CompletableFuture.runAsync(this::waitForReceiveData);
    }
}
