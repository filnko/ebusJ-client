# ebusJ - Java client for [ebusd](https://github.com/john30/ebusd)

This project provides java classes to access the ebusd easily.
Not all functions are implemented. Feel free to submit a pull request.

For more information and usage instructions concerning ebusd visit john30's repo [here](https://github.com/john30/ebusd).

## Read
Read value from a configured message. The result will be retrieved directly from the eBUS.

Use with or without circuit:
```
String read(String name) throws EbusException;
```

```
String read(String name, String circuit) throws EbusException;
```

## Write
Write value in a configured message.

```
void write(String name, String circuit, String value) throws EbusException;
```

## Find
Find configured messages by name.

```
List<EbusFindResult> find() throws EbusException;
```

## State
Report the bus state (signal acquisition).

```
String state() throws EbusException;
```

## Information
Reports information about the daemon, the configuration, and seen devices.

```
String info() throws EbusException;
```
