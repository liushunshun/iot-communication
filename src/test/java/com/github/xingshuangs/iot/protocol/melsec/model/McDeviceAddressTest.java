/*
 * MIT License
 *
 * Copyright (c) 2021-2099 Oscura (xingshuang) <xingshuang_cool@163.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.xingshuangs.iot.protocol.melsec.model;

import com.github.xingshuangs.iot.exceptions.McCommException;
import com.github.xingshuangs.iot.protocol.melsec.enums.EMcDeviceCode;
import org.junit.Test;

import static org.junit.Assert.*;


public class McDeviceAddressTest {

    @Test(expected = McCommException.class)
    public void createByNoLetter() {
        McDeviceAddress.createBy("133");
    }

    @Test(expected = McCommException.class)
    public void createByNoNumber() {
        McDeviceAddress.createBy("D");
    }

    @Test
    public void createBy1() {
        McDeviceAddress d133 = McDeviceAddress.createBy("D133");
        assertEquals(EMcDeviceCode.D, d133.getDeviceCode());
        assertEquals(133, d133.getHeadDeviceNumber());
        assertEquals(1, d133.getDevicePointsCount());
    }

    @Test
    public void createBy2() {
        byte[] data = new byte[]{0x01, 0x02};
        McDeviceContent d133 = McDeviceContent.createBy("D133", data);
        assertEquals(EMcDeviceCode.D, d133.getDeviceCode());
        assertEquals(133, d133.getHeadDeviceNumber());
        assertEquals(1, d133.getDevicePointsCount());
        assertArrayEquals(data, d133.getData());
    }
}