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

package net.filnko.ebusJ.client;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.*;

/**
 * Connects to ebusd via Telnet
 */

public class EbusdClient {

    private TelnetClient telnet = new TelnetClient();
    private InputStream in;
    private PrintStream out;

    /**
     * Constructor of EbusdClient
     *
     * @param server IP or domain name of ebusd
     * @param port Port of ebusd
     * @throws EbusException
     */

    public EbusdClient(String server, int port) throws EbusException {
        try {
            telnet.connect(server, port);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
        } catch (IOException e) {
            throw new EbusException("error when opening ebusd connection", e);
        }
    }

    /**
     * Sends a command to ebusd and receives the response
     *
     * @param command command to send to ebusd
     * @return response from ebusd
     * @throws EbusException
     */

    public String readValue(String command) throws EbusException {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> task = executor.submit(new EbusdTask(command, in, out));

        try {
            return task.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            task.cancel(true);
        } catch (InterruptedException | ExecutionException e) {
            if (e.getCause() instanceof EbusException)
                throw (EbusException)e.getCause();
            throw new EbusException("error in execution", e.getCause());
        }

        executor.shutdownNow();
        throw new EbusException("ebusd didn't respond in time");

    }

    /**
     * Disconnects from ebusd
     *
     * @throws EbusException
     */

    public void disconnect() throws EbusException {
        try {
            telnet.disconnect();
        } catch (IOException e) {
            throw new EbusException("error when closing ebusd telnet connection", e);
        }
    }

    /**
     * Closes the connection on finalize
     *
     * @throws EbusException
     */
    protected void finalize() throws EbusException {
        disconnect();
    }


}
