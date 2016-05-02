package tcc.appbluetooth;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class Usuario {

    private X509Certificate certificado;

    public Usuario(X509Certificate certificado) {
        this.certificado = certificado;
    }

    public Usuario() {
        this.certificado = null;
    }

    public X509Certificate getCertificado() {
        return certificado;
    }

    public void setCertificado(X509Certificate certificado) {
        this.certificado = certificado;
    }

    public void leCertificado(File pathToFile, String passphrase) throws CertificateException, FileNotFoundException {
        try {
            KeyStore p12 = KeyStore.getInstance("pkcs12");
            p12.load(new FileInputStream(pathToFile), passphrase.toCharArray());
            Enumeration e = p12.aliases();
            String alias = (String) e.nextElement();
            this.setCertificado((X509Certificate) p12.getCertificate(alias));

            Log.d("certificado",this.certificado.getPublicKey().toString());

        } catch (Exception e) {}
    }

}
