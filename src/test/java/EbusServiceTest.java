/*
 * ebusj - Java client for ebusd.
 * Copyright (C) 2016 Christoph Filnkößl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import net.filnko.ebusJ.client.EbusException;
import net.filnko.ebusJ.client.EbusFindResult;
import net.filnko.ebusJ.client.EbusService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class EbusServiceTest {

    private static EbusService ebusService;

    @Test
    public void info() throws EbusException {
        String info = ebusService.info();
        Assert.assertNotNull(info);
        Assert.assertTrue(info.contains("version"));
        Assert.assertTrue(info.contains("ebusd"));
    }

    @Test
    public void state() throws EbusException {
        String info = ebusService.state();
        Assert.assertNotNull(info);
        Assert.assertTrue(info.contains("signal"));
    }

    @Test
    public void read() throws EbusException {
        String info = ebusService.read("datetime");
        Assert.assertNotNull(info);
        Assert.assertTrue(info.length()>2);
    }

    @Test(expected = EbusException.class)
    public void readException() throws EbusException {
        ebusService.read("iosdfjncoajifjosjoj");
    }

    @Test
    public void find() throws EbusException {
        List<EbusFindResult> infos = ebusService.find();
        Assert.assertNotNull(infos);
        Assert.assertTrue(infos.size()>10);
    }

    /*
    @Test
    public void write() throws EbusException {
        ebusService.write("HwcTempDesired", "f43", "30");
    }

    @Test(expected = EbusException.class)
    public void writeException() throws EbusException {
        ebusService.write("iosdfjncoajifjosjoj", "iosdfjncoajifjosjoj", "iosdfjncoajifjosjoj");
    }
    */

    @BeforeClass
    public static void setUp() throws EbusException {
        ebusService = new EbusService();
    }

    @AfterClass
    public static void tearDown() throws EbusException {
        ebusService.disconnect();
    }

}
