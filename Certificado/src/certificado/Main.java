package certificado;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

public class Main {

    public static void main(String[] args) throws IOException, CertificateEncodingException, ClassNotFoundException {
        
        //gera um certificado com o nome Anhembi Morumbi
        X509Certificate cert = Certificado.geraCertificado("Anhembi Morumbi");
        
        //Extrai o certificado para área de trabalho no formato .cer
        Certificado.extraiCertificado(cert);
        
        //Códifica o certificado para base64 para ser salvo no banco de dados
        String StringCert64 = Certificado.certParaBase64(cert);
        System.out.println(StringCert64);
        
        //Usa string para gerar um certificado
        X509Certificate cert64 = Certificado.base64ParaCert(StringCert64);
        
        //Extrai o certificado gerado pela String
        Certificado.extraiCertificado(cert64, "CertificadoBase64");

    }

}
