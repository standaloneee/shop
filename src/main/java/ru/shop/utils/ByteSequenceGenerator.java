package ru.shop.utils;

import java.util.UUID;

public class ByteSequenceGenerator {

    public static byte[] UUIDtoByteArray(UUID ... uuids) {
        StringBuilder sb = new StringBuilder();
        for (var item : uuids) {
            sb.append(item.toString());
        }
        return sb.toString().getBytes();
    }

    public static byte[] StringToByteArray(String ... strings) {
        StringBuilder sb = new StringBuilder();
        for (var item : strings) {
            sb.append(item);
        }
        return sb.toString().getBytes();
    }

}
