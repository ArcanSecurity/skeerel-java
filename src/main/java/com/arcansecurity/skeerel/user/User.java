package com.arcansecurity.skeerel.user;

import com.arcansecurity.skeerel.user.address.BaseAddress;

import java.util.UUID;

/**
 * @author Florian Pradines
 */
public class User {

    private final UUID uid;

    private final String mail;

    private final BaseAddress shippingAddress;

    private final BaseAddress billingAddress;

    public User(UUID uid, String mail) {
        this(uid, mail, null, null);
    }

    public User(UUID uid, String mail, BaseAddress shippingAddress, BaseAddress billingAddress) {
        this.uid = uid;
        this.mail = mail;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
    }

    public UUID getUid() {
        return uid;
    }

    public String getMail() {
        return mail;
    }

    public BaseAddress getShippingAddress() {
        return shippingAddress;
    }

    public BaseAddress getBillingAddress() {
        return billingAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", mail='" + mail + '\'' +
                ", shippingAddress=" + shippingAddress +
                ", billingAddress=" + billingAddress +
                '}';
    }
}
