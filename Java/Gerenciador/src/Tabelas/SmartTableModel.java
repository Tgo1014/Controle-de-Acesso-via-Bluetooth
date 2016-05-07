
package Tabelas;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class SmartTableModel extends AbstractTableModel {

    private ArrayList<Smartphone> dados;
    private String[] colunas = {"IMEI", "ICCID", "Usuário"};
    
    public SmartTableModel(){
        dados = new ArrayList();
    }
    
    public void addRow(Smartphone s){
        this.dados.add(s);
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
            case 0: return dados.get(linha).getIMEI();
            case 1: return dados.get(linha).getICCID();
            case 2: return dados.get(linha).getNM_USUARIO();
        }   
        return null;
    }
    
}
