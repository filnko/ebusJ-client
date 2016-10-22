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

public class EbusFindResult {

    public String type;
    public String circuit;
    public String name;
    public String comment;
    public String datatype;

    public EbusFindResult(String type, String circuit, String name, String comment, String datatype) {
        this.type = type;
        this.circuit = circuit;
        this.name = name;
        this.comment = comment;
        this.datatype = datatype;
    }

    public EbusFindResult(String[] values) {
        this.type = values[0];
        this.circuit = values[1];
        this.name = values[2];
        this.comment = values[3];
        this.datatype = (10<= values.length) ? values[10] : null;
    }

    @Override
    public String toString() {
        return "EbusFindResult{" +
                "type='" + type + '\'' +
                ", circuit='" + circuit + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", datatype='" + datatype + '\'' +
                '}';
    }

}
