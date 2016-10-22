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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

class EbusdTask implements Callable<String> {

    private String command;
    private InputStream in;
    private PrintStream out;

    EbusdTask(String command, InputStream in, PrintStream out) {
        this.command = command;
        this.in = in;
        this.out = out;
    }

    /**
     * Sends to and receives from ebusd.
     * Timeout of 3 seconds is defined in code in case ebusd doesn't respond.
     * Response from ebusd must end with "\n\n".
     *
     * @return response from ebusd
     * @throws EbusException
     */
    @Override
    public String call() throws EbusException, UnsupportedEncodingException {

        out.println(command);
        out.flush();

        StringBuilder sb = new StringBuilder();
        String end = "\n\n"; // every response ends with two newlines
        try {
            while (true) {
                sb.append((char) in.read());
                if (sb.toString().endsWith(end)) break;
            }
        } catch (IOException e) {
            throw new EbusException("error when reading response from ebusd", e);
        }

        // remove new lines at the end
        String response = sb.toString().replace(end, "");

        // fix encoding (telnet delivers latin1, java need UTF8)
        response = new String(response.getBytes("latin1"), "UTF8");

        if (response.startsWith("ERR:") && response.length() < 100)
            throw new EbusException(response);

        return response;

    }

}
