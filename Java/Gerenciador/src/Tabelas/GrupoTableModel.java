
package Tabelas;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class GrupoTableModel extends AbstractTableModel  {
    
    private ArrayList<Grupo> dados;
    private String[] colunas = {"Código", "Grupo", "Hora Início", "Hora Fim", "Data Início", "Data Fim"};
    
    public GrupoTableModel(){
        dados = new ArrayList();
    }
    
    public void addRow(Grupo g){
        this.dados.add(g);
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
            case 0: return dados.get(linha).getID_GRUPO();
            case 1: return dados.get(linha).getNM_GRUPO();
            case 2: return dados.get(linha).getHR_INICIO_ACESSO();
            case 3: return dados.get(linha).getHR_FIM_ACESSO();
            case 4: return dados.get(linha).getDT_INICIO_ACESSO();
            case 5: return dados.get(linha).getDT_FIM_ACESSO();
        }   
        return null;
    }
      
}
