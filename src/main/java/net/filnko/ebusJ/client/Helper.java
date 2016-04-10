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

import java.util.ArrayList;
import java.util.List;

class Helper {

    /**
     * processes the results from ebusd's find operation
     *
     * @param input ebusd response
     * @return List of results
     */
    static List<EbusFindResult> processFinds(String input) {

        List<EbusFindResult> ebusFindResults = new ArrayList<>();
        String[] finds = input.split(System.getProperty("line.separator"));

        for (String find : finds) {
            String[] values = find.split(",");
            ebusFindResults.add(new EbusFindResult(values[0], values[1], values[2], values[3]));
        }

        return ebusFindResults;

    }

}
