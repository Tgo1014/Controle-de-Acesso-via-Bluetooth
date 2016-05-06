
package Tabelas;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class UsuarioTableModel extends AbstractTableModel {

    private ArrayList<Usuario> dados;
    private String[] colunas = {"Usuário"};
     
    public UsuarioTableModel(){
        dados = new ArrayList();
    }
     
    public void addRow(Usuario u){
        this.dados.add(u);
        this.fireTableDataChanged();
    }
 
    @Override
    public String getColumnName(int num){
        return this.colunas[num];
    }
 
    @Override
    public int getRowCount() {
        return dados.size();
    }
 
    @Override
    public int getColumnCount() {
        return colunas.length;
    }
 
    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(coluna){
            case 0: return dados.get(linha).getNM_USUARIO();
        }   
        return null;
    }
    
}
