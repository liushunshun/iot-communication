package com.github.xingshuangs.iot.protocol.s7.utils;

import com.github.xingshuangs.iot.protocol.s7.enums.EArea;
import com.github.xingshuangs.iot.protocol.s7.model.RequestItem;
import com.sun.org.apache.bcel.internal.generic.RET;
import org.junit.Test;

import static org.junit.Assert.*;


public class S7AddressUtilTest {

    @Test
    public void parseByte() {
        RequestItem requestItem = S7AddressUtil.parseByte("DB12.3.5", 3);
        assertEquals(3, requestItem.getCount());
        assertEquals(EArea.DATA_BLOCKS, requestItem.getArea());
        assertEquals(12, requestItem.getDbNumber());
        assertEquals(3, requestItem.getByteAddress());
        assertEquals(0, requestItem.getBitAddress());

        requestItem = S7AddressUtil.parseByte("DB11.24.6", 5);
        assertEquals(5, requestItem.getCount());
        assertEquals(EArea.DATA_BLOCKS, requestItem.getArea());
        assertEquals(11, requestItem.getDbNumber());
        assertEquals(24, requestItem.getByteAddress());
        assertEquals(0, requestItem.getBitAddress());
    }

    @Test
    public void parseBit() {
        RequestItem requestItem = S7AddressUtil.parseBit("DB12.3.5");
        assertEquals(1, requestItem.getCount());
        assertEquals(EArea.DATA_BLOCKS, requestItem.getArea());
        assertEquals(12, requestItem.getDbNumber());
        assertEquals(3, requestItem.getByteAddress());
        assertEquals(5, requestItem.getBitAddress());
    }
}