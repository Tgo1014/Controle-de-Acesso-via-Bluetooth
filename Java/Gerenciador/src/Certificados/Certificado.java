package Certificados;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

public class Certificado {

    public static X509Certificate geraCertificado(String nome) {
        try {
            CertAndKeyGen keyGen = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
            keyGen.generate(2048);

            //Gera um certificado auto-assinado com 1 ano de validade
            return keyGen.getSelfCertificate(new X500Name("CN=" + nome), (long) 365 * 24 * 3600);

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | IOException | CertificateException | SignatureException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void extraiCertificado(X509Certificate cert) throws IOException, CertificateEncodingException {
        File desktop = new File(System.getProperty("user.home") + File.separator + "Desktop\\Certificado.cer");
        byte[] buffer = cert.getEncoded();
        try (FileOutputStream os = new FileOutputStream(desktop)) {
            os.write(buffer);
        }
    }

    public static void extraiCertificado(X509Certificate cert, String nomeArquivo) throws IOException, CertificateEncodingException {
        File desktop = new File(System.getProperty("user.home") + File.separator + "Desktop\\" + nomeArquivo + ".cer");
        byte[] buffer = cert.getEncoded();
        try (FileOutputStream os = new FileOutputStream(desktop)) {
            os.write(buffer);
        }
    }

    public static String certParaBase64(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(o);
        }
        return Base64.getUrlEncoder().encodeToString(baos.toByteArray());
    }

    public static X509Certificate base64ParaCert(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getUrlDecoder().decode(s);
        X509Certificate o;
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            o = (X509Certificate) ois.readObject();
        }
        return o;
    }

}
