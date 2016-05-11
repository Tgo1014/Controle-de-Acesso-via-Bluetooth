
package Tabelas;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class DispositivoTableModel extends AbstractTableModel {
    
    private ArrayList<Dispositivo> dados;
    private String[] colunas = {"MAC ADRESS", "Dispositivo"};
     
    public DispositivoTableModel(){
        dados = new ArrayList();
    }
     
    public void addRow(Dispositivo d){
        this.dados.add(d);
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
            case 0: return dados.get(linha).getMAC_ADRESS();
            case 1: return dados.get(linha).getNM_DISPOSITIVO();
        }   
        return null;
    }
    
}
