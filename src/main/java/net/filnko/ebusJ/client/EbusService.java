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

import java.util.List;

public class EbusService {

    private EbusdClient ebusdClient;

    private static final String ebusdHost = "0.0.0.0";
    private static final int ebusdPort = 8888;

    /**
     * Creates the EbusService
     *
     * @throws EbusException
     */

    public EbusService() throws EbusException {
        ebusdClient = new EbusdClient(ebusdHost, ebusdPort);
    }

    /**
     * Sends a command to ebusd an returns the response as String
     *
     * @param command command for ebusd
     * @return ebusd response
     * @throws EbusException
     */

    private String sendCommand(String command) throws EbusException {
            return ebusdClient.readValue(command);
    }

    /**
     * Uses the ebusd info function
     * Reports information about the daemon, configuration and seen devices
     *
     * @return infos as multi line String
     * @throws EbusException
     */

    public String info() throws EbusException {
        return sendCommand("info");
    }

    /**
     * Uses the ebusd state function
     *
     * @return state from ebusd
     * @throws EbusException
     */

    public String state() throws EbusException {
        return sendCommand("state");
    }

    /**
     * Uses the ebus find function
     *
     * @return A list of results from ebusd
     * @throws EbusException
     */

    public List<EbusFindResult> find() throws EbusException {
        return Helper.processFinds(sendCommand("find -f"));
    }

    /**
     * Uses the ebus write function
     *
     * @param name the name of the message to send
     * @param circuit the circuit of the message to send
     * @param value a single field value
     * @throws EbusException
     */

    public void write(String name, String circuit, String value) throws EbusException {
        String result = sendCommand("write -c "+circuit+" "+name+" "+value);
        if (!result.equals("done")) throw new EbusException("couldn't write to ebus");
    }

    /**
     * Uses the ebus read function
     *
     * @param name the name of the message to read
     * @return result
     * @throws EbusException
     */

    public String read(String name) throws EbusException {
        return sendCommand("read -f "+name);
    }

    /**
     * Uses the ebus read function
     *
     * @param name the name of the message to read
     * @param circuit the circuit of the message to read
     * @return result
     * @throws EbusException
     */

    public String read(String name, String circuit) throws EbusException {
        return sendCommand("read -f -c "+circuit+" "+name);
    }

    /**
     * Closes the ebus connection
     *
     * @throws EbusException
     */

    public void disconnect() throws EbusException {
        ebusdClient.disconnect();

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
