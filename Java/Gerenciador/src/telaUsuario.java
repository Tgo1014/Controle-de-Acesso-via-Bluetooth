import ClassesDAO.UsuarioDAO;
import Tabelas.Usuario;
import Tabelas.UsuarioTableModel;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import Certificados.Certificado;
import ClassesDAO.GrupoDAO;
import ClassesDAO.Usuario_GrupoDAO;
import Email.splashEmail;
import Tabelas.Grupo;
import Tabelas.GrupoTableModel;
import Tabelas.Usuario_Grupo;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;

public class telaUsuario extends javax.swing.JFrame {

    public telaUsuario() throws SQLException, Exception {

        try {

            initComponents();
            this.setLocationRelativeTo(null);
            pUsuarios.setVisible(false);
            sbCarregarGridUsuario();
            sbCarregarGridGrupo();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            throw e;
        }

    }

    public String sbGerarCertificado(String nome) throws IOException, CertificateEncodingException, ClassNotFoundException {

        try {

            //gera um certificado com o nome 
            X509Certificate cert = Certificado.geraCertificado("Anhembi Morumbi - " + nome);

            //Extrai o certificado para área de trabalho no formato .cer
            Certificado.extraiCertificado(cert);

            //Códifica o certificado para base64 para ser salvo no banco de dados
            String StringCert64 = Certificado.certParaBase64(cert);
            System.out.println(StringCert64);

            //Usa string para gerar um certificado
            //X509Certificate cert64 = Certificado.base64ParaCert(StringCert64);
            //Extrai o certificado gerado pela String
            //Certificado.extraiCertificado(cert64, "CertificadoBase64");
            return StringCert64;

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            throw e;
        }

    }

    public void sbCarregarGridUsuario() throws SQLException, ClassNotFoundException {

        try {

            UsuarioTableModel modelo = new UsuarioTableModel();
            UsuarioDAO user = new UsuarioDAO();

            ArrayList<Usuario> dados = user.buscarDados();

            for (Usuario u : dados) {
                modelo.addRow(u);
            }

            jTable1.setModel(modelo);
            jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            throw e;
        }

    }

    public void sbCarregarGridGrupo() throws SQLException, Exception {

        try {

            GrupoTableModel modelo = new GrupoTableModel();
            GrupoDAO user = new GrupoDAO();

            ArrayList<Grupo> dados = user.buscarDados();

            for (Grupo g : dados) {
                modelo.addRow(g);
            }

            jTable2.setModel(modelo);
            jTable2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            throw e;
        }

    }

    public boolean isGrupo() {
        try {
            return jTable2.getRowCount() > 0;
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            throw e;
        }
    }

    public boolean fnValida() {
        try {
            
            Boolean retorno = true;
            
            if (txtNome.getText().equals("")){
                JOptionPane.showMessageDialog(null, "O campo Nome não pode ficar em branco!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                retorno = false;
            }
            
            if (txtEmail.getText().equals("")){
                JOptionPane.showMessageDialog(null, "O campo E-mail não pode ficar em branco!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                retorno = false;
            }
            
            retorno = jTable2.getSelectedRows().length > 0;
            
            return retorno;
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pUsuarios = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        txtEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(640, 478));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Usuários");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        pUsuarios.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Cadastro de Usuários", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        pUsuarios.setToolTipText("");

        jLabel2.setText("Nome:");

        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 0));
        jLabel3.setText("Selecione no Mínimo um Grupo");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel4.setText("Email:");

        javax.swing.GroupLayout pUsuariosLayout = new javax.swing.GroupLayout(pUsuarios);
        pUsuarios.setLayout(pUsuariosLayout);
        pUsuariosLayout.setHorizontalGroup(
            pUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pUsuariosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pUsuariosLayout.createSequentialGroup()
                        .addGroup(pUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(pUsuariosLayout.createSequentialGroup()
                                .addGroup(pUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCadastrar)))
                        .addGap(24, 24, 24))
                    .addGroup(pUsuariosLayout.createSequentialGroup()
                        .addGroup(pUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pUsuariosLayout.setVerticalGroup(
            pUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pUsuariosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(8, 8, 8)
                .addGroup(pUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadastrar)
                    .addComponent(btnCancelar))
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(336, 336, 336)
                        .addComponent(btnAdicionar)
                        .addGap(6, 6, 6)
                        .addComponent(btnVoltar))
                    .addComponent(jScrollPane1)
                    .addComponent(pUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdicionar)
                            .addComponent(btnVoltar))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        setBounds(0, 0, 658, 614);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        try {
            this.dispose();
            new menuInicial().setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        try {
            if (isGrupo()) {
                pUsuarios.setVisible(true);
                txtNome.setText("");
                txtEmail.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "É necessário ter pelo menos um Grupo Cadastrado\nPara Cadastrar um Usuário", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new menuInicial().setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        pUsuarios.setVisible(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed

        try {

            if (fnValida()) {

                Usuario user = new Usuario();
                user.setNM_USUARIO(txtNome.getText());
                user.setCERTIFICADO(sbGerarCertificado(txtNome.getText()));
                user.setEMAIL(txtEmail.getText());

                UsuarioDAO u = new UsuarioDAO();
                int ID_USUARIO = u.inserirDadosRetID(user);

                for (int i = 0; i < jTable2.getSelectedRows().length; i++) {

                    int linhas[] = jTable2.getSelectedRows();
                    int ID_GRUPO = Integer.parseInt(jTable2.getValueAt(linhas[i], 0).toString());

                    Usuario_Grupo ug = new Usuario_Grupo();
                    ug.setID_GRUPO(ID_GRUPO);
                    ug.setID_USUARIO(ID_USUARIO);

                    Usuario_GrupoDAO ugDAO = new Usuario_GrupoDAO();
                    ugDAO.inserirDados(ug);

                }

                if (!txtEmail.getText().equals("")){
                    Email.Email.enviaEmail(txtEmail.getText(), new File(System.getProperty("user.home") + File.separator + "Desktop\\" + txtNome.getText() + ".cer"), txtNome.getText());
                } else {
                    sbGerarCertificado(txtNome.getText());
                }

                JOptionPane.showMessageDialog(null, "Usuário inserido com Sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);

                pUsuarios.setVisible(false);
                sbCarregarGridUsuario();
                sbCarregarGridGrupo();

            } else {
                JOptionPane.showMessageDialog(null, "É Necessário selecionar no mínimo um grupo", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            Logger.getLogger(telaUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(telaUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        } catch (CertificateEncodingException ex) {
            Logger.getLogger(telaUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(telaUsuario.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(telaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnCadastrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(telaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(telaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(telaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(telaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new telaUsuario().setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(telaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(telaUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel pUsuarios;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
