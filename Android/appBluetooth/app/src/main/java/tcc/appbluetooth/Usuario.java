package tcc.appbluetooth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

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

    public void leCertificado(File pathToFile) throws CertificateException, FileNotFoundException {
        try {

            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            FileInputStream fis = new FileInputStream(pathToFile);
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(fis);

            this.setCertificado(cert);

        } catch (Exception e) {
        }
    }

}
