package com.brugnot.security.core.user.impl;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Created by Antonin on 03/12/2016.
 */
public class KeystoreLoader {

    private InputStream keystoreFile;

    private KeyStore keyStore;

    //TODO : Add an Enum validation for invalid keystoreType refusal
    private String keystoreType;

    private String password;

    public KeystoreLoader(InputStream keystoreFile, String keystoreType, String password) {
        this.keystoreFile = keystoreFile;
        this.keystoreType = keystoreType;
        this.password = password;
    }

    @PostConstruct
    public void load(){

        try {
            this.keyStore = KeyStore.getInstance(this.keystoreType);
            this.keyStore.load(this.keystoreFile,this.password.toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }

    }

    public void setKeystoreFile(InputStream keystoreFile) {
        this.keystoreFile = keystoreFile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setKeystoreType(String keystoreType) {this.keystoreType = keystoreType;}

    public KeyStore getKeyStore() {
        return keyStore;
    }
}
