package com.dlc.dlcconversiontool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\6\21 0021.
 */

public class DataHandlingTool {

    /**
     * 对数据进行拆分
     *
     * @param head           头部数据
     * @param wordByteLength 文档上字节长度
     * @param originData     原始数据
     * @return
     */
    public List<byte[]> getFilterDatas(byte[] head, int wordByteLength, byte[] originData) {
        if (originData == null || originData.length < head.length + wordByteLength * 2) {
            return new ArrayList<>();
        }
        byte[] lengthBytes = new byte[wordByteLength * 2];
        for (int i = 0; i < wordByteLength * 2; i++) {
            lengthBytes[i] = originData[head.length + i];
        }
        int length = Integer.parseInt(new String(lengthBytes), 16);//内容的长度
        int dataLength = head.length + wordByteLength * 2 + length;//单条数据的总长度
        int packet = originData.length / dataLength;//共多少个包
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < packet; i++) {
            byte[] data = new byte[length];
            System.arraycopy(originData, head.length + wordByteLength * 2 + dataLength * i, data, 0, length);
            list.add(data);
        }
        return list;
    }
}
